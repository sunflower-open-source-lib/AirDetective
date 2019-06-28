package com.example.administrator.airdetective.view.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.airdetective.R;
import com.example.administrator.airdetective.controller.FragmentController_newHouse;
import com.example.administrator.airdetective.model.RoomInfoBean;
import com.example.administrator.airdetective.model.familymodel.FamilyBean;
import com.example.administrator.airdetective.model.familymodel.FamilyList;
import com.example.administrator.airdetective.util.request.AddRoomRequest;
import com.example.administrator.airdetective.util.tool.MyHandlerMsg;
import com.example.administrator.airdetective.view.fragment.fragment_new_house.MsgNewHouse;
import com.example.administrator.airdetective.view.fragment.fragment_new_house.NewHouseFragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.RequestBody;

@SuppressLint("Registered")
public class NewHouseActivity extends AppCompatActivity implements View.OnClickListener, MsgNewHouse {

    @BindView(R.id.finish)
    Button finish;
    private Spinner sp1;
    private FragmentController_newHouse mController;
    private static String TAG = "NewHouseActivity";
    private NewHouseFragment fragment;
    private Handler handler = new MyHandler ();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_new_home );
        ButterKnife.bind ( this );
        Toolbar toolbar = findViewById ( R.id.activity_home_toolbar );
        setSupportActionBar ( toolbar );
        initView ();
    }

    private void initView() {
        mController = FragmentController_newHouse.getInstance ( this, R.id.fragment_new_house_container );
        sp1 = (Spinner) findViewById ( R.id.spinnerr );
        initSpinner ();
    }

    /**
     * 初始化spinner组件
     */
    private void initSpinner() {
        // 声明一个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (
                this, android.R.layout.simple_spinner_item, setSpinnerData () );
        sp1.setAdapter ( adapter );
        sp1.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 在选中之后触发
                Log.d ( "MainActivity onItemSelected", "onItemSelected: position is" + position );
                fragment = mController.showFragment ( position );
                Toast.makeText ( NewHouseActivity.this,
                        parent.getItemAtPosition ( position ).toString (),
                        Toast.LENGTH_SHORT ).show ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText ( NewHouseActivity.this,
                        "nothing selected",
                        Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    private List<String> setSpinnerData() {
        // 数据源
        List<String> dataList = new ArrayList<String> ();
        dataList.add ( "房屋样式1" );
        dataList.add ( "房屋样式2" );
        dataList.add ( "房屋样式3" );
        dataList.add ( "房屋样式4" );
        return dataList;
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onStop() {
        super.onStop ();
        mController = null;
        FragmentController_newHouse.destoryController ();
        Log.i ( TAG, "onStop: is running" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();
        if (mController != null) {
            mController = null;
            FragmentController_newHouse.destoryController ();
        }
        Log.i ( TAG, "onDestroy: is running" );
    }

    @OnClick(R.id.finish)
    public void onViewClicked(View view) {
        switch (view.getId ()) {
            case R.id.finish:
                getNewRoomInfo ();
                break;
        }
    }

    private void getNewRoomInfo() {
        if (fragment.submitInfo () == null) {
            Toast.makeText ( this, "房间信息填写不完整", Toast.LENGTH_SHORT )
                    .show ();
        } else {
            Toast.makeText ( this, "房间信息完整", Toast.LENGTH_SHORT )
                    .show ();

            FamilyList familyList = FamilyList.getFamilyList ();
            ArrayList<FamilyBean> familyBeans = familyList.getList ();
            RoomInfoBean roomInfoBean[] = fragment.submitInfo ();

            if (familyBeans.size () != 0) {
                if (!familyBeans.get ( 0 ).getFid ().equals ( "" )) {
                    for (RoomInfoBean aRoomInfoBean : roomInfoBean) {
                        RequestBody requestBody = new FormBody.Builder ()
                                .add ( "roomName", aRoomInfoBean.getRoomName () )
                                .add ( "deviceId", aRoomInfoBean.getMacId () )
                                .add ( "fid", familyBeans.get ( 1 ).getFid () )
                                .build ();

                        new AddRoomRequest ( requestBody, handler, getCookie () );
                    }
                }
            }
        }
    }

    private String getCookie() {
        SharedPreferences sharedPreferences = getSharedPreferences ( "cookie",
                MODE_PRIVATE );
        return sharedPreferences.getString ( "cookie", "" );
    }

    @SuppressLint("HandlerLeak")
    private class MyHandler extends Handler implements MyHandlerMsg {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_FAIL:
                    Toast.makeText ( NewHouseActivity.this, "网络请求失败", Toast.LENGTH_SHORT )
                            .show ();
                    break;

                case ADDROOM_SUCCESS:
                    if (judge ( msg.obj.toString () )) {
                        Toast.makeText ( NewHouseActivity.this, "新建房间成功", Toast.LENGTH_SHORT )
                                .show ();
                        new Thread (  ){
                            @Override
                            public void run() {
                                super.run ();
                                try {
                                    sleep ( 2000 );
                                } catch (InterruptedException e) {
                                    e.printStackTrace ();
                                }
                                Intent intent = new Intent ( NewHouseActivity.this, MainActivity.class );
                                startActivity ( intent );
                            }
                        };
                    } else
                        Toast.makeText ( NewHouseActivity.this, "新建房间失败", Toast.LENGTH_SHORT )
                                .show ();
                    break;
            }
        }
    }

    private boolean judge(String json) {

        Gson gson = new Gson ();

        try {
            JsonObject jsonObject = (JsonObject) new JsonParser ().parse ( json );
            if ("1".equals ( jsonObject.get ( "code" ).getAsString () )) {
                return true;
            }
        } catch (Exception ignored) {
            Log.i ( TAG, "getFamilyInfo: " + ignored );
            return false;
        }
        return false;
    }
}
