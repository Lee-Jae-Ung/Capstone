package com.example.restapi;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    //public LineChart chart;
    //public LineChart chart4;

    //public PieChart pieChart;
    //public PieChart pieChart2;



    //Handler mHandler = null;
    //Handler mHandler2 = null;

    //double rms = 0.0;
    //double peak = 0.0;
    public static Context mContext;

    ListView listview ;
    MainListBtnAdapter adapter;
    ArrayList<MainListBtn> items = new ArrayList<MainListBtn>() ;
    //public static Context context_main;
    DbOpenHelper mDbOpenHelper;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
        //mHandler = new Handler();
        //mHandler2 = new Handler();
        //chart = (LineChart) findViewById(R.id.chart);
        //chart4 = (LineChart) findViewById(R.id.chart4);
        //pieChart = (PieChart)findViewById(R.id.chart2);
        //pieChart2 = (PieChart)findViewById(R.id.chart3);

        //Button button1 = findViewById(R.id.button1);
        //Button button2 = findViewById(R.id.button2);
        //Button button3 = findViewById(R.id.control);
        //Thread thread2 = new Thread(new );
        //thread2.start();


        //Log.v("dbtest",""+mDbOpenHelper.selectColumns());



        //ListView listview ;
        //ainListBtnAdapter adapter;
        //ArrayList<MainListBtn> items = new ArrayList<MainListBtn>() ;

        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();
        Cursor iCursor = mDbOpenHelper.selectColumns();
        String Result = null;
        while (iCursor.moveToNext()) {

            @SuppressLint("Range") String tempId = iCursor.getString(iCursor.getColumnIndex("_id"));
            @SuppressLint("Range") String tempDev = iCursor.getString(iCursor.getColumnIndex("devicename"));
            @SuppressLint("Range") String tempLoc = iCursor.getString(iCursor.getColumnIndex("location"));
            @SuppressLint("Range") String tempIp = iCursor.getString(iCursor.getColumnIndex("ip"));
            loadItemsFromDB(items,tempDev,tempLoc,tempIp);
        }
        // items 로드.

        //loadItemsFromDB(items,"abd","ulsans","203.250.77.241");
        // Adapter 생성
        adapter = new MainListBtnAdapter(MainActivity.this, R.layout.activity_control, items) ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.device_list_view);
        listview.setAdapter(adapter);



        //Log.v("test", "" + Result);


    }

    @Override
    public void onRefresh(){
        updateLayoutView();

        swipeRefreshLayout.setRefreshing(false);
    }
    public void updateLayoutView(){

    }


//메인 버튼
/*
        button1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(getApplicationContext(),DrawStatus.class);
                startActivity(intent1);
            }
        });




        //Thread thread2 = new Thread(new );
        //thread2.start();

        button2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(getApplicationContext(),DrawFeature.class);
                startActivity(intent2);
            }
        });
        */

/*
        button3.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent(getApplicationContext(),SetIP.class);
                startActivity(intent3);
            }
        });

 */

