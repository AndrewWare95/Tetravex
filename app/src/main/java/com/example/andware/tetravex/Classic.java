package com.example.andware.tetravex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andware.tetravex.gameManager.Game;
import com.example.andware.tetravex.gameManager.Tile;
import com.example.andware.tetravex.data.BoardAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Classic extends Activity implements View.OnTouchListener, View.OnDragListener
{
    static final int PAUSE_REQUEST = 1;
    private Game mPuzzle;
    private int gameType = 0;
    private GridView mTargetGridView;
    private GridView mSourceGridView;
    private Tile mDraggedTile = null;
    private int mDraggedTileStartingX = -1;
    private int mDraggedTilePosition = -1;
    private CountDownTimer cT;
    private SimpleDateFormat sdf;
    private String currentDateAndTime;
    private long timeRemaining;
    private long time;
    private String minutes;
    private String username;
    private String sec;
    private String type;
    private String actualTime;
    private int seconds;
    private int counter = 0;
    private Chronometer mTimer;
    private long mPausedTime = 0;
    public DatabaseManager myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDb = new DatabaseManager(this);

        TextView puzzleCounter, puzzleCounterValue;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //TODO value only passed through once
            username = extras.getString("username");
            gameType = extras.getInt("key");
            if (gameType == 2 || gameType == 3){
                counter = extras.getInt("counter");
            }
        }

        switch (gameType){
            case 1:
                setContentView(R.layout.activity_time_trial);
                type = "Time Trial";
                break;
            case 2:
                type = "Arcade";
                setContentView(R.layout.activity_arcade);
                puzzleCounter = (TextView) findViewById(R.id.puzzles_completed_text);
                puzzleCounterValue = (TextView) findViewById(R.id.puzzles_completed_value);
                puzzleCounter.setText((R.string.puzzles_completed));
                puzzleCounterValue.setText(Integer.toString(counter));
                break;
            case 3:
                type = "Arcade";
                setContentView(R.layout.activity_arcade);
                puzzleCounter = (TextView) findViewById(R.id.puzzles_completed_text);
                puzzleCounter.setText(R.string.puzzles_completed);
                puzzleCounterValue = (TextView) findViewById(R.id.puzzles_completed_value);
                puzzleCounterValue.setText(Integer.toString(counter));
                break;
            case 4:
                default:
                    type = "Classic";
                    setContentView(R.layout.activity_classic);
                    mTimer = (Chronometer) findViewById(R.id.timer);
                    time = SystemClock.elapsedRealtime()-mTimer.getBase();

                break;
        }

        // get the size of the board from the saved setting
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        //TODO
        String boardSize = settings.getString(getString(R.string.pref_size_key),
                Constants.DEFAULT_SIZE);
        // access the setting to decide whether or not to color the tiles
        boolean colorTiles = settings.getBoolean(getString(R.string.pref_color_key), true);
        //TODO

        mPuzzle = new Game(Integer.valueOf(boardSize));
        mPuzzle.setColor(colorTiles);

        mTargetGridView = (GridView) findViewById(R.id.target_board);
        mSourceGridView = (GridView) findViewById(R.id.source_board);

        formatTargetBoard();
        formatSourceBoard();

        if (gameType == 1){
            countDownTimer(10000);
            //TODO change above value
        }
        else if (gameType == 2){
            countDownTimer(30000);
            //TODO change above value
        }
        else if (gameType == 3){
            assert extras != null;
            timeRemaining = extras.getLong("timeRemain");
            countDownTimerGame();
        }
    }

    public void addToLeaderboard(){
        int size = mPuzzle.getSize();
        String grid = size + " x " + size;
        long ss, mm;
        mm = time/60;
        ss = time%60;
        if(mm==0){actualTime = "00:";}
        else{actualTime = ""+mm+":";}

        if(ss < 10){actualTime +="0"+ss;}
        else{actualTime +=""+ss;}
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String difficulty = settings.getString(getString(R.string.pref_difficulty_key), Constants.DEFAULT_DIFFICULTY);
        String shape = settings.getString(getString(R.string.pref_shape_key), Constants.DEFAULT_SHAPE);
        boolean isInserted;
        if (type.matches("Classic")){
             isInserted = myDb.insertData(username, actualTime, currentDateAndTime, difficulty, grid, shape, type, time);
        }
        else if (type.matches("Time Trial")){
            String formattedTime = formatTime(String.valueOf(timeRemaining));
            isInserted = myDb.insertData(username, formattedTime, currentDateAndTime, difficulty, grid, shape, type, timeRemaining);
        }
        else {
            isInserted = myDb.insertData(username, String.valueOf(counter), currentDateAndTime, difficulty, grid, shape, type, counter);
        }

        /**if (isInserted){
            Toast.makeText(Classic.this, "Data Inserted", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(Classic.this, "It didn't work", Toast.LENGTH_LONG).show();
        }**/
    }

    public String formatTime(String time){
        int t, seconds, milliseconds;
        t = Integer.parseInt(time);
        seconds = t/1000;
        milliseconds = t%1000;
        time = ""+seconds+"."+milliseconds+"s";
        return time;
    }

    private void countDownTimerGame() {
        cT = new CountDownTimer(timeRemaining, 1000) {
            TextView textView = (TextView) findViewById(R.id.timer);
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                minutes = String.format("%02d", millisUntilFinished/60000);
                seconds = (int)( (millisUntilFinished%60000)/1000);
                sec = String.format("%02d",seconds);
                textView.setText("" +minutes+":"+sec);
            }
            @Override
            public void onFinish() {
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                currentDateAndTime = sdf.format(new Date());
                addToLeaderboard();
                textView.setText("Number of puzzles completed : "+counter);
                cancel();
            }
        };
        cT.start();
    }

    private void countDownTimer(long time){
        cT =  new CountDownTimer(time, 1000) {
            TextView textView = (TextView) findViewById(R.id.timer);
            public void onTick(long millisUntilFinished) {
                timeRemaining = millisUntilFinished;
                minutes = String.format("%02d", millisUntilFinished/60000);
                seconds = (int)( (millisUntilFinished%60000)/1000);
                sec = String.format("%02d",seconds);
                textView.setText("" +minutes+":"+sec);
            }
            public void onFinish() {
                textView.setText(R.string.game_over);
                //showPuzzleFailedToast();
                //// TODO: 22/02/2017
                showButtonsOnCompleted();
                cancel();
            }
        };
        cT.start();
    }

    @Override
    public void onPause(){
        super.onPause();
        if (mPuzzle.getState().equals(Game.PuzzleState.IN_PROGRESS)) {
            mPuzzle.setState(Game.PuzzleState.PAUSED);
            pauseTimer();
        }
    }

    private void pauseTimer() {
        mTimer.stop();
        mPausedTime = SystemClock.elapsedRealtime() - mTimer.getBase();
        mPuzzle.setState(Game.PuzzleState.PAUSED);
    }

    public void pauseButtonClicked(View view) {
        if (mPuzzle.getState().equals(Game.PuzzleState.IN_PROGRESS)) {
            pauseTimer();
        }
        Intent intent = new Intent(this, PauseActivity.class);
        startActivityForResult(intent, 1);
    }

    private void resumeTimer() {
        mPuzzle.setState(Game.PuzzleState.IN_PROGRESS);
        mTimer.setBase(SystemClock.elapsedRealtime() - mPausedTime);
        mTimer.start();
    }

    @Override
    public void onResume(){
        super.onResume();
        if (mPuzzle.getState().equals(Game.PuzzleState.PAUSED)) {
            mPuzzle.setState(Game.PuzzleState.IN_PROGRESS);
            resumeTimer();
        }
    }

    private void puzzleSolvedActions() {
        if (gameType == 1){//time trial
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            currentDateAndTime = sdf.format(new Date());
            addToLeaderboard();
            cT.cancel();
            mPuzzle.setState(Game.PuzzleState.COMPLETED);
            // set the tiles to not be draggable
            mTargetGridView.setOnTouchListener(null);
            showPuzzleSolvedToast();
            showButtonsOnCompleted();
        }
        else if (gameType == 2 || gameType == 3){//arcade
            counter++;
            mPuzzle.setState(Game.PuzzleState.COMPLETED);
            mTargetGridView.setOnTouchListener(null);
            Intent intent = new Intent(this, Classic.class);
            intent.putExtra("key", 3);
            intent.putExtra("username", username);
            intent.putExtra("counter", counter);
            intent.putExtra("timeRemain", timeRemaining);
            startActivity(intent);
            finish();
            if (gameType == 3){
                cT.cancel();
            }
        }
        else{
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            currentDateAndTime = sdf.format(new Date());
            Log.d("TESTING", mTimer.toString());
            addToLeaderboard();
            mTimer.stop();
            mPuzzle.setState(Game.PuzzleState.COMPLETED);
            // set the tiles to not be draggable
            mTargetGridView.setOnTouchListener(null);
            showPuzzleSolvedToast();
            showButtonsOnCompleted();
        }
    }

    private void showPuzzleSolvedToast() {
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(getLayoutInflater().inflate(R.layout.toast_complete, null));
        toast.show();
    }

    private void showPuzzleFailedToast(){
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(getLayoutInflater().inflate(R.layout.toast_failed, null));
        toast.show();
    }

    private void showButtonsOnCompleted() {
        LinearLayout buttons = (LinearLayout) findViewById(R.id.puzzle_buttons);
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        fadeIn.setDuration(Constants.BUTTONS_FADE_IN_MS);
        buttons.startAnimation(fadeIn);
        buttons.setVisibility(View.VISIBLE);
    }

    public void newGameButtonClicked(View view) {
        Intent intent = new Intent(this, Classic.class);
        switch (gameType){
            case 1: //time trial
                //String time = cT.toString();
                //cT.cancel();
                intent.putExtra("key", 1);
                break;
            case 4:
            default:
                mTimer.stop();
                break;
        }
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    public void helpButton(View view){
        Intent intent = new Intent(this, HowTo.class);
        intent.putExtra("key", gameType);
        startActivity(intent);
    }


    public void quitGameClicked(View view) {
        // confirm with dialog
        DialogInterface.OnClickListener dialogClickListener =
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                if (gameType == 1 || gameType == 2 || gameType == 3){
                                    cT.cancel();
                                }
                                else{mTimer.stop();}
                                finish();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE: // fall-through
                            default:
                                break;
                        }
                    }
                };
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialog_quit_game_prompt));
        builder.setPositiveButton(getResources().getString(R.string.dialog_ok), dialogClickListener);
        builder.setNegativeButton(getResources().getString(R.string.dialog_cancel), dialogClickListener);
        builder.show();
    }

    /**
     * Sets the adapter and number of columns for the target board GridView
     */
    private void formatTargetBoard() {
        mTargetGridView.setAdapter(new BoardAdapter(this, false, mPuzzle));
        mTargetGridView.setNumColumns(mPuzzle.getSize());
        mTargetGridView.setOnTouchListener(this);
    }

    /**
     * Set the adapter and number of columns for the source board GridView
     */
    private void formatSourceBoard() {
        mSourceGridView.setAdapter(new BoardAdapter(this, true, mPuzzle));
        mSourceGridView.setNumColumns(mPuzzle.getSize());
        mSourceGridView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            GridView parent = (GridView) v;

            int x = (int) event.getX();
            int y = (int) event.getY();
            int position = parent.pointToPosition(x, y);
            int relativePosition = position - parent.getFirstVisiblePosition();
            final View target = parent.getChildAt(relativePosition);

            // drag only numbered tiles
            if (target != null &&
                    target.getTag().equals(Constants.TAG_NUMBERED_TILE)) {
                // start a drag event - on a separate thread to not interfere with the
                // touch event already in progress

                target.post(new Runnable() {
                    @Override
                    public void run() {
                        ClipData data = ClipData.newPlainText("DragData", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(target);
                        target.startDrag(data, shadowBuilder, target, 0);
                        target.setVisibility(View.INVISIBLE);
                    }
                });

                mDraggedTilePosition = parent.getPositionForView(target);
                if (parent.equals(mSourceGridView)) {
                    mDraggedTileStartingX = mPuzzle.getSize();
                } else {
                    mDraggedTileStartingX = 0;
                }
                mDraggedTile = mPuzzle.getTileByPosition(mDraggedTileStartingX, mDraggedTilePosition);
                mPuzzle.setTile(mDraggedTileStartingX, mDraggedTilePosition, null);

                // draw an empty slot where the dragged tile just was
                ((BoardAdapter) parent.getAdapter()).notifyDataSetChanged();

                // start the timer the first time a tile is touched
                if (gameType == 0) {
                    if (mPuzzle.getState().equals(Game.PuzzleState.NEW)) {
                        mPuzzle.setState(Game.PuzzleState.IN_PROGRESS);
                        mTimer.setBase(SystemClock.elapsedRealtime());
                        mTimer.start();
                    }
                }
            }
            // report that the event was consumed
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        // Handles each of the expected events
        switch (event.getAction()) {

            //signal for the start of a drag and drop operation.
            case DragEvent.ACTION_DRAG_STARTED:
                break;

            //the drag point has entered the bounding box of the View
            case DragEvent.ACTION_DRAG_ENTERED:
                ((ImageView) v).setColorFilter(Color.YELLOW);
                break;

            //the user has moved the drag shadow outside the bounding box of the View
            case DragEvent.ACTION_DRAG_EXITED:
                ((ImageView) v).clearColorFilter();
                break;

            //drag shadow has been released,the drag point is within the bounding box of the View
            case DragEvent.ACTION_DROP:
                GridView targetGrid = (GridView) v.getParent();
                int index = targetGrid.indexOfChild(v);

                int droppedStartIndex;
                if (targetGrid.equals(mSourceGridView)) {
                    droppedStartIndex = mPuzzle.getSize();
                } else {
                    droppedStartIndex = 0;
                }
                mPuzzle.setTile(droppedStartIndex, index, mDraggedTile);
                ((BoardAdapter) targetGrid.getAdapter()).notifyDataSetChanged();

                // validate the board now the tile has been dropped
                if (mPuzzle.isSolved()) {
                    puzzleSolvedActions();
                }
                break;

            //the drag and drop operation has concluded.
            case DragEvent.ACTION_DRAG_ENDED:
                if (!event.getResult()) {
                    // tile dropped in an invalid area of the screen; put it back
                    mPuzzle.setTile(mDraggedTileStartingX, mDraggedTilePosition, mDraggedTile);
                    ((BoardAdapter) mTargetGridView.getAdapter()).notifyDataSetChanged();
                    ((BoardAdapter) mSourceGridView.getAdapter()).notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        if (mPuzzle.getState().equals(Game.PuzzleState.IN_PROGRESS)) {
            pauseTimer();
            DialogInterface.OnClickListener dialogClickListener =
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int selection) {
                            switch (selection) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    if (gameType == 1 || gameType == 2 || gameType == 3){
                                        cT.cancel();
                                    }
                                    else{mTimer.stop();}
                                    finish();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                default:
                                    resumeTimer();
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
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    resumeTimer();
                }
            });
            builder.show();
        }
        else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PAUSE_REQUEST) {
            // handle result codes from the PauseActivity
            if (resultCode == PauseActivity.RESULT_NEW_GAME) {
                Intent intent = new Intent(this, Classic.class);
                startActivity(intent);
                finish();
            } else if (resultCode == PauseActivity.RESULT_QUIT_GAME) {
                // Go back to the dashboard
                finish();
            }
        }
    }

}
