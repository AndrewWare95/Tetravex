package com.example.andware.tetravex;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

        //Setting text of each button
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
                Intent intent = new Intent(view.getContext(),LoginActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    //Pressing back button causes dialog box asking if you wish to enter login screen
    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selection) {
                        switch (selection) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent = new Intent(MainMenu.this ,LoginActivity.class);
                                startActivityForResult(intent, 0);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                            default:
                                break;
                        }
                    }
                };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialog_exit_to_login));
        builder.setPositiveButton(getResources().getString(R.string.dialog_ok), dialogClickListener);
        builder.setNegativeButton(getResources().getString(R.string.dialog_cancel), dialogClickListener);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });
        builder.show();
    }
}
