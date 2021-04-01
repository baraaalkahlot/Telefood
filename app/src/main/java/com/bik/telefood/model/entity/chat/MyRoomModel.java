package com.bik.telefood.model.entity.chat;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyRoomModel extends MainResponse {
    @SerializedName("rooms")
    @Expose
    private List<RoomModel> rooms = null;

    public List<RoomModel> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomModel> rooms) {
        this.rooms = rooms;
    }
}
