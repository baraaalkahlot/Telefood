package com.bik.telefood.ui.more;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.databinding.ActivitySubscriptionRenewalBinding;

public class SubscriptionRenewalActivity extends AppCompatActivity {

    private ActivitySubscriptionRenewalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubscriptionRenewalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSend.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}