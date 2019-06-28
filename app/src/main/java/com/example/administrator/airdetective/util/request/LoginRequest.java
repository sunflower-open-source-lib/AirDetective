package com.example.administrator.airdetective.util.request;

import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

import okhttp3.RequestBody;

//18846833759/123456
public class LoginRequest{
    private static final String TAG = "LoginRequest";
    private static final String KEY = "login";

    public LoginRequest(RequestBody requestBody, Handler loginHandler, SharedPreferences sharedPreferences ){
        Log.i ( TAG, "开始登陆请求！" );
        new HttpRequest (requestBody, KEY, loginHandler, sharedPreferences);

    }

}
