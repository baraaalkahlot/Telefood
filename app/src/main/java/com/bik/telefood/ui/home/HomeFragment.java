package com.bik.telefood.ui.home;

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
import androidx.viewpager2.widget.ViewPager2;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.databinding.FragmentHomeBinding;
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

public class HomeFragment extends Fragment implements ViewPager2.PageTransformer, ProductAdapter.OnProductClickListener, FilterDialogFragment.OnFilterChangeListener, CategoryAdapter.OnCategorySelectListener {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    private HomeSlidePagerAdapter homeSlidePagerAdapter;
    private ServicesViewModel servicesViewModel;
    private List<ServicesItemModel> servicesItemModels;
    private HashMap<String, String> params;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
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

        homeSlidePagerAdapter = new HomeSlidePagerAdapter(getActivity());
        binding.pagerProviders.setAdapter(homeSlidePagerAdapter);
        binding.pagerProviders.setPageTransformer(this);

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
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        int pageHeight = page.getHeight();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(0f);

        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                page.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                page.setTranslationX(-horzMargin + vertMargin / 2);
            }

            // Scale the page down (between MIN_SCALE and 1)
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);

            // Fade the page relative to its size.
            page.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0f);
        }
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