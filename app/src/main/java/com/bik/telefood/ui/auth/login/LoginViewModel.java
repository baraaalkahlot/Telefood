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

    public LiveData<LoginResponse> addUserSection(int phoneNumber, String password, Context context, FragmentManager fragmentManager) {
        return loginRepository.addUserModel(phoneNumber, password, context, fragmentManager);
    }

    public void updateUserSection(String m_avatar, String m_name, String m_phone, String m_governorate_id, String m_city_id, String m_governorate, String m_city, String chasedPlanName, String remainingDaysInPlan, boolean sendNotification, String rating) {
        loginRepository.updateUserModel(m_avatar, m_name, m_phone, m_governorate_id, m_city_id, m_governorate, m_city, chasedPlanName, remainingDaysInPlan, sendNotification, rating);
    }

    public void updateNotification(boolean sendNotification) {
        loginRepository.updateNotification(sendNotification);
    }


    public void deleteUserTable() {
        loginRepository.deleteUserTable();
    }


}
