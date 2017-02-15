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
import com.example.andware.tetravex.game.Game;
import com.example.andware.tetravex.game.Tile;

/**
 * Adapter class for the target board and source board GridViews
 */
public class BoardAdapter extends BaseAdapter {
    private Context mContext;
    private boolean mSourceBoard;
    private Game mPuzzle;

    private final int TARGET_BOARD_START_IDX = 0;
    private final String TAG = "BoardAdapter";

    public BoardAdapter(Context context, boolean srcBoardAdapter, Game puzzle) {
        mContext = context;
        mPuzzle = puzzle;
        mSourceBoard = srcBoardAdapter;
    }

    @Override
    public int getCount() {
        int size = mPuzzle.getSize();
        return size * size;
    }

    @Override
    public Object getItem(int position) {
        Tile tile = (mSourceBoard ?
                mPuzzle.getTileByPosition(mPuzzle.getSize(), position) :
                mPuzzle.getTileByPosition(TARGET_BOARD_START_IDX, position));
        return tile;
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

            /**this can be changed, taking 2 parameters (size + shape) for later.*/
            int tileLayoutId = getTileLayoutId(mPuzzle.getSize());
            tileView = inflater.inflate(tileLayoutId, parent, false);
            tileView.setTag(Constants.TAG_NUMBERED_TILE);

            // set the numbers & colors from the Tile object
            this.setTileViewNumbers(tileView, tile);

            // set the touch listener to start drag events when the tile is touched
            tileView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // return false to ignore further MotionEvents
                    // and give the parent a chance to handle MotionEvent
                    return false;
                }
            });
            // don't listen for drag events
            tileView.setOnDragListener(null);
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

    /**
     * Get the width for a Tile's View
     * @param parent the parent GridView object
     * @return the measured width for the Tile
     */
    private int getTileWidth(GridView parent) {
        int width;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            width = parent.getColumnWidth();
        }
        else {
            // workaround since getColumnWidth() is not in older APIs
            // assumes the width is the same for all children
            int children = parent.getChildCount();
            if (children > 0) {
                width =  parent.getChildAt(0).getMeasuredWidth();
            }
            else {
                width =  parent.getWidth() / parent.getNumColumns();
            }
        }
        return width;
    }

    /**
     * Set the numbers of a Tile object
     * @param view the View containing the inflated tile layout
     * @param tile the POJO containing the backing Tile data
     */
    private void setTileViewNumbersTriangle(View view, Tile tile){
        TextView northTextView = (TextView)view.findViewById(R.id.tile_bottom);
        northTextView.setText(String.valueOf(tile.getNorth()));
        stylizeQuadrant(northTextView, tile.getNorth(), mPuzzle.isColor());

        TextView eastTextView = (TextView)view.findViewById(R.id.tile_left);
        eastTextView.setText(String.valueOf(tile.getEast()));
        stylizeQuadrant(eastTextView, tile.getEast(), mPuzzle.isColor());

        TextView westTextView = (TextView)view.findViewById(R.id.tile_right);
        westTextView.setText(String.valueOf(tile.getWest()));
        stylizeQuadrant(westTextView, tile.getWest(), mPuzzle.isColor());
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

    /**
     * Color one side of the tile, or set it to gray based on application setting
     * @param view the view to apply the style
     * @param side the number that identifies the color of the tile's side
     * @param colorTiles {@code true} for color tiles; {@code false} for gray tiles
     */
    private void stylizeQuadrant(TextView view, int side, boolean colorTiles) {
        if(colorTiles) {
            colorizeQuadrant(view, side);
        }
        else {
            setGrayQuadrant(view);
        }
    }

    /**
     * Color one side of the tile gray
     * @param view the view that should receive a gray background
     */
    private void setGrayQuadrant(TextView view) {
        view.setTextColor(mContext.getResources().getColor(R.color.black));
        Drawable d = view.getBackground();
        d.mutate().setColorFilter(mContext.getResources().getColor(R.color.tile_bkgnd_gray),
                PorterDuff.Mode.MULTIPLY);
    }

    /**
     * Color one side of the tile
     * @param view the view that should receive a colored background
     * @param side the number that identifies the color of the tile's side
     */
    private void colorizeQuadrant(TextView view, int side) {
        // set text, set text color, set background color
        Drawable d = view.getBackground();

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
                break;
            default:
                // log the error
                Log.e(TAG, "Unsupported number: " + String.valueOf(side));
                break;
        }
    }

    /**
     * Get the layout ID for the appropriate tile layout based on board size
     * @param boardSize the size of the Tetravex board
     * @return the layout ID of the appropriate tile layout
     */
    private  int getTileLayoutIdTriangle(int boardSize){
        int layoutId;

        switch (boardSize){
            case 2:
                layoutId = R.layout.triangle;
                break;

            default:
                layoutId = R.layout.triangle;
        }
        return layoutId;
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
                // fall-through, 3 is the default
            default:
                layoutId = R.layout.tile_3_layout;
                break;
        }
        return layoutId;
    }
}
