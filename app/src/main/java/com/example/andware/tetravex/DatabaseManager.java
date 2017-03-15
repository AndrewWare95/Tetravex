package com.example.andware.tetravex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by andwa on 13/03/2017.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "classicTest.dp";
    public static final String TABLE_NAME = "classicTest_table";
    public static final String COL_1 = "USERNAME";
    public static final String COL_2 = "TIME";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "DIFFICULTY";
    public static final String COL_5 = "GRID";
    public static final String COL_6 = "SHAPE";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (USERNAME TEXT, TIME TEXT, DATE TEXT, DIFFICULTY TEXT, GRID TEXT, SHAPE TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);
    }

    public boolean intsertData(String username, String time, String date, String difficulty, String grid, String shape){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, username);
        contentValues.put(COL_2, time);
        contentValues.put(COL_3, date);
        contentValues.put(COL_4, difficulty);
        contentValues.put(COL_5, grid);
        contentValues.put(COL_6, shape);
        db.insert(TABLE_NAME, null, contentValues);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        }
        else {
            return true;
        }

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }
}
