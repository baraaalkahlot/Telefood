package com.bik.telefood.ui.favorite;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.bik.telefood.model.entity.Autherntication.vendors.VendorsResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import retrofit2.Response;

public class FavoriteProvidersViewModel extends AndroidViewModel {
    private final NetworkUtils networkUtils;
    private MutableLiveData<VendorsResponse> vendorsResponseMutableLiveData;
    private LiveData<VendorsResponse> vendorsResponseLiveData;

    public FavoriteProvidersViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public void setNameQuery(String type, Context context, FragmentManager fragmentManager) {
        vendorsResponseMutableLiveData = new MutableLiveData<>();
        vendorsResponseLiveData = Transformations.map(vendorsResponseMutableLiveData, input -> {
            networkUtils.getApiInterface().myFavoritesVendors(type).enqueue(new BaseCallBack<VendorsResponse>(context, fragmentManager, false) {
                @Override
                protected void onFinishWithSuccess(VendorsResponse result, Response<VendorsResponse> response) {
                    vendorsResponseMutableLiveData.setValue(result);
                }
            });
            return input;
        });
    }

    public LiveData<VendorsResponse> getVendorsResponseLiveData() {
        return vendorsResponseLiveData;
    }
}