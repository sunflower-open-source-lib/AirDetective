package com.example.administrator.airdetective.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.administrator.airdetective.view.fragment.fragment_room.Fragment_room_style1;
import com.example.administrator.airdetective.view.fragment.fragment_room.Fragment_room_style2;
import com.example.administrator.airdetective.view.fragment.fragment_room.Fragment_room_style3;
import com.example.administrator.airdetective.view.fragment.fragment_room.Fragment_room_style4;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class FragmentController {
    private int containerId;
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private static FragmentController controller;

    public static FragmentController getInstance(FragmentActivity activity, int containerId) {
        if (controller == null) {
            controller = new FragmentController(activity, containerId);
        }
        return controller;
    }

    private FragmentController(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();

        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new Fragment_room_style1 ());
        fragments.add(new Fragment_room_style2 ());
        fragments.add(new Fragment_room_style3 ());
        fragments.add(new Fragment_room_style4 ());


        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commit();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        Log.d(TAG, "showFragment: gotten fragment is"+fragment.toString());
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commit();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commit();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }
    public static void destoryController(){
        controller = null;
    }
}
