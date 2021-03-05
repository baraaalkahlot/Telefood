package com.bik.telefood.ui.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bik.telefood.databinding.FragmentActionTypeDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

public class ActionTypeDialogFragment extends BottomSheetDialogFragment {

    private final OnActionSelectListener onActionSelectListener;
    private FragmentActionTypeDialogBinding binding;

    public ActionTypeDialogFragment(OnActionSelectListener onActionSelectListener) {
        this.onActionSelectListener = onActionSelectListener;
    }

    public static ActionTypeDialogFragment newInstance(OnActionSelectListener onActionSelectListener) {
        return new ActionTypeDialogFragment(onActionSelectListener);
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentActionTypeDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.tvAttachFile.setOnClickListener(v -> {
            dismiss();
            onActionSelectListener.onAttachFile();
        });

        binding.tvAttachPhotos.setOnClickListener(v -> {
            dismiss();
            onActionSelectListener.onAttachPhotos();
        });
    }

    public interface OnActionSelectListener {
        void onAttachFile();

        void onAttachPhotos();
    }
}