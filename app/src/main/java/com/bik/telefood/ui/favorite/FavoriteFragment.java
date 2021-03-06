package com.bik.telefood.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bik.telefood.CommonUtils.LoginDialog;
import com.bik.telefood.CommonUtils.SharedPreferencesHelper;
import com.bik.telefood.R;
import com.bik.telefood.databinding.FragmentFavoriteBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(getLayoutInflater(), container, false);

        if (!SharedPreferencesHelper.isLoggedIn(getActivity().getApplication())) {
            new LoginDialog().show(getActivity().getSupportFragmentManager(), "LoginDialog");
        } else {
            binding.pager.setAdapter(new FavoriteAdapter(getActivity()));
            new TabLayoutMediator(binding.tabLayout, binding.pager,
                    (tab, position) -> {
                        if (position == 1)
                            tab.setText(R.string.title_product);
                        else tab.setText(R.string.title_provider);
                    }
            ).attach();
        }
        return binding.getRoot();
    }
}