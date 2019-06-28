package com.example.administrator.airdetective.view.fragment.fragment_new_house;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.airdetective.R;
import com.example.administrator.airdetective.model.RoomInfoBean;
import com.example.administrator.airdetective.view.dialog.NewHouseDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Fragment_newHouse_style1 extends NewHouseFragment implements MsgNewHouse{

    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.btn_room1)
    Button btnRoom1;
    @BindView(R.id.btn_room2)
    Button btnRoom2;
    @BindView(R.id.btn_room3)
    Button btnRoom3;
    @BindView(R.id.btn_room4)
    Button btnRoom4;
    @BindView(R.id.btn_room5)
    Button btnRoom5;
    Unbinder unbinder;

    private RoomInfoBean []roomList = new RoomInfoBean[5];

    private Handler handler = new MyHandler ();
    private NewHouseDialog newHouseDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate ( R.layout.fragment_newhome_style1, container, false );

        unbinder = ButterKnife.bind ( this, v );
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        unbinder.unbind ();
    }

    @OnClick({R.id.btn_room1, R.id.btn_room2, R.id.btn_room3, R.id.btn_room4, R.id.btn_room5})
    public void onViewClicked(View view) {



        switch (view.getId ()) {
            case R.id.btn_room1:
                addNewHouseInfo ( ROOM1 );
                break;

            case R.id.btn_room2:
                addNewHouseInfo ( ROOM2 );
                break;

            case R.id.btn_room3:
                addNewHouseInfo ( ROOM3 );
                break;

            case R.id.btn_room4:
                addNewHouseInfo ( ROOM4 );
                break;

            case R.id.btn_room5:
                addNewHouseInfo ( ROOM5 );
                break;
        }
    }

    public RoomInfoBean[] submitInfo() {
        for (RoomInfoBean aRoomList : roomList) {
            if (aRoomList.getMacId ().equals ( "" ))
                return null;
        }
        return roomList;
    }

    private void addNewHouseInfo(int roomId){
        newHouseDialog = new NewHouseDialog ( getContext (), handler, roomId );
        newHouseDialog.setCancelable ( true );
        newHouseDialog.show ();
    }

    @SuppressLint("HandlerLeak")
    public class MyHandler extends Handler {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ROOM1:
                    RoomInfoBean roomInfoBean1 = (RoomInfoBean) msg.obj;
                    String btn1_info = "macid:" + roomInfoBean1.getMacId () + "\n room name:" + roomInfoBean1.getRoomName ();
                    btnRoom1.setText ( btn1_info );
                    roomList[0] = roomInfoBean1 ;
                    break;

                case ROOM2:
                    RoomInfoBean roomInfoBean2 = (RoomInfoBean) msg.obj;
                    String btn2_info = "macid:" + roomInfoBean2.getMacId () + "\n room name:" + roomInfoBean2.getRoomName ();
                    btnRoom2.setText ( btn2_info );
                    roomList[1] = roomInfoBean2 ;
                    break;

                case ROOM3:
                    RoomInfoBean roomInfoBean3 = (RoomInfoBean) msg.obj;
                    String btn3_info = "macid:" + roomInfoBean3.getMacId () + "\n room name:" + roomInfoBean3.getRoomName ();
                    btnRoom3.setText ( btn3_info );
                    roomList[2] = roomInfoBean3 ;
                    break;

                case ROOM4:
                    RoomInfoBean roomInfoBean4 = (RoomInfoBean) msg.obj;
                    String btn4_info = "macid:" + roomInfoBean4.getMacId () + "\n room name:" + roomInfoBean4.getRoomName ();
                    btnRoom4.setText ( btn4_info );
                    roomList[3] = roomInfoBean4 ;
                    break;

                case ROOM5:
                    RoomInfoBean roomInfoBean5 = (RoomInfoBean) msg.obj;
                    String btn5_info = "macid:" + roomInfoBean5.getMacId () + "\n room name:" + roomInfoBean5.getRoomName ();
                    btnRoom5.setText ( btn5_info );
                    roomList[4] = roomInfoBean5 ;
                    break;
            }
        }
    }
}
