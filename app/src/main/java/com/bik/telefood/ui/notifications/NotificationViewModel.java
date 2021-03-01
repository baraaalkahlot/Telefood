package com.bik.telefood.ui.notifications;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.entity.Autherntication.NotificationResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import retrofit2.Response;

public class NotificationViewModel extends AndroidViewModel {
    private NetworkUtils networkUtils;
    private MutableLiveData<NotificationResponse> notificationResponseMutableLiveData;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<NotificationResponse> getNotifications(Context context, FragmentManager fragmentManager) {
        notificationResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().getNotification().enqueue(new BaseCallBack<NotificationResponse>(context, fragmentManager, false) {
            @Override
            protected void onFinishWithSuccess(NotificationResponse result, Response<NotificationResponse> response) {
                notificationResponseMutableLiveData.setValue(response.body());
            }
        });
        return notificationResponseMutableLiveData;
    }
}
