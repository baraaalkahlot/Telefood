package com.bik.telefood.ui.auth.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.CommonUtils.MainActivity;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityLoginBinding;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.auth.EnterPhoneActivity;
import com.bik.telefood.ui.auth.SignUpActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.tvActionSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        binding.tvActionResetPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, EnterPhoneActivity.class);
            startActivity(intent);
        });
        binding.btnLogin.setOnClickListener(v -> checkValidation());

        binding.tvActionAsGuest.setOnClickListener(v -> moveToMain());
    }


    private void checkValidation() {
        String phoneNumber = binding.etPhoneNumber.getText().toString();
        String password = binding.etPassword.getText().toString();

        if (phoneNumber.isEmpty()) {
            binding.etPhoneNumber.setError(getString(R.string.error_msg_missing_phone_number));
            return;
        }

        if (password.isEmpty()) {
            binding.etPassword.setError(getString(R.string.error_msg_missing_password));
            return;
        }

        try {
            int phone = Integer.parseInt(phoneNumber);
            Log.d("wasd", "checkValidation: " + phoneNumber);
            Log.d("wasd", "checkValidation: " + phone);
            Log.d("wasd", "checkValidation: " + password);
            login(phone, password);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private void login(int phoneNumber, String password) {
        loginViewModel.addUserSection(phoneNumber, password, this, getSupportFragmentManager()).observe(this, loginResponse -> {
            // Cache Token
            SharedPreferences.Editor preferences = getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE).edit();
            preferences.putString(ApiConstant.AUTHORIZATION, loginResponse.getAccessToken());

            // Cache Login Status
            preferences.putBoolean(AppConstant.USER_STATUS, true);

            // Cache User Id
            preferences.putString(AppConstant.USER_ID, String.valueOf(loginResponse.getUser().getId()));

            // Cache User Type
            String userRole = (loginResponse.getUser().getRole().equals(ApiConstant.ROLE_USER)) ? ApiConstant.ROLE_USER : ApiConstant.ROLE_VENDOR;
            preferences.putString(AppConstant.USER_TYPE, userRole);
            preferences.apply();

            moveToMain();
        });

    }

    private void moveToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}