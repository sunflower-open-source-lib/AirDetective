package com.example.administrator.airdetective.view.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.airdetective.R;
import com.example.administrator.airdetective.util.request.RegisterRequest;
import com.example.administrator.airdetective.util.tool.MyHandlerMsg;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * @author LG32
 * 注册功能
 */
@SuppressLint("Registered")
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText password;
    private EditText confirm_password;
    private EditText username;

    private RegisterHandler handler = new RegisterHandler ();

    private Map<String, String> information = new HashMap<> ();

    private static final int INFORMATION_OK = 0;//输入的信息ok
    private static final int INFORMATION_NULL = 1;//输入的信息有空的
    private static final int PASSWORD_MISS = 2;//输入的密码有误（两次输入不相同）
    private static final String TAG = "注册页面";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );
        initView ();
    }

    private void initView() {
        Toolbar toolbar = findViewById ( R.id.toolbar );
        password = findViewById ( R.id.password );
        confirm_password = findViewById ( R.id.confirm_password );
        username = findViewById ( R.id.username );
        Button finish = findViewById ( R.id.finish );

        setSupportActionBar ( toolbar );
        finish.setOnClickListener ( this );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.finish:
                getEditTextInfo ();
                switch (judgeInformation ( information )) {
                    case INFORMATION_NULL:
                        Toast.makeText ( this, "请输入完整的信息", Toast.LENGTH_SHORT ).show ();
                        break;
                    case PASSWORD_MISS:
                        Toast.makeText ( this, "两次输入的密码不相同", Toast.LENGTH_SHORT ).show ();
                        break;
                    case INFORMATION_OK:
                        Log.i ( TAG, "信息正确" );
                        new RegisterRequest ( requestBodyBuild (), handler );
                        break;
                }
                break;
        }
    }

    private RequestBody requestBodyBuild() {
        return new FormBody.Builder ()
                .add ( "password", information.get ( "password" ) )
                .add ( "username", information.get ( "username" ) )
                .build ();
    }

    private void getEditTextInfo() {
        information.put ( "password", password.getText ().toString () );
        information.put ( "confirm_password", confirm_password.getText ().toString () );
        information.put ( "username", username.getText ().toString () );
    }

    private int judgeInformation(Map<String, String> information) {
        for (Map.Entry<String, String> entry : information.entrySet ()) {
            if (entry.getValue () == null) {
                return INFORMATION_NULL;
            }
        }
        if (information.get ( "password" ).equals ( information.get ( "confirm_password" ) )) {
            return INFORMATION_OK;
        } else {
            return PASSWORD_MISS;
        }
    }


    @SuppressLint("HandlerLeak")
    public class RegisterHandler extends Handler implements MyHandlerMsg {


        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_FAIL:
                    Toast.makeText ( RegisterActivity.this, "网络请求失败", Toast.LENGTH_SHORT )
                            .show ();

                    break;

                case REGISTER_SUCCESS:
                    resultJudge ( getCode ( (String) msg.obj ) );
                    break;
            }
        }
    }

    /**
     * 判断是否注册成功
     * @param code 服务器返回的code
     */
    private void resultJudge(String code) {
        Log.i ( "loginJudge: ", code );
        switch (code) {

            case "0":
                Toast.makeText ( RegisterActivity.this, "手机号已被注册", Toast.LENGTH_SHORT )
                        .show ();
                break;

            case "1":
                Toast.makeText ( RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT )
                        .show ();
                Intent loginIntent = new Intent ();
                loginIntent.setClass ( RegisterActivity.this, LoginActivity.class );
                startActivity ( loginIntent );
                finish ();
                break;
        }
    }

    private String getCode(String json) {
        try {
            JsonObject jsonObject = (JsonObject) new JsonParser ().parse ( json );
            return jsonObject.get ( "code" ).getAsString ();
        } catch (Exception e) {
            e.printStackTrace ();
            return "404";
        }
    }
}
