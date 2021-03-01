package com.bik.telefood.ui.notifications;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bik.telefood.databinding.ItemNotificationBinding;
import com.bik.telefood.model.entity.Autherntication.NotificationModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private List<NotificationModel> notifications;

    public NotificationAdapter(List<NotificationModel> notifications) {
        this.notifications = notifications;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemNotificationBinding itemNotificationBinding;

        public ViewHolder(@NonNull ItemNotificationBinding itemView) {
            super(itemView.getRoot());
            itemView.tvTitleNotification.setBackground(null);
            itemView.tvContent.setBackground(null);
            itemView.tvDate.setBackground(null);
            itemNotificationBinding = itemView;
        }

        public void bind(int position) {
            NotificationModel notificationModel = notifications.get(position);
            itemNotificationBinding.tvTitleNotification.setText(notificationModel.getTitle());
            itemNotificationBinding.tvContent.setText(notificationModel.getDesc());
            itemNotificationBinding.tvDate.setText(notificationModel.getCreatedAt());
        }
    }
}
