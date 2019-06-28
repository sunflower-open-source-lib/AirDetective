package com.example.administrator.airdetective.server;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.administrator.airdetective.util.request.HttpRequest;

@SuppressLint("Registered")
public class HttpServer extends Service {

    private HttpRequest httpRequest = new HttpRequest ();

    private IBinder iBinder = new HttpBinder();

    public class HttpBinder extends Binder{

        public HttpServer getHttpService(){
            return HttpServer.this;
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate ();
    }

    @Override
    public void onDestroy() {
        super.onDestroy ();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind ( intent );
    }
}
