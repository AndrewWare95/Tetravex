package com.example.andware.tetravex;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;

import java.util.Locale;

import static android.R.attr.typeface;

public class HowTo extends AppCompatActivity {
    private int gameType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gameType = extras.getInt("key");
        }

        TextView textView = (TextView) findViewById(R.id.howTo);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "PLASMATI.TTF");

        textView.setTypeface(custom_font);

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
