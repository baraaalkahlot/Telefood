package com.bik.telefood.ui.more.ads;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.databinding.ActivityMyAdsBinding;
import com.bik.telefood.ui.bottomsheet.AdsActionDialogFragment;
import com.bik.telefood.ui.common.adapter.CategoryAdapter;
import com.bik.telefood.ui.common.viewmodel.CategoriesViewModel;

public class MyAdsActivity extends AppCompatActivity implements MyAdsAdapter.OnCardClickListener {

    private ActivityMyAdsBinding binding;
    private MyAdsAdapter myAdsAdapter;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyAdsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myAdsAdapter = new MyAdsAdapter(this);
        binding.rvProduct.setAdapter(myAdsAdapter);

        CategoriesViewModel categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        categoriesViewModel.getCategoriesListLiveData().observe(this, categoryModelList -> {
            if (categoryModelList.isEmpty()) {
                categoriesViewModel.updateCategoriesList(this, getSupportFragmentManager());
            } else {
                categoryAdapter = new CategoryAdapter(categoryModelList);
                binding.rvCategory.setAdapter(categoryAdapter);
            }
        });


    }

    @Override
    public void onClick() {
        AdsActionDialogFragment.newInstance().show(getSupportFragmentManager(), "AdsActionDialogFragment");
    }
}