package com.example.administrator.airdetective.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.administrator.airdetective.R;
import com.jn.chart.charts.LineChart;
import com.jn.chart.data.Entry;
import com.jn.chart.manager.LineChartManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Fragment_statistics extends Fragment {

    private Context context;
    private LineChart mLineChart1;
    private Spinner sp1;
    private Spinner sp2;
    private View view;
//    private LineChart mLineChart2;
//    private LineChart mLineChart3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate ( R.layout.fragment_statistics, container, false );
        this.context = getActivity ();
        mLineChart1 = view.findViewById ( R.id.lineChart1 );
//        mLineChart2 = view.findViewById ( R.id.lineChart2 );
//        mLineChart3 = view.findViewById ( R.id.lineChart3 );
        //设置图表的描述
        mLineChart1.setDescription ( "周数" );
//        mLineChart2.setDescription ( "节点2" );
//        mLineChart3.setDescription ( "节点3" );
        mLineChart1.setTouchEnabled(true);
        mLineChart1.setScaleEnabled(false);
//        设置点击折线点时，显示其数值
//        MyMakerView mv = new MyMakerView (context, R.layout.item_mark_layout);
//        mLineChart1.setMarkerView(mv);

//        initLineChart1 ();
//        initLineChart2 ();
//        initLineChart3 ();

//        initView ();
        return view;
    }

    private void initView(){
        sp1 =  view.findViewById ( R.id.spinner_1 );
        sp2 = view.findViewById ( R.id.spinner_2 );
        initSpinner (sp1, 1);
        initSpinner ( sp2, 2 );
    }

    /**
     * 初始化spinner组件
     */
    private void initSpinner(Spinner sp1, int num) {
        // 声明一个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (
                Objects.requireNonNull ( getContext () ), android.R.layout.simple_spinner_item, setSpinnerData (num) );
        sp1.setAdapter ( adapter );
        sp1.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 在选中之后触发
                Log.d ( "MainActivity onItemSelected", "onItemSelected: position is" + position );
