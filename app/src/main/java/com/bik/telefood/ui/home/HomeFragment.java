package com.bik.telefood.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.databinding.FragmentHomeBinding;
import com.bik.telefood.ui.bottomsheet.FilterDialogFragment;
import com.bik.telefood.ui.common.CategoryAdapter;
import com.bik.telefood.ui.common.ProductAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });

        binding.ibFilter.setOnClickListener(v -> FilterDialogFragment.newInstance().show(getActivity().getSupportFragmentManager(), "FilterDialogFragment"));

        categoryAdapter = new CategoryAdapter();
        binding.rvCategory.setAdapter(categoryAdapter);

        productAdapter = new ProductAdapter();
        binding.rvProduct.setAdapter(productAdapter);


        return binding.getRoot();
    }
}