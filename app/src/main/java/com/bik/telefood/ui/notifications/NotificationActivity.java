package com.bik.telefood.ui.notifications;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bik.telefood.databinding.ActivityNotificationBinding;

public class NotificationActivity extends AppCompatActivity {

    private ActivityNotificationBinding binding;
    private NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notificationAdapter = new NotificationAdapter();
        binding.rvNotifications.setAdapter(notificationAdapter);
    }
}