package com.bik.telefood.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bik.telefood.CommonUtils.AppConstant;
import com.bik.telefood.model.network.ApiConstant;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

@Keep
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        SharedPreferences.Editor preferences = getSharedPreferences(AppConstant.APP_NETWORK, Context.MODE_PRIVATE).edit();
        preferences.putString(AppConstant.FCM_TOKEN, s);
        preferences.apply();
        Log.e("wasd", "onNewToken: " + s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String type = remoteMessage.getData().get("notificationType");
        String roomID = remoteMessage.getData().get("roomID");

        Log.d("wasd", "onMessageReceived: type = " + type);
        Log.d("wasd", "onMessageReceived: roomID = " + roomID);
        SharedPreferences.Editor preferences = getSharedPreferences(AppConstant.ADD_DATA, Context.MODE_PRIVATE).edit();
        if (type != null && type.equals(ApiConstant.NOTIFICATION_TYPE_CHAT)) {
            Log.d("wasd", "onMessageReceived: chat");
            // Cache Chat Received
            preferences.putBoolean(AppConstant.IS_THERE_MESSAGE, true);
            preferences.putString(AppConstant.ROOM_ID, roomID);
            Notification.getInstance().addNewMessage(roomID);
        } else {
            Log.d("wasd", "onMessageReceived: notification");
            // Cache Notification Received
            preferences.putBoolean(AppConstant.IS_THERE_NOTIFICATION, true);
            Notification.getInstance().addNewNotification(true);
        }
        preferences.apply();
    }

    public static class Notification {
        private static Notification instance;
        private MutableLiveData<Boolean> newNotification;
        private MutableLiveData<String> newMessage;

        private Notification() {
            newNotification = new MutableLiveData<>();
            newMessage = new MutableLiveData<>();
        }

        public static Notification getInstance() {
            if (instance == null) {
                instance = new Notification();
            }
            return instance;
        }

        public LiveData<String> getNewMessage() {
            return newMessage;
        }

        public void addNewMessage(String roomId) {
            newMessage.postValue(roomId);
        }

        public LiveData<Boolean> getNewNotification() {
            return newNotification;
        }

        public void addNewNotification(Boolean value) {
            newNotification.postValue(value);
        }
    }


}
