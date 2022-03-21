package com.example.restapi;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothClass;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SetIP extends AppCompatActivity implements View.OnClickListener {
    Button btn_Update;
    Button btn_Insert;
    Button btn_Select;
    Button btn_delete;
    EditText edit_device;
    EditText edit_location;
    EditText edit_Ip;
    TextView text_device;
    TextView text_location;
    TextView text_Ip;
    CheckBox check_DeviceName;
    CheckBox check_Location;
    CheckBox check_Ip;

    long nowIndex;
    String DeviceName;
    String Location;
    String Ip;
    String sort = "DeviceName";

    ArrayAdapter<String> arrayAdapter;

    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();
    private DbOpenHelper mDbOpenHelper;


    ArrayList<MainListBtn> items = new ArrayList<MainListBtn>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setip);

        btn_Insert = (Button) findViewById(R.id.btn_insert);
        btn_Insert.setOnClickListener(this);
        btn_Update = (Button) findViewById(R.id.btn_update);
        btn_Update.setOnClickListener(this);
        btn_Select = (Button) findViewById(R.id.btn_select);
        btn_Select.setOnClickListener(this);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);

        edit_device = (EditText) findViewById(R.id.edit_device);
        edit_location = (EditText) findViewById(R.id.edit_location);
        edit_Ip = (EditText) findViewById(R.id.edit_Ip);
        text_device = (TextView) findViewById(R.id.text_device);
        text_location = (TextView) findViewById(R.id.text_location);
        text_Ip = (TextView) findViewById(R.id.text_Ip);
        check_DeviceName = (CheckBox) findViewById(R.id.check_devicename);
        check_DeviceName.setOnClickListener(this);
        check_Location = (CheckBox) findViewById(R.id.check_location);
        check_Location.setOnClickListener(this);
        check_Ip = (CheckBox) findViewById(R.id.check_ip);
        check_Ip.setOnClickListener(this);


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.db_list_view);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(onClickListener);
        listView.setOnItemLongClickListener(longClickListener);

        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        showDatabase(sort);

        btn_Insert.setEnabled(true);
        btn_Update.setEnabled(false);
        btn_delete.setEnabled(false);
    }

    public void setInsertMode(){
        edit_device.setText("");
        edit_location.setText("");
        edit_Ip.setText("");
        btn_Insert.setEnabled(true);
        btn_Update.setEnabled(false);
        btn_delete.setEnabled(false);
    }

    private AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("On Click", "position = " + position);
            nowIndex = Long.parseLong(arrayIndex.get(position));
            Log.e("On Click", "nowIndex = " + nowIndex);
            Log.e("On Click", "Data: " + arrayData.get(position));
            String[] tempData = arrayData.get(position).split("\\s+");
            Log.e("On Click", "Split Result = " + tempData);
            edit_device.setText(tempData[0].trim());
            edit_location.setText(tempData[1].trim());
            edit_Ip.setText(tempData[2].trim());

            btn_Insert.setEnabled(false);
            btn_Update.setEnabled(true);
            btn_delete.setEnabled(true);
        }
    };

    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d("Long Click", "position = " + position);
            nowIndex = Long.parseLong(arrayIndex.get(position));
            String[] nowData = arrayData.get(position).split("\\s+");
            String viewData = nowData[0] + ", " + nowData[1] + ", " + nowData[2];
            AlertDialog.Builder dialog = new AlertDialog.Builder(SetIP.this);
            dialog.setTitle("데이터 삭제")
                    .setMessage("해당 데이터를 삭제 하시겠습니까?" + "\n" + viewData)
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(SetIP.this, "데이터를 삭제했습니다.", Toast.LENGTH_SHORT).show();
                            mDbOpenHelper.deleteColumn(nowIndex);
                            showDatabase(sort);
                            setInsertMode();
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(SetIP.this, "삭제를 취소했습니다.", Toast.LENGTH_SHORT).show();
                            setInsertMode();
                        }
                    })
                    .create()
                    .show();
            return false;
        }
    };

    public void showDatabase(String sort){
        Cursor iCursor = mDbOpenHelper.sortColumn(sort);
        Log.d("showDatabase", "DB Size: " + iCursor.getCount());
        arrayData.clear();
        arrayIndex.clear();
        while(iCursor.moveToNext()){
            @SuppressLint("Range") String tempIndex = iCursor.getString(iCursor.getColumnIndex("_id"));
            @SuppressLint("Range") String tempDevice = iCursor.getString(iCursor.getColumnIndex("devicename"));
            tempDevice = setTextLength(tempDevice,10);
            @SuppressLint("Range") String tempLocation = iCursor.getString(iCursor.getColumnIndex("location"));
            tempLocation = setTextLength(tempLocation,10);
            @SuppressLint("Range") String tempIp = iCursor.getString(iCursor.getColumnIndex("ip"));
            tempIp = setTextLength(tempIp,10);


            String Result = tempDevice + tempLocation + tempIp;
            arrayData.add(Result);
            arrayIndex.add(tempIndex);
        }
        arrayAdapter.clear();
        arrayAdapter.addAll(arrayData);
        arrayAdapter.notifyDataSetChanged();
    }

    public String setTextLength(String text, int length){
        if(text.length()<length){
            int gap = length - text.length();
            for (int i=0; i<gap; i++){
                text = text + " ";
            }
        }
        return text;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                DeviceName = edit_device.getText().toString();
                Location = edit_location.getText().toString();
                Ip = edit_Ip.getText().toString();

                if(DeviceName.equals("")||Location.equals("")||Ip.equals("")) {
                    Toast.makeText(SetIP.this,"값을 입력하시오",Toast.LENGTH_SHORT);
                    Log.v("insert","실패");
                }
                else {


                    mDbOpenHelper.open();
                    mDbOpenHelper.insertColumn(DeviceName, Location, Ip);
                    showDatabase(sort);
                    setInsertMode();
                    Log.v("dbtest",""+mDbOpenHelper);
                    edit_device.requestFocus();
                    edit_device.setCursorVisible(true);
                    Intent intent1 = ((MainActivity)MainActivity.mContext).getIntent();
                    ((MainActivity)MainActivity.mContext).finish(); //현재 액티비티 종료 실시
                    ((MainActivity)MainActivity.mContext).overridePendingTransition(0, 0); //효과 없애기
                    ((MainActivity)MainActivity.mContext).startActivity(intent1); //현재 액티비티 재실행 실시
                    ((MainActivity)MainActivity.mContext).overridePendingTransition(0, 0);
                }


                break;

            case R.id.btn_update:

                DeviceName = edit_device.getText().toString();
                Location = edit_location.getText().toString();
                Ip = edit_Ip.getText().toString();



                mDbOpenHelper.updateColumn(nowIndex,DeviceName, Location, Ip);
                showDatabase(sort);
                setInsertMode();
                edit_device.requestFocus();
                edit_device.setCursorVisible(true);

                Intent intent2 = ((MainActivity)MainActivity.mContext).getIntent();
                ((MainActivity)MainActivity.mContext).finish(); //현재 액티비티 종료 실시
                ((MainActivity)MainActivity.mContext).overridePendingTransition(0, 0); //효과 없애기
                ((MainActivity)MainActivity.mContext).startActivity(intent2); //현재 액티비티 재실행 실시
                ((MainActivity)MainActivity.mContext).overridePendingTransition(0, 0);
                break;

            case R.id.btn_delete:
                DeviceName = edit_device.getText().toString();
                Location = edit_location.getText().toString();
                Ip = edit_Ip.getText().toString();
                mDbOpenHelper.deleteColumn(nowIndex);
                showDatabase(sort);
                setInsertMode();
                edit_device.requestFocus();
                edit_device.setCursorVisible(true);
                Intent intent3 = ((MainActivity)MainActivity.mContext).getIntent();
                ((MainActivity)MainActivity.mContext).finish(); //현재 액티비티 종료 실시
                ((MainActivity)MainActivity.mContext).overridePendingTransition(0, 0); //효과 없애기
                ((MainActivity)MainActivity.mContext).startActivity(intent3); //현재 액티비티 재실행 실시
                ((MainActivity)MainActivity.mContext).overridePendingTransition(0, 0);
                break;

            case R.id.btn_select:
                showDatabase(sort);
                break;

            case R.id.check_devicename:
                check_Location.setChecked(false);
                check_Ip.setChecked(false);
                sort = "devicename";
                break;

            case R.id.check_location:
                check_DeviceName.setChecked(false);
                check_Ip.setChecked(false);
                sort = "location";
                break;

            case R.id.check_ip:
                check_DeviceName.setChecked(false);
                check_Location.setChecked(false);
                sort = "ip";
                break;
        }

    }
    public boolean loadItemsFromDB(ArrayList<MainListBtn> list,String devname,String location, String ip) {
        MainListBtn item ;
        int i ;

        if (list == null) {
            list = new ArrayList<MainListBtn>() ;
        }

        // 순서를 위한 i 값을 1로 초기화.
        i = 1 ;

        // 아이템 생성.
        item = new MainListBtn() ;
        item.setText1(devname) ;
        item.setText2(location) ;
        item.setText3(ip) ;
        list.add(item) ;
        i++ ;


        return true ;
    }


}
