package com.bik.telefood.ui.more.setting;

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

public class SettingViewModel extends AndroidViewModel {


    private final NetworkUtils networkUtils;
    private MutableLiveData<MainResponse> NotificationResponseMutableLiveData;

    public SettingViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);

    }

    public LiveData<MainResponse> toggleNotification(Context context, FragmentManager fragmentManager) {
        NotificationResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().toggleNotification().enqueue(new BaseCallBack<MainResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(MainResponse result, Response<MainResponse> response) {
                NotificationResponseMutableLiveData.setValue(response.body());
            }
        });
        return NotificationResponseMutableLiveData;
    }
}
