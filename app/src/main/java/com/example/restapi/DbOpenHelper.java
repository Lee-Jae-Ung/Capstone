package com.example.restapi;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class DbOpenHelper {

    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    public static class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(DataBases.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context){
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException{
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create(){
        mDBHelper.onCreate(mDB);
    }

    public void close(){
        mDB.close();
    }

    // Insert DB
    public long insertColumn(String section,String devicename, String location, String ip){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.SECTION, section);
        values.put(DataBases.CreateDB.DEVICENAME, devicename);
        values.put(DataBases.CreateDB.LOCATION, location);
        values.put(DataBases.CreateDB.IP, ip);
        return mDB.insert(DataBases.CreateDB._TABLENAME0, null, values);
    }


    // Update DB
    public boolean updateColumn(long id, String section,String devicename, String location, String ip){
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.SECTION, section);
        values.put(DataBases.CreateDB.DEVICENAME, devicename);
        values.put(DataBases.CreateDB.LOCATION, location);
        values.put(DataBases.CreateDB.IP, ip);

        return mDB.update(DataBases.CreateDB._TABLENAME0, values, "_id=" + id, null) > 0;
    }

    // Delete All
    public void deleteAllColumns() {
        mDB.delete(DataBases.CreateDB._TABLENAME0, null, null);
    }

    // Delete DB
    public boolean deleteColumn(long id){
        return mDB.delete(DataBases.CreateDB._TABLENAME0, "_id="+id, null) > 0;
    }
    // Select DB
    public Cursor selectColumns(){
        return mDB.query(DataBases.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }


    // sort by column
    public Cursor sortColumn(String sort){
        Cursor c = mDB.rawQuery( "SELECT * FROM device_list ORDER BY " + sort + ";", null);
        return c;
    }

    public Cursor sortColumnDist(){
        Cursor c = mDB.rawQuery( "SELECT DISTINCT section, location ,ip FROM device_list ORDER BY section;", null);
        return c;
    }

    public Cursor selectDevice(String section_str){
        Cursor c = mDB.rawQuery( "SELECT devicename FROM device_list WHERE section =" + section_str + ";", null);
        return c;
    }


}
