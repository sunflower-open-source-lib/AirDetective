package com.example.administrator.airdetective.util.request;

import android.os.Handler;
import android.util.Log;

import okhttp3.Cookie;
import okhttp3.RequestBody;

public class AddRoomRequest {

    private static final String TAG = "AddRoomRequest";
    private static final String KEY = "addRoom";

    public AddRoomRequest(RequestBody requestBody, Handler handler, String cookie){
        Log.i ( TAG, "开始注册请求！" );
        new HttpRequest (requestBody, KEY, handler, cookie);
    }

}
