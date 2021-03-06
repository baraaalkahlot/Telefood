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

    private static final String ARG_ITEM_ID = "item_id";
    private FragmentAdsActionDialogBinding binding;
    private int id;
    private int position;
    private ConfirmDialogFragment.OnDeleteItemConfirmListener onDeleteItemConfirmListener;
    private OnAdsClickListener onAdsClickListener;

    public AdsActionDialogFragment(int id, ConfirmDialogFragment.OnDeleteItemConfirmListener onDeleteItemConfirmListener, OnAdsClickListener onAdsClickListener, int position) {
        this.onDeleteItemConfirmListener = onDeleteItemConfirmListener;
        this.onAdsClickListener = onAdsClickListener;
        this.id = id;
        this.position = position;
    }

    public static AdsActionDialogFragment newInstance(int id, ConfirmDialogFragment.OnDeleteItemConfirmListener onDeleteItemConfirmListener, OnAdsClickListener onAdsClickListener, int position) {
        final AdsActionDialogFragment fragment = new AdsActionDialogFragment(id, onDeleteItemConfirmListener, onAdsClickListener, position);
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdsActionDialogBinding.inflate(inflater, container, false);
        id = getArguments().getInt(ARG_ITEM_ID, 0);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.tvDeletePromotion.setOnClickListener(v -> {
            dismiss();
            if (id != 0)
                ConfirmDialogFragment.newInstance(id, onDeleteItemConfirmListener, position).show(getActivity().getSupportFragmentManager(), "ConfirmDialogFragment");
        });

        binding.tvEditPromotion.setOnClickListener(v -> {
            dismiss();
            onAdsClickListener.onEdit(id);
        });

        binding.tvPromotionDetails.setOnClickListener(v -> {
            dismiss();
            onAdsClickListener.onDetailsPreview(id);
        });
    }

    public interface OnAdsClickListener {
        void onEdit(int id);

        void onDetailsPreview(int id);

        void onDelete(int position);
    }
}