package com.bik.telefood.ui.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bik.telefood.databinding.FragmentAdsActionDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class AdsActionDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_ITEM_COUNT = "item_count";
    private FragmentAdsActionDialogBinding binding;

    public static AdsActionDialogFragment newInstance() {
        final AdsActionDialogFragment fragment = new AdsActionDialogFragment();
        final Bundle args = new Bundle();
//        args.putInt(ARG_ITEM_COUNT, itemCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdsActionDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.tvDeletePromotion.setOnClickListener(v -> {
            dismiss();
            ConfirmDialogFragment.newInstance().show(getActivity().getSupportFragmentManager(), "ConfirmDialogFragment");
        });
    }
}