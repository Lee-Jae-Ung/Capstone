package com.example.restapi;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private LineChart chart;
    private Thread thread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = (LineChart) findViewById(R.id.chart);

        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.getAxisRight().setEnabled(false);
        chart.getLegend().setTextColor(Color.WHITE);
        chart.animateXY(2000,2000);
        chart.invalidate();

        LineData data = new LineData();
        chart.setData(data);

        feedMultiple();
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
    }
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
    private void addEntry() {
        LineData data = chart.getData();
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 40) + 30f), 0);
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
                        Thread.sleep(3000);
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



    @RequiresApi(api = Build.VERSION_CODES.N)
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
                        resultText = new Task().execute("http://203.250.77.238:50001/manage/Status/RawData.csv").get();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    String[] Ch1 = resultText.replace("Ch1", "").split(",");

                    double[] nums = Arrays.stream(Ch1).mapToDouble(Double::parseDouble).toArray();


                    // textview.setText(nums[0]);

                    Log.v("button", "2 : " + nums[0]);
                    Log.v("button", "2 : " + (nums[0] + 10));
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
                //break;
            case R.id.button3:

        }
    }



}