package com.example.andware.tetravex.gameManager;

public class Tile
{
    /* Edge colors */
    private int mNorth;
    private int mWest;
    private int mEast;
    private int mSouth;
    private int mNorth1;
    private int mNorth2;

    /* Solution location */
    private int mX;
    private int mY;

    public Tile (int x, int y)
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

    public void setNorth(int north) {
        this.mNorth = north;
    }

    public int getWest() {
        return mWest;
    }

    public void setWest(int mWest) {
        this.mWest = mWest;
    }

    public int getEast() {
        return mEast;
    }

    public void setEast(int mEast) {
        this.mEast = mEast;
    }

    public int getSouth() {
        return mSouth;
    }

    public void setSouth(int mSouth) {
        this.mSouth = mSouth;
    }

    public int getNorth1() {
        return mNorth1;
    }

    public void setNorth1(int north) {
        this.mNorth1 = north;
    }

    public int getNorth2() {
        return mNorth2;
    }

    public void setNorth2(int north) {
        this.mNorth2 = north;
    }
}