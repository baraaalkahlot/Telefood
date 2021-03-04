package com.bik.telefood.ui.common.viewmodel;

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

import retrofit2.Response;

public class ToggleFavoriteViewModel extends AndroidViewModel {
    private final NetworkUtils networkUtils;
    private MutableLiveData<MainResponse> mainResponseMutableLiveData;

    public ToggleFavoriteViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<MainResponse> favoriteToggle(String type, int id, Context context, FragmentManager fragmentManager) {
        mainResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().favoriteToggle(type, id).enqueue(new BaseCallBack<MainResponse>(context, fragmentManager, false) {
            @Override
            protected void onFinishWithSuccess(MainResponse result, Response<MainResponse> response) {
                mainResponseMutableLiveData.setValue(result);
            }
        });
        return mainResponseMutableLiveData;
    }
}
