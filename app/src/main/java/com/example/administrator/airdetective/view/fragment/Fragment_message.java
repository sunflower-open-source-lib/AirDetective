package com.example.administrator.airdetective.view.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.airdetective.R;
import com.example.administrator.airdetective.model.AdminInfoBean;
import com.example.administrator.airdetective.view.Activity.LoginActivity;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Fragment_message extends Fragment {

    @BindView(R.id.telephone)
    TextView telephone;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.bt_sl)
    Button btSl;
    Unbinder unbinder;
    private View view;
    private boolean root = true;
    private AdminInfoBean admin = AdminInfoBean.getAdminInfoBean ();

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        view = inflater.inflate ( R.layout.fragment_message, null );
        unbinder = ButterKnife.bind ( this, view );
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceStare) {

        super.onActivityCreated ( savedInstanceStare );
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        unbinder.unbind ();
    }

    private void initView(){
        String tel = admin.getTel ();
        root = !tel.equals ( "未登录" );

        btSl = view.findViewById ( R.id.bt_sl );
        if (root){
            btSl.setText ( "注销" );
        }else {
            btSl.setText ( "登录" );
        }
    }

    @OnClick(R.id.bt_sl)
    public void onViewClicked(View view) {
        switch (view.getId ()){
            case R.id.bt_sl:
                if (root){
                    AlertDialog.Builder dialog = new AlertDialog.Builder ( Objects.requireNonNull ( getActivity () ) );
                    dialog.setTitle ( "注销" );
                    dialog.setMessage ( "是否注销该用户" );
                    dialog.setCancelable ( false );
                    dialog.setPositiveButton ( "OK", new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            root = false;
                            btSl.setText ( "登录" );
                            admin.clearAdminInfoBean ();
                        }
                    } );
                    dialog.setNegativeButton ( "Cancel", new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText ( getActivity (), "点击取消", Toast.LENGTH_SHORT )
                                    .show ();
                        }
                    } );
                    dialog.show ();
                    break;
                }else {
                    root = true;
                    btSl.setText ( "注销" );
                    Toast.makeText ( getActivity (), "登录成功", Toast.LENGTH_SHORT )
                            .show ();
                    Intent loginIntent = new Intent ( getActivity (), LoginActivity.class );
                    startActivity ( loginIntent );
                    break;
                }
        }
    }
}