//                fragment = mController.showFragment ( position );
//                positionMark = position;
//                Toast.makeText ( NewHouseActivity.this,
//                        parent.getItemAtPosition ( position ).toString (),
//                        Toast.LENGTH_SHORT ).show ();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText ( NewHouseActivity.this,
//                        "nothing selected",
//                        Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    private List<String> setSpinnerData(int num) {
        // 数据源
        List<String> xVals = new ArrayList<String> ();
        if (num ==1){
            xVals.add("西创-509实验室");
            xVals.add("西-新E1105");
            xVals.add("西-新1329");
            xVals.add("西创-610");
            xVals.add("西创-505");
            xVals.add ( "新创-506" );
        }

        if (num == 2){
            xVals.add ( "2019 第二学期"  );
            xVals.add ( "2019 第一学期" );
        }


        return xVals;
    }

    private void initLineChart1() {
        //设置x轴的数据
        ArrayList<String> xValues = new ArrayList<> ();
        for (int i = 0; i < 20; i++) {
            xValues.add ( "" + i );
        }

        //设置y轴的数据
        ArrayList<Entry> yValue = new ArrayList<> ();
        yValue.add ( new Entry ( 13, 1 ) );
        yValue.add ( new Entry ( 6, 2 ) );
        yValue.add ( new Entry ( 3, 3 ) );
        yValue.add ( new Entry ( 7, 4 ) );
        yValue.add ( new Entry ( 2, 5 ) );
        yValue.add ( new Entry ( 5, 6 ) );
        yValue.add ( new Entry ( 12, 7 ) );
        yValue.add ( new Entry ( 3 , 8) );
        //设置折线的名称
        LineChartManager.setLineName ( "当前学期值" );
        //创建一条折线的图表
        LineChartManager.initSingleLineChart(context,mLineChart1,xValues,yValue);

        //设置第二条折线y轴的数据
        ArrayList<Entry> yValue1 = new ArrayList<> ();
        yValue1.add ( new Entry ( 17, 1 ) );
        yValue1.add ( new Entry ( 3, 2 ) );
        yValue1.add ( new Entry ( 5, 3 ) );
        yValue1.add ( new Entry ( 4, 4 ) );
        yValue1.add ( new Entry ( 3, 5 ) );
        yValue1.add ( new Entry ( 7, 6 ) );
        yValue1.add ( new Entry ( 10, 7 ) );
        LineChartManager.setLineName1 ( "上月值" );
        //创建两条折线的图表
//        LineChartManager.initDoubleLineChart ( context, mLineChart1, xValues, yValue, yValue1 );
    }

    private void initLineChart2() {
        //设置x轴的数据
        ArrayList<String> xValues1 = new ArrayList<> ();
        for (int i = 0; i < 15; i++) {
            xValues1.add ( "" + i );
        }

        //设置y轴的数据
        ArrayList<Entry> yValue2 = new ArrayList<> ();
        yValue2.add ( new Entry ( 13, 1 ) );
        yValue2.add ( new Entry ( 16, 2 ) );
        yValue2.add ( new Entry ( 2, 3 ) );
        yValue2.add ( new Entry ( 6, 4 ) );
        yValue2.add ( new Entry ( 9, 5 ) );
        yValue2.add ( new Entry ( 3, 6 ) );
        yValue2.add ( new Entry ( 12, 7 ) );
        //设置折线的名称
        LineChartManager.setLineName ( "当月值" );
        //创建一条折线的图表
//        LineChartManager.initSingleLineChart(context,mLineChart,xValues,yValue);

        //设置第二条折线y轴的数据
        ArrayList<Entry> yValue3 = new ArrayList<> ();
        yValue3.add ( new Entry ( 14, 1 ) );
        yValue3.add ( new Entry ( 13, 2 ) );
        yValue3.add ( new Entry ( 6, 3 ) );
        yValue3.add ( new Entry ( 5, 4 ) );
        yValue3.add ( new Entry ( 3, 5 ) );
        yValue3.add ( new Entry ( 7, 6 ) );
        yValue3.add ( new Entry ( 10, 7 ) );
        LineChartManager.setLineName1 ( "上月值" );
        //创建两条折线的图表
//        LineChartManager.initDoubleLineChart ( context, mLineChart2, xValues1, yValue2, yValue3 );
    }

    private void initLineChart3() {
        //设置x轴的数据
        ArrayList<String> xValues2 = new ArrayList<> ();
        for (int i = 0; i < 15; i++) {
            xValues2.add ( "" + i );
        }

        //设置y轴的数据
        ArrayList<Entry> yValue2 = new ArrayList<> ();
        yValue2.add ( new Entry ( 13, 1 ) );
        yValue2.add ( new Entry ( 16, 2 ) );
        yValue2.add ( new Entry ( 2, 3 ) );
        yValue2.add ( new Entry ( 6, 4 ) );
        yValue2.add ( new Entry ( 9, 5 ) );
        yValue2.add ( new Entry ( 3, 6 ) );
        yValue2.add ( new Entry ( 12, 7 ) );
        //设置折线的名称
        LineChartManager.setLineName ( "当月值" );
        //创建一条折线的图表
//        LineChartManager.initSingleLineChart(context,mLineChart,xValues,yValue);

        //设置第二条折线y轴的数据
        ArrayList<Entry> yValue3 = new ArrayList<> ();
        yValue3.add ( new Entry ( 14, 1 ) );
        yValue3.add ( new Entry ( 13, 2 ) );
        yValue3.add ( new Entry ( 6, 3 ) );
        yValue3.add ( new Entry ( 7, 4 ) );
        yValue3.add ( new Entry ( 3, 5 ) );
        yValue3.add ( new Entry ( 5, 6 ) );
        yValue3.add ( new Entry ( 10, 7 ) );
        LineChartManager.setLineName1 ( "上月值" );
        //创建两条折线的图表
//        LineChartManager.initDoubleLineChart ( context, mLineChart3, xValues2, yValue2, yValue3 );
    }
}
