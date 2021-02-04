package com.bik.telefood.ui.onboarding;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityUserTypeBinding;
import com.bik.telefood.ui.auth.AuthActivity;

public class UserTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUserTypeBinding binding = ActivityUserTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnConsumerType.setOnClickListener(v -> navigateToAuthFlow());
        binding.btnProviderType.setOnClickListener(v -> navigateToAuthFlow());
    }

    private void navigateToAuthFlow() {
        startActivity(new Intent(this, AuthActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_left_out, R.anim.slide_left_in);
    }
}