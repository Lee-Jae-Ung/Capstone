package com.example.restapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
public class SetIP extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setip); //xml , java 소스 연결

        EditText device = findViewById(R.id.dname);
        EditText ip = findViewById(R.id.ip);






    }

}
