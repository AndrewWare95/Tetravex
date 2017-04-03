package com.example.andware.tetravex;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;



public class CursorManager extends CursorAdapter {
    public int isNorm;


    public CursorManager(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        if (isNorm == 1) {
            return LayoutInflater.from(context).inflate(R.layout.leaderboard_list_layout, parent, false);
        }
        else{
            return LayoutInflater.from(context).inflate(R.layout.leaderboard_list_unfinished_layout, parent, false);
        }
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView l1 = (TextView) view.findViewById(R.id.leaderboard_1);
        TextView l2 = (TextView) view.findViewById(R.id.leaderboard_2);

        if (isNorm == 1) {
            TextView l3 = (TextView) view.findViewById(R.id.leaderboard_3);
            String userName = cursor.getString(cursor.getColumnIndexOrThrow("USERNAME"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("TIME"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("DATE"));

            l1.setText(userName);
            l2.setText(time);
            l3.setText(date);
        }
        else{
            String userName = cursor.getString(cursor.getColumnIndexOrThrow("USERNAME"));
            int unfinished = cursor.getInt(cursor.getColumnIndexOrThrow("UNFINISHED"));
            String unfin = Integer.toString(unfinished);
            l1.setText(userName);
            l2.setText(unfin);
        }
    }

    public void setLeaderboardType(int isNormal){
        isNorm = isNormal;
    }
}
