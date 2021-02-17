package com.bik.telefood.CommonUtils;

import android.content.Context;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ConfirmDialog {
    public ConfirmDialog(Context context, String title, String message, OnPositiveButtonListener listener) {

        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(context.getString(android.R.string.ok), (dialogInterface, i) -> listener.onClick())
                .setCancelable(false)
                .show();
    }

    public interface OnPositiveButtonListener {
        void onClick();
    }
}
