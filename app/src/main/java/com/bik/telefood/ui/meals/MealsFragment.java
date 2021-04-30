package com.bik.telefood.ui.meals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentMealsBinding;
import com.bik.telefood.model.entity.general.services.ServicesItemModel;
import com.bik.telefood.model.entity.general.services.ServicesListModel;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.bottomsheet.FilterDialogFragment;
import com.bik.telefood.ui.common.adapter.CategoryAdapter;
import com.bik.telefood.ui.common.adapter.ProductAdapter;
import com.bik.telefood.ui.common.ui.ProductDetailsActivity;
import com.bik.telefood.ui.common.viewmodel.CategoriesViewModel;
import com.bik.telefood.ui.common.viewmodel.ServicesViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MealsFragment extends Fragment implements FilterDialogFragment.OnFilterChangeListener, ProductAdapter.OnProductClickListener, CategoryAdapter.OnCategorySelectListener {

    private static final int ACTION_GO_TO_PRODUCT_DETAILS = 103;
    private FragmentMealsBinding binding;
    private MealsViewModel mealsViewModel;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private ServicesViewModel servicesViewModel;
    private List<ServicesItemModel> servicesItemModels;
    private HashMap<String, String> params;
    private SkeletonScreen productSkeleton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMealsBinding.inflate(inflater, container, false);
        mealsViewModel = new ViewModelProvider(this).get(MealsViewModel.class);
        servicesViewModel = new ViewModelProvider(this).get(ServicesViewModel.class);

        binding.ibFilter.setOnClickListener(v -> FilterDialogFragment.newInstance(this).show(getActivity().getSupportFragmentManager(), "FilterDialogFragment"));


        binding.etSearch.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH && !textView.getText().toString().isEmpty()) {
                servicesItemModels.clear();
                params.clear();
                productAdapter.resetPager();
                params.put(ApiConstant.FILTER_NAME, textView.getText().toString().trim());
                loadServiceList(1, params);
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
                    servicesItemModels.clear();
                    params.clear();
                    productAdapter.resetPager();
                    loadServiceList(1, params);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        CategoriesViewModel categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        categoriesViewModel.getCategoriesListLiveData().observe(getViewLifecycleOwner(), categoryModelList -> {
            categoryAdapter = new CategoryAdapter(categoryModelList, this, getActivity());
            binding.rvCategory.setAdapter(categoryAdapter);
        });


        servicesItemModels = new ArrayList<>();
        params = new HashMap<>();
        productAdapter = new ProductAdapter(servicesItemModels, this, getContext());
        binding.rvProduct.setAdapter(productAdapter);
        productSkeleton = Skeleton.bind(binding.rvProduct)
                .adapter(productAdapter)
                .color(R.color.alto)
                .duration(500)
                .load(R.layout.item_product)
                .show();


        productAdapter.setOnLoadingRequestListener(page -> loadServiceList(page, params));
        loadServiceList(1, params);
        return binding.getRoot();
    }

    private void loadServiceList(Integer page, HashMap<String, String> mParams) {
        hideEmptyStatus();
        servicesViewModel.getServices(page, mParams, getContext(), getActivity().getSupportFragmentManager(), false).observe(getViewLifecycleOwner(), servicesResponse -> {
            if (page == 1) {
                productSkeleton.hide();
            }
            ServicesListModel servicesListModel = servicesResponse.getServices();
            if (servicesListModel.getData() == null || servicesListModel.getData().isEmpty()) {
                showEmptyStatus();
            }
            if (servicesListModel.getLastPage() == servicesListModel.getCurrentPage()) {
                productAdapter.setLastPage(true);
            }
            servicesItemModels.addAll(servicesListModel.getData());
            productAdapter.notifyDataSetChanged();
            productAdapter.setLoading(false);
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
    public void OnProductClick(int id, Boolean favorite) {
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra(AppConstant.PRODUCT_ID, id);
        intent.putExtra(AppConstant.PRODUCT_IS_FAVORITE, favorite);
        startActivityForResult(intent, ACTION_GO_TO_PRODUCT_DETAILS);
    }

    @Override
    public void onChanged(int governorateModelId, int cityModelId, int fromPrice, int toPrice) {
        servicesItemModels.clear();
        params.clear();
        productAdapter.resetPager();
        if (governorateModelId != 0)
            params.put(ApiConstant.FILTER_GOVERNORATE, String.valueOf(governorateModelId));

        if (cityModelId != 0)
            params.put(ApiConstant.FILTER_CITY, String.valueOf(cityModelId));

        if (fromPrice != 0)
            params.put(ApiConstant.FILTER_FROM_PRICE, String.valueOf(fromPrice));

        if (toPrice != 0)
            params.put(ApiConstant.FILTER_TO_PRICE, String.valueOf(toPrice));

        loadServiceList(1, params);
    }

    @Override
    public void onClearFilterClick() {
        servicesItemModels.clear();
        params.clear();
        productAdapter.resetPager();
        loadServiceList(1, params);
    }

    @Override
    public void onSelect(int id) {
        servicesItemModels.clear();
        params.clear();
        productAdapter.resetPager();
        params.put(ApiConstant.FILTER_CATEGORY, String.valueOf(id));
        loadServiceList(1, params);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_GO_TO_PRODUCT_DETAILS && resultCode == Activity.RESULT_OK) {
            servicesItemModels.clear();
            params.clear();
            productAdapter.resetPager();
            loadServiceList(1, params);
        }
    }
}