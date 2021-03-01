package com.bik.telefood.ui.notifications;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bik.telefood.R;
import com.bik.telefood.databinding.ActivityNotificationBinding;
import com.bik.telefood.model.entity.Autherntication.NotificationModel;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private ActivityNotificationBinding binding;
    private NotificationAdapter notificationAdapter;
    private NotificationViewModel notificationViewModel;
    private SkeletonScreen notificationsSkeleton;
    private List<NotificationModel> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notifications = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(notifications);
        binding.rvNotifications.setAdapter(notificationAdapter);

        notificationsSkeleton = Skeleton.bind(binding.rvNotifications)
                .adapter(notificationAdapter)
                .color(R.color.mercury)
                .duration(500)
                .load(R.layout.item_notification)
                .show();

        notificationViewModel.getNotifications(this, getSupportFragmentManager()).observe(this, notificationResponse -> {
            notificationsSkeleton.hide();
            if (notificationResponse.getNotifications().isEmpty()) {
                binding.lottieEmptyNotification.setVisibility(View.VISIBLE);
                binding.lottieEmptyNotification.playAnimation();
                binding.rvNotifications.setVisibility(View.GONE);
            } else {
                notifications.addAll(notificationResponse.getNotifications());
                binding.lottieEmptyNotification.cancelAnimation();
                binding.lottieEmptyNotification.setVisibility(View.GONE);
                binding.rvNotifications.setVisibility(View.VISIBLE);
                notificationAdapter.notifyDataSetChanged();
            }
        });
    }
}