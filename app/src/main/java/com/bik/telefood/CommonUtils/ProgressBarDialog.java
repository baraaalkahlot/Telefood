package com.bik.telefood.CommonUtils;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bik.telefood.databinding.DialogProgressBarBinding;

public class ProgressBarDialog extends DialogFragment {
    private DialogProgressBarBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogProgressBarBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getDialog() != null && getDialog().getWindow() != null) {
            Dialog dialog = getDialog();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.lottieProgressBar.cancelAnimation();
    }
}
