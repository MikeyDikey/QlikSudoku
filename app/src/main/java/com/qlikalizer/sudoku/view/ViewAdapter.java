package com.qlikalizer.sudoku.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

/**
 * Created by admin on 2016-01-06.
 */
public class ViewAdapter extends BaseAdapter {
    private Context mContext;
    private char[][] mSudoku;

    public ViewAdapter(Context c, char[][] sudoku) {
        mSudoku = sudoku;
        mContext = c;
    }

    public int getCount() {
        return 9*9;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new TextView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            button = new Button(mContext);
        } else {
            button = (Button) convertView;
        }

        int row = position / 9;
        int col = position % 9;

        button.setText(String.valueOf(mSudoku[row][col]));
        return button;
    }

    public void setSudoku(char[][] sudoku) {
        mSudoku = sudoku;
    }

}
