package com.bik.telefood.model.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.bik.telefood.R;
import com.bik.telefood.common.AppConstant;
import com.bik.telefood.model.entity.MainResponse;
import com.bik.telefood.ui.auth.AuthActivity;
import com.bik.telefood.utils.MessageDialog;
import com.bik.telefood.utils.NoConnectionDialog;
import com.bik.telefood.utils.ProgressBarDialog;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseCallBack<T> {

    public abstract void onSuccess(Response<T> response);

    public abstract void onFailure(String message);

    public abstract void onUnAuthorized();


    public abstract static class SuccessCallback<T> extends BaseCallBack<T> implements Callback<T> {

        private static final String TAG = BaseCallBack.class.getName();
        private FragmentManager fragmentManager;
        private Activity activity;
        private ProgressBarDialog progressBarDialog;
        private boolean progressBar;

        public SuccessCallback(Activity activity, FragmentManager fragmentManager, boolean progressBar) {
            this.activity = activity;
            this.fragmentManager = fragmentManager;
            this.progressBar = progressBar;

            if (progressBar) {
                //Show Progress Dialog
                progressBarDialog = new ProgressBarDialog();
                progressBarDialog.show(fragmentManager, "ProgressBarDialog");
            }
        }

        @Override
        public void onResponse(@NotNull Call<T> call, Response<T> response) {
            if (progressBar) {
                if (progressBarDialog.isVisible())
                    progressBarDialog.dismiss();
            }

            if (response.code() == 401) onUnAuthorized();

            MainResponse mainResponse = (MainResponse) response.body();
/*            if (mainResponse != null) {
                if (response.code() >= 400 && response.code() < 599)
                    onFailure(mainResponse.getMessage());
                else {
                    if (mainResponse.isStatus()) {
                        onSuccess(response);
                    } else {
                        new MessageDialog(activity, activity.getString(R.string.title_erorr_message), mainResponse.getMessage());
                    }
                }
            }*/
        }

        @Override
        public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
            if (progressBar)
                progressBarDialog.dismiss();

            new NoConnectionDialog().show(fragmentManager, "NoConnectionDialog");
        }

        @Override
        public void onSuccess(Response<T> response) {
        }

        @Override
        public void onFailure(String message) {
            new MessageDialog(activity, activity.getString(R.string.title_error_message), message);
        }

        @Override
        public void onUnAuthorized() {
            Toast.makeText(activity, R.string.error_message_un_authorized, Toast.LENGTH_LONG).show();
            // Cache new status >> SIGN OUT
            SharedPreferences.Editor editor = activity.getSharedPreferences(AppConstant.USER_STATUS, Context.MODE_PRIVATE).edit();
            editor.putBoolean(AppConstant.USER_STATUS, false);
            editor.apply();

/*            FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
            firebaseMessaging.setAutoInitEnabled(false);
            firebaseMessaging.deleteToken();*/

            Intent intent = new Intent(activity, AuthActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
    }
}
