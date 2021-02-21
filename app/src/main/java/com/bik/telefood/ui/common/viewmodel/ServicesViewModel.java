package com.bik.telefood.ui.common.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.entity.general.services.ServicesResponse;
import com.bik.telefood.model.entity.general.singleservices.SingleServiceResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import java.util.HashMap;

import retrofit2.Response;

public class ServicesViewModel extends AndroidViewModel {

    private final NetworkUtils networkUtils;
    private MutableLiveData<ServicesResponse> servicesResponseMutableLiveData;
    private MutableLiveData<SingleServiceResponse> singleServiceResponseMutableLiveData;

    public ServicesViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<ServicesResponse> getServices(Integer page, HashMap<String, String> params, Context context, FragmentManager fragmentManager, boolean progressEnabled) {
        servicesResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().getServicesList(page, params).enqueue(new BaseCallBack<ServicesResponse>(context, fragmentManager, progressEnabled) {
            @Override
            protected void onFinishWithSuccess(ServicesResponse result, Response<ServicesResponse> response) {
                servicesResponseMutableLiveData.setValue(response.body());
            }
        });
        return servicesResponseMutableLiveData;
    }

    public LiveData<SingleServiceResponse> getSingleServicesList(int id, Context context, FragmentManager fragmentManager, boolean progressEnabled) {
        singleServiceResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().getSingleServicesList(id).enqueue(new BaseCallBack<SingleServiceResponse>(context, fragmentManager, progressEnabled) {
            @Override
            protected void onFinishWithSuccess(SingleServiceResponse result, Response<SingleServiceResponse> response) {
                singleServiceResponseMutableLiveData.setValue(response.body());
            }
        });
        return singleServiceResponseMutableLiveData;
    }

}
