package com.example.silence.franlink;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.formatter.LineChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class TemptActivity extends BaseActivity{

    private int flagtemhum;

    private lecho.lib.hellocharts.view.LineChartView chartView;
    java.util.List<lecho.lib.hellocharts.model.Line> chartlines = new java.util.ArrayList<lecho.lib.hellocharts.model.Line>();
    List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    List<PointValue> pointValues = new ArrayList<PointValue>();
    Line chartline = new Line();
    LineChartData lineChartData = new LineChartData();
    LineChartValueFormatter chartValueFormatter = new SimpleLineChartValueFormatter(2);
    Axis axisX = new Axis();
    Axis axisY = new Axis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tempt);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.back);
        }
        chartView=(LineChartView)findViewById(R.id.chartView);
        initTem();
        flagtemhum=0;
        Button temhum=(Button)findViewById(R.id.button_temhum);
        temhum.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (flagtemhum==0){
                    initHum();
                    flagtemhum=1;
                }else {
                    initTem();
                    flagtemhum=0;
                }
            }
        });
    }

    public void setKLine(int type,List<DateForLine> list){
        chartlines.clear();
        mAxisXValues.clear();
        pointValues.clear();
        for(DateForLine date:list){
            String time=date.getTime();
            AxisValue axisValue=new AxisValue(parseHour2Ten(time));
            axisValue.setLabel(subTimeDay(time)+"日"+subTimeHour(time)+"时");
            mAxisXValues.add(axisValue);
        }
        for(DateForLine date:list){
            String time=date.getTime();
            PointValue pointValue=new PointValue(parseHour2Ten(time),Float.parseFloat(date.getValue()));
            if(type==1){
                pointValue.setLabel(date.getValue()+"℃");
                chartline.setFormatter(chartValueFormatter);
            }else if(type==2){
                pointValue.setLabel(date.getValue()+"%PH");
            }
            pointValues.add(pointValue);
        }
        chartline.setValues(pointValues);
        chartline.setColor(Color.BLUE);
        chartline.setPointColor(Color.BLUE);
        chartline.setShape(ValueShape.CIRCLE);
        chartline.setAreaTransparency(0);
        chartline.setPointRadius(2);
        chartline.setCubic(true);
        chartline.setFilled(false);
        chartline.setHasLines(true);
        chartline.setHasPoints(true);
        chartline.setHasLabels(true);
        chartline.setStrokeWidth(2);
        chartlines.add(chartline);

        lineChartData.setLines(chartlines);

        axisX.setValues(mAxisXValues).setHasLines(true).setTextColor(Color.BLACK).setLineColor(Color.WHITE).setTextSize(12).setName("时间");;
        axisY.setHasLines(true).setTextColor(Color.BLACK).setLineColor(Color.WHITE);
        if(type==1){
            axisY.setName("温度");
        }else if(type==2){
            axisY.setName("湿度");
        }

//        axisX.setMaxLabelChars(8);
        lineChartData.setAxisXBottom(axisX);
        lineChartData.setAxisYLeft(axisY);

        lineChartData.setValueLabelBackgroundColor(Color.TRANSPARENT);
        lineChartData.setValueLabelBackgroundEnabled(false);

        chartView.setLineChartData(lineChartData);
        chartView.setInteractive(true);
        chartView.setZoomEnabled(true);
        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        final Viewport v = new Viewport(chartView.getMaximumViewport());
        if(type==1){
            v.top=40;
            v.bottom=-20;
        }else if(type==2){
            v.top=100;
            v.bottom=0;
        }
        chartView.setMaximumViewport(v);
        v.left=parseHour2Ten(list.get(list.size()-5).getTime());
        v.right =parseHour2Ten(list.get(list.size()-1).getTime());
        chartView.setCurrentViewport(v);

//        chartView.setViewportAnimationListener(new ChartAnimationListener() {
//            @Override
//            public void onAnimationStarted() {
//            }
//            @Override
//            public void onAnimationFinished() {
//                chartView.setMaximumViewport(v);
//                chartView.setViewportAnimationListener(null);
//
//            }
//        });
//        chartView.setCurrentViewport(v);

    }


    public String subTimeMonth(String time){
        return time.substring(4,6);
    }
    public String subTimeDay(String time){
        return time.substring(6,8);
    }
    public String subTimeHour(String time){
        return time.substring(8,10);
    }
    public int parseHour2Ten(String time){
        return Integer.parseInt(time.substring(6,8))*24+
                Integer.parseInt(time.substring(8,10));
    }
    private void  initTem(){
        List<DateForLine> list1=new ArrayList<>();
        list1.add(new DateForLine("201807121130","26"));
        list1.add(new DateForLine("201807121230","26"));
        list1.add(new DateForLine("201807121330","25"));
        list1.add(new DateForLine("201807121430","24"));
        list1.add(new DateForLine("201807121530","23"));
        list1.add(new DateForLine("201807121630","24"));
        list1.add(new DateForLine("201807121730","24"));
        list1.add(new DateForLine("201807121830","25"));
        list1.add(new DateForLine("201807121930","26"));
        list1.add(new DateForLine("201807122030","24"));
        list1.add(new DateForLine("201807122130","26"));
        list1.add(new DateForLine("201807122230","26"));
        setKLine(1,list1);
    }
    private void  initHum(){
        List<DateForLine> list2=new ArrayList<>();
        list2.add(new DateForLine("201807121130","70"));
        list2.add(new DateForLine("201807121230","66"));
        list2.add(new DateForLine("201807121330","64"));
        list2.add(new DateForLine("201807121430","63"));
        list2.add(new DateForLine("201807121530","65"));
        list2.add(new DateForLine("201807121630","65"));
        list2.add(new DateForLine("201807121730","69"));
        list2.add(new DateForLine("201807121830","70"));
        list2.add(new DateForLine("201807121930","66"));
        list2.add(new DateForLine("201807122030","68"));
        list2.add(new DateForLine("201807122130","67"));
        list2.add(new DateForLine("201807122230","69"));
        setKLine(2,list2);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}