package com.bik.telefood.ui.meals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.databinding.FragmentMealsBinding;
import com.bik.telefood.ui.bottomsheet.FilterDialogFragment;
import com.bik.telefood.ui.common.adapter.CategoryAdapter;
import com.bik.telefood.ui.common.adapter.ProductAdapter;

public class MealsFragment extends Fragment {

    private FragmentMealsBinding binding;
    private MealsViewModel mealsViewModel;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMealsBinding.inflate(inflater, container, false);
        mealsViewModel = new ViewModelProvider(this).get(MealsViewModel.class);

        mealsViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });

        categoryAdapter = new CategoryAdapter();
        binding.rvCategory.setAdapter(categoryAdapter);

        productAdapter = new ProductAdapter();
        binding.rvProduct.setAdapter(productAdapter);

        binding.ibFilter.setOnClickListener(v -> FilterDialogFragment.newInstance().show(getActivity().getSupportFragmentManager(), "FilterDialogFragment"));
        return binding.getRoot();
    }
}