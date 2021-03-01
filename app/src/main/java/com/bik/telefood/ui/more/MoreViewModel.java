package com.bik.telefood.ui.more;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.entity.Autherntication.UpdateProfileResponse;
import com.bik.telefood.model.entity.MainResponse;
import com.bik.telefood.model.entity.general.AboutAppResponse;
import com.bik.telefood.model.entity.general.BankInfoResponse;
import com.bik.telefood.model.entity.general.PackageResponse;
import com.bik.telefood.model.entity.general.PrivacyPolicyResponse;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class MoreViewModel extends AndroidViewModel {

    private final NetworkUtils networkUtils;
    private MutableLiveData<UpdateProfileResponse> updateResponseMutableLiveData;
    private MutableLiveData<MainResponse> changePasswordResponseMutableLiveData;
    private MutableLiveData<BankInfoResponse> bankInfoResponseMutableLiveData;
    private MutableLiveData<AboutAppResponse> aboutAppResponseMutableLiveData;
    private MutableLiveData<PrivacyPolicyResponse> privacyPolicyResponseMutableLiveData;
    private MutableLiveData<PackageResponse> packageResponseMutableLiveData;
    private MutableLiveData<MainResponse> subscriptionResponseMutableLiveData;

    public MoreViewModel(@NonNull Application application) {
        super(application);
        networkUtils = NetworkUtils.getInstance(application);
    }

    public LiveData<UpdateProfileResponse> updateProfile(HashMap<String, RequestBody> params, MultipartBody.Part avatar, Context context, FragmentManager fragmentManager) {
        updateResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().updateProfile(params, avatar).enqueue(new BaseCallBack<UpdateProfileResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(UpdateProfileResponse result, Response<UpdateProfileResponse> response) {
                updateResponseMutableLiveData.setValue(response.body());
            }
        });
        return updateResponseMutableLiveData;
    }

    public LiveData<UpdateProfileResponse> getProfile(Context context, FragmentManager fragmentManager) {
        updateResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().getProfile().enqueue(new BaseCallBack<UpdateProfileResponse>(context, fragmentManager, false) {
            @Override
            protected void onFinishWithSuccess(UpdateProfileResponse result, Response<UpdateProfileResponse> response) {
                updateResponseMutableLiveData.setValue(response.body());
            }
        });
        return updateResponseMutableLiveData;
    }

    public LiveData<MainResponse> changePassword(HashMap<String, String> params, Context context, FragmentManager fragmentManager) {
        changePasswordResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().changePassword(params).enqueue(new BaseCallBack<MainResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(MainResponse result, Response<MainResponse> response) {
                changePasswordResponseMutableLiveData.setValue(response.body());
            }
        });
        return changePasswordResponseMutableLiveData;
    }

    public LiveData<BankInfoResponse> getBankInfo(Context context, FragmentManager fragmentManager) {
        bankInfoResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().getBankInfo().enqueue(new BaseCallBack<BankInfoResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(BankInfoResponse result, Response<BankInfoResponse> response) {
                bankInfoResponseMutableLiveData.setValue(response.body());
            }
        });
        return bankInfoResponseMutableLiveData;
    }

    public LiveData<AboutAppResponse> aboutApp(Context context, FragmentManager fragmentManager) {
        aboutAppResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().aboutApp().enqueue(new BaseCallBack<AboutAppResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(AboutAppResponse result, Response<AboutAppResponse> response) {
                aboutAppResponseMutableLiveData.setValue(response.body());
            }
        });
        return aboutAppResponseMutableLiveData;
    }

    public LiveData<PrivacyPolicyResponse> privacyPolicy(Context context, FragmentManager fragmentManager) {
        privacyPolicyResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().privacyPolicy().enqueue(new BaseCallBack<PrivacyPolicyResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(PrivacyPolicyResponse result, Response<PrivacyPolicyResponse> response) {
                privacyPolicyResponseMutableLiveData.setValue(response.body());
            }
        });
        return privacyPolicyResponseMutableLiveData;
    }

    public LiveData<PackageResponse> getSubscriptionPackages(Context context, FragmentManager fragmentManager) {
        packageResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().getSubscriptionPackages().enqueue(new BaseCallBack<PackageResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(PackageResponse result, Response<PackageResponse> response) {
                packageResponseMutableLiveData.setValue(response.body());
            }
        });
        return packageResponseMutableLiveData;
    }

    public LiveData<MainResponse> newSubscription(MultipartBody.Part image, int packageId, Context context, FragmentManager fragmentManager) {
        subscriptionResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().newSubscription(image, packageId).enqueue(new BaseCallBack<MainResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(MainResponse result, Response<MainResponse> response) {
                subscriptionResponseMutableLiveData.setValue(response.body());
            }
        });
        return subscriptionResponseMutableLiveData;
    }
}