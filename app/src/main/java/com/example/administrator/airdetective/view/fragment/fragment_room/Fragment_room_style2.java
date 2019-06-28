package com.example.administrator.airdetective.view.fragment.fragment_room;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.airdetective.R;
import com.example.administrator.airdetective.model.airinfomodel.AirBean;
import com.example.administrator.airdetective.model.airinfomodel.AirList;
import com.example.administrator.airdetective.model.airinfomodel.AirRoomListBean;
import com.example.administrator.airdetective.view.Activity.NewHouseActivity;
import com.jn.chart.charts.CombinedChart;
import com.jn.chart.charts.PieChart;
import com.jn.chart.components.Legend;
import com.jn.chart.data.BarEntry;
import com.jn.chart.data.Entry;
import com.jn.chart.data.PieData;
import com.jn.chart.data.PieDataSet;
import com.jn.chart.formatter.ValueFormatter;
import com.jn.chart.highlight.Highlight;
import com.jn.chart.listener.OnChartValueSelectedListener;
import com.jn.chart.manager.CombinedChartManager;
import com.jn.chart.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.support.constraint.Constraints.TAG;

public class Fragment_room_style2 extends Fragment {

    private View view;
    private Spinner sp1;

    public static Fragment_room_style2 newInstance(){
        Fragment_room_style2 fragment_room_style2 = new Fragment_room_style2();
        Bundle b = new Bundle();
        b.putString("fragment","TwoCateFragment");


        return fragment_room_style2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i ( "fragment_room_style1", "onCreateView: running " );

        view = inflater.inflate( R.layout.fragment_room_style2,container,false);
//        initView();
        return view;
    }

    private void initView(){
//        sp1 = (Spinner) view.findViewById ( R.id.spinner );
        initSpinner ();
    }

    /**
     * 初始化spinner组件
     */
    private void initSpinner() {
        // 声明一个ArrayAdapter用于存放简单数据
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (
                Objects.requireNonNull ( getContext () ), android.R.layout.simple_spinner_item, setSpinnerData () );
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

    private List<String> setSpinnerData() {
        // 数据源
        List<String> dataList = new ArrayList<String> ();
        dataList.add ( "软件16-5" );
        dataList.add ( "软件16-6" );
        dataList.add ( "软件16-7" );
        dataList.add ( "软件16-8" );
        return dataList;
    }


    @Override
    public void onStart() {
        super.onStart ();
        Log.i ( "fragment_room_style1", "onStart: running " );

        ArrayList<PieChart> pieCharts = new ArrayList<> (  );
        pieCharts.add ( view.<PieChart>findViewById ( R.id.pieChart1 ) );

        Context context = getActivity ();
        AirList airList = AirList.getAirList ();
        ArrayList<AirRoomListBean> airRoomListBeans = airList.getList ();
        try{

            ArrayList<ArrayList<Float>> arrayLists = new ArrayList<ArrayList<Float>> (  );
//            for(int i = 0; i < airRoomListBeans.size (); i++){
//                ArrayList<Float> date = new ArrayList<> (  );
//                ArrayList<AirBean> airBeans = airRoomListBeans.get ( i ).getAirInfos ();
//                AirBean airBean = airBeans.get ( airBeans.size () - 1 );
//                date.add ( airBean.getTemperature () );
//                date.add ( airBean.getHumidity () );
//                date.add ( airBean.getPm25 () );
//                date.add ( airBean.getTvoc () );
//                arrayLists.add ( date );
//            }
//
//            for(int i = 0; i < arrayLists.size (); i++){
//                initPieChart( context, pieCharts.get ( i ), arrayLists.get ( i ) );
//            }

            ArrayList<Float> date = new ArrayList<> (  );
//            date.add ( (float) 10 );
//            date.add ( (float) 32 );
//            date.add ( (float) 38 );
//            date.add ( (float) 15 );
//            date.add ( (float) 5 );
            initPieChart( context, pieCharts.get ( 0 ), date );


        }catch (Exception ignored){
            Log.d ( TAG, "onStart: "+ ignored );
        }
    }

    public void initPieChart(Context context, PieChart pieChart, ArrayList<Float> date){

        int sum = 0;

        pieChart.setDescription ( "空气指标分布" );
        pieChart.animateXY (1000, 1000);
        pieChart.setDrawSliceText ( false );
        pieChart.setHoleRadius ( 40f );
        pieChart.setCenterTextSize ( 8f );
        pieChart.setCenterText ( generateCenterText(sum) );
        Legend legend = pieChart.getLegend ();

        if(sum == 0){
            pieChart.setData ( generateEmptyPieData () );
            pieChart.setHighlightPerTapEnabled ( false );
            legend.setEnabled ( false );
            return;
        }

        pieChart.setData ( generatePieData ( date ) );

        legend.setEnabled ( true );
        legend.setPosition ( Legend.LegendPosition.BELOW_CHART_CENTER );
        pieChart.setHighlightPerTapEnabled ( true );

        //圆盘点击事件
        pieChart.setOnChartValueSelectedListener ( new OnChartValueSelectedListener () {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        } );
    }


    /**
     * 中间文字绘制
     *
     * @param sum 总数
     * @return SpannableString类型对象
     */
    private SpannableString generateCenterText(int sum) {
        String total = Integer.toString(sum);
        SpannableString s = new SpannableString ( "0" + "\n 监控天数\n设备未连接");
        s.setSpan(new RelativeSizeSpan (5f), 0, total.length(), 0);
        s.setSpan(new ForegroundColorSpan ( Color.rgb(88, 146, 240)), 0, total.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.rgb(153, 153, 153)), total.length(), s.length(), 0);

        return s;
    }

