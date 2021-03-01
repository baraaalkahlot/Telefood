package com.bik.telefood.ui.more.ads;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityMyAdsBinding;
import com.bik.telefood.model.entity.general.services.ServicesItemModel;
import com.bik.telefood.ui.ads.AdsViewModel;
import com.bik.telefood.ui.bottomsheet.AdsActionDialogFragment;
import com.bik.telefood.ui.bottomsheet.ConfirmDialogFragment;
import com.bik.telefood.ui.common.adapter.CategoryAdapter;
import com.bik.telefood.ui.common.viewmodel.CategoriesViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;

public class MyAdsActivity extends AppCompatActivity implements MyAdsAdapter.OnCardClickListener, CategoryAdapter.OnCategorySelectListener, ConfirmDialogFragment.OnDeleteItemConfirmListener, AdsActionDialogFragment.OnEditAdsListener {

    private static final int ACTION_EDIT_ADS = 107;
    private ActivityMyAdsBinding binding;
    private MyAdsAdapter myAdsAdapter;
    private CategoryAdapter categoryAdapter;
    private AdsViewModel adsViewModel;
    private List<ServicesItemModel> services;
    private SkeletonScreen skeletonScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAdsBinding.inflate(getLayoutInflater());
        adsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        setContentView(binding.getRoot());

        services = new ArrayList<>();
        myAdsAdapter = new MyAdsAdapter(this, services, this);
        binding.rvProduct.setAdapter(myAdsAdapter);
        skeletonScreen = Skeleton.bind(binding.rvProduct)
                .adapter(myAdsAdapter)
                .color(R.color.mercury)
                .duration(500)
                .load(R.layout.item_my_product)
                .show();

        CategoriesViewModel categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        categoriesViewModel.getCategoriesListLiveData().observe(this, categoryModelList -> {
            if (categoryModelList.isEmpty()) {
                categoriesViewModel.updateCategoriesList(this, getSupportFragmentManager());
            } else {
                categoryAdapter = new CategoryAdapter(categoryModelList, this);
                binding.rvCategory.setAdapter(categoryAdapter);
            }
        });

        loadMyAds();
    }

    private void loadMyAds() {
        adsViewModel.getMyAds(this, getSupportFragmentManager()).observe(this, myServiceResponse -> {
            skeletonScreen.hide();
            services.addAll(myServiceResponse.getServices());
            myAdsAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onClick(int id) {
        AdsActionDialogFragment.newInstance(id, this, this).show(getSupportFragmentManager(), "AdsActionDialogFragment");
    }

    @Override
    public void onSelect(int id) {

    }

    @Override
    public void onDelete() {
        skeletonScreen.show();
        services.clear();
        loadMyAds();
    }

    @Override
    public void onEdit(int id) {
        Intent intent = new Intent(this, EditAdsActivity.class);
        intent.putExtra(AppConstant.PRODUCT_ID, id);
        startActivityForResult(intent, ACTION_EDIT_ADS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_EDIT_ADS && resultCode == RESULT_OK) {
            skeletonScreen.show();
            services.clear();
            loadMyAds();
        }
    }
}