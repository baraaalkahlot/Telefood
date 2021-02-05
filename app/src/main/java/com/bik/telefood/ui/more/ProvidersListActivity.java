package com.bik.telefood.ui.more;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.databinding.ActivityProvidersListBinding;

public class ProvidersListActivity extends AppCompatActivity {

    private ActivityProvidersListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProvidersListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}