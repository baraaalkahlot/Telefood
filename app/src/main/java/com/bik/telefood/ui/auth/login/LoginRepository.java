package com.bik.telefood.ui.auth.login;

import android.app.Application;
import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.model.database.TelefoodDataBase;
import com.bik.telefood.model.database.UserSectionDao;
import com.bik.telefood.model.entity.auth.LoginResponse;
import com.bik.telefood.model.entity.general.UserModel;
import com.bik.telefood.model.network.BaseCallBack;
import com.bik.telefood.model.network.NetworkUtils;

import retrofit2.Response;

public class LoginRepository {

    private static final String TAG = LoginRepository.class.getName();
    private static LoginRepository sInstance;
    private final NetworkUtils networkUtils;
    private final UserSectionDao userSectionDao;
    private MutableLiveData<LoginResponse> loginResponseMutableLiveData;

    private LoginRepository(Application application) {
        networkUtils = NetworkUtils.getInstance(application);
        userSectionDao = TelefoodDataBase.getInstance(application).userSectionDao();
    }

    public static LoginRepository getInstance(Application application) {
        if (sInstance == null) {
            sInstance = new LoginRepository(application);
        }
        return sInstance;
    }

    public LiveData<UserModel> getUserModel() {
        return userSectionDao.getUserModel();
    }

    public LiveData<LoginResponse> updateUserModel(int phoneNumber, String password, Context context, FragmentManager fragmentManager) {
        loginResponseMutableLiveData = new MutableLiveData<>();
        networkUtils.getApiInterface().login(phoneNumber, password).enqueue(new BaseCallBack<LoginResponse>(context, fragmentManager, true) {
            @Override
            protected void onFinishWithSuccess(LoginResponse result, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse != null && loginResponse.getUser() != null) {
                    userSectionDao.addMainSectionItems(loginResponse.getUser());
                }
                loginResponseMutableLiveData.setValue(response.body());
            }
        });
        return loginResponseMutableLiveData;
    }

    public void deleteUserTable() {
        userSectionDao.deleteAll();
    }
}
