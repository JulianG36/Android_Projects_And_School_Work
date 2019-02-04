package com.example.julia.homeworkcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by julia on 4/16/2018.
 */

public class Database extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Homework.db";
    public static final String TABLE_NAME = "Homework_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DUEDATE";
    public static final String COL_3 = "CLASSASSIGNED";
    public static final String COL_4 = "DESCRIPTION";
    public static final String COL_5 = "REMINDER";
    public static final String COL_6 = "ASSIGNED_DATE";
    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,  DUEDATE TEXT, CLASSASSIGNED TEXT, " +
                "DESCRIPTION TEXT, REMINDER BOOLEAN, ASSIGNED_DATE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String DueDate, String ClassAssigned,String Description,
                              Boolean Reminder, String AssignedDate){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, DueDate );
        contentValues.put(COL_3, ClassAssigned);
        contentValues.put(COL_4, Description);
        contentValues.put(COL_5, Reminder);
        contentValues.put(COL_6, AssignedDate);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public void deleteRow(int index){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+ TABLE_NAME +" where "+ COL_1 + " = "+ index );
    }
    public Cursor getAllData(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select * from "+ TABLE_NAME, null);
        return res;

    }
    public Cursor getSingleitem(int row, String col){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor res = sqLiteDatabase.rawQuery("select " + col + " from " + TABLE_NAME + " where " + COL_1 + " = " + row, null);
        return res;
    }
    public void deleteAll(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from " + TABLE_NAME);
    }
}
