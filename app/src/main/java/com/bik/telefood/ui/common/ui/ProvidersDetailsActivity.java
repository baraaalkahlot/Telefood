package com.bik.telefood.ui.common.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.databinding.ActivityProvidersDetailsBinding;
import com.bik.telefood.ui.common.adapter.CategoryAdapter;
import com.bik.telefood.ui.common.adapter.ProductAdapter;

public class ProvidersDetailsActivity extends AppCompatActivity {

    private ActivityProvidersDetailsBinding binding;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProvidersDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoryAdapter = new CategoryAdapter();
        binding.rvCategory.setAdapter(categoryAdapter);


        productAdapter = new ProductAdapter();
        binding.rvProduct.setAdapter(productAdapter);
    }
}