package com.example.administrator.airdetective.view.fragment.fragment_room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.airdetective.R;
import com.example.administrator.airdetective.model.airinfomodel.AirBean;
import com.example.administrator.airdetective.model.airinfomodel.AirList;
import com.example.administrator.airdetective.model.airinfomodel.AirRoomListBean;
import com.example.administrator.airdetective.view.Activity.ShowAirInfoActivity;
import com.jn.chart.charts.PieChart;
import com.jn.chart.components.Legend;
import com.jn.chart.data.Entry;
import com.jn.chart.data.PieData;
import com.jn.chart.data.PieDataSet;
import com.jn.chart.formatter.ValueFormatter;
import com.jn.chart.highlight.Highlight;
import com.jn.chart.listener.OnChartValueSelectedListener;
import com.jn.chart.utils.ViewPortHandler;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.support.constraint.Constraints.TAG;

public class Fragment_room_style1 extends Fragment {

    @BindView(R.id.cardView1)
    CardView cardView1;
    @BindView(R.id.cardView2)
    CardView cardView2;
    @BindView(R.id.cardView3)
    CardView cardView3;
    @BindView(R.id.cardView4)
    CardView cardView4;
    @BindView(R.id.cardView5)
    CardView cardView5;
    Unbinder unbinder;
    @BindView(R.id.tv_room1)
    TextView tvRoom1;
    @BindView(R.id.tv_room2)
    TextView tvRoom2;
    @BindView(R.id.tv_room3)
    TextView tvRoom3;
    @BindView(R.id.tv_room4)
    TextView tvRoom4;
    @BindView(R.id.tv_room5)
    TextView tvRoom5;
    private View view;
    private ArrayList<AirRoomListBean> airRoomListBeans;

    public static Fragment_room_style1 newInstance() {
        Fragment_room_style1 fragment_room_style1 = new Fragment_room_style1 ();
        Bundle b = new Bundle ();
        b.putString ( "fragment", "TwoCateFragment" );
        return fragment_room_style1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i ( "fragment_room_style1", "onCreateView: running " );

        view = inflater.inflate ( R.layout.fragment_room_style1, container, false );

        unbinder = ButterKnife.bind ( this, view );
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {
        super.onStart ();
        Log.i ( "fragment_room_style1", "onStart: running " );

        ArrayList<PieChart> pieCharts = new ArrayList<> ();
        pieCharts.add ( view.<PieChart>findViewById ( R.id.pieChart1 ) );
        pieCharts.add ( view.<PieChart>findViewById ( R.id.pieChart2 ) );
        pieCharts.add ( view.<PieChart>findViewById ( R.id.pieChart3 ) );
        pieCharts.add ( view.<PieChart>findViewById ( R.id.pieChart4 ) );
        pieCharts.add ( view.<PieChart>findViewById ( R.id.pieChart5 ) );

        ArrayList<TextView> textViews = new ArrayList<> (  );
        textViews.add ( tvRoom1 );
        textViews.add ( tvRoom2 );
        textViews.add ( tvRoom3 );
        textViews.add ( tvRoom4 );
        textViews.add ( tvRoom5 );

        Context context = getActivity ();
        AirList airList = AirList.getAirList ();
        airRoomListBeans = airList.getList ();

        ArrayList<ArrayList<Float>> arrayLists = new ArrayList<ArrayList<Float>> ();

        for (int i = 0; i < airRoomListBeans.size (); i++) {
            ArrayList<Float> date = new ArrayList<> ();
            ArrayList<AirBean> airBeans = airRoomListBeans.get ( i ).getAirInfos ();
            Log.i ( TAG, "onStart: airBeans " + airBeans.size () );
            try {
                AirBean airBean = airBeans.get ( airBeans.size () - 1 );
                date.add ( airBean.getTemperature () );
                date.add ( airBean.getHumidity () );
                date.add ( airBean.getPm25 () );
                date.add ( airBean.getTvoc () );
                arrayLists.add ( date );

                TextView textView = textViews.get ( i );
                textView.setText ( airRoomListBeans.get ( i ).getRoom ().getRoomName () + "\n最后更新时间为"
                        + dateFormat ( airRoomListBeans.get(i).getAirInfos ().get ( airBeans.size () - 1 ).getDate () ) );
            } catch (Exception ignored) {
                Log.d ( TAG, "onStart: " + ignored );
            }


        }

        for (int i = 0; i < arrayLists.size (); i++) {
            initPieChart ( context, pieCharts.get ( i ), arrayLists.get ( i ) );
        }

    }


    public void initPieChart(Context context, PieChart pieChart, ArrayList<Float> date) {

        int sum = 15;

        pieChart.setDescription ( "空气质量监控" );
        pieChart.animateXY ( 1000, 1000 );
        pieChart.setDrawSliceText ( false );
        pieChart.setHoleRadius ( 40f );
        pieChart.setCenterTextSize ( 3f );
        pieChart.setCenterText ( generateCenterText ( sum ) );
        Legend legend = pieChart.getLegend ();

        if (sum == 0) {
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
        String total = Integer.toString ( sum );
        SpannableString s = new SpannableString ( total + "\n 监测天数" );
        s.setSpan ( new RelativeSizeSpan ( 5f ), 0, total.length (), 0 );
        s.setSpan ( new ForegroundColorSpan ( Color.rgb ( 88, 146, 240 ) ), 0, total.length (), 0 );
        s.setSpan ( new ForegroundColorSpan ( Color.rgb ( 153, 153, 153 ) ), total.length (), s.length (), 0 );

        return s;
    }

    /**
     * 图表数据设置
     *
     * @param date
     * @return
     */
    protected PieData generatePieData(ArrayList<Float> date) {
        ArrayList<Entry> yVals = new ArrayList<> ();
        ArrayList<String> xVals = new ArrayList<> ();

        xVals.add ( "温度" );
        xVals.add ( "湿度" );
        xVals.add ( "PM2.5" );
        xVals.add ( "tvoc" );

        yVals.add ( new Entry ( date.get ( 0 )/22 * 100, 0 ) );
        yVals.add ( new Entry ( date.get ( 1 )/50 * 100, 1 ) );
        yVals.add ( new Entry ( date.get ( 2 )/75 * 100, 2 ) );
        yVals.add ( new Entry ( date.get ( 3 )/600 * 100, 3 ) );

        PieDataSet pieDataSet = new PieDataSet ( yVals, "" );
        pieDataSet.setValueFormatter ( new ValueFormatter () {//圆环内文字设置
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int n = (int) value;

                String str = n + "%";
                if (n == 0) {
                    str = "";
                }
                return str;
            }
        } );

        ArrayList<Integer> colors = new ArrayList<> ();
        colors.add ( Color.rgb ( 23, 213, 159 ) );
        colors.add ( Color.rgb ( 245, 166, 35 ) );
        colors.add ( Color.rgb ( 184, 233, 134 ) );
        colors.add ( Color.rgb ( 255, 205, 210 ) );
        pieDataSet.setColors ( colors );//颜色设置

        pieDataSet.setSliceSpace ( 2f );
        pieDataSet.setValueTextColor ( Color.WHITE );
        pieDataSet.setValueTextSize ( 12f );

        return new PieData ( xVals, pieDataSet );
    }

    /**
     * 空图表数据设置
     *
     * @return PieData对象
     */
    protected PieData generateEmptyPieData() {
        ArrayList<Entry> yVals = new ArrayList<> ();
        ArrayList<String> xVals = new ArrayList<> ();

        xVals.add ( "无数据" );
        yVals.add ( new Entry ( (float) 1, 1 ) );

        PieDataSet pieDataSet = new PieDataSet ( yVals, "" );
        pieDataSet.setValueFormatter ( new ValueFormatter () {//圆环内文字设置为空
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "";
            }
        } );

        ArrayList<Integer> colors = new ArrayList<> ();
        colors.add ( Color.rgb ( 153, 153, 153 ) );
        pieDataSet.setColors ( colors );

        pieDataSet.setSliceSpace ( 2f );
        pieDataSet.setValueTextColor ( Color.WHITE );
        pieDataSet.setValueTextSize ( 12f );

        return new PieData ( xVals, pieDataSet );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        unbinder.unbind ();
    }

    @OnClick({R.id.cardView1, R.id.cardView2, R.id.cardView3, R.id.cardView4, R.id.cardView5})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId ()) {
            case R.id.cardView1:
                intent = new Intent ( getActivity (), ShowAirInfoActivity.class );
                intent.putExtra ( "airRoomListBeans", airRoomListBeans.get ( 0 ) );
                Log.i ( TAG, "initValue: DeviceId" + airRoomListBeans.get ( 0 ).getAirInfos ().get ( 0 ).getDeviceId () );
                startActivity ( intent );
                break;

            case R.id.cardView2:
                intent = new Intent ( getActivity (), ShowAirInfoActivity.class );
                intent.putExtra ( "airRoomListBeans", airRoomListBeans.get ( 1 ) );
                startActivity ( intent );
                break;

            case R.id.cardView3:
                intent = new Intent ( getActivity (), ShowAirInfoActivity.class );
                intent.putExtra ( "airRoomListBeans", airRoomListBeans.get ( 2 ) );
                startActivity ( intent );
                break;

            case R.id.cardView4:
                intent = new Intent ( getActivity (), ShowAirInfoActivity.class );
                intent.putExtra ( "airRoomListBeans", airRoomListBeans.get ( 3 ) );
                startActivity ( intent );
                break;

            case R.id.cardView5:
                intent = new Intent ( getActivity (), ShowAirInfoActivity.class );
                intent.putExtra ( "airRoomListBeans", airRoomListBeans.get ( 4 ) );
                startActivity ( intent );
                break;
        }
    }

    private String dateFormat(String date){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd'T'HH:mm:ss" );
        try {
            Date date1 = format.parse ( date );
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss" );
            return sdf.format ( date1 );
        } catch (ParseException e) {
            e.printStackTrace ();
            return null;
        }
    }
}
