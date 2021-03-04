package com.bik.telefood.ui.common.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.entity.Autherntication.vendors.SingleVendorsResponse;
import com.bik.telefood.model.entity.Autherntication.vendors.VendorsResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import java.util.HashMap;

import retrofit2.Response;

public class VendorsViewModel extends AndroidViewModel {
    private final NetworkUtils networkUtils;
    private MutableLiveData<VendorsResponse> vendorsResponseMutableLiveData;
    private MutableLiveData<SingleVendorsResponse> singleVendorsResponseMutableLiveData;

    public VendorsViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<VendorsResponse> getVendors(Integer page, HashMap<String, String> params, Context context, FragmentManager fragmentManager) {
        vendorsResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().getVendors(page, params).enqueue(new BaseCallBack<VendorsResponse>(context, fragmentManager, false) {
            @Override
            protected void onFinishWithSuccess(VendorsResponse result, Response<VendorsResponse> response) {
                vendorsResponseMutableLiveData.setValue(result);
            }
        });
        return vendorsResponseMutableLiveData;
    }

    public LiveData<SingleVendorsResponse> getSingleVendors(Integer page, int id, Context context, FragmentManager fragmentManager) {
        singleVendorsResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().getSingleVendors(page, id).enqueue(new BaseCallBack<SingleVendorsResponse>(context, fragmentManager, false) {
            @Override
            protected void onFinishWithSuccess(SingleVendorsResponse result, Response<SingleVendorsResponse> response) {
                singleVendorsResponseMutableLiveData.setValue(result);
            }
        });
        return singleVendorsResponseMutableLiveData;
    }
}
