package com.example.administrator.airdetective.view.fragment.fragment_room;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.airdetective.R;
import com.jn.chart.charts.BarChart;
import com.jn.chart.data.BarEntry;
import com.jn.chart.manager.BarChartManager;
import java.util.ArrayList;

public class Fragment_room_style3 extends Fragment {

    private BarChart barChart;
    private Context context;

    public static Fragment_room_style3 newInstance(){
        Fragment_room_style3 fragment_room_style3 = new Fragment_room_style3();
        Bundle b = new Bundle();
        b.putString("fragment","TwoCateFragment");
        return fragment_room_style3;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_room_style3, container, false);
        barChart = view.findViewById(R.id.barChart);
        this.context = getActivity();
//        initChart(context,barChart);
        return view;
    }

    private void initChart(Context context, BarChart barChart) {
        barChart.setDescription("实验室情况");
        barChart.setTouchEnabled ( true );

        ArrayList<String> xValues = new ArrayList<>();
        xValues.add("实验室103");
        xValues.add("实验室203");
        xValues.add("实验室204");
        xValues.add("实验室205");
        xValues.add("实验室303");
        xValues.add("实验室304");

        ArrayList<BarEntry> yValues = new ArrayList<>();
        yValues.add(new BarEntry((float) 2,0));
        yValues.add(new BarEntry((float) 3,1));
        yValues.add(new BarEntry((float) 4,2));
        yValues.add(new BarEntry((float) 5,3));
        yValues.add(new BarEntry((float) 1,4));
        yValues.add(new BarEntry((float) 3,5));
        BarChartManager.setUnit("单位：次/周");
        BarChartManager.initBarChart(context,barChart,xValues,yValues);
    }
}
