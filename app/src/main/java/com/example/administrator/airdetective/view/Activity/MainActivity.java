package com.example.administrator.airdetective.view.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.airdetective.model.airinfomodel.AirBean;
import com.example.administrator.airdetective.model.airinfomodel.AirList;
import com.example.administrator.airdetective.model.airinfomodel.AirRoomListBean;
import com.example.administrator.airdetective.model.airinfomodel.RoomBean;
import com.example.administrator.airdetective.model.familymodel.FamilyBean;
import com.example.administrator.airdetective.model.familymodel.FamilyList;
import com.example.administrator.airdetective.util.request.GetFamilyInfoRq;
import com.example.administrator.airdetective.util.request.GetTimingAirInfoRq;
import com.example.administrator.airdetective.util.tool.MyHandlerMsg;
import com.example.administrator.airdetective.view.adapter.BottomViewAdapter;
import com.example.administrator.airdetective.view.fragment.BottomNavigationViewHelper;
import com.example.administrator.airdetective.R;
import com.example.administrator.airdetective.model.AdminInfoBean;
import com.example.administrator.airdetective.view.fragment.Fragment_home;
import com.example.administrator.airdetective.view.fragment.Fragment_message;
import com.example.administrator.airdetective.view.fragment.Fragment_statistics;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private MenuItem menuItem;
    private ViewPager viewPager;
    private BottomNavigationView navigation;
    private FloatingActionMenu mActionMenu;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private MainHandler mainHandler = new MainHandler ();
    private FamilyList familyList = FamilyList.getFamilyList ();
    private AirList airList = AirList.getAirList ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        viewPager = findViewById ( R.id.viewPager );
        navigation = findViewById ( R.id.navigation );
        BottomNavigationViewHelper.disableShiftMode ( navigation );
        navigation.setOnNavigationItemSelectedListener ( mOnNavigationItemSelectedListener );
        viewPager.addOnPageChangeListener ( addOnPageChangeListener );
        mDrawerLayout = findViewById ( R.id.drawer_layout );
        navigationView = findViewById ( R.id.nav_view );

        ActionBar actionBar = getSupportActionBar ();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled ( true );
            actionBar.setHomeAsUpIndicator ( R.drawable.ic_menu );
        }

        initView ();
        onNavViewClick ();
        initNavi ();

        initFamily ();
    }


    private void initView() {

        FloatingActionButton mItemLinearlayout = findViewById ( R.id.menu_item_linearlayout );
        FloatingActionButton mItemGridlayout = findViewById ( R.id.menu_item_gridlayout );
        mActionMenu = findViewById ( R.id.actionmenu );
        mActionMenu.setVisibility ( View.VISIBLE );
        mItemLinearlayout.setOnClickListener ( this );
        mItemGridlayout.setOnClickListener ( this );
    }

    private void initNavi() {
        TextView tv_telephoto = navigationView.
                getHeaderView ( 0 ).findViewById ( R.id.tv_telephone );
        TextView tv_username = navigationView.
                getHeaderView ( 0 ).findViewById ( R.id.tv_username );
        AdminInfoBean admin = AdminInfoBean.getAdminInfoBean ();
        tv_telephoto.setText ( admin.getTel () );
        tv_username.setText ( admin.getUser_name () );
    }

    private void initFamily() {

        RequestBody requestBody = new FormBody.Builder ()
                .build ();

        new GetFamilyInfoRq ( requestBody, mainHandler, getCookie () );

    }

    private void initAirInfo(String fid) {
        RequestBody requestBody = new FormBody.Builder ()
                .add ( "fid", fid )
//                .add ( "date", Objects.requireNonNull ( getTodayDate () ) )
//                .add ( "date", "2019-06-27" )
                .build ();
        new GetTimingAirInfoRq ( requestBody, mainHandler, getCookie () );
    }

    private String getCookie() {
        SharedPreferences sharedPreferences = getSharedPreferences ( "cookie",
                MODE_PRIVATE );
        return sharedPreferences.getString ( "cookie", "" );
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener () {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId ()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem ( 0 );
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem ( 1 );
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem ( 2 );
                    return true;
            }
            return false;
        }
    };

    private ViewPager.OnPageChangeListener addOnPageChangeListener =
            new ViewPager.OnPageChangeListener () {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (menuItem != null) {
                        menuItem.setChecked ( false );
                    } else {
                        navigation.getMenu ().getItem ( 0 ).setChecked ( false );
                    }
                    menuItem = navigation.getMenu ().getItem ( position );
                    menuItem.setChecked ( true );
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            };

    /**
     * 注册底端导航栏
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        List<Fragment> fragmentList = new ArrayList<> ();
        Fragment fragment_home = new Fragment_home ();
        Fragment fragment_statistics = new Fragment_statistics ();
        Fragment fragment_message = new Fragment_message ();
        fragmentList.add ( fragment_home );
        fragmentList.add ( fragment_statistics );
        fragmentList.add ( fragment_message );

        BottomViewAdapter adapter = new BottomViewAdapter ( getSupportFragmentManager (), fragmentList );

        viewPager.setAdapter ( adapter );
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer ( GravityCompat.START );
                break;
            default:
        }
        return true;
    }

    private void onNavViewClick() {
        navigationView.setNavigationItemSelectedListener ( new NavigationView.OnNavigationItemSelectedListener () {
            @SuppressLint("RtlHardcoded")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId ()) {
                    case R.id.nav_myInfo:
                        Toast.makeText ( MainActivity.this, "点击个人信息", Toast.LENGTH_SHORT )
                                .show ();
                        break;

                    case R.id.nav_checkNew:
                        Toast.makeText ( MainActivity.this, "点击检查更新", Toast.LENGTH_SHORT )
                                .show ();
                        break;

                    case R.id.nav_menu:
                        Toast.makeText ( MainActivity.this, "点击收藏", Toast.LENGTH_SHORT )
                                .show ();
                        break;

                    case R.id.nav_family:
                        Toast.makeText ( MainActivity.this, "点击个人信息", Toast.LENGTH_SHORT )
                                .show ();
                        break;
                }
                mDrawerLayout.closeDrawer ( Gravity.LEFT );
                return true;
            }
        } );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.menu_item_linearlayout:
                Toast.makeText ( MainActivity.this, "点击增加", Toast.LENGTH_SHORT )
                        .show ();
                Intent newIntent = new Intent ();
                newIntent.setClass ( this, NewHouseActivity.class );
                startActivity ( newIntent );
                break;
            case R.id.menu_item_gridlayout:
                Toast.makeText ( MainActivity.this, "点击删除", Toast.LENGTH_SHORT )
                        .show ();
                break;
        }
    }


    @SuppressLint("HandlerLeak")
    private class MainHandler extends Handler implements MyHandlerMsg {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_FAIL:
                    Toast.makeText ( MainActivity.this, "网络请求失败", Toast.LENGTH_SHORT )
                            .show ();
                    break;

                case GETTIMINGAIRINFO_SUCCESS:
                    Log.i ( TAG, "GETTIMINGAIRINFO_SUCCESS handleMessage: 网络请求成功" );
                    Log.i ( TAG, "handleMessage: " + msg.obj.toString () );
                    getAirInfo ( msg.obj.toString () );
                    setupViewPager ( viewPager );
                    break;

                case GETFAMILYINFO_SUCCESS:
                    Log.i ( TAG, "handleMessage: 网络请求成功" );
                    getFamilyInfo ( msg.obj.toString () );
                    List<FamilyBean> familyBeans = familyList.getList ();
                    if (!familyBeans.get ( 0 ).getFid ().equals ( "" ))
                        initAirInfo ( familyBeans.get ( 0 ).getFid () );
                    else
                        Toast.makeText ( MainActivity.this, "无数据", Toast.LENGTH_SHORT )
                                .show ();
                    break;
            }
        }
    }


    private void judgement(String code) {
        switch (code) {
            case "0":
//                getFoodList ();
                break;
            case "1":
                Toast.makeText ( MainActivity.this, "设备数据为空", Toast.LENGTH_SHORT )
                        .show ();
                break;

            case "2":
//                initData ();
                break;

            case "3":
//                Toast.makeText ( MainActivity.this, "食物列表为空", Toast.LENGTH_SHORT )
//                        .show ();
                break;
        }
    }


    /**
     * 得到家庭组信息
     *
     * @param json 返回的未被解析的json格式数据
     */
    private void getFamilyInfo(String json) {

        Gson gson = new Gson ();

            try {
                JsonObject jsonObject = (JsonObject) new JsonParser ().parse ( json );
                if ("1".equals ( jsonObject.get ( "code" ).getAsString () )) {
                    JsonArray jsonArray = jsonObject.getAsJsonArray ( "data" );

                    ArrayList<FamilyBean> familyBeans = new ArrayList<> ();

                    for (JsonElement food : jsonArray) {
                        FamilyBean familyBean = gson.fromJson ( food,
                                new TypeToken<FamilyBean> () {
                                }.getType () );
                        familyBeans.add ( familyBean );

                        Log.i ( TAG, "getFamily: " + familyBean.getFamilyName () );
                    }

                    familyList.setList ( familyBeans );
                }
            } catch (JsonSyntaxException e) {
                e.printStackTrace ();
        }
    }

    private void getAirInfo(String json){

        Gson gson = new Gson ();

        try {
            JsonObject jsonObject = (JsonObject) new JsonParser ().parse ( json );
            if ("1".equals ( jsonObject.get ( "code" ).getAsString () )) {
                JsonArray jsonArray = jsonObject.getAsJsonArray ( "data" );

                Log.i ( TAG, "getAirInfo: " + jsonArray.toString () );

                ArrayList<AirRoomListBean> airRoomListBeans = new ArrayList<> (  );

                for (JsonElement air : jsonArray) {
                    AirRoomListBean airRoomListBean = new AirRoomListBean ();
                    JsonObject secondObject  = (JsonObject) new JsonParser ().parse ( String.valueOf ( air ) );
                    RoomBean roomBean = gson.fromJson ( secondObject .get ( "room" ), new TypeToken<RoomBean> (){
                    }.getType () );

                    airRoomListBean.setRoom ( roomBean );
//                    roomBeans.add ( roomBean );

                    JsonArray secondJson = secondObject.getAsJsonArray ( "airInfos" );

                    ArrayList<AirBean> airBeans = new ArrayList<> (  );

                    for (JsonElement second : secondJson){
                        AirBean airBean = gson.fromJson ( second,
                                new TypeToken<AirBean> () {
                                }.getType () );
                        airBeans.add ( airBean );

                        Log.i ( TAG, "getAirInfo: " + airBean.getDeviceId () );
                    }
                    airRoomListBean.setAirInfos ( airBeans );
                    airRoomListBeans.add ( airRoomListBean );
                    Log.i ( TAG, "getRoomName: " + roomBean.getRoomName () );
                }
                airList.setList ( airRoomListBeans );
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace ();
        }
    }

    private String getTodayDate(){

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd" );
        try {
            String date = format.format ( new Date() );
            Log.i (TAG, "getTodayDate" + date );
            return date;
        } catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
    }
}
