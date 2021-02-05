package com.bik.telefood.ui.support;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.databinding.ActivityTechnicalSupportBinding;

public class TechnicalSupportActivity extends AppCompatActivity {

    private ActivityTechnicalSupportBinding binding;
    private SupportTicketAdapter supportTicketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTechnicalSupportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        supportTicketAdapter = new SupportTicketAdapter();
        binding.rvTicket.setAdapter(supportTicketAdapter);

        binding.fabContactUs.setOnClickListener(v -> startActivity(new Intent(this, ContactUsActivity.class)));
    }
}