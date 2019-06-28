package com.example.administrator.airdetective.model.airinfomodel;

import java.io.Serializable;

public class RoomBean implements Serializable {

    private int roomId;

    private String roomName;

    private int fid;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }
}
