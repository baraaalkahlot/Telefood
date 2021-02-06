package com.bik.telefood.ui.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityMessageBinding;

public class MessageActivity extends AppCompatActivity {

    private ActivityMessageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    binding.ivMic.setImageResource(R.drawable.ic_microphone);
                    binding.ivImage.setVisibility(View.VISIBLE);
                    binding.ivLocation.setVisibility(View.VISIBLE);
                } else {
                    binding.ivMic.setImageResource(R.drawable.ic_send);
                    binding.ivImage.setVisibility(View.GONE);
                    binding.ivLocation.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}