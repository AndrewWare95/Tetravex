package com.example.andware.tetravex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tetravex.dp";
    private static final String TABLE_NAME = "main_table";
    private static final String COL_0 = "_id";
    private static final String COL_1 = "USERNAME";
    private static final String COL_2 = "TIME";
    private static final String COL_3 = "DATE";
    private static final String COL_4 = "DIFFICULTY";
    private static final String COL_5 = "GRID";
    private static final String COL_6 = "SHAPE";
    private static final String COL_7 = "GAMETYPE";
    private static final String COL_8 = "COMPAREVALUE";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, TIME TEXT, DATE TEXT, DIFFICULTY TEXT, GRID TEXT, SHAPE TEXT, GAMETYPE TEXT, COMPAREVALUE INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String username, String time, String date, String difficulty, String grid, String shape, String currentGameType, long compareValue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, username);
        contentValues.put(COL_2, time);
        contentValues.put(COL_3, date);
        contentValues.put(COL_4, difficulty);
        contentValues.put(COL_5, grid);
        contentValues.put(COL_6, shape);
        contentValues.put(COL_7, currentGameType);
        contentValues.put(COL_8, compareValue);
        //db.insert(TABLE_NAME, null, contentValues);
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

    public Cursor getGridData(String grid) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+ " where GRID = '"+grid+"' ", null);
        return res;
    }
    public Cursor getDifficultyData(String difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where DIFFICULTY = '"+difficulty+"' ", null);
        return res;
    }
    public Cursor getShapeData(String shape) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where SHAPE = '"+shape+"' ", null);
        return res;
    }

    public Cursor getFilteredData(String difficulty, String grid, String shape, String currentGameType) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where DIFFICULTY = '"+difficulty+"' AND GRID = '"+grid+"' AND SHAPE = '"+shape+"' AND GAMETYPE = '"+currentGameType+"'", null);
        return res;
    }
}
