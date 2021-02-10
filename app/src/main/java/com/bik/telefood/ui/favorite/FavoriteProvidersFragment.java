package com.bik.telefood.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.databinding.FragmentFavoriteProvidersBinding;

public class FavoriteProvidersFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentFavoriteProvidersBinding binding;
    private FavoriteProvidersViewModel mViewModel;

    public static FavoriteProvidersFragment newInstance(int index) {
        FavoriteProvidersFragment fragment = new FavoriteProvidersFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteProvidersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FavoriteProvidersViewModel.class);
        // TODO: Use the ViewModel
    }

}