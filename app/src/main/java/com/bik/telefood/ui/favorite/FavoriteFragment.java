package com.bik.telefood.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentFavoriteBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private FavoriteViewModel mViewModel;

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(getLayoutInflater(), container, false);
        binding.pager.setAdapter(new FavoriteAdapter(getActivity()));
        new TabLayoutMediator(binding.tabLayout, binding.pager,
                (tab, position) -> {
                    if (position == 1)
                        tab.setText(R.string.title_product);
                    else tab.setText(R.string.title_provider);
                }
        ).attach();
        mViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        mViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);
        // TODO: Use the ViewModel
    }

}