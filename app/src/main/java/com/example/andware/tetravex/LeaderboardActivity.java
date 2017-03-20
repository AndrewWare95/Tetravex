package com.example.andware.tetravex;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class LeaderboardActivity extends AppCompatActivity {
    DatabaseManager myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_classic);
        myDb = new DatabaseManager(this);

        Cursor todoCursor = myDb.getAllData();
        ListView lvItems = (ListView) findViewById(R.id.list_view);
        CursorManager todoAdapter = new CursorManager(this, todoCursor);
        lvItems.setAdapter(todoAdapter);
       // populateListView();
    }



    /*public void populateListView(){
        Cursor cursor = myDb.getAllData();
        String[] fromFieldNames = new String[] {DatabaseManager.COL_1, DatabaseManager.COL_2, DatabaseManager.COL_3};
        int[] toViewIDs = new int[] {R.id.leaderboard_1, R.id.leaderboard_2, R.id.leaderboard_3};
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.testing_layout, cursor, fromFieldNames, toViewIDs, 0);
        ListView myList = (ListView) findViewById(R.id.list_view);
        myList.setAdapter(myCursorAdapter);



    }*/
}
