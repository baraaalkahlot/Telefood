package com.bik.telefood.ui.common.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.entity.general.CitiesResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import retrofit2.Response;

public class CitiesViewModel extends AndroidViewModel {

    private NetworkUtils networkUtils;
    private MutableLiveData<CitiesResponse> citiesResponseMutableLiveData;

    public CitiesViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<CitiesResponse> getCityList(int governorate, Context context, FragmentManager fragmentManager) {
        if (citiesResponseMutableLiveData == null) {
            citiesResponseMutableLiveData = new MutableLiveData<>();
            networkUtils.getApiInterface().getCitiesResponseList(governorate).enqueue(new BaseCallBack<CitiesResponse>(context, fragmentManager, true) {
                @Override
                protected void onFinishWithSuccess(CitiesResponse result, Response<CitiesResponse> response) {
                    citiesResponseMutableLiveData.setValue(response.body());
                }
            });
        }
        return citiesResponseMutableLiveData;
    }

    public void setLiveDataNull() {
        citiesResponseMutableLiveData = null;
    }
}
