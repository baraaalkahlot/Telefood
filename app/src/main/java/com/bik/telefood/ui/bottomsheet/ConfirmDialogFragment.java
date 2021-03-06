package com.bik.telefood.ui.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.databinding.FragmentConfirmDialogBinding;
import com.bik.telefood.ui.ads.AdsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class ConfirmDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_ITEM_ID = "item_id";
    private FragmentConfirmDialogBinding binding;
    private int id;
    private int position;
    private AdsViewModel AdsViewModel;
    private OnDeleteItemConfirmListener onDeleteItemConfirmListener;

    public ConfirmDialogFragment(OnDeleteItemConfirmListener onDeleteItemConfirmListener, int position) {
        this.onDeleteItemConfirmListener = onDeleteItemConfirmListener;
        this.position = position;
    }

    public static ConfirmDialogFragment newInstance(int id, OnDeleteItemConfirmListener onDeleteItemConfirmListener, int position) {
        final ConfirmDialogFragment fragment = new ConfirmDialogFragment(onDeleteItemConfirmListener, position);
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentConfirmDialogBinding.inflate(inflater, container, false);
        AdsViewModel = new ViewModelProvider(this).get(AdsViewModel.class);
        id = getArguments().getInt(ARG_ITEM_ID, 0);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.btnCancelDialog.setOnClickListener(v -> dismiss());
        binding.btnConfimDialog.setOnClickListener(v -> {
            AdsViewModel.deleteService(id, getContext(), getActivity().getSupportFragmentManager()).observe(getViewLifecycleOwner(), mainResponse -> dismiss());
            onDeleteItemConfirmListener.onDelete(position);
        });
    }

    public interface OnDeleteItemConfirmListener {
        void onDelete(int position);
    }
}