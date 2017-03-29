package com.example.andware.tetravex.gameManager;

import android.util.Log;

import com.example.andware.tetravex.R;

import java.util.ArrayList;
import java.util.Arrays;
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
    private int counter;
    private String difficulty;
    private Tile[][] mBoard;
    private int[] colourListN;
    private int[] colourListS;
    private int[] colourListE;
    private int[] colourListW;
    private boolean mColor = true;
    private PuzzleState state = PuzzleState.NEW;
    /* Random number generator */
    private final Random mRand;

    public Game(int size, String diff)
    {
        mSize = size;
        counter = 0;
        difficulty = diff;
        mBoard = new Tile[size * 2][size];
        mRand = new Random();
        initializeBoard();
    }

    private void setup(String difficulty)
    {
        if (!difficulty.matches("Easy")) {
            colourListN = new int[RAND_COLOR_RANGE];
            colourListS = new int[RAND_COLOR_RANGE];
            colourListE = new int[RAND_COLOR_RANGE];
            colourListW = new int[RAND_COLOR_RANGE];

            for (int i = 0; i < RAND_COLOR_RANGE; i++) {
                colourListN[i] = 0;
                colourListS[i] = 0;
                colourListE[i] = 0;
                colourListW[i] = 0;
            }
        }
    }
    private void setColourCounter(int n, int side){
        //Sides : North = 0, South = 1, East = 2, West = 3
        if (!difficulty.matches("Easy")) {
            if (side == 0)
                colourListN[n] = colourListN[n] + 1;
            else if (side == 1)
                colourListS[n] = colourListS[n] + 1;
            else if (side == 2)
                colourListE[n] = colourListE[n] + 1;
            else
                colourListW[n] = colourListW[n] + 1;
        }
    }

    private void checkUniqueEdges(){
        Log.d("TESTING", "             ");
        Log.d("TESTING", "N"+Arrays.toString(colourListN));
        Log.d("TESTING", "S"+Arrays.toString(colourListS));
        Log.d("TESTING", "E"+Arrays.toString(colourListE));
        Log.d("TESTING", "W"+Arrays.toString(colourListW));
        boolean fixNorth = false;
        boolean fixEast = false;
        boolean fixSouth = false;
        boolean fixWest = false;
        for (int i = 0; i < RAND_COLOR_RANGE; i++) {
            if (colourListN[i] >= 1 && colourListS[i] == 0) {
                fixSouth = true;
            }
            if (colourListE[i] >= 1 && colourListW[i] == 0) {
                fixWest = true;
            }
            if (colourListS[i] >= 1 && colourListN[i] == 0) {
                fixNorth = true;
            }
            if (colourListW[i] >= 1 && colourListE[i] == 0) {
                fixEast = true;
            }
        }
        if (fixNorth || fixSouth){
            for (int i = 0; i < mSize; i++){
                int n = mRand.nextInt(RAND_COLOR_RANGE);
                mBoard[mSize - 1][mSize - 1 - i].setSouth(n);
                mBoard[0][i].setNorth(n);
                colourListS[n]++;
                colourListN[n]++;
            }
        }
        if (fixEast || fixWest){
            for (int i = 0; i < mSize; i++){
                int n = mRand.nextInt(RAND_COLOR_RANGE);
                mBoard[i][0].setWest(n);
                mBoard[mSize-1-i][mSize-1].setEast(n);
                colourListE[n]++;
                colourListW[n]++;
            }
        }
        if (difficulty.matches("Easy")){
            uniqueEdges(2);
        }
        else if (difficulty.matches("Medium")){
            uniqueEdges(1);
        }
    }

    private void uniqueEdges(int num){
        ArrayList<Integer> uniqueNumN = new ArrayList<>();
        ArrayList<Integer> uniqueNumE = new ArrayList<>();
        ArrayList<Integer> uniqueNumS = new ArrayList<>();
        ArrayList<Integer> uniqueNumW = new ArrayList<>();
        for(int i = 0; i < RAND_COLOR_RANGE; i++){
            if (colourListN[i] >= 1 && colourListS[i] == 1) {
                uniqueNumS.add(i);
            }
            if (colourListS[i] >= 1 && colourListN[i] == 1) {
                uniqueNumN.add(i);
            }
            if (colourListE[i] >= 1 && colourListW[i] == 1) {
                uniqueNumW.add(i);
            }
            if (colourListW[i] >= 1 && colourListE[i] == 1) {
                uniqueNumE.add(i);
            }
        }
        for(int z = 0; z < num; z++){
            int randomEdge = mRand.nextInt(mSize);
            if (!uniqueNumN.isEmpty()) {
                int n = mRand.nextInt(uniqueNumN.size());
                mBoard[0][randomEdge].setNorth(n);
                uniqueNumN.clear();
            }
            else if (!uniqueNumE.isEmpty()) {
                int n = mRand.nextInt(uniqueNumE.size());
                mBoard[mSize - 1 - randomEdge][mSize - 1].setEast(n);
                uniqueNumE.clear();
            }
            else if (!uniqueNumS.isEmpty()) {
                int n = mRand.nextInt(uniqueNumS.size());
                mBoard[mSize - 1][mSize - 1 - randomEdge].setSouth(n);
                uniqueNumS.clear();
            }
            else if (!uniqueNumW.isEmpty()) {
                int n = mRand.nextInt(uniqueNumW.size());
                mBoard[randomEdge][0].setWest(n);
                uniqueNumW.clear();
            }
        }
    }

    private void initializeBoard() {
        setup(difficulty);
        for (int x = 0; x < mSize; x++) {
            for (int y = 0; y < mSize; y++) {
                mBoard[x][y] = new Tile(x, y);
            }
        }
        /**
        for (int x = 0; x < mSize; x++){
            for (int y = 0; y <mSize; y++){
                int n = mRand.nextInt(RAND_COLOR_RANGE);
                mBoard[x][y].setNorth(n);
                mBoard[x][y].setEast(n);
                mBoard[x][y].setWest(n);
                mBoard[x][y].setSouth(n);
                mBoard[x][y].setNorth1(n);
                mBoard[x][y].setNorth2(n);
            }
        }**/

        /**room for algorithm for triangle version here **/

        /* Pick random colours for edges */
        for (int y = 0; y < mSize; y++) {
            for (int x = 0; x <= mSize; x++) {
                // random int in [0,RAND_COLOR_RANGE)
                int n = mRand.nextInt(RAND_COLOR_RANGE);
                if (x - 1 >= 0) {
                    setColourCounter(n,1);
                    mBoard[x -1][y].setSouth(n);
                }
                if (x < mSize) {
                    setColourCounter(n,0);
                    mBoard[x][y].setNorth(n);
                }
            }
        }
        for (int y = 0; y <= mSize; y++) {
            for (int x = 0; x < mSize; x++) {
                int n = mRand.nextInt(RAND_COLOR_RANGE);
                if (y - 1 >= 0) {
                    setColourCounter(n,2);
                    mBoard[x][y-1].setEast(n);
                }
                if (y < mSize) {
                    setColourCounter(n,3);
                    mBoard[x][y].setWest(n);
                }
            }
        }

        /**for (int x = 0; x < mSize; x++) {
            for (int y = 0; y <= mSize; y++) {
                // random int in [0,RAND_COLOR_RANGE)
                int n = mRand.nextInt(RAND_COLOR_RANGE);
                if (y - 1 >= 0) {
                    setColourCounter(n,1);
                    mBoard[x][y-1].setSouth(n);
                }
                if (y < mSize) {
                    setColourCounter(n,0);
                    mBoard[x][y].setNorth(n);
                }
            }
        }
        for (int x = 0; x <= mSize; x++) {
            for (int y = 0; y < mSize; y++) {
                int n = mRand.nextInt(RAND_COLOR_RANGE);
                if (x - 1 >= 0) {
                    setColourCounter(n,2);
                    mBoard[x-1][y].setEast(n);
                }
                if (x < mSize) {
                    setColourCounter(n,3);
                    mBoard[x][y].setWest(n);
                }
            }
        }**/
        /**if (isSolved()){
            counter++;
        }**/

        if (!difficulty.matches("Easy")) {
            checkUniqueEdges();
        }

        /* Pick up the tiles... */
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        Tile [] test = new Tile[mSize*mSize];
        int count = 0;
        for (int x = 0; x < mSize; x++) {
            for (int y = 0; y < mSize; y++) {
                tiles.add(mBoard[x][y]);
                //LOOK AT NOTES
                test[count] = mBoard[x][y];
                count++;
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
        //permuteHelper(test, 0);
        //Log.d("TESTCOUNTER", ""+counter);
    }

    private void permuteHelper(Tile[] arr, int index){

        if(index >= arr.length - 1){ //If we are at the last element - nothing left to permute
            //System.out.println(Arrays.toString(arr));
            //Print the array
            //System.out.print("[");
            for(int i = 0; i < arr.length - 1; i++){
                //System.out.print(arr[i] + ", ");

                int x = i / mSize;
                int y = i % mSize;
                mBoard[x][y] = arr[index];
            }

            if(arr.length > 0)
                //System.out.print(arr[arr.length - 1]);
            //System.out.println("]");

            if (isSolved()){
                counter++;
            }
            for (int h=0;h<mSize;h++)
            {
                for (int l=0;l<mSize;l++){
                    mBoard[h][l] = null;
                }
            }
            return;
        }
        for(int i = index; i < arr.length; i++){ //For each index in the sub array arr[index...end]
            //Swap the elements at indices index and i
            Tile t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;

            int x = i / mSize;
            int y = i % mSize;
            mBoard[x][y] = t;
            //setTile();

            if (isSolved()){
                counter++;
            }

            //Recurse on the sub array arr[index+1...end]
            permuteHelper(arr, index+1);
            //Swap the elements back
            t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;
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
                break;
        }
        return pair;
    }
}
