package com.bik.telefood.ui.common.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.databinding.ActivityProvidersDetailsBinding;
import com.bik.telefood.ui.common.adapter.CategoryAdapter;
import com.bik.telefood.ui.common.adapter.ProductAdapter;
import com.bik.telefood.ui.common.viewmodel.CategoriesViewModel;
import com.bik.telefood.ui.common.viewmodel.ServicesViewModel;

public class ProvidersDetailsActivity extends AppCompatActivity implements CategoryAdapter.OnCategorySelectListener {

    private ActivityProvidersDetailsBinding binding;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private ServicesViewModel servicesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProvidersDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        servicesViewModel = new ViewModelProvider(this).get(ServicesViewModel.class);
        CategoriesViewModel categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        categoriesViewModel.getCategoriesListLiveData().observe(this, categoryModelList -> {
            if (categoryModelList.isEmpty()) {
                categoriesViewModel.updateCategoriesList(this, getSupportFragmentManager());
            } else {
                categoryAdapter = new CategoryAdapter(categoryModelList, this);
                binding.rvCategory.setAdapter(categoryAdapter);
            }
        });


        servicesViewModel.getServices(null, null, this, getSupportFragmentManager(), true).observe(this, servicesResponse -> {
            productAdapter = new ProductAdapter(servicesResponse.getServices().getData(), null, this);
            binding.rvProduct.setAdapter(productAdapter);
        });
    }

    @Override
    public void onSelect(int id) {

    }
}