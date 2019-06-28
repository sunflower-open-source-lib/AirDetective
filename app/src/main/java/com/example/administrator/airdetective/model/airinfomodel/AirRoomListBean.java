package com.example.administrator.airdetective.model.airinfomodel;

import java.io.Serializable;
import java.util.ArrayList;

public class AirRoomListBean implements Serializable{

    private RoomBean room;

    private ArrayList<AirBean> airInfos;

    public RoomBean getRoom() {
        return room;
    }

    public void setRoom(RoomBean room) {
        this.room = room;
    }

    public ArrayList<AirBean> getAirInfos() {
        return airInfos;
    }

    public void setAirInfos(ArrayList<AirBean> airInfos) {
        this.airInfos = airInfos;
    }

}
