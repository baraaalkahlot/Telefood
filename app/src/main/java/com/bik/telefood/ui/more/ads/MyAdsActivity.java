package com.bik.telefood.ui.more.ads;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityMyAdsBinding;
import com.bik.telefood.model.entity.general.services.ServicesItemModel;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.ads.AdsViewModel;
import com.bik.telefood.ui.bottomsheet.AdsActionDialogFragment;
import com.bik.telefood.ui.bottomsheet.ConfirmDialogFragment;
import com.bik.telefood.ui.bottomsheet.FilterDialogFragment;
import com.bik.telefood.ui.common.adapter.CategoryAdapter;
import com.bik.telefood.ui.common.ui.ProductDetailsActivity;
import com.bik.telefood.ui.common.viewmodel.CategoriesViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAdsActivity extends AppCompatActivity implements MyAdsAdapter.OnCardClickListener, CategoryAdapter.OnCategorySelectListener, ConfirmDialogFragment.OnDeleteItemConfirmListener, AdsActionDialogFragment.OnAdsClickListener, FilterDialogFragment.OnFilterChangeListener {

    private static final int ACTION_EDIT_ADS = 107;
    private ActivityMyAdsBinding binding;
    private MyAdsAdapter myAdsAdapter;
    private CategoryAdapter categoryAdapter;
    private AdsViewModel adsViewModel;
    private List<ServicesItemModel> services;
    private SkeletonScreen skeletonScreen;
    private HashMap<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAdsBinding.inflate(getLayoutInflater());
        adsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        setContentView(binding.getRoot());

        services = new ArrayList<>();
        params = new HashMap<>();

        myAdsAdapter = new MyAdsAdapter(this, services, this);
        binding.rvProduct.setAdapter(myAdsAdapter);
        skeletonScreen = Skeleton.bind(binding.rvProduct)
                .adapter(myAdsAdapter)
                .color(R.color.mercury)
                .duration(500)
                .load(R.layout.item_my_product)
                .show();

        binding.ibFilter.setOnClickListener(v -> FilterDialogFragment.newInstance(this).show(this.getSupportFragmentManager(), "FilterDialogFragment"));


        CategoriesViewModel categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        categoriesViewModel.getCategoriesListLiveData().observe(this, categoryModelList -> {
            if (categoryModelList.isEmpty()) {
                categoriesViewModel.updateCategoriesList(this, getSupportFragmentManager());
            } else {
                categoryAdapter = new CategoryAdapter(categoryModelList, this, this);
                binding.rvCategory.setAdapter(categoryAdapter);
            }
        });

        binding.etSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH && !textView.getText().toString().isEmpty()) {
                params.clear();
                params.put(ApiConstant.FILTER_NAME, textView.getText().toString().trim());
                loadMyAds(params);
                return true;
            }
            return false;
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    params.clear();
                    loadMyAds(params);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loadMyAds(params);
    }

    private void loadMyAds(HashMap<String, String> params) {
        hideEmptyStatus();
        services.clear();
        adsViewModel.getMyAds(params, this, getSupportFragmentManager()).observe(this, myServiceResponse -> {
            skeletonScreen.hide();
            if (myServiceResponse.getServices() == null || myServiceResponse.getServices().isEmpty()) {
                showEmptyStatus();
            }
            services.addAll(myServiceResponse.getServices());
            myAdsAdapter.notifyDataSetChanged();
        });
    }

    private void showEmptyStatus() {
        binding.rvProduct.setVisibility(View.GONE);
        binding.includeEmptyStatusProduct.constraintLayoutEmptyStatusProduct.setVisibility(View.VISIBLE);
    }

    private void hideEmptyStatus() {
        binding.rvProduct.setVisibility(View.VISIBLE);
        binding.includeEmptyStatusProduct.constraintLayoutEmptyStatusProduct.setVisibility(View.GONE);
    }

    @Override
    public void onClick(int id, int position) {
        AdsActionDialogFragment.newInstance(id, this, this, position).show(getSupportFragmentManager(), "AdsActionDialogFragment");
    }

    @Override
    public void onSelect(int id) {
        params.clear();
        params.put(ApiConstant.FILTER_CATEGORY, String.valueOf(id));
        loadMyAds(params);
    }

    @Override
    public void onDelete(int position) {
        services.remove(position);
        myAdsAdapter.notifyItemRemoved(position);
        myAdsAdapter.notifyItemRangeChanged(position, services.size());
        if (services.isEmpty()) showEmptyStatus();
    }

    @Override
    public void onEdit(int id) {
        Intent intent = new Intent(this, EditAdsActivity.class);
        intent.putExtra(AppConstant.PRODUCT_ID, id);
        startActivityForResult(intent, ACTION_EDIT_ADS);
    }

    @Override
    public void onDetailsPreview(int id) {
        Intent intent = new Intent(this, ProductDetailsActivity.class);
        intent.putExtra(AppConstant.PRODUCT_ID, id);
        intent.putExtra(AppConstant.PRODUCT_NO_FAVORITE, true);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_EDIT_ADS && resultCode == RESULT_OK) {
            skeletonScreen.show();
            loadMyAds(params);
        }
    }

    @Override
    public void onChanged(int governorateModelId, int cityModelId, int fromPrice, int toPrice) {
        params.clear();
        if (governorateModelId != 0)
            params.put(ApiConstant.FILTER_GOVERNORATE, String.valueOf(governorateModelId));

        if (cityModelId != 0)
            params.put(ApiConstant.FILTER_CITY, String.valueOf(cityModelId));

        if (fromPrice != 0)
            params.put(ApiConstant.FILTER_FROM_PRICE, String.valueOf(fromPrice));

        if (toPrice != 0)
            params.put(ApiConstant.FILTER_TO_PRICE, String.valueOf(toPrice));

        loadMyAds(params);
    }

    @Override
    public void onClearFilterClick() {
        params.clear();
        loadMyAds(params);
    }
}