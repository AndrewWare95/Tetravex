package com.example.andware.tetravex.data;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andware.tetravex.Constants;
import com.example.andware.tetravex.R;
import com.example.andware.tetravex.gameManager.Game;
import com.example.andware.tetravex.gameManager.Tile;

import static android.view.View.VISIBLE;

public class BoardAdapter extends BaseAdapter {
    private Context mContext;
    private boolean mSourceBoard;
    private Game mPuzzle;
    private String mColour;

    private final int TARGET_BOARD_START_IDX = 0;

    public BoardAdapter(Context context, boolean srcBoardAdapter, Game puzzle, String colour) {
        mContext = context;
        mPuzzle = puzzle;
        mSourceBoard = srcBoardAdapter;
        mColour = colour;
    }

    @Override
    public int getCount() {
        int size = mPuzzle.getSize();
        return size * size;
    }

    @Override
    public Object getItem(int position) {
        return (mSourceBoard ?
                mPuzzle.getTileByPosition(mPuzzle.getSize(), position) :
                mPuzzle.getTileByPosition(TARGET_BOARD_START_IDX, position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // initialize the View each time since the board may have changed due to drag & drop
        View tileView;
        Tile tile;

        if(mSourceBoard){
            tile = mPuzzle.getTileByPosition(mPuzzle.getSize(), position);
        }
        else {
            tile = mPuzzle.getTileByPosition(TARGET_BOARD_START_IDX, position);
        }

        // there is a tile in this space, draw it

        if(tile != null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            int tileLayoutId = getTileLayoutId(mPuzzle.getSize());
            tileView = inflater.inflate(tileLayoutId, parent, false);
            tileView.setTag(Constants.TAG_NUMBERED_TILE);

            // set the numbers & colors from the Tile object
            this.setTileViewNumbers(tileView, tile);

            // set the touch listener to start drag events when the tile is touched
            tileView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.setVisibility(VISIBLE);

                    // return false to ignore further MotionEvents
                    // and give the parent a chance to handle MotionEvent
                    return false;
                }
            });
            // don't listen for drag events
            tileView.setOnDragListener(null);
            //tileView.setOnClickListener(null);
        }
        // no tile, draw the empty slot
        else {
            tileView = new ImageView(mContext);
            tileView.setTag(Constants.TAG_EMPTY_TILE);
            ((ImageView)tileView).setImageResource(R.drawable.empty_tile);
            tileView.setVisibility(View.VISIBLE);

            // make the empty tile listen for drag events
            tileView.setOnDragListener((View.OnDragListener)mContext);
            // don't start a drag event when the empty slot is touched
            tileView.setOnTouchListener(null);
        }

        int width = getTileWidth((GridView)parent);
        tileView.setLayoutParams(new GridView.LayoutParams(width, width));
        tileView.setPadding(0, 0, 0, 0);

        return tileView;
    }

    private int getTileWidth(GridView parent) {
        return parent.getColumnWidth();
    }

    private void setTileViewNumbers(View view, Tile tile) {
        TextView northTextView = (TextView)view.findViewById(R.id.tile_north);
        northTextView.setText(String.valueOf(tile.getNorth()));
        stylizeQuadrant(northTextView, tile.getNorth(), mPuzzle.isColor());

        TextView southTextView = (TextView)view.findViewById(R.id.tile_south);
        southTextView.setText(String.valueOf(tile.getSouth()));
        stylizeQuadrant(southTextView, tile.getSouth(), mPuzzle.isColor());

        TextView eastTextView = (TextView)view.findViewById(R.id.tile_east);
        eastTextView.setText(String.valueOf(tile.getEast()));
        stylizeQuadrant(eastTextView, tile.getEast(), mPuzzle.isColor());

        TextView westTextView = (TextView)view.findViewById(R.id.tile_west);
        westTextView.setText(String.valueOf(tile.getWest()));
        stylizeQuadrant(westTextView, tile.getWest(), mPuzzle.isColor());
    }

    private void stylizeQuadrant(TextView view, int side, boolean colorTiles) {
        if(colorTiles) {
            colorizeQuadrant(view, side);
        }
        else {
            setGrayQuadrant(view);
        }
    }

    private void setGrayQuadrant(TextView view) {
        view.setTextColor(mContext.getResources().getColor(R.color.black));
        Drawable d = view.getBackground();
        d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_bkgnd_gray),
                PorterDuff.Mode.MULTIPLY);
    }

    private void colorizeQuadrant(TextView view, int side) {
        // set text, set text color, set background color
        Drawable d = view.getBackground();


        String TAG = "BoardAdapter";
        if (mColour.matches("Normal")) {
            switch (side) {
                case 0:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_bkgnd_0),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 1:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_bkgnd_1),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 2:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_bkgnd_2),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 3:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_bkgnd_3),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 4:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_bkgnd_4),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 5:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_bkgnd_5),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 6:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_bkgnd_6),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 7:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_bkgnd_7),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 8:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_bkgnd_8),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 9:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_bkgnd_9),
                            PorterDuff.Mode.MULTIPLY);
                case 10:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_bkgnd_10),
                            PorterDuff.Mode.MULTIPLY);
                default:
                    // log the error
                    Log.e(TAG, "Unsupported number: " + String.valueOf(side));
                    break;
            }
        }

        else if (mColour.matches("Blue")) {
            switch (side) {
                case 0:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_blue_0),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 1:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_blue_1),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 2:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_blue_2),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 3:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_blue_3),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 4:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_blue_4),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 5:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_blue_5),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 6:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_blue_6),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 7:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_blue_7),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 8:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_blue_8),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 9:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_blue_9),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 10:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_blue_10),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                default:
                    // log the error
                    Log.e(TAG, "Unsupported number: " + String.valueOf(side));
                    break;
            }
        }

        else if (mColour.matches("Red")){
            switch (side) {
                case 0:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_red_0),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 1:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_red_1),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 2:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_red_2),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 3:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_red_3),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 4:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_red_4),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 5:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_red_5),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 6:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_red_6),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 7:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_red_7),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 8:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_red_8),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 9:
                    view.setTextColor(mContext.getResources().getColor(R.color.white));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_red_9),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                case 10:
                    view.setTextColor(mContext.getResources().getColor(R.color.black));
                    d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_red_10),
                            PorterDuff.Mode.MULTIPLY);
                    break;
                default:
                    // log the error
                    Log.e(TAG, "Unsupported number: " + String.valueOf(side));
                    break;
            }
        }
    }

    private int getTileLayoutId(int boardSize) {
        int layoutId;
        switch (boardSize) {
            case 2:
                layoutId = R.layout.tile_2_layout;
                break;
            case 4:
                layoutId = R.layout.tile_4_layout;
                break;
            case 5:
                layoutId = R.layout.tile_5_layout;
                break;
            case 3:
            default:
                layoutId = R.layout.tile_3_layout;
                break;
        }
        return R.layout.tile_3_layout;
    }
}
