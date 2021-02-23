package com.bik.telefood.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.CommonUtils.MainActivity;
import com.bik.telefood.databinding.ActivityUserTypeBinding;
import com.bik.telefood.model.network.ApiConstant;
import com.bik.telefood.ui.auth.SignUpActivity;

public class UserTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUserTypeBinding binding = ActivityUserTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnConsumerType.setOnClickListener(v -> navigateToAuthFlow(ApiConstant.ROLE_USER));
        binding.btnProviderType.setOnClickListener(v -> navigateToAuthFlow(ApiConstant.ROLE_VENDOR));
        binding.tvActionAsGuest.setOnClickListener(v -> moveToMain());
    }

    private void navigateToAuthFlow(String role) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra(ApiConstant.ROLE, role);
        startActivity(intent);
        finish();
    }

    private void moveToMain() {
        Intent intent = new Intent(UserTypeActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}