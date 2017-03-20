package com.example.andware.tetravex;

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
        classicButton.setTypeface(face);
        timeTrialButton.setTypeface(face);
        arcadeButton.setTypeface(face);


        classicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),LeaderboardActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        timeTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),LeaderboardActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        arcadeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),LeaderboardActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
