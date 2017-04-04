package com.example.andware.tetravex;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class HowTo extends AppCompatActivity {
    private int gameType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
        boolean leaderboard = false;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameType = extras.getInt("key");
            leaderboard = extras.getBoolean("leaderboard");
        }

        TextView textView = (TextView) findViewById(R.id.howTo);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "DroidSans.TTF");

        textView.setTypeface(custom_font);

        //Change text to leaderboard information.
        if (leaderboard){
            textView.setText(R.string.leader_board_how_to);
        }
        else {
            //Change text depending on current game type.
            switch (gameType) {
                case 1:
                    textView.setText(R.string.how_to_time_trial);
                    break;
                case 2:
                    textView.setText(R.string.how_to_arcade);
                    break;
                case 3:
                default:
                    textView.setText(R.string.how_to_classic);
                    break;
            }
        }
    }

}
