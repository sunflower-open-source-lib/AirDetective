package com.example.administrator.airdetective.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.airdetective.R;
import com.example.administrator.airdetective.view.fragment.fragment_room.Fragment_room_style1;
import com.example.administrator.airdetective.view.fragment.fragment_room.Fragment_room_style2;
import com.example.administrator.airdetective.view.fragment.fragment_room.Fragment_room_style3;
import com.example.administrator.airdetective.view.fragment.fragment_room.Fragment_room_style4;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Fragment_home extends Fragment {

    private View view;
    private List<String> family;
    private ViewPager view_pager;
    private SlidingTabLayout mSlidingTabLayout;
    private TextView tvDes;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i ( "fragment_home", "onCreateView: running " );
        view = inflater.inflate ( R.layout.fragment_home, container, false );
        tvDes = view.findViewById ( R.id.tvDes );
        tvDes.setText ( "人体适应温度约为22度\n适宜湿度约为50%\n我国pm2.5标准值为小于75微克/立方米\n" +
                "tvoc正常值为600mg\n"
        + "以下饼状图中数值小于100%为正常，高于则为不达标");
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated ( savedInstanceState );

        mSlidingTabLayout = Objects.requireNonNull ( this.getView () ).findViewById(R.id.tab_layout);
        view_pager = this.getView().findViewById(R.id.fragment_home_container);

        initFragment ();
    }

    @Override
    public void onStart() {
        super.onStart ();
        Log.i ( "fragment_home", "onStart: running " );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
    }

    public void initFragment(){
        family = new ArrayList<> (  );
//        family.add ( "家庭1" );
//        family.add ( "家庭2" );
//        family.add ( "家庭3" );
//        family.add ( "家庭4" );
        family.add ( "统计1" );
        family.add ( "统计2" );
        family.add ( "统计3" );
        family.add ( "统计4" );

        final ArrayList<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add( Fragment_room_style1.newInstance());
        fragmentList.add ( Fragment_room_style2.newInstance () );
        fragmentList.add ( Fragment_room_style3.newInstance () );
        fragmentList.add ( Fragment_room_style4.newInstance () );

        final MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList);
        view_pager.setAdapter(adapter);
        view_pager.setOverScrollMode( ViewPager.OVER_SCROLL_NEVER);
        view_pager.setOffscreenPageLimit(family.size());
        view_pager.setCurrentItem(0);
        mSlidingTabLayout.setViewPager(view_pager);

        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return family.get(position);
        }
    }
}
