package com.example.andware.tetravex.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the data needed for a Tetravex puzzle
 */
public class Game
{
    private final int RAND_COLOR_RANGE = 9;

    public enum PuzzleState {
        NEW,
        IN_PROGRESS,
        PAUSED,
        COMPLETED
    }

    private final int NORTH = 0;
    private final int EAST  = 1;
    private final int SOUTH = 2;
    private final int WEST  = 3;

    private int mSize;
    private Tile[][] mBoard;
    private boolean mColor = true;
    private PuzzleState state = PuzzleState.NEW;
    /* Random number generator */
    private final Random mRand;

    public Game(int size)
    {
        mSize = size;
        mBoard = new Tile[size * 2][size];
        mRand = new Random();
        initializeBoard();
    }

    private void initializeBoard() {
        for (int x = 0; x < mSize; x++) {
            for (int y = 0; y < mSize; y++) {
                mBoard[x][y] = new Tile(x, y);
            }
        }

        /*for (int x = 0; x < mSize; x++){
            for (int y = 0; y <mSize; y++){
                int n = mRand.nextInt(RAND_COLOR_RANGE);
                mBoard[x][y].setNorth(n);
                mBoard[x][y].setEast(n);
                mBoard[x][y].setWest(n);
            }
        }*/

        /**room for algorithm for triangle version here **/

        /* Pick random colours for edges */
        for (int x = 0; x < mSize; x++) {
            for (int y = 0; y <= mSize; y++) {
                // random int in [0,RAND_COLOR_RANGE)
                int n = mRand.nextInt(RAND_COLOR_RANGE);
                if (y - 1 >= 0)
                    mBoard[x][y - 1].setSouth(n);
                if (y < mSize)
                    mBoard[x][y].setNorth(n);
            }
        }
        for (int x = 0; x <= mSize; x++) {
            for (int y = 0; y < mSize; y++) {
                int n = mRand.nextInt(RAND_COLOR_RANGE);
                if (x - 1 >= 0)
                    mBoard[x - 1][y].setEast(n);
                if (x < mSize)
                    mBoard[x][y].setWest(n);
            }
        }

        /* Pick up the tiles... */
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for (int x = 0; x < mSize; x++) {
            for (int y = 0; y < mSize; y++) {
                tiles.add(mBoard[x][y]);
                mBoard[x][y] = null;
            }
        }

        /* ...and place then randomly on the right hand side */
        for (int x = 0; x < mSize; x++) {
            for (int y = 0; y < mSize; y++) {
                int n = mRand.nextInt(tiles.size());
                // get the nth tile from the list
                Tile tile = tiles.get(n);
                mBoard[x + mSize][y] = tile;
                tiles.remove (tile);
            }
        }
    }

    public int getSize() {
        return mSize;
    }

    public void setColor(boolean value) {
        mColor = value;
    }

    public boolean isColor() {
        return mColor;
    }

    public PuzzleState getState() {
        return state;
    }

    public void setState(PuzzleState state) {
        this.state = state;
    }

    public Tile getTileByPosition(int xStartIdx, int position) {
        int posCursor = 0;
        boolean matchFound = false;
        Tile tile = null;
        // check bounds on position first
        if(position >= 0 || position < (mSize * mSize)) {
            for (int x = xStartIdx; x < mSize * 2; x++) {
                for (int y = 0; y < mSize; y++) {
                    if (posCursor == position) {
                        tile = mBoard[x][y];
                        matchFound = true;
                        break;
                    }
                    else {
                        posCursor++;
                    }
                }
                if(matchFound) {
                    break;
                }
            }
        }
        return tile;
    }

    public void setTile(int xStartIdx, int position, Tile tile) {
        int posCursor = 0;
        boolean tileSet = false;
        // check bounds on position first
        if(position >= 0 || position < (mSize * mSize)) {
            for (int x = xStartIdx; x < mSize * 2; x++) {
                for (int y = 0; y < mSize; y++) {
                    if (posCursor == position) {
                        mBoard[x][y] = tile;
                        tileSet = true;
                        break;
                    }
                    else {
                        posCursor++;
                    }
                }
                if(tileSet) {
                    break;
                }
            }
        }
    }

    public boolean isSolved() {
        for (int x = 0; x < mSize; x++) {
            for (int y = 0; y < mSize; y++) {
                Tile tile = mBoard[x][y];

                // Return as soon as we know the result
                if(y > 0) { // check neighbor to the west
                    if(!neighborIsPair(tile, mBoard[x][y-1], WEST)) {
                        return false;
                    }
                }
                if(x > 0) { // check neighbor to the north
                    if(!neighborIsPair(tile, mBoard[x-1][y], NORTH)) {
                        return false;
                    }
                }
                if(y < mSize - 1) { // check neighbor to the east
                    if(!neighborIsPair(tile, mBoard[x][y+1], EAST)) {
                        return false;
                    }
                }
                if(x < mSize - 1) { // check neighbor to the south
                    if(!neighborIsPair(tile, mBoard[x+1][y], SOUTH)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean neighborIsPair(Tile tile, Tile neighbor, int side) {
        // initial guard clause
        if(tile == null || neighbor == null) {
            return false;
        }
        boolean pair = false;
        switch (side) {
            case NORTH:
                if(tile.getNorth() == neighbor.getSouth()) {
                    pair = true;
                }
                break;
            case EAST:
                if(tile.getEast() == neighbor.getWest()) {
                    pair = true;
                }
                break;
            case SOUTH:
                if(tile.getSouth() == neighbor.getNorth()) {
                    pair = true;
                }
                break;
            case WEST:
                if(tile.getWest() == neighbor.getEast()) {
                    pair = true;
                }
                break;
            default:
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid side given for comparison: ");
                sb.append(side);
                Log.e("TetravexAndroid", sb.toString());
                break;
        }
        return pair;
    }
}
