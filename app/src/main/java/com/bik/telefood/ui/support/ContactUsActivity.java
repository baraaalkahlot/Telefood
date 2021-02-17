package com.bik.telefood.ui.support;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.databinding.ActivityContactUsBinding;

public class ContactUsActivity extends AppCompatActivity {

    private ActivityContactUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}