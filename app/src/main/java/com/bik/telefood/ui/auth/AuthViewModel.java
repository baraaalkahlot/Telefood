package com.bik.telefood.ui.auth;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.bik.telefood.model.network.NetworkUtils;

public class AuthViewModel extends AndroidViewModel {

    private NetworkUtils networkUtils;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }
}