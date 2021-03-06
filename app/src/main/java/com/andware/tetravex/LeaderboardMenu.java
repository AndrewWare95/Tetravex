package com.andware.tetravex;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LeaderboardMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard_menu);

        Typeface face = Typeface.createFromAsset(getAssets(), "ARCADECLASSIC.TTF");
        Button classicButton=(Button)findViewById(R.id.classicButtonLeaderboard);
        Button timeTrialButton=(Button)findViewById(R.id.timeTrialLeaderboard);
        Button arcadeButton=(Button)findViewById(R.id.arcadeButtonLeaderboard);
        Button unfinishedButton=(Button)findViewById(R.id.unfinishedLeaderboard);
        classicButton.setTypeface(face);
        timeTrialButton.setTypeface(face);
        arcadeButton.setTypeface(face);
        unfinishedButton.setTypeface(face);


        classicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),LeaderboardActivity.class);
                intent.putExtra("leader", 0);
                startActivityForResult(intent, 0);
            }
        });

        timeTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),LeaderboardActivity.class);
                intent.putExtra("leader", 1);
                startActivityForResult(intent, 0);
            }
        });

        arcadeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),LeaderboardActivity.class);
                intent.putExtra("leader", 2);
                startActivityForResult(intent, 0);
            }
        });

        unfinishedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),LeaderboardActivity.class);
                intent.putExtra("leader", 3);
                startActivityForResult(intent, 0);
            }
        });
    }
}
