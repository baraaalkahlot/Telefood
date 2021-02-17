package com.bik.telefood.ui.auth.login;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bik.telefood.model.entity.auth.LoginResponse;
import com.bik.telefood.model.entity.general.UserModel;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository loginRepository;
    private LiveData<UserModel> loginResponseMutableLiveData;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = LoginRepository.getInstance(application);
        loginResponseMutableLiveData = loginRepository.getUserModel();
    }

    public LiveData<UserModel> getUserModel() {
        return loginResponseMutableLiveData;
    }

    public LiveData<LoginResponse> updateMainSection(int phoneNumber, String password, Context context, FragmentManager fragmentManager) {
        return loginRepository.updateUserModel(phoneNumber, password, context, fragmentManager);
    }

    public void deleteUserTable() {
        loginRepository.deleteUserTable();
    }
}
