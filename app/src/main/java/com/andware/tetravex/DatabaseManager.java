package com.andware.tetravex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tetravex_android_database.dp";
    private static final String TABLE_NAME = "main_table";
    private static final String TABLE_USERS = "users_table";
    private static final String TABLE_UNFINISHED = "unfinished_table";
    private static final String COL_1 = "USERNAME";
    private static final String COL_2 = "TIME";
    private static final String COL_3 = "DATE";
    private static final String COL_4 = "DIFFICULTY";
    private static final String COL_5 = "GRID";
    private static final String COL_6 = "SHAPE";
    private static final String COL_7 = "GAMETYPE";
    private static final String COL_8 = "COMPAREVALUE";
    private static final String COL_UNFINISHED = "UNFINISHED";


    DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, TIME TEXT, DATE TEXT, DIFFICULTY TEXT, GRID TEXT, SHAPE TEXT, GAMETYPE TEXT, COMPAREVALUE INTEGER) ");
        db.execSQL("create table "+TABLE_USERS+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT) ");
        db.execSQL("create table "+TABLE_UNFINISHED+" ( _id INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, UNFINISHED INTEGER) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_UNFINISHED);
        onCreate(db);
    }

    boolean insertData(String username, String time, String date, String difficulty, String grid, String shape, String currentGameType, long compareValue){
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
        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    //Insert data into username table if new user is created.
    boolean insertUsername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, username);
        long result = db.insert(TABLE_USERS, null, contentValues);

        return result != -1;
    }

    //Insert new data into table if new user is added.
    boolean insertUnfinished(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, username);
        contentValues.put(COL_UNFINISHED, 0);
        long result = db.insert(TABLE_UNFINISHED, null, contentValues);

        return result != -1;
    }

    /*public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("select * from "+TABLE_NAME, null);
        return mCursor;
    }*/

    //Method returns true if the username entered already exists in the database.
    boolean userDoesNotExist(String username){
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

    //Returns number of unfinished puzzles of a specific user.
    Cursor getUnfinishedPuzzleData(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select UNFINISHED from "+TABLE_UNFINISHED+" where USERNAME = '"+username+"' ", null);
    }

    //Returns all data from unfinished puzzles table.
    Cursor getAllUnfinishedData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from "+TABLE_UNFINISHED+" ", null);
    }

    //Modifies the unfinished puzzles table if a user does not finish a puzzle.
    void modifyUnfinishedPuzzleInfo(String username, int count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_UNFINISHED, count);
        db.update(TABLE_UNFINISHED, contentValues, "USERNAME = '"+username+"'", null);
    }

    void removeAllScores(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+TABLE_NAME);
    }


    //Returns filtered data depending on the settings passed through.
    Cursor getFilteredData(String difficulty, String grid, String shape, String currentGameType) {
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
