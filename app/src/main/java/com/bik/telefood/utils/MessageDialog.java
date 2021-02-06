package com.bik.telefood.utils;

import android.content.Context;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MessageDialog {

    public MessageDialog(Context context, String title, String message) {

        new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(context.getString(android.R.string.ok), (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }
}
