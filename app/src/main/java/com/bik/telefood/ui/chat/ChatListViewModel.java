package com.bik.telefood.ui.chat;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.entity.chat.MyRoomModel;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import retrofit2.Response;

public class ChatListViewModel extends AndroidViewModel {

    private final NetworkUtils networkUtils;
    private MutableLiveData<MyRoomModel> myRoomModelMutableLiveData;

    public ChatListViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<MyRoomModel> getMyRooms(Context context, FragmentManager fragmentManager) {
        myRoomModelMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().getMyRooms().enqueue(new BaseCallBack<MyRoomModel>(context, fragmentManager, false) {
            @Override
            protected void onFinishWithSuccess(MyRoomModel result, Response<MyRoomModel> response) {
                myRoomModelMutableLiveData.setValue(result);
            }
        });
        return myRoomModelMutableLiveData;
    }
}
