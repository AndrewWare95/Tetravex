package com.example.andware.tetravex.game;

import com.example.andware.tetravex.Constants;
import org.joda.time.DateTime;
/**
 * Created by andwa on 07/02/2017.
 */

public class Score {

    private int mSize = Constants.DEFAULT_BOARD_SIZE;
    private String mElapsedTime = null;
    private DateTime mDate = null;

    /**
     * Initializes a newly created Score object
     * @param boardSize the size of the board
     * @param elapsedTime String representation of the time retrieved from the Chronometer view
     * @param date the date the score was achieved
     */
    public Score(int boardSize, String elapsedTime, DateTime date) {
        mSize = boardSize;
        mElapsedTime = elapsedTime;
        mDate = date;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(int size) {
        mSize = size;
    }

    public String getElapsedTime() {
        return mElapsedTime;
    }

    public void setElapsedTime(String elapsedTime) {
        mElapsedTime = elapsedTime;
    }

    public DateTime getDate() {
        return mDate;
    }

    public void setDate(DateTime date) {
        mDate = date;
    }
}
