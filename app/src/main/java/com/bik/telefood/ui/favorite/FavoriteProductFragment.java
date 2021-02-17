package com.bik.telefood.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.databinding.FragmentFavoriteProductBinding;

public class FavoriteProductFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentFavoriteProductBinding binding;
    private FavoriteProductViewModel mViewModel;
    private FavoriteProductAdapter favoriteProductAdapter;

    public static FavoriteProductFragment newInstance(int index) {
        FavoriteProductFragment fragment = new FavoriteProductFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FavoriteProductViewModel.class);

        favoriteProductAdapter = new FavoriteProductAdapter();
        binding.rvProduct.setAdapter(favoriteProductAdapter);
        // TODO: Use the ViewModel
    }

}