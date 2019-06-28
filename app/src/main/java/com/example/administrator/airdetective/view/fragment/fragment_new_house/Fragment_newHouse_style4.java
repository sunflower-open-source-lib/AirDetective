package com.example.administrator.airdetective.view.fragment.fragment_new_house;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.airdetective.R;
import com.example.administrator.airdetective.model.RoomInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Fragment_newHouse_style4 extends NewHouseFragment {

    @BindView(R.id.btn_room1)
    Button btnRoom1;
    @BindView(R.id.btn_room2)
    Button btnRoom2;
    @BindView(R.id.btn_room3)
    Button btnRoom3;
    Unbinder unbinder;
    private static String TAG = "Fragment_newHouse_style4";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate ( R.layout.fragment_newhome_style4, container, false );

        unbinder = ButterKnife.bind ( this, v );
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        unbinder.unbind ();
    }

    @OnClick({R.id.btn_room1, R.id.btn_room2, R.id.btn_room3})
    public void onViewClicked(View view) {
        switch (view.getId ()) {
            case R.id.btn_room1:
                Toast.makeText ( getActivity (), "点击按钮1", Toast.LENGTH_SHORT )
                        .show ();
                break;

            case R.id.btn_room2:
                break;

            case R.id.btn_room3:
                break;
        }
    }

    @Override
    public RoomInfoBean[] submitInfo() {
        return new RoomInfoBean[0];
    }
}
