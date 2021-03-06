package com.andware.tetravex.gameManager;

public class Tile
{
    /* Edge colors */
    private int mNorth;
    private int mWest;
    private int mEast;
    private int mSouth;

    /* Solution location */
    private int mX;
    private int mY;

    Tile(int x, int y)
    {
        this.mX = x;
        this.mY = y;
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int getNorth() {
        return mNorth;
    }

    void setNorth(int north) {
        this.mNorth = north;
    }

    public int getWest() {
        return mWest;
    }

    void setWest(int mWest) {
        this.mWest = mWest;
    }

    public int getEast() {
        return mEast;
    }

    void setEast(int mEast) {
        this.mEast = mEast;
    }

    public int getSouth() {
        return mSouth;
    }

    void setSouth(int mSouth) {
        this.mSouth = mSouth;
    }

}