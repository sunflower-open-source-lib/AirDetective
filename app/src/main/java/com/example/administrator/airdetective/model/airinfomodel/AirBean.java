package com.example.administrator.airdetective.model.airinfomodel;

import java.io.Serializable;

public class AirBean implements Serializable{

    private int id;

    private int deviceId;

    private String date;

    private float temperature;

    private float humidity;

    private float pm25;

    private float tvoc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getPm25() {
        return pm25;
    }

    public void setPm25(float pm25) {
        this.pm25 = pm25;
    }

    public float getTvoc() {
        return tvoc;
    }

    public void setTvoc(float tvoc) {
        this.tvoc = tvoc;
    }
}
