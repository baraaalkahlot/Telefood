package com.bik.telefood.ui.favorite;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.bik.telefood.model.entity.general.services.ServicesResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import retrofit2.Response;

public class FavoriteProductViewModel extends AndroidViewModel {
    private NetworkUtils networkUtils;
    private MutableLiveData<ServicesResponse> servicesResponseMutableLiveData;
    private LiveData<ServicesResponse> servicesResponseLiveData;

    public FavoriteProductViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
        servicesResponseMutableLiveData = new MutableLiveData<>();
        servicesResponseLiveData = Transformations.map(servicesResponseMutableLiveData, input -> input);
    }

    public void setFavoritesProduct(String type, Context context, FragmentManager fragmentManager) {
        networkUtils.getApiInterface().myFavoritesProduct(type).enqueue(new BaseCallBack<ServicesResponse>(context, fragmentManager, false) {
            @Override
            protected void onFinishWithSuccess(ServicesResponse result, Response<ServicesResponse> response) {
                servicesResponseMutableLiveData.setValue(result);
            }
        });
    }

    public LiveData<ServicesResponse> getServicesResponseLiveData() {
        return servicesResponseLiveData;
    }
}