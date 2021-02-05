package com.bik.telefood.ui.ads;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.databinding.FragmentAdsBinding;
import com.bik.telefood.ui.bottomsheet.SuccessDialogFragment;

public class AdsFragment extends Fragment {

    private FragmentAdsBinding binding;
    private AdsViewModel adsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAdsBinding.inflate(inflater, container, false);
        adsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        adsViewModel.getText().observe(getViewLifecycleOwner(), s -> {
        });

        binding.btnSend.setOnClickListener(v -> {
            SuccessDialogFragment.newInstance().show(getActivity().getSupportFragmentManager(), "SuccessDialogFragment");
        });

        return binding.getRoot();
    }
}