package com.bik.telefood.ui.support;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.entity.MainResponse;
import com.bik.telefood.model.entity.support.MyTicketResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Part;

public class SupportViewModel extends AndroidViewModel {

    private final NetworkUtils networkUtils;
    private MutableLiveData<MyTicketResponse> myTicketResponseMutableLiveData;
    private MutableLiveData<MainResponse> mainResponseMutableLiveData;

    public SupportViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<MyTicketResponse> getMyTicket(Context context, FragmentManager fragmentManager) {
        myTicketResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().getMyTicket().enqueue(new BaseCallBack<MyTicketResponse>(context, fragmentManager, false) {
            @Override
            protected void onFinishWithSuccess(MyTicketResponse result, Response<MyTicketResponse> response) {
                myTicketResponseMutableLiveData.setValue(result);
            }
        });
        return myTicketResponseMutableLiveData;
    }

    public LiveData<MainResponse> addTicket(HashMap<String, RequestBody> params, @Part MultipartBody.Part[] attachment, Context context, FragmentManager fragmentManager) {
        mainResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().addTicket(params, attachment).enqueue(new BaseCallBack<MainResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(MainResponse result, Response<MainResponse> response) {
                mainResponseMutableLiveData.setValue(result);
            }
        });
        return mainResponseMutableLiveData;
    }
}
