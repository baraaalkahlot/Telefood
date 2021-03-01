package com.bik.telefood.model.entity.Autherntication;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResponse extends MainResponse {
    @SerializedName("notifications")
    @Expose
    private List<NotificationModel> notifications = null;

    public List<NotificationModel> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationModel> notifications) {
        this.notifications = notifications;
    }
}
