package com.bik.telefood.ui.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bik.telefood.databinding.FragmentSuccessDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class SuccessDialogFragment extends BottomSheetDialogFragment {

    private FragmentSuccessDialogBinding binding;
    private static SuccessDialogFragment sInstance;
    private final String msg;

    public SuccessDialogFragment(String msg) {
        this.msg = msg;
    }

    public static SuccessDialogFragment newInstance(String msg) {
        if (sInstance == null)
            sInstance = new SuccessDialogFragment(msg);
        return sInstance;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSuccessDialogBinding.inflate(inflater, container, false);
        binding.tvAdsMsg.setText(msg);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.btnBack.setOnClickListener(v -> dismiss());
    }
}