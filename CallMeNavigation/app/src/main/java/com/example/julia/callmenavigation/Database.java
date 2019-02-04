package com.example.julia.callmenavigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by julia on 4/16/2018.
 */

public class Database extends SQLiteOpenHelper{
    private LocationTimeObject locationTimeObject;
    private final String TAG =  "Database";
    public static final String DATABASE_NAME = "Locations2.db";
    public static final String TABLE_NAME = "Saved_Locations_Table";
    public static final String COL_1 = "LONGITUDE";
    public static final String COL_2 = "LATITUDE";
    public static final String COL_3 = "TIME";
    public static final String COL_4 = "DATE";
    public static final String COL_5 = "ID";
    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        this.locationTimeObject = locationTimeObject.Instance();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, LATITUDE FLOAT" +
                ",LONGITUDE DOUBLE, TIME TEXT, DATE TEXT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public Integer insertData(Double Latitude, Double Longitude, String Time, String Date){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, Latitude);
        contentValues.put(COL_2, Longitude);
        contentValues.put(COL_3, Time);
        contentValues.put(COL_4, Date);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Cursor res = sqLiteDatabase.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        res.moveToLast();
        Log.i(TAG, "Insert ID = " +res.getInt(0));
        return res.getInt(0);
    }
    public void deleteData(int index)
    {
        this.locationTimeObject = locationTimeObject.Instance();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_NAME, "ID =" + index, null);
        if (result == -1){
            Log.i(TAG, "Did not delete Data");
        }
        else {
            Log.i(TAG, "Deleted");
        }
        for(int i = 0; i<locationTimeObject.getLength();i++){
            if(locationTimeObject.getSingleID(i)==index){
               locationTimeObject.setSingleChecked(i,false);
            }
        }
    }
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from "+ TABLE_NAME, null);
        return res;
    }
}
