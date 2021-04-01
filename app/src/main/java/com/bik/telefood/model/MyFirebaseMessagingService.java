package com.bik.telefood.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.bik.telefood.CommonUtils.AppConstant;
import com.google.firebase.messaging.FirebaseMessagingService;

@Keep
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        SharedPreferences.Editor preferences = getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE).edit();
        preferences.putString(AppConstant.FCM_TOKEN, s);
        preferences.apply();
        Log.d("wasd", "onNewToken: " + s);
    }
}
