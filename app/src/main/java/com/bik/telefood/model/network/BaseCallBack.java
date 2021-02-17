package com.bik.telefood.model.network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.MessageDialog;
import com.bik.telefood.CommonUtils.NoConnectionDialog;
import com.bik.telefood.CommonUtils.ProgressBarDialog;
import com.bik.telefood.R;
import com.bik.telefood.model.entity.MainResponse;
import com.bik.telefood.ui.auth.login.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallBack<T> implements Callback<T> {

    private static final String TAG = BaseCallBack.class.getName();
    private Context context;
    private ProgressBarDialog progressBarDialog;
    private FragmentManager fragmentManager;


    public BaseCallBack(Context context, FragmentManager fragmentManager, boolean showProgressDialog) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        if (showProgressDialog) {
            startProgress();
        }
    }

    @Override
    public void onResponse(@NotNull Call<T> call, Response<T> response) {
        endProgress();
        if (response.isSuccessful()) {
            MainResponse mainResponse = (MainResponse) response.body();
            if (mainResponse != null && mainResponse.isStatus()) {
                onFinishWithSuccess(response.body(), response);
            } else onFinishWithError(mainResponse.getMsg());
        } else {
            if (response.code() == 401) {
                onUnAuthorized();
                return;
            }
            Gson gson = new Gson();
            Type type = new TypeToken<MainResponse>() {
            }.getType();
            MainResponse errorResponse = gson.fromJson(response.errorBody().charStream(), type);
            onFinishWithError(errorResponse.getMsg());
        }
    }

    @Override
    public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
        endProgress();
        new NoConnectionDialog().show(fragmentManager, "NoConnectionDialog");
    }

    protected abstract void onFinishWithSuccess(T result, Response<T> response);

    private void startProgress() {
        progressBarDialog = new ProgressBarDialog();
        progressBarDialog.show(fragmentManager, "ProgressBarDialog");
    }

    private void endProgress() {
        if (progressBarDialog != null) {
            Log.d("wasd", "endProgress: ");
            if (progressBarDialog.isVisible()) {
                progressBarDialog.dismiss();
            }
        }
    }

    private void onUnAuthorized() {
        Toast.makeText(context, R.string.error_message_un_authorized, Toast.LENGTH_LONG).show();
        // Cache new status >> SIGN OUT
        SharedPreferences.Editor editor = context.getSharedPreferences(AppConstant.USER_STATUS, Context.MODE_PRIVATE).edit();
        editor.putBoolean(AppConstant.USER_STATUS, false);
        editor.apply();

/*            FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
            firebaseMessaging.setAutoInitEnabled(false);
            firebaseMessaging.deleteToken();*/

        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void onFinishWithError(String message) {
        new MessageDialog(context, context.getString(R.string.title_error_message), message);
    }
}

