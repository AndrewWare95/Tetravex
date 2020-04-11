package com.andware.tetravex;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class PauseActivity extends AppCompatActivity {

    public static final int RESULT_NEW_GAME   = 2;

    public static final int RESULT_QUIT_GAME   = 3;
    public DatabaseManager myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pause2);
        myDb = new DatabaseManager(this);
    }


    //Resume finishes pause activity and resumes current game.
    public void resumeButtonClicked(View view) {
        finish();
    }


    //New game button clicked, create new game and add to unfinished counter.
    public void newGameButtonClickedUnfinished(View view) {
        // confirm with dialog
        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selection) {
                        switch (selection) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent = getIntent();
                                setResult(RESULT_NEW_GAME, intent);
                                addUnsolvedPuzzle();
                                finish();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE: // fall-through
                            default:
                                break;
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialog_restart_game));
        builder.setPositiveButton(getResources().getString(R.string.dialog_ok), dialogClickListener);
        builder.setNegativeButton(getResources().getString(R.string.dialog_cancel), dialogClickListener);
        builder.show();
    }


    //Quit game and exit to main menu, add to unfinished puzzle counter
    public void quitGameButtonClicked(View view) {
        // confirm with dialog
        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Intent intent = getIntent();
                                setResult(RESULT_QUIT_GAME, intent);
                                addUnsolvedPuzzle();
                                finish();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE: // fall-through
                            default:
                                break;
                        }
                    }
                };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialog_quit_game_prompt));
        builder.setPositiveButton(getResources().getString(R.string.dialog_ok),
                dialogClickListener);
        builder.setNegativeButton(getResources().getString(R.string.dialog_cancel),
                dialogClickListener);
        builder.show();
    }

    //Method used to increment unfinished puzzle counter and modify database.
    public void addUnsolvedPuzzle(){
        Bundle extras = getIntent().getExtras();
        String username;
        if (extras != null) {
            username = extras.getString("username");
            Cursor todoCursor = myDb.getUnfinishedPuzzleData(username);
            while (todoCursor.moveToNext()) {
                int unfinishedPuzzles = todoCursor.getInt(0);
                unfinishedPuzzles++;
                myDb.modifyUnfinishedPuzzleInfo(username, unfinishedPuzzles);
            }
        }
    }

}
