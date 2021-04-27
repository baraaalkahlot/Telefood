package com.bik.telefood.CommonUtils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.bik.telefood.model.network.ApiConstant;

public class SharedPreferencesHelper {

    public static boolean isLoggedIn(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE);
        return preferences.getBoolean(AppConstant.USER_STATUS, false);
    }

    public static String getToken(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE);
        return preferences.getString(ApiConstant.AUTHORIZATION, AppConstant.EMPTY);
    }

    public static String getUserType(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE);
        return preferences.getString(AppConstant.USER_TYPE, AppConstant.EMPTY);
    }

    public static String getUserId(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE);
        return preferences.getString(AppConstant.USER_ID, AppConstant.EMPTY);
    }

    public static String getUserName(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE);
        return preferences.getString(AppConstant.USER_NAME, AppConstant.EMPTY);
    }

    public static String getFcmToken(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE);
        return preferences.getString(AppConstant.FCM_TOKEN, "Empty");
    }

    public static boolean isVerified(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE);
        return preferences.getBoolean(AppConstant.USER_VERIFIED, false);
    }

    public static boolean isThereNotification(Application application) {
        SharedPreferences preferences = application.getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE);
        return preferences.getBoolean(AppConstant.IS_THERE_NOTIFICATION, false);
    }

    public static void clearAllData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
