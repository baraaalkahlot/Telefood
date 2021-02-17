package com.bik.telefood.ui.auth;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.entity.MainResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class AuthViewModel extends AndroidViewModel {

    private final NetworkUtils networkUtils;
    private MutableLiveData<MainResponse> resetPasswordResponseMutableLiveData;
    private MutableLiveData<MainResponse> signUpResponseMutableLiveData;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<MainResponse> register(HashMap<String, RequestBody> params, MultipartBody.Part avatar, Context context, FragmentManager fragmentManager) {
        signUpResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().register(params, avatar).enqueue(new BaseCallBack<MainResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(MainResponse result, Response<MainResponse> response) {
                signUpResponseMutableLiveData.setValue(response.body());
            }
        });
        return signUpResponseMutableLiveData;
    }

    public LiveData<MainResponse> resetPassword(HashMap<String, String> params, Context context, FragmentManager fragmentManager) {
        resetPasswordResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().resetPassword(params).enqueue(new BaseCallBack<MainResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(MainResponse result, Response<MainResponse> response) {
                resetPasswordResponseMutableLiveData.setValue(response.body());
            }
        });
        return resetPasswordResponseMutableLiveData;
    }

}