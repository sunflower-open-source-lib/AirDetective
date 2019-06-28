package com.example.administrator.airdetective.view.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.airdetective.R;
import com.example.administrator.airdetective.model.airinfomodel.AirBean;
import com.example.administrator.airdetective.model.airinfomodel.AirRoomListBean;
import com.example.administrator.airdetective.util.request.GetDeviceInfoRq;
import com.example.administrator.airdetective.util.tool.MyHandlerMsg;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jn.chart.charts.LineChart;
import com.jn.chart.data.Entry;
import com.jn.chart.manager.LineChartManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.RequestBody;

@SuppressLint("Registered")
public class ShowAirInfoActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private LineChart mLineChart1;
    private Spinner sp1;
    private Spinner sp2;
    List<String> xVals;
    private static String TAG = "ShowAirInfoActivity";
    private MyHandler myHandler = new MyHandler ();
    private ArrayList<AirBean> airBeans = new ArrayList<> (  );
    private AirRoomListBean airRoomListBean;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_showairinfo );
        ButterKnife.bind ( this );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        initView ();
        initValue ();
    }

    private void initValue() {
        Intent intent = getIntent ();
        airRoomListBean = (AirRoomListBean) intent.getSerializableExtra ( "airRoomListBeans" );
        if (airRoomListBean == null)
            Log.i ( TAG, "initValue: airRoomListBean is null" );
        else {
            int deviceId = airRoomListBean.getAirInfos ().get ( 0 ).getDeviceId ();
            Log.i ( TAG, "initValue: DeviceId" + deviceId );
            Log.i ( TAG, "initValue: fid" + airRoomListBean.getRoom ().getFid () );
            initAirInfo ( String.valueOf ( deviceId ), getTodayDate () );
            tvTitle.setText ( "一天内温度情况" );
        }
    }

    private void initView() {
        mLineChart1 = findViewById ( R.id.lineChart );
        //设置图表的描述
        mLineChart1.setDescription ( "时间" );
        mLineChart1.setTouchEnabled ( true );
        mLineChart1.setScaleEnabled ( true );

        sp1 = findViewById ( R.id.spinner_1 );
        sp2 = findViewById ( R.id.spinner_2 );
        initSpinner1 ( sp1 );
        initSpinner2 ( sp2 );
    }

    /**
     * 初始化spinner组件
     */
    private void initSpinner1(Spinner sp1) {
        // 声明一个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (
                Objects.requireNonNull ( this ), android.R.layout.simple_spinner_item, setSpinnerData ( 1 ) );
        sp1.setAdapter ( adapter );
        sp1.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 在选中之后触发
                Log.d ( "ShowAirInfoActivity onItemSelected", "onItemSelected: position is" + position
                        + " id is " + id );

                ArrayList<Float> yValue = new ArrayList<> ();

                switch (position) {
                    case 0:
                        tvTitle.setText ( "一天内温度情况" );
                        for (int i = 0; i <= 23; i++) {
                            if (15 * i < airBeans.size ())
                                yValue.add ( (float) airBeans.get ( 15 * i ).getTemperature () );
                            else
                                yValue.add ( 0f );
                        }
                        initLineChart ( yValue, 0 );
                        break;

                    case 1:
                        tvTitle.setText ( "一天内湿度情况" );
                        for (int i = 0; i <= 23; i++) {
                            if (15 * i < airBeans.size ())
                                yValue.add ( (float) airBeans.get ( 15 * i ).getHumidity () );
                            else
                                yValue.add ( 0f );
                        }
                        initLineChart ( yValue, 0 );
                        break;

                    case 2:
                        tvTitle.setText ( "一天内pm2.5情况" );
                        for (int i = 0; i <= 23; i++) {
                            if (15 * i < airBeans.size ())
                                yValue.add ( (float) airBeans.get ( 15 * i ).getPm25 () );
                            else
                                yValue.add ( 0f );
                        }
                        initLineChart ( yValue, 0 );
                        break;

                    case 3:
                        tvTitle.setText ( "一天内tvoc情况" );
                        for (int i = 0; i <= 23; i++) {
                            if (15 * i < airBeans.size ())
                                yValue.add ( (float) airBeans.get ( 15 * i ).getTvoc () );
                            else
                                yValue.add ( 0f );
                        }
                        initLineChart ( yValue, 0 );
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
    }

    private void initSpinner2(Spinner sp1) {
        // 声明一个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (
                Objects.requireNonNull ( this ), android.R.layout.simple_spinner_item, setSpinnerData ( 2 ) );
        sp1.setAdapter ( adapter );
        sp1.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 在选中之后触发
                Log.d ( "ShowAirInfoActivity onItemSelected", "onItemSelected: position is" + position
                        + " id is " + id );
                if (airRoomListBean == null)
                    Log.i ( TAG, "initValue: airRoomListBean is null" );
                else {
                    int deviceId = airRoomListBean.getAirInfos ().get ( 0 ).getDeviceId ();
                    switch (position) {
                        case 0:
                            initAirInfo ( String.valueOf ( deviceId ), "2019-06-27" );
                            break;
                        case 1:
                            initAirInfo ( String.valueOf ( deviceId ), "2019-06-26" );
                            break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        } );
    }

    private List<String> setSpinnerData(int num) {
        // 数据源
        xVals = new ArrayList<String> ();
        if (num == 1) {
            xVals.add ( "温度" );
            xVals.add ( "湿度" );
            xVals.add ( "PM2.5" );
            xVals.add ( "tvoc" );
        }

        if (num == 2) {
            xVals.add ( "2019-6-27" );
            xVals.add ( "2019-6-26" );
        }
        return xVals;
    }

    private void initLineChart(ArrayList<Float> yValue, int position) {

        ArrayList<String> xValues = new ArrayList<> ();
        for (int i = 0; i < 24; i++) {
            xValues.add ( String.valueOf ( i ) );
        }

        ArrayList<Entry> yValues = new ArrayList<> ();

        for (int i = 0; i < yValue.size (); i++) {
            yValues.add ( new Entry ( yValue.get ( i ), i + 1 ) );
        }

        LineChartManager.setLineName ( xVals.get ( position ) );
        LineChartManager.initSingleLineChart ( this, mLineChart1, xValues, yValues );
    }

    @SuppressLint("HandlerLeak")
    private class MyHandler extends Handler implements MyHandlerMsg {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage ( msg );

            switch (msg.what) {
                case GETDEVICE_SUCCESS:
                    Log.i ( TAG, "handleMessage: " + msg.obj.toString () );
                    getAirInfo ( msg.obj.toString () );

                    ArrayList<Float> yValue = new ArrayList<> ();
                    for (int i = 0; i <= 23; i++) {
//                yValue.add ( (float)(1+Math.random()*16 ));
                        if (15 * i < airBeans.size ())
                            yValue.add ( (float) airBeans.get ( 15 * i ).getTemperature () );
                        else
                            yValue.add ( 0f );
                    }

                    initLineChart ( yValue, 0 );
                    break;
            }
        }
    }

    private void initAirInfo(String deviceId, String date) {
        Log.i ( TAG, "initAirInfo: " + date );
        RequestBody requestBody = new FormBody.Builder ()
                .add ( "deviceId", deviceId )
                .add ( "date", date )
                .build ();
        new GetDeviceInfoRq ( requestBody, myHandler, getCookie () );
    }

    private String getCookie() {
        SharedPreferences sharedPreferences = getSharedPreferences ( "cookie",
                MODE_PRIVATE );
        return sharedPreferences.getString ( "cookie", "" );
    }

    private String getTodayDate() {

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd" );
        try {
            String date = format.format ( new Date () );
            Log.i ( TAG, "getTodayDate" + date );
            return date;
        } catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }

    private void getAirInfo(String json) {

        Gson gson = new Gson ();

        try {
            JsonObject jsonObject = (JsonObject) new JsonParser ().parse ( json );
            if ("1".equals ( jsonObject.get ( "code" ).getAsString () )) {
                JsonArray jsonArray = jsonObject.getAsJsonArray ( "data" );

                Log.i ( TAG, "getAirInfo: " + jsonArray.toString () );
                airBeans = new ArrayList<> ();
                for (JsonElement air : jsonArray) {
                    AirBean airBean = gson.fromJson ( air,
                            new TypeToken<AirBean> () {
                            }.getType () );
                    airBeans.add ( airBean );
                    Log.i ( TAG, "getAirInfo: " + airBean.getDeviceId () );
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace ();
        }
    }
}
