package com.example.andware.tetravex;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Typeface face = Typeface.createFromAsset(getAssets(), "ARCADECLASSIC.TTF");
        Button t1=(Button)findViewById(R.id.classicButton);
        Button t2=(Button)findViewById(R.id.timeTrialButton);
        Button t3=(Button)findViewById(R.id.leaderboardButton);
        Button t4=(Button)findViewById(R.id.settingsButton);
        Button t5=(Button)findViewById(R.id.exitMainButton);
        Button t6=(Button)findViewById(R.id.unknownTitleButton);
        t1.setTypeface(face);
        t2.setTypeface(face);
        t3.setTypeface(face);
        t4.setTypeface(face);
        t5.setTypeface(face);
        t6.setTypeface(face);


        Button classicButton = (Button) findViewById(R.id.classicButton);
        classicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Classic.class);
                intent.putExtra("key", 0);
                startActivityForResult(intent, 0);
            }
        });

        Button timeTrialButton = (Button) findViewById(R.id.timeTrialButton);
        timeTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Classic.class);
                intent.putExtra("key", 1);
                startActivityForResult(intent, 1);
            }
        });

        Button unknownButton = (Button) findViewById(R.id.unknownTitleButton);
        unknownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Classic.class);
                intent.putExtra("key", 2);
                startActivityForResult(intent, 2);
            }
        });

        Button leaderButton = (Button) findViewById(R.id.leaderboardButton);
        leaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),LeaderboardActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        Button settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SettingsActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        Button exitButton = (Button) findViewById(R.id.exitMainButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //closes app
                finish();
                //brings to login, but pressing back brings you to main menu again
                //Intent intent = new Intent(view.getContext(),LoginActivity.class);
                //startActivityForResult(intent, 0);
            }
        });
    }
}
