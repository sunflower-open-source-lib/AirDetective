package com.example.administrator.airdetective.util.request;

import android.os.Handler;
import android.util.Log;

import okhttp3.RequestBody;

public class GetDeviceInfoRq {

    private static final String TAG = "GetDeviceInfoRq";
    private static final String KEY = "getDeviceDataAirInfo";

    public GetDeviceInfoRq(RequestBody requestBody, Handler handler, String cookie) {
        Log.i ( TAG, "开始某设备某天空气质量信息请求！" );

        new HttpRequest ( requestBody, KEY, handler, cookie );
    }
}
