package com.bik.telefood.ui.common.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.databinding.ActivityProvidersDetailsBinding;
import com.bik.telefood.ui.common.adapter.CategoryAdapter;
import com.bik.telefood.ui.common.adapter.ProductAdapter;
import com.bik.telefood.ui.common.viewmodel.CategoriesViewModel;

public class ProvidersDetailsActivity extends AppCompatActivity {

    private ActivityProvidersDetailsBinding binding;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProvidersDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CategoriesViewModel categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        categoriesViewModel.getCategoriesListLiveData().observe(this, categoryModelList -> {
            if (categoryModelList.isEmpty()) {
                categoriesViewModel.updateCategoriesList(this, getSupportFragmentManager());
            } else {
                categoryAdapter = new CategoryAdapter(categoryModelList);
                binding.rvCategory.setAdapter(categoryAdapter);
            }
        });


        productAdapter = new ProductAdapter();
        binding.rvProduct.setAdapter(productAdapter);
    }
}