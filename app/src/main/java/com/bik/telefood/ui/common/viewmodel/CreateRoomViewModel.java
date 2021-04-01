package com.bik.telefood.ui.common.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.entity.chat.CreateRoomResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import retrofit2.Response;

public class CreateRoomViewModel extends AndroidViewModel {
    private NetworkUtils networkUtils;
    private MutableLiveData<CreateRoomResponse> createRoomResponseMutableLiveData;

    public CreateRoomViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<CreateRoomResponse> createOrFindRoom(int userId, Context context, FragmentManager fragmentManager) {
        createRoomResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().createOrFindRoom(userId).enqueue(new BaseCallBack<CreateRoomResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(CreateRoomResponse result, Response<CreateRoomResponse> response) {
                createRoomResponseMutableLiveData.setValue(result);
            }
        });
        return createRoomResponseMutableLiveData;
    }
}
