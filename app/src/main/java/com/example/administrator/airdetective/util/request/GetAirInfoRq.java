package com.example.administrator.airdetective.util.request;

import android.os.Handler;
import android.util.Log;

import com.example.administrator.airdetective.view.Activity.MainActivity;

import okhttp3.Request;
import okhttp3.RequestBody;

public class GetAirInfoRq {

    private static final String TAG = "GetAirInfoRq";
    private static final String KEY = "getAirInfo";

    public GetAirInfoRq(RequestBody requestBody, Handler handler, String cookie) {
        Log.i ( TAG, "开始空气质量信息请求！" );

        new HttpRequest ( requestBody, KEY, handler, cookie );
    }
}