/*
        //차트1
        chart.setDrawGridBackground(true);
        chart.setBackgroundColor(getResources().getColor(R.color.black));
        chart.setGridBackgroundColor(R.color.black);

// description text
        chart.getDescription().setEnabled(true);
        Description des = chart.getDescription();
        des.setEnabled(true);
        des.setText("RMS");
        des.setTextSize(15f);
        des.setTextColor(R.color.white);

// touch gestures (false-비활성화)
        chart.setTouchEnabled(false);

// scaling and dragging (false-비활성화)
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);

//auto scale
        chart.setAutoScaleMinMaxEnabled(true);

// if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

//X축
        chart.getXAxis().setDrawGridLines(true);
        chart.getXAxis().setDrawAxisLine(false);

        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setDrawGridLines(false);

//Legend
        Legend l = chart.getLegend();
        l.setEnabled(true);
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setTextSize(12f);
        l.setTextColor(R.color.white);

//Y축
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setTextColor(getResources().getColor(R.color.white));
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(getResources().getColor(R.color.white));

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);


// don't forget to refresh the drawing
        chart.invalidate();




        //차트2
        chart4.setDrawGridBackground(true);
        chart4.setBackgroundColor(getResources().getColor(R.color.black));
        chart4.setGridBackgroundColor(R.color.black);

// description text
        chart4.getDescription().setEnabled(true);
        Description des1 = chart4.getDescription();
        des1.setEnabled(true);
        des1.setText("PEAK");
        des1.setTextSize(15f);
        des1.setTextColor(R.color.white);

// touch gestures (false-비활성화)
        chart4.setTouchEnabled(false);

// scaling and dragging (false-비활성화)
        chart4.setDragEnabled(false);
        chart4.setScaleEnabled(false);

//auto scale
        chart4.setAutoScaleMinMaxEnabled(true);

// if disabled, scaling can be done on x- and y-axis separately
        chart4.setPinchZoom(false);

//X축
        chart4.getXAxis().setDrawGridLines(true);
        chart4.getXAxis().setDrawAxisLine(false);

        chart4.getXAxis().setEnabled(true);
        chart4.getXAxis().setDrawGridLines(false);

//Legend
        Legend ll = chart4.getLegend();
        ll.setEnabled(true);
        ll.setFormSize(10f); // set the size of the legend forms/shapes
        ll.setTextSize(12f);
        ll.setTextColor(R.color.white);

//Y축
        YAxis leftAxis1 = chart4.getAxisLeft();
        leftAxis1.setEnabled(true);
        leftAxis1.setTextColor(getResources().getColor(R.color.white));
        leftAxis1.setDrawGridLines(true);
        leftAxis1.setGridColor(getResources().getColor(R.color.white));

        YAxis rightAxis1 = chart.getAxisRight();
        rightAxis1.setEnabled(false);


// don't forget to refresh the drawing
        chart4.invalidate();





        //원차트1

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);


        ArrayList yValues = new ArrayList();

        yValues.add(new PieEntry(30f,"USAGE"));
        yValues.add(new PieEntry(70f,"REMAIN"));


        Description description = new Description();
        description.setText("CPU"); //라벨
        description.setTextSize(15);
        pieChart.setDescription(description);

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);


        PieData data1 = new PieData();
        PieDataSet dataSet = new PieDataSet(yValues,"Usage");


        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);


        data1.addDataSet(dataSet);
        data1.setValueTextSize(10f);
        data1.setValueTextColor(Color.YELLOW);

        pieChart.setData(data1);


        pieChart.invalidate();


        //원차트2

        //원차트2
        pieChart2.setUsePercentValues(true);
        pieChart2.getDescription().setEnabled(false);
        pieChart2.setExtraOffsets(5,10,5,5);

        pieChart2.setDragDecelerationFrictionCoef(0.95f);

        pieChart2.setDrawHoleEnabled(false);
        pieChart2.setHoleColor(Color.WHITE);
        pieChart2.setTransparentCircleRadius(61f);



        ArrayList yValues2 = new ArrayList();

        yValues2.add(new PieEntry((float)cpu,"USAGE"));
        yValues2.add(new PieEntry((float)(100-cpu),"REMAIN"));


        Description description2 = new Description();
        description2.setText("RAM"); //라벨
        description2.setTextSize(15);
        pieChart2.setDescription(description2);

        pieChart2.animateY(1000, Easing.EasingOption.EaseInOutCubic);



        PieData data2 = new PieData();
        PieDataSet dataSet2 = new PieDataSet(yValues2,"Usage");
        data2.addDataSet(dataSet);




        dataSet2.setSliceSpace(3f);
        dataSet2.setSelectionShift(5f);
        dataSet2.setColors(ColorTemplate.JOYFUL_COLORS);



        //PieData data = new PieData();


        data2.setValueTextSize(10f);
        data2.setValueTextColor(Color.YELLOW);

        pieChart2.setData(data2);
        pieChart2.invalidate();




        Thread t1 = new Thread(new Runnable() {
            String resultText1 = "[NULL]";
            @Override
            public void run() {
                try {
                    resultText1 = new Task().execute("http://203.250.77.238:50001/manage/Status/info").get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                try {
                    JSONObject jsonObject = new JSONObject(resultText1);
                    cpu = Double.parseDouble(jsonObject.getString("cpu").replace("%", ""));
                    ram_total = Double.parseDouble(jsonObject.getString("ram_total").replace("MB", ""));
                    ram_usage = Double.parseDouble(jsonObject.getString("ram_usage").replace("MB", ""));
                    ram_usage_total = Double.parseDouble(jsonObject.getString("ram_usage_total").replace("%", ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });




        Thread t = new Thread(new Runnable() {
            //TextView textview = (TextView) findViewById(R.id.result1);
            String resultText = "[NULL]";
            //String Ch1[];

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                while(true) {

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        resultText = new Task().execute("http://203.250.77.240:50001/manage/Status/test").get();

                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONObject jsonObject = new JSONObject(resultText);
                        avg = Double.parseDouble(jsonObject.getString("AVG"));
                        std = Double.parseDouble(jsonObject.getString("STD"));
                        max = Double.parseDouble(jsonObject.getString("MAX"));
                        min = Double.parseDouble(jsonObject.getString("MIN"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.v("testdata1","zz");

                    //Ch1 = resultText.replace("Ch1", "").split(",");
                    //double[] nums;
                    //nums = Arrays.stream(Ch1).mapToDouble(Double::parseDouble).toArray();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            addEntry(avg,chart);
                            addEntry(std,chart4);

                        }
                    });

                }
            }
        });


        //t.start();

        //thread1 = new getFeature();



        chart = (LineChart) findViewById(R.id.chart);
        LineData chartData = new LineData();


        int w;
        float z=0;
        for(w=0;w<5120;w++){
            entry_chart.add(new Entry(z,Float.parseFloat(Ch1[w])));
            z++;
        }


        //entry_chart.add(new Entry(0,0));

        LineDataSet lineDataSet = new LineDataSet(entry_chart,"line1");
        chartData.addDataSet(lineDataSet);

        chart.setData(chartData);
        chart.invalidate();



        chart = (LineChart) findViewById(R.id.chart);

        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setTextColor(Color.WHITE);
        chart.animateXY(2000,2000);
        chart.invalidate();

        LineData data = new LineData();
        chart.setData(data);

        feedMultiple();
*/



    public boolean loadItemsFromDB(ArrayList<MainListBtn> list,String devname,String location, String ip) {
        MainListBtn item ;
        int i ;

        if (list == null) {
            list = new ArrayList<MainListBtn>() ;
        }

        // 순서를 위한 i 값을 1로 초기화.
        i = 1 ;


        item = new MainListBtn() ;

        item.setText1(devname) ;
        item.setText2(location) ;
        item.setText3(ip) ;



        // 아이템 생성.
        list.add(item) ;
        i++ ;



        return true ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.search_action:
                Intent intent3 = new Intent(getApplicationContext(),SetIP.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//데이터받는 스레드
/*
    private class getFeature implements Runnable {
        //private final AtomicBoolean condition = new AtomicBoolean(false);

        public getFeature(){

        }

        String resultText = "[NULL]";

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void run() {


            //Log.v("thread","condition : "+condition);
            while(true) {
                Log.v("thread","run");

                try {
                    Thread.sleep(1000);
                    resultText = new Task().execute("http://203.250.77.240:50001/manage/Status/feature").get();

                    JSONObject jsonObject = new JSONObject(resultText);
                    rms = Double.parseDouble(jsonObject.getString("RMS"));
                    peak = Double.parseDouble(jsonObject.getString("PEAK"));
                    Log.v("dattt","rms : "+rms);
                    Log.v("dattt","peak : "+peak);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.v("testdata1","zz");

                //푸시알림림

               if(rms>0.8){

                    NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    //Notification 객체를 생성해주는 건축가객체 생성(AlertDialog 와 비슷)
                    NotificationCompat.Builder builder= null;

                    //Oreo 버전(API26 버전)이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨.
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                        String channelID="channel_01"; //알림채널 식별자
                        String channelName="MyChannel01"; //알림채널의 이름(별명)

                        //알림채널 객체 만들기
                        NotificationChannel channel= new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);

                        //알림매니저에게 채널 객체의 생성을 요청
                        notificationManager.createNotificationChannel(channel);

                        //알림건축가 객체 생성
                        builder=new NotificationCompat.Builder(MainActivity.this, channelID);


                    }else{
                        //알림 건축가 객체 생성
                        builder= new NotificationCompat.Builder(MainActivity.this, (Notification) null);
                    }


                    //건축가에게 원하는 알림의 설정작업
                    builder.setSmallIcon(android.R.drawable.ic_menu_view);

                    //상태바를 드래그하여 아래로 내리면 보이는
                    //알림창(확장 상태바)의 설정
                    builder.setContentTitle("*이상신호*");//알림창 제목
                    builder.setContentText("이상신호감지");//알림창 내용
                    //알림창의 큰 이미지
                    //Bitmap bm= BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background);
                    //builder.setLargeIcon(bm);//매개변수가 Bitmap을 줘야한다.

                    //건축가에게 알림 객체 생성하도록
                    Notification notification=builder.build();

                    //알림매니저에게 알림(Notify) 요청
                    notificationManager.notify(1, notification);
                }

                //Ch1 = resultText.replace("Ch1", "").split(",");
                //double[] nums;
                //nums = Arrays.stream(Ch1).mapToDouble(Double::parseDouble).toArray();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        //addEntry(rms,chart);
                        //addEntry(peak,chart4);

                    }
                });

            }
        }

    }
*/
//라인데이터 관리
    /*
    private void addEntry(double num,LineChart chart) {

        LineData data = chart.getData();

        if (data == null) {
            data = new LineData();
            chart.setData(data);
        }

        ILineDataSet set = data.getDataSetByIndex(0);
        // set.addEntry(...); // can be called as well

        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }

        data.addEntry(new Entry((float)i++, (float)num), 0);


        //data.addEntry(new Entry((float)set.getEntryCount(), (float)num), 0);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        chart.notifyDataSetChanged();

        chart.setVisibleXRangeMaximum(60);
        // this automatically refreshes the chart (calls invalidate())
        chart.moveViewTo(data.getEntryCount(), 50f, YAxis.AxisDependency.LEFT);

    }



    private LineDataSet createSet() {



        LineDataSet set = new LineDataSet(null, "Real-time Line Data");
        set.setLineWidth(1f);
        set.setDrawValues(false);
        set.setValueTextColor(getResources().getColor(R.color.white));
        set.setColor(getResources().getColor(R.color.green));
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawCircles(false);
        set.setHighLightColor(Color.rgb(190, 190, 190));

        return set;
    }

*/
//파이차트
/*
    public void mOnClick(View v){
        //getFeature runnabl = new getFeature();
        //Thread thread2 = new Thread(new getFeature());

        switch (v.getId()){
            case R.id.button1:

                new Thread(new Runnable() {
                    String resultText1 = "[NULL]";
                    @Override
                    public void run() {
                        try {
                            resultText1 = new Task().execute("http://203.250.77.240:50001/manage/Status/info").get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONObject jsonObject = new JSONObject(resultText1);
                            cpu = Double.parseDouble(jsonObject.getString("cpu").replace("%", ""));
                            ram_total = Double.parseDouble(jsonObject.getString("ram_total").replace("MB", ""));
                            ram_usage = Double.parseDouble(jsonObject.getString("ram_usage").replace("MB", ""));
                            ram_usage_per = Double.parseDouble(jsonObject.getString("ram_usage_per").replace("%", ""));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Log.v("RAM","msg : "+ram_usage_per);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //원차트1

                                pieChart.setUsePercentValues(true);
                                pieChart.getDescription().setEnabled(false);
                                pieChart.setExtraOffsets(5,10,5,5);

                                pieChart.setDragDecelerationFrictionCoef(0.95f);

                                pieChart.setDrawHoleEnabled(false);
                                pieChart.setHoleColor(Color.WHITE);
                                pieChart.setTransparentCircleRadius(61f);


                                ArrayList yValues = new ArrayList();

                                yValues.add(new PieEntry((float)cpu,"USAGE"));
                                yValues.add(new PieEntry((float)(100-cpu),"AVAILABLE"));


                                Description description = new Description();
                                description.setText("CPU"); //라벨
                                description.setTextSize(5);
                                pieChart.setDescription(description);

                                pieChart.animateY(1000, Easing.EaseInOutCubic);


                                PieData data1 = new PieData();
                                PieDataSet dataSet = new PieDataSet(yValues,"%");


                                dataSet.setSliceSpace(3f);
                                dataSet.setSelectionShift(5f);
                                dataSet.setColors(ColorTemplate.JOYFUL_COLORS);


                                data1.addDataSet(dataSet);
                                data1.setValueTextSize(10f);
                                data1.setValueTextColor(Color.YELLOW);

                                pieChart.setData(data1);


                                pieChart.invalidate();


                                //******원차트2******
                                pieChart2.setUsePercentValues(true);
                                pieChart2.getDescription().setEnabled(false);
                                pieChart2.setExtraOffsets(5,10,5,5);

                                pieChart2.setDragDecelerationFrictionCoef(0.95f);

                                pieChart2.setDrawHoleEnabled(false);
                                pieChart2.setHoleColor(Color.WHITE);
                                pieChart2.setTransparentCircleRadius(61f);



                                ArrayList yValues2 = new ArrayList();

                                yValues2.add(new PieEntry((float)ram_usage_per,"USAGE"));
                                yValues2.add(new PieEntry((float)(100-ram_usage_per),"AVAILABLE"));


                                Description description2 = new Description();
                                description2.setText("RAM"); //라벨
                                description2.setTextSize(5);
                                pieChart2.setDescription(description2);

                                pieChart2.animateY(1000, Easing.EaseInOutCubic);



                                PieData data2 = new PieData();
                                PieDataSet dataSet2 = new PieDataSet(yValues2,"%");
                                data2.addDataSet(dataSet2);




                                dataSet2.setSliceSpace(3f);
                                dataSet2.setSelectionShift(5f);
                                dataSet2.setColors(ColorTemplate.JOYFUL_COLORS);



                                //PieData data = new PieData();


                                data2.setValueTextSize(10f);
                                data2.setValueTextColor(Color.YELLOW);

                                pieChart2.setData(data2);
                                pieChart2.invalidate();
                            }
                        });

                    }
                }).start();

            case R.id.button2:

                //thread2.start()

        }
    }
*/
/*
    private class ExThread extends Thread {
        TextView textview = (TextView) findViewById(R.id.result1);
        String resultText = "[NULL]";
        public ExThread(){

        }

        public void run(){

            try {
                resultText = new Task().execute("http://203.250.77.238:50001/manage/Status/RawData.csv").get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            //String Ch1[];
            //Ch1 = resultText.replace("Ch1", "").split(",");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textview.setText(resultText);
                }
            });
        }
    }

 */
/*
    public void mOnClick(View v){

        switch (v.getId()){
            case R.id.button1:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textview = (TextView) findViewById(R.id.result1);
                        String resultText = "[NULL]";
                        try {
                            resultText = new Task().execute("http://203.250.77.238:50001/manage/Status/info").get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        double cpu=0.0;
                        double ram_total=0.0;
                        double ram_usage=0.0;
                        double ram_usage_total=0.0;

                        try {
                            JSONObject jsonObject = new JSONObject(resultText);
                            cpu = Double.parseDouble(jsonObject.getString("cpu").replace("%",""));
                            ram_total = Double.parseDouble(jsonObject.getString("ram_total").replace("MB",""));
                            ram_usage = Double.parseDouble(jsonObject.getString("ram_usage").replace("MB",""));
                            ram_usage_total = Double.parseDouble(jsonObject.getString("ram_usage_total").replace("%",""));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.v("button","1 : "+ cpu);
                        Log.v("button","1 : "+ (cpu + 1));
                        textview.setText(resultText);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }).start();
                break;

            case R.id.button2:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textview = (TextView)findViewById(R.id.result1);
                        String resultText = "[NULL]";
                        try{
                            resultText = new Task().execute("http://203.250.77.238:50001/manage/Status/RawData.csv").get();
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        catch (ExecutionException e){
                            e.printStackTrace();
                        }

                        textview.setText(resultText);

                        Log.v("button","2 : ");
                        Log.v("button","2 : ");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                    }
                }).start();
                break;

        }
    }

 */
/*
    private void addEntry() {


        LineData data = chart.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }
            float z=0;
            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);


            //data.addEntry(new Entry(z, Float.parseFloat(Ch1[0])), 0);
            data.notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.setVisibleXRangeMaximum(10);
            chart.moveViewToX(data.getEntryCount());
        }
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setFillAlpha(110);
        set.setFillColor(Color.parseColor("#d7e7fa"));
        set.setColor(Color.parseColor("#0B80C9"));
        set.setCircleColor(Color.parseColor("#FFA1B4DC"));
        //set.setCircleColorHole(Color.BLUE);
        set.setValueTextColor(Color.WHITE);
        set.setDrawValues(false);
        set.setLineWidth(2);
        set.setCircleRadius(6);
        set.setDrawCircleHole(false);
        set.setDrawCircles(false);
        set.setValueTextSize(9f);
        set.setDrawFilled(true);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setHighLightColor(Color.rgb(244, 117, 117));
        return set;
    }
    private void feedMultiple() {
        if (thread != null)
            thread.interrupt();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                addEntry();
            }
        };

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    runOnUiThread(runnable);

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }


                }
            }
        });
        thread.start();
    }
    protected void onPause() {
        super.onPause();
        if (thread != null)
            thread.interrupt();
    }
*/

    /*
    public void mOnClick(View v) {
        TextView textview = (TextView) findViewById(R.id.result1);
        String resultText = "[NULL]";
        switch (v.getId()) {

            case R.id.button1:

                try {
                    resultText = new Task().execute("http://203.250.77.238:50001/manage/Status/info").get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                double cpu = 0.0;
                double ram_total = 0.0;
                double ram_usage = 0.0;
                double ram_usage_total = 0.0;

                try {
                    JSONObject jsonObject = new JSONObject(resultText);
                    cpu = Double.parseDouble(jsonObject.getString("cpu").replace("%", ""));
                    ram_total = Double.parseDouble(jsonObject.getString("ram_total").replace("MB", ""));
                    ram_usage = Double.parseDouble(jsonObject.getString("ram_usage").replace("MB", ""));
                    ram_usage_total = Double.parseDouble(jsonObject.getString("ram_usage_total").replace("%", ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.v("button", "1 : " + cpu);
                Log.v("button", "1 : " + (cpu + 1));
                textview.setText(resultText);


                break;

            case R.id.button2:
                while(true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        resultText = new Task().execute("http://203.250.77.238:50001/manage/Status/RawData.csv").get();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    String Ch1[];
                    Ch1 = resultText.replace("Ch1", "").split(",");

                    //nums = Arrays.stream(Ch1).mapToDouble(Double::parseDouble).toArray();


                    //ArrayList<BarEntry> barEntries = new ArrayList<BarEntry>();

                    // textview.setText(nums[0]);

                    //Log.v("button", "2 : " + nums[0]);
                    //Log.v("button", "2 : " + (nums[0] + 10));


                    chart = (LineChart) findViewById(R.id.chart);
                    LineData chartData = new LineData();


                    int w;
                    float z = 0;
                    for (w = 0; w < 100; w++) {
                        entry_chart.add(new Entry(z, Float.parseFloat(Ch1[w])));
                        z++;
                    }


                    LineDataSet lineDataSet = new LineDataSet(entry_chart, "line1");
                    chartData.addDataSet(lineDataSet);

                    chart.setData(chartData);
                    chart.invalidate();

                }

                //break;

            case R.id.button3:


        }
    }
*/


}