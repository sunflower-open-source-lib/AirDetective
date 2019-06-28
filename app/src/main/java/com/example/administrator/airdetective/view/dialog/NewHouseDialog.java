package com.example.administrator.airdetective.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.airdetective.R;
import com.example.administrator.airdetective.model.RoomInfoBean;
import com.example.administrator.airdetective.view.fragment.fragment_new_house.MsgNewHouse;

import android.os.Handler;

import static android.support.constraint.Constraints.TAG;

/**
 * 创建房间与设备的输入的dialog
 */
public class NewHouseDialog extends Dialog implements MsgNewHouse{

    private EditText etRoomName;
    private EditText etMacid;
    private TextView tvCancel;
    private TextView tvFinish;
    private Context context;

    private RoomInfoBean roomInfoBean;

    private Handler handler;

    private final int ROOMID;

    public NewHouseDialog(Context context, Handler handler, int ROOMID) {
        super ( context, R.style.DialogTheme );
        this.context = context;
        this.handler = handler;
        this.ROOMID = ROOMID;
        setContentView ( R.layout.dialog_layout );


        Window window = getWindow ();

        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes ();

        params.gravity = Gravity.CENTER;

        window.setAttributes ( params );

        etRoomName = findViewById ( R.id.et_room_name );
        etMacid = findViewById ( R.id.et_macid );
        tvFinish = findViewById ( R.id.tv_finish );
        tvCancel = findViewById ( R.id.tv_cancel );
        tvCancel.setClickable ( true );
        tvFinish.setClickable ( true );

        onClicked ();
    }


    /**
     * 按钮监听事件
     */
    private void onClicked() {
        tvCancel.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Log.i ( TAG, "onViewClicked: click cancel" );
                hide ();
            }
        } );

        tvFinish.setOnClickListener ( new View.OnClickListener (){
            @Override
            public void onClick(View view) {
                String tempRoomName = etRoomName.getText ().toString ();
                String tempMacId = etMacid.getText ().toString ();
                if (tempRoomName.equals ( "" ) || tempMacId.equals ( "" )) {
                    Toast.makeText ( context, "房间名称或设备id不能为空", Toast.LENGTH_SHORT ).show ();
                } else {
                    setRoomInfo ( tempRoomName, tempMacId );
                    hide ();
                }
            }
        } );
    }

    private void setRoomInfo(String roomName, String macId) {
        roomInfoBean = new RoomInfoBean ();
        roomInfoBean.setRoomName ( roomName );
        roomInfoBean.setMacId ( macId );
        roomInfoBean.setRoomId ( ROOMID );

        Message msg = new Message ();
        msg.what = ROOMID;
        msg.obj = roomInfoBean;
        handler.sendMessage ( msg );
    }

    public RoomInfoBean getRoomInfo() {
        if (roomInfoBean != null) {
            return roomInfoBean;
        }
        return null;
    }
}
