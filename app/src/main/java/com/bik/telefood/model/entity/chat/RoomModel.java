package com.bik.telefood.model.entity.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomModel {

    @SerializedName("room_id")
    @Expose
    private int roomId;
    @SerializedName("user_id")
    @Expose
    private int userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_avatar")
    @Expose
    private String userAvatar;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
