package com.bik.telefood.ui.meals;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MealsFragment extends Fragment implements FilterDialogFragment.OnFilterChangeListener, ProductAdapter.OnProductClickListener, CategoryAdapter.OnCategorySelectListener {

    private FragmentMealsBinding binding;
    private MealsViewModel mealsViewModel;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private ServicesViewModel servicesViewModel;
    private List<ServicesItemModel> servicesItemModels;
    private HashMap<String, String> params;

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
                if (count == 0) {
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
            categoryAdapter = new CategoryAdapter(categoryModelList, this);
            binding.rvCategory.setAdapter(categoryAdapter);
        });

        servicesItemModels = new ArrayList<>();
        params = new HashMap<>();
        productAdapter = new ProductAdapter(servicesItemModels, this);
        binding.rvProduct.setAdapter(productAdapter);
        productAdapter.setOnLoadingRequestListener(page -> loadServiceList(page, params));
        loadServiceList(1, params);
        return binding.getRoot();
    }

    private void loadServiceList(Integer page, HashMap<String, String> mParams) {
        servicesViewModel.getServices(page, mParams, getContext(), getActivity().getSupportFragmentManager(), true).observe(getViewLifecycleOwner(), servicesResponse -> {
            ServicesListModel servicesListModel = servicesResponse.getServices();
            if (servicesListModel.getLastPage() == servicesListModel.getCurrentPage()) {
                productAdapter.setLastPage(true);
            }
            servicesItemModels.addAll(servicesListModel.getData());
            productAdapter.notifyDataSetChanged();
            productAdapter.setLoading(false);
        });
    }

    @Override
    public void OnProductClick(int id) {
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra(AppConstant.PRODUCT_ID, id);
        startActivity(intent);
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
}