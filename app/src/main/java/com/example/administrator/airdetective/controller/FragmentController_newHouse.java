package com.example.administrator.airdetective.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.administrator.airdetective.view.fragment.fragment_new_house.Fragment_newHouse_style1;
import com.example.administrator.airdetective.view.fragment.fragment_new_house.Fragment_newHouse_style2;
import com.example.administrator.airdetective.view.fragment.fragment_new_house.Fragment_newHouse_style3;
import com.example.administrator.airdetective.view.fragment.fragment_new_house.Fragment_newHouse_style4;
import com.example.administrator.airdetective.view.fragment.fragment_new_house.NewHouseFragment;

import java.util.ArrayList;
import static android.content.ContentValues.TAG;

public class FragmentController_newHouse {

    private int containerId;
    private FragmentManager fm;
    private ArrayList<NewHouseFragment> fragments;

    private static FragmentController_newHouse controller;

    public static FragmentController_newHouse getInstance(FragmentActivity activity, int containerId) {
        if (controller == null) {
            controller = new FragmentController_newHouse(activity, containerId);
        }
        return controller;
    }

    private FragmentController_newHouse(FragmentActivity activity, int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();

        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<NewHouseFragment>();
        fragments.add(new Fragment_newHouse_style1 ());
        fragments.add(new Fragment_newHouse_style2 ());
        fragments.add(new Fragment_newHouse_style3 ());
        fragments.add(new Fragment_newHouse_style4 ());


        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commit();
    }

    public NewHouseFragment showFragment(int position) {
        hideFragments();
        NewHouseFragment fragment = fragments.get(position);
        Log.d(TAG, "showFragment: gotten fragment is"+fragment.toString());
        try{
            FragmentTransaction ft = fm.beginTransaction();
            ft.show(fragment);
            ft.commit();
        }catch (Exception ignored){
            Log.d ( TAG, "showFragment: " + ignored );
        }
        return fragment;
    }

    private void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        try{
            for(Fragment fragment : fragments) {
                if(fragment != null) {
                    ft.hide(fragment);
                }
            }
            ft.commit();
        }catch (Exception ignored){
            Log.d ( TAG, "hideFragments: " + ignored );
        }

    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

    public static void destoryController(){
        controller = null;
    }
}
