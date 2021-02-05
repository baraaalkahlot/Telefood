package com.bik.telefood.ui.more;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.databinding.ActivityEditUserInfoBinding;

public class EditUserInfoActivity extends AppCompatActivity {

    private ActivityEditUserInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditUserInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnSave.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });
    }
}