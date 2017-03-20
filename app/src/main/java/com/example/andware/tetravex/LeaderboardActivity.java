package com.example.andware.tetravex;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_leaderboard, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.help:
                startActivity(new Intent(this, HowTo.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
