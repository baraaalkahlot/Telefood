package com.bik.telefood.ui.common.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.entity.general.GovernoratesResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import retrofit2.Response;

public class GovernorateViewModel extends AndroidViewModel {

    private final NetworkUtils networkUtils;
    private MutableLiveData<GovernoratesResponse> governoratesResponseMutableLiveData;

    public GovernorateViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<GovernoratesResponse> getGovernorateList(Context context, FragmentManager fragmentManager) {
        if (governoratesResponseMutableLiveData == null) {
            governoratesResponseMutableLiveData = new MutableLiveData<>();
            networkUtils.getApiInterface().getGovernoratesList().enqueue(new BaseCallBack<GovernoratesResponse>(context, fragmentManager, true) {
                @Override
                protected void onFinishWithSuccess(GovernoratesResponse result, Response<GovernoratesResponse> response) {
                    governoratesResponseMutableLiveData.setValue(response.body());
                }
            });
        }
        return governoratesResponseMutableLiveData;
    }
}
