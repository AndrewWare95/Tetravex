package com.example.andware.tetravex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tetravex_android_database.dp";
    private static final String TABLE_NAME = "main_table";
    private static final String TABLE_USERS = "users_table";
    private static final String TABLE_UNFINISHED = "unfinished_table";
    private static final String COL_0 = "_id";
    private static final String COL_1 = "USERNAME";
    private static final String COL_2 = "TIME";
    private static final String COL_3 = "DATE";
    private static final String COL_4 = "DIFFICULTY";
    private static final String COL_5 = "GRID";
    private static final String COL_6 = "SHAPE";
    private static final String COL_7 = "GAMETYPE";
    private static final String COL_8 = "COMPAREVALUE";
    private static final String COL_UNFINISHED = "UNFINISHED";


    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, TIME TEXT, DATE TEXT, DIFFICULTY TEXT, GRID TEXT, SHAPE TEXT, GAMETYPE TEXT, COMPAREVALUE INTEGER) ");
        db.execSQL("create table "+TABLE_USERS+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT) ");
        db.execSQL("create table "+TABLE_UNFINISHED+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, UNFINISHED INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_UNFINISHED);
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
    public boolean insertUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, username);
        long result = db.insert(TABLE_USERS, null, contentValues);

        if (result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean insertUnfinished(String username, int unfinished){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, username);
        contentValues.put(COL_UNFINISHED, unfinished);
        long result = db.insert(TABLE_UNFINISHED, null, contentValues);

        if (result == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("select * from "+TABLE_NAME, null);
        return mCursor;
    }

    public boolean userDoesNotExist(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "select * from "+TABLE_USERS+" where USERNAME = '"+username+"' ";
        Cursor mCursor = db.rawQuery(query, null);

        if (mCursor.getCount() <= 0){
            mCursor.close();
            return true;
        }
        mCursor.close();
        return false;
    }

    public Cursor getUnfinishedPuzzleData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("", null);
        return mCursor;
    }

    public Cursor getFilteredData(String difficulty, String grid, String shape, String currentGameType) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;
        if (currentGameType.matches("Classic")){
            res = db.rawQuery("select * from "+TABLE_NAME+" where DIFFICULTY = '"+difficulty+"' AND GRID = '"+grid+"' AND SHAPE = '"+shape+"' AND GAMETYPE = '"+currentGameType+"' ORDER BY COMPAREVALUE", null);
        }
        else{
            res = db.rawQuery("select * from "+TABLE_NAME+" where DIFFICULTY = '"+difficulty+"' AND GRID = '"+grid+"' AND SHAPE = '"+shape+"' AND GAMETYPE = '"+currentGameType+"' ORDER BY COMPAREVALUE DESC", null);
        }
        return res;
    }
}
