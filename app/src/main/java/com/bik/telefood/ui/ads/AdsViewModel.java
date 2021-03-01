package com.bik.telefood.ui.ads;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.entity.Autherntication.MyServiceResponse;
import com.bik.telefood.model.entity.MainResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class AdsViewModel extends AndroidViewModel {

    private final NetworkUtils networkUtils;
    private MutableLiveData<MainResponse> mainResponseMutableLiveData;
    private MutableLiveData<MyServiceResponse> myServiceResponseMutableLiveData;

    public AdsViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<MyServiceResponse> getMyAds(Context context, FragmentManager fragmentManager) {
        myServiceResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().myService().enqueue(new BaseCallBack<MyServiceResponse>(context, fragmentManager, false) {
            @Override
            protected void onFinishWithSuccess(MyServiceResponse result, Response<MyServiceResponse> response) {
                myServiceResponseMutableLiveData.setValue(result);
            }
        });
        return myServiceResponseMutableLiveData;
    }

    public LiveData<MainResponse> addService(HashMap<String, RequestBody> params, MultipartBody.Part[] images, Context context, FragmentManager fragmentManager) {
        mainResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().addService(params, images).enqueue(new BaseCallBack<MainResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(MainResponse result, Response<MainResponse> response) {
                mainResponseMutableLiveData.setValue(response.body());
            }
        });
        return mainResponseMutableLiveData;
    }

    public LiveData<MainResponse> updateService(HashMap<String, RequestBody> params, MultipartBody.Part[] images, Context context, FragmentManager fragmentManager) {
        mainResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().updateService(params, images).enqueue(new BaseCallBack<MainResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(MainResponse result, Response<MainResponse> response) {
                mainResponseMutableLiveData.setValue(response.body());
            }
        });
        return mainResponseMutableLiveData;
    }

    public LiveData<MainResponse> deleteService(int id, Context context, FragmentManager fragmentManager) {
        mainResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().deleteService(id).enqueue(new BaseCallBack<MainResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(MainResponse result, Response<MainResponse> response) {
                mainResponseMutableLiveData.setValue(result);
            }
        });
        return mainResponseMutableLiveData;
    }
}