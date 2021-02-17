package com.bik.telefood.ui.auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityResetPasswordBinding;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.auth.login.LoginActivity;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {

    private AuthViewModel authViewModel;
    private String phoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityResetPasswordBinding binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        if (getIntent() != null) {
            phoneNumber = getIntent().getStringExtra(AppConstant.PHONE_NUMBER);
        }

        binding.btnSave.setOnClickListener(v -> {
            String password = binding.etPassword.getText().toString();
            String confirmPassword = binding.etConfirmPassword.getText().toString();

            if (password.isEmpty()) {
                binding.ilPassword.setError(getString(R.string.error_msg_missing_password));
                return;
            }
            if (confirmPassword.isEmpty()) {
                binding.ilConfirmPassword.setError(getString(R.string.error_msg_missing_password));
                return;
            }

            HashMap<String, String> params = new HashMap<>();
            params.put(ApiConstant.PHONE, phoneNumber);
            params.put(ApiConstant.PASSWORD, password);
            params.put(ApiConstant.PASSWORD_CONFIRMATION, confirmPassword);

            authViewModel.resetPassword(params, this, getSupportFragmentManager()).observe(this, mainResponse -> {
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            });
        });
    }
}