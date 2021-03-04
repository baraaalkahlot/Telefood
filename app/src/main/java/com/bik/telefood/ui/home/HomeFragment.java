package com.bik.telefood.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentHomeBinding;
import com.bik.telefood.model.entity.Autherntication.vendors.FeaturedVendorsModel;
import com.bik.telefood.model.entity.general.services.ServicesItemModel;
import com.bik.telefood.ui.common.adapter.CategoryAdapter;
import com.bik.telefood.ui.common.adapter.ProductAdapter;
import com.bik.telefood.ui.common.ui.ProductDetailsActivity;
import com.bik.telefood.ui.common.ui.ProvidersDetailsActivity;
import com.bik.telefood.ui.common.viewmodel.CategoriesViewModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment implements ProductAdapter.OnProductClickListener, CategoryAdapter.OnCategorySelectListener, HomeSliderAdapter.OnHomeSliderClickListener, FeaturedServicesAdapter.OnProductClickListener {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private CategoryAdapter categoryAdapter;
    private FeaturedServicesAdapter featuredServicesAdapter;
    private List<ServicesItemModel> servicesItemModels;
    private HashMap<String, String> params;
    private SkeletonScreen productSkeleton;
    private SkeletonScreen vendorsSkeleton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        List<FeaturedVendorsModel> featuredVendorsModels = new ArrayList<>();
        HomeSliderAdapter homeSliderAdapter = new HomeSliderAdapter(this, featuredVendorsModels);
        binding.rvProviders.setAdapter(homeSliderAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.rvProviders);

        vendorsSkeleton = Skeleton.bind(binding.rvProviders)
                .adapter(homeSliderAdapter)
                .color(R.color.mercury)
                .duration(500)
                .load(R.layout.item_providers_card_skeleton)
                .show();
        homeViewModel.getFeaturedVendors(getContext(), getActivity().getSupportFragmentManager(), false).observe(getViewLifecycleOwner(), featuredVendorsResponse -> {
            vendorsSkeleton.hide();
            homeSliderAdapter.setFeaturedVendorsModels(featuredVendorsResponse.getVendors());
        });

        CategoriesViewModel categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        categoriesViewModel.getCategoriesListLiveData().observe(getViewLifecycleOwner(), categoryModelList -> {
            categoryAdapter = new CategoryAdapter(categoryModelList, this, getActivity());
            binding.rvCategory.setAdapter(categoryAdapter);
        });

        servicesItemModels = new ArrayList<>();
        params = new HashMap<>();
        featuredServicesAdapter = new FeaturedServicesAdapter(servicesItemModels, this, getContext());
        binding.rvProduct.setAdapter(featuredServicesAdapter);
        productSkeleton = Skeleton.bind(binding.rvProduct)
                .adapter(featuredServicesAdapter)
                .color(R.color.mercury)
                .duration(500)
                .load(R.layout.item_product)
                .show();

        loadFeaturedProductList(params);
        return binding.getRoot();
    }

    private void loadFeaturedProductList(HashMap<String, String> mParams) {
        productSkeleton.show();
        hideEmptyStatus();
        homeViewModel.getFeaturedServices(mParams, getContext(), getActivity().getSupportFragmentManager(), false).observe(getViewLifecycleOwner(), servicesResponse -> {
            List<ServicesItemModel> servicesListModel = servicesResponse.getServices();
            if (servicesListModel == null || servicesListModel.isEmpty()) {
                showEmptyStatus();
            } else {
                servicesItemModels.addAll(servicesListModel);
                featuredServicesAdapter.notifyDataSetChanged();
            }
            productSkeleton.hide();
        });
    }

    @Override
    public void OnProductClick(int id, Boolean favorite) {
        Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
        intent.putExtra(AppConstant.PRODUCT_ID, id);
        intent.putExtra(AppConstant.PRODUCT_IS_FAVORITE, favorite);
        startActivity(intent);
    }

    @Override
    public void onSelect(int id) {
    }

    @Override
    public void onHomeSliderClick(long id) {
        Intent intent = new Intent(getActivity(), ProvidersDetailsActivity.class);
        intent.putExtra(AppConstant.USER_ID, (int) id);
        startActivity(intent);
    }

    private void showEmptyStatus() {
        binding.rvProduct.setVisibility(View.GONE);
        binding.includeEmptyStatusProduct.constraintLayoutEmptyStatusProduct.setVisibility(View.VISIBLE);
    }

    private void hideEmptyStatus() {
        binding.rvProduct.setVisibility(View.VISIBLE);
        binding.includeEmptyStatusProduct.constraintLayoutEmptyStatusProduct.setVisibility(View.GONE);
    }

}