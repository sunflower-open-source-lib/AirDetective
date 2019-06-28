package com.example.administrator.airdetective.model.airinfomodel;


import java.util.ArrayList;

public class AirList {

    private ArrayList<AirRoomListBean> list;

    private static AirList airList;

    private AirList(){}

    public static AirList getAirList(){
        if (airList == null ){
            airList = new AirList ();
            return airList;
        }
        else
            return airList;
    }

    public ArrayList<AirRoomListBean> getList() {
        return list;
    }

    public void setList(ArrayList<AirRoomListBean> list) {
        this.list = list;
    }

    public int size(){return list.size ();}

    public void clearList(){
        list.clear ();
    }
}
