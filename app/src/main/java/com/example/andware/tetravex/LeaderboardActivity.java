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


public class LeaderboardActivity extends AppCompatActivity {
    DatabaseManager myDb;
    private int leaderBoardType;
    private String game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            leaderBoardType = extras.getInt("leader");
        }
        if (leaderBoardType != 3) {
            setContentView(R.layout.activity_leaderboard_classic);
            TextView leaderBoardHeading = (TextView) findViewById(R.id.timeTableHeading);

            //Set the heading for the middle column depending on the leaderboard type.
            if (leaderBoardType == 0) {
                leaderBoardHeading.setText("Time");
                game = "Classic";
            } else if (leaderBoardType == 1) {
                leaderBoardHeading.setText("Remaining");
                game = "Time Trial";
            } else {
                leaderBoardHeading.setText("Completed");
                game = "Arcade";
            }
            matchTableToSettings();
        }
        else {
            setContentView(R.layout.activity_leaderboard_unfinished);
            populateUnfinishedPuzzleTable();
        }
    }

    //Unless unfinished table, then refresh data if you re-enter from howTo or settings page.
    @Override
    public void onResume()
    {
        super.onResume();
        if (leaderBoardType != 3) {
            matchTableToSettings();
        }
    }

    //This populates the unfinished puzzles table with username and no. of unfinished puzzles.
    public void populateUnfinishedPuzzleTable(){
        myDb = new DatabaseManager(this);
        //Cursor todoCursor = myDb.getAllData();
        Cursor todoCursor = myDb.getAllUnfinishedData();
        ListView lvItems = (ListView) findViewById(R.id.list_view_unfinished);
        CursorManager todoAdapter = new CursorManager(this, todoCursor);
        todoAdapter.setLeaderboardType(0);
        lvItems.setAdapter(todoAdapter);
    }


    //Loads the screen with the relevant data depending on the different settings applied.
    public void matchTableToSettings(){
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String difficulty = settings.getString(getString(R.string.pref_difficulty_key), Constants.DEFAULT_DIFFICULTY);
        String boardSize = settings.getString(getString(R.string.pref_size_key), Constants.DEFAULT_SIZE);
        String shape = settings.getString(getString(R.string.pref_shape_key), Constants.DEFAULT_SHAPE);
        String grid = boardSize + " x " + boardSize;

        myDb = new DatabaseManager(this);
        Cursor todoCursor = myDb.getFilteredData(difficulty, grid, shape, game);
        ListView lvItems = (ListView) findViewById(R.id.list_view);
        CursorManager todoAdapter = new CursorManager(this, todoCursor);

        todoAdapter.setLeaderboardType(1);
        lvItems.setAdapter(todoAdapter);
    }

    public void removeAllData(){
        myDb = new DatabaseManager(this);
        myDb.removeAllScores();
        matchTableToSettings();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_leaderboard, menu);
        return true;
    }

    //Options menu in top right of the screen, gives options of howTo and settings page
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.remove_all_data:
                removeAllData();
                return true;

            case R.id.help:
                Intent intent = new Intent(LeaderboardActivity.this ,HowTo.class);
                intent.putExtra("leaderboard", true);
                startActivityForResult(intent, 0);
                return true;

            case R.id.settings_header:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
