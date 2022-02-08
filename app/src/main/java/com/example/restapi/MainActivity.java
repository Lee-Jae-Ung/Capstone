package com.example.restapi;


import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public LineChart chart;

    public float i=0;
    ArrayList<Entry> entry_chart = new ArrayList<>();


    Handler mHandler = null;

    String Ch1[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mHandler = new Handler();
        chart = (LineChart) findViewById(R.id.chart);




        Thread t = new Thread(new Runnable() {
            TextView textview = (TextView) findViewById(R.id.result1);
            String resultText = "[NULL]";
            //String Ch1[];

            @Override
            public void run() {
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

                    Ch1 = resultText.replace("Ch1", "").split(",");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addEntry(Double.parseDouble(Ch1[0]));
                        }
                    });

                }
            }
        });
        t.start();
        chart.setDrawGridBackground(true);
        chart.setBackgroundColor(getResources().getColor(R.color.black));
        chart.setGridBackgroundColor(R.color.black);

// description text
        chart.getDescription().setEnabled(true);
        Description des = chart.getDescription();
        des.setEnabled(true);
        des.setText("Real-Time DATA");
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
        leftAxis.setTextColor(getResources().getColor(R.color.purple_200));
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(getResources().getColor(R.color.purple_200));

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);


// don't forget to refresh the drawing
        chart.invalidate();



/*
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
*/

/*
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

        //TextView textview = (TextView)findViewById(R.id.result1);
        //String resultText = "[NULL]";

        /*
        try{
            resultText = new Task().execute().get();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }

        textview.setText(resultText);

         */



        //ExThread thread1 = new ExThread();
        //thread1.start();
    }

    private void addEntry(double num) {

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



        data.addEntry(new Entry((float)set.getEntryCount(), (float)num), 0);
        data.notifyDataChanged();

        // let the chart know it's data has changed
        chart.notifyDataSetChanged();

        chart.setVisibleXRangeMaximum(150);
        // this automatically refreshes the chart (calls invalidate())
        chart.moveViewTo(data.getEntryCount(), 50f, YAxis.AxisDependency.LEFT);

    }

    private LineDataSet createSet() {



        LineDataSet set = new LineDataSet(null, "Real-time Line Data");
        set.setLineWidth(1f);
        set.setDrawValues(false);
        set.setValueTextColor(getResources().getColor(R.color.white));
        set.setColor(getResources().getColor(R.color.white));
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setDrawCircles(false);
        set.setHighLightColor(Color.rgb(190, 190, 190));

        return set;
    }



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