package com.example.restapi;

import android.provider.BaseColumns;

public final class DataBases {

    public static final class CreateDB implements BaseColumns{
        public static final String DEVICENAME = "devicename";
        public static final String LOCATION = "location";
        public static final String IP = "ip";
        public static final String _TABLENAME0 = "device_list";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +DEVICENAME+" text not null , "
                +LOCATION+" text not null , "
                +IP+" integer not null ); ";
    }
}
