package com.bik.telefood.ui.home;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.entity.Autherntication.vendors.FeaturedVendorsResponse;
import com.bik.telefood.model.entity.general.services.ServicesResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import java.util.HashMap;

import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {

    private final NetworkUtils networkUtils;
    private MutableLiveData<ServicesResponse> servicesResponseMutableLiveData;
    private MutableLiveData<FeaturedVendorsResponse> featuredVendorsResponseMutableLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<ServicesResponse> getFeaturedServices(Integer page, HashMap<String, String> params, Context context, FragmentManager fragmentManager, boolean progressEnabled) {
        servicesResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().getFeaturedServices(page, params).enqueue(new BaseCallBack<ServicesResponse>(context, fragmentManager, progressEnabled) {
            @Override
            protected void onFinishWithSuccess(ServicesResponse result, Response<ServicesResponse> response) {
                servicesResponseMutableLiveData.setValue(response.body());
            }
        });
        return servicesResponseMutableLiveData;
    }

    public LiveData<FeaturedVendorsResponse> getFeaturedVendors(Context context, FragmentManager fragmentManager, boolean progressEnabled) {
        featuredVendorsResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().getFeaturedVendors().enqueue(new BaseCallBack<FeaturedVendorsResponse>(context, fragmentManager, progressEnabled) {
            @Override
            protected void onFinishWithSuccess(FeaturedVendorsResponse result, Response<FeaturedVendorsResponse> response) {
                featuredVendorsResponseMutableLiveData.setValue(response.body());
            }
        });
        return featuredVendorsResponseMutableLiveData;
    }
}