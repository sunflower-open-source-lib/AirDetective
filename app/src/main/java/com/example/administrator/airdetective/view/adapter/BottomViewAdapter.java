package com.example.administrator.airdetective.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class BottomViewAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public BottomViewAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super ( fm );
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get ( position );
    }

    @Override
    public int getCount() {
        return fragmentList.size ();
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }
}
