package com.bik.telefood.ui.ads;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.entity.AddServicesRequestBody;
import com.bik.telefood.model.entity.Autherntication.MyServiceResponse;
import com.bik.telefood.model.entity.Autherntication.UploadImagesResponse;
import com.bik.telefood.model.entity.MainResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Response;

public class AdsViewModel extends AndroidViewModel {

    private final NetworkUtils networkUtils;
    private MutableLiveData<MainResponse> mainResponseMutableLiveData;
    private MutableLiveData<MyServiceResponse> myServiceResponseMutableLiveData;
    private MutableLiveData<UploadImagesResponse> uploadImagesResponseMutableLiveData;

    public AdsViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<MyServiceResponse> getMyAds(HashMap<String, String> params, Context context, FragmentManager fragmentManager) {
        myServiceResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().myService(params).enqueue(new BaseCallBack<MyServiceResponse>(context, fragmentManager, false) {
            @Override
            protected void onFinishWithSuccess(MyServiceResponse result, Response<MyServiceResponse> response) {
                myServiceResponseMutableLiveData.setValue(result);
            }
        });
        return myServiceResponseMutableLiveData;
    }

    public LiveData<MainResponse> addService(AddServicesRequestBody body, Context context, FragmentManager fragmentManager) {
        mainResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().addService(body).enqueue(new BaseCallBack<MainResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(MainResponse result, Response<MainResponse> response) {
                mainResponseMutableLiveData.setValue(response.body());
            }
        });
        return mainResponseMutableLiveData;
    }

    public LiveData<MainResponse> updateService(AddServicesRequestBody body, Context context, FragmentManager fragmentManager) {
        mainResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().updateService(body).enqueue(new BaseCallBack<MainResponse>(context, fragmentManager, true) {
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

    public LiveData<UploadImagesResponse> uploadImage(MultipartBody.Part image, Context context, FragmentManager fragmentManager) {
        uploadImagesResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().uploadImage(image).enqueue(new BaseCallBack<UploadImagesResponse>(context, fragmentManager, false) {
            @Override
            protected void onFinishWithSuccess(UploadImagesResponse result, Response<UploadImagesResponse> response) {
                uploadImagesResponseMutableLiveData.setValue(result);
            }
        });
        return uploadImagesResponseMutableLiveData;
    }
}