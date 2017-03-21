package com.example.andware.tetravex;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.andware.tetravex.gameManager.Game;

public class LeaderboardActivity extends AppCompatActivity {
    DatabaseManager myDb;
    private int leaderBoardType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_classic);


        //Mve down to matchTable method, then pass through varialbles to new methoed in database manager, return filtered result
        // for the displayed table

        TextView leaderBoardHeading = (TextView) findViewById(R.id.timeTableHeading);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            leaderBoardType = extras.getInt("leader");
        }
        if (leaderBoardType == 0){leaderBoardHeading.setText("Time");}
        else if (leaderBoardType == 1){leaderBoardHeading.setText("Remaining");}
        else {leaderBoardHeading.setText("Completed");}

        matchTableToSettings();

    }


    public void matchTableToSettings(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String difficulty = settings.getString(getString(R.string.pref_difficulty_key), Constants.DEFAULT_DIFFICULTY);
        String boardSize = settings.getString(getString(R.string.pref_size_key), Constants.DEFAULT_SIZE);
        String shape = settings.getString(getString(R.string.pref_shape_key), Constants.DEFAULT_SHAPE);

        String grid = boardSize + " x " + boardSize;

        myDb = new DatabaseManager(this);
        //Cursor todoCursor = myDb.getAllData();
        Cursor todoCursor = myDb.getFilteredData(difficulty, grid, shape);
        ListView lvItems = (ListView) findViewById(R.id.list_view);
        CursorManager todoAdapter = new CursorManager(this, todoCursor);
        lvItems.setAdapter(todoAdapter);
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