    /**
     * 图表数据设置
     *
     * @param date
     * @return
     */
    protected PieData generatePieData(ArrayList<Float> date) {
        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();

        xVals.add("优秀");
        xVals.add("良好");
        xVals.add("中等");
        xVals.add("及格");
        xVals.add("不及格");



        yVals.add(new Entry((float) date.get ( 0 ), 0));
        yVals.add(new Entry((float) date.get ( 1 ), 1));
        yVals.add(new Entry((float) date.get ( 2 ), 2));
        yVals.add ( new Entry ( (float) date.get ( 3 ), 3 ) );
        yVals.add ( new Entry ( (float) date.get ( 4 ), 4 ) );


        PieDataSet pieDataSet = new PieDataSet(yVals, "");
        pieDataSet.setValueFormatter(new ValueFormatter () {//圆环内文字设置
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int n = (int) value;

                String str = n + "%";
                if (n == 0) {
                    str = "";
                }
                return str;
            }
        });

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(23, 213, 159));
        colors.add(Color.rgb(245, 166, 35));
        colors.add(Color.rgb(184, 233, 134));
        colors.add ( Color.rgb ( 255, 205, 210 ) );
        colors.add ( Color.rgb ( 220, 20, 60 ) );
        colors.add ( Color.rgb ( 199, 21, 133 ) );
        colors.add ( Color.rgb ( 75, 0 , 130 ) );
        colors.add ( Color.rgb ( 100,149,237 ) );
        colors.add ( Color.rgb ( 0,191,255 ) );
        colors.add ( Color.rgb ( 255,215,0 ) );
        pieDataSet.setColors(colors);//颜色设置

        pieDataSet.setSliceSpace(4f);
        pieDataSet.setValueTextColor(Color.WHITE);
        pieDataSet.setValueTextSize(16f);

        return new PieData(xVals, pieDataSet);
    }

    /**
     * 空图表数据设置
     *
     * @return PieData对象
     */
    protected PieData generateEmptyPieData() {
        ArrayList<Entry> yVals = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();

        xVals.add("无数据");
        yVals.add(new Entry((float) 1, 1));

        PieDataSet pieDataSet = new PieDataSet(yVals, "");
        pieDataSet.setValueFormatter(new ValueFormatter () {//圆环内文字设置为空
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "";
            }
        });

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(153, 153, 153));
        pieDataSet.setColors(colors);

        pieDataSet.setSliceSpace(4f);
        pieDataSet.setValueTextColor( Color.WHITE);
        pieDataSet.setValueTextSize(16f);

        return new PieData (xVals, pieDataSet);
    }

}
