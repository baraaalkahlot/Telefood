package com.bik.telefood.model.entity.chat;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateRoomResponse extends MainResponse {
    @SerializedName("room")
    @Expose
    private int roomId;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
