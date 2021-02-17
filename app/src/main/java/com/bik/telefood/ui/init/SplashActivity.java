package com.bik.telefood.ui.init;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.CommonUtils.MainActivity;
import com.bik.telefood.CommonUtils.SharedPreferencesHelper;
import com.bik.telefood.databinding.ActivitySplashBinding;
import com.bik.telefood.ui.onboarding.OnBoardingActivity;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashBinding binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler(getMainLooper()).postDelayed(() -> {
            Intent intent;
            if (SharedPreferencesHelper.isLoggedIn(SplashActivity.this.getApplication())) {
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, OnBoardingActivity.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }, 2500);
    }
}