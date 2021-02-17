package com.bik.telefood.ui.more.ads;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.databinding.ActivityMyAdsBinding;
import com.bik.telefood.ui.bottomsheet.AdsActionDialogFragment;
import com.bik.telefood.ui.common.adapter.CategoryAdapter;

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

        categoryAdapter = new CategoryAdapter();
        binding.rvCategory.setAdapter(categoryAdapter);

    }

    @Override
    public void onClick() {
        AdsActionDialogFragment.newInstance().show(getSupportFragmentManager(), "AdsActionDialogFragment");
        ;
    }
}