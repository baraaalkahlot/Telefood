package com.bik.telefood.common;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    public static boolean isLoggedIn(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.USER_STATUS, Context.MODE_PRIVATE);
        return preferences.getBoolean(AppConstant.USER_STATUS, false);
    }
/*
    public static String getToken(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.AUTHORIZATION, Context.MODE_PRIVATE);
        return preferences.getString(AppConstant.AUTHORIZATION, "Empty");
    }*/

    public static String getUserType(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.USER_TYPE, Context.MODE_PRIVATE);
        return preferences.getString(AppConstant.USER_TYPE, "Empty");
    }

    public static String getUserId(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.USER_ID, Context.MODE_PRIVATE);
        return preferences.getString(AppConstant.USER_ID, "Empty");
    }

    public static String getUserName(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.USER_NAME, Context.MODE_PRIVATE);
        return preferences.getString(AppConstant.USER_NAME, "Empty");
    }
/*
    public static String getFcmToken(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.FCM_TOKEN, Context.MODE_PRIVATE);
        return preferences.getString(AppConstant.FCM_TOKEN, "Empty");
    }*/

    public static boolean isVerified(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.USER_VERIFIED, Context.MODE_PRIVATE);
        return preferences.getBoolean(AppConstant.USER_VERIFIED, false);
    }

}
