package com.bik.telefood.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.bik.telefood.model.network.NetworkUtils;

public class HomeViewModel extends AndroidViewModel {

    private NetworkUtils networkUtils;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }


}