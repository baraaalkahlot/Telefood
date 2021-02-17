package com.bik.telefood.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
    }

    private void navigateToAuthFlow(String role) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra(ApiConstant.ROLE, role);
        startActivity(intent);
        finish();
    }
}