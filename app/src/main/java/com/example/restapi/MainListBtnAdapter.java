package com.example.restapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainListBtnAdapter extends ArrayAdapter  {

    ArrayList<MainListBtn> list = new ArrayList<MainListBtn>();
    // 버튼 클릭 이벤트를 위한 Listener 인터페이스 정의.
    public interface ListBtnClickListener {
        void onListBtnClick(int position) ;
    }

    // 생성자로부터 전달된 resource id 값을 저장.
    int resourceId ;
    // 생성자로부터 전달된 ListBtnClickListener  저장.
    private ListBtnClickListener listBtnClickListener ;


    // ListViewBtnAdapter 생성자. 마지막에 ListBtnClickListener 추가.
    MainListBtnAdapter(Context context, int resource, ArrayList<MainListBtn> list) {
        super(context, resource, list) ;

        // resource id 값 복사. (super로 전달된 resource를 참조할 방법이 없음.)
        this.resourceId = resource ;

    }

    // 새롭게 만든 Layout을 위한 View를 생성하는 코드
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position ;
        final Context context = parent.getContext();

        // 생성자로부터 저장된 resourceId(listview_btn_item)에 해당하는 Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId/*R.layout.listview_btn_item*/, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)로부터 위젯에 대한 참조 획득
        final TextView devname = (TextView) convertView.findViewById(R.id.devname);
        final TextView location = (TextView) convertView.findViewById(R.id.location);
        final TextView ip = (TextView) convertView.findViewById(R.id.ipaddr);
        final ImageView cstatus = (ImageView) convertView.findViewById(R.id.default_status);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        final MainListBtn listViewItem = (MainListBtn) getItem(position);

        // 아이템 내 각 위젯에 데이터 반영

        devname.setText(listViewItem.getText1());
        location.setText(listViewItem.getText2());
        ip.setText(listViewItem.getText3());






        //if(resul)

        // button1 클릭 시 TextView(textView1)의 내용 변경.
        Button button1 = (Button) convertView.findViewById(R.id.status);
        button1.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //status눌렀을때 실행되는 함수
                Intent intent1 = new Intent(context.getApplicationContext(),DrawStatus.class);
                intent1.putExtra("ip",listViewItem.getText3());
                context.startActivity(intent1);

            }
        });

        // button2의 TAG에 position값 지정. Adapter를 click listener로 지정.
        Button button2 = (Button) convertView.findViewById(R.id.feautre);
        button2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //Feature눌렀을때 실행되는 함수
                Intent intent2 = new Intent(context.getApplicationContext(),DrawFeature.class);
                intent2.putExtra("ip",listViewItem.getText3());
                context.startActivity(intent2);
            }
        });

        return convertView;
    }

    public void addItemToList(String devname, String location, String Ip){
        MainListBtn listdata = new MainListBtn();

        listdata.setText1(devname);
        listdata.setText2(location);
        listdata.setText3(Ip);

        //값들의 조립이 완성된 listdata객체 한개를 list배열에 추가
        list.add(listdata);

    }

}
