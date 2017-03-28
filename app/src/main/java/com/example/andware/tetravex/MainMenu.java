package com.example.andware.tetravex;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        username = "Username Error";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
        }

        Typeface face = Typeface.createFromAsset(getAssets(), "ARCADECLASSIC.TTF");
        Button classicButton=(Button)findViewById(R.id.classicButton);
        Button timeTrialButton=(Button)findViewById(R.id.timeTrialButton);
        Button unknownButton=(Button)findViewById(R.id.unknownTitleButton);
        Button leaderButton=(Button)findViewById(R.id.leaderboardButton);
        Button settingsButton=(Button)findViewById(R.id.settingsButton);
        Button exitButton=(Button)findViewById(R.id.exitMainButton);
        classicButton.setTypeface(face);
        timeTrialButton.setTypeface(face);
        unknownButton.setTypeface(face);
        leaderButton.setTypeface(face);
        settingsButton.setTypeface(face);
        exitButton.setTypeface(face);


        classicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Classic.class);
                intent.putExtra("username", username);
                intent.putExtra("key", 0);
                startActivityForResult(intent, 0);
            }
        });

        timeTrialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Classic.class);
                intent.putExtra("username", username);
                intent.putExtra("key", 1);
                startActivityForResult(intent, 1);
            }
        });

        unknownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),Classic.class);
                intent.putExtra("username", username);
                intent.putExtra("key", 2);
                startActivityForResult(intent, 2);
            }
        });

        leaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),LeaderboardMenu.class);
                startActivityForResult(intent, 0);
            }
        });


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SettingsActivity.class);
                startActivityForResult(intent, 0);
            }
        });


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
