package view;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.qlikalizer.sudoku.qliksudoku.R;
import com.qlikalizer.sudoku.qliksudoku.Sudoku;

/**
 * Created by admin on 2016-01-06.
 */
public class ViewAdapter extends BaseAdapter {
    private Context mContext;
    private char[][] mSudoku  = new char[9][9];

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
        /*
        TextView textView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            textView = new TextView(mContext);

            int widh_height = 60;
            textView.setLayoutParams(new GridView.LayoutParams(widh_height, widh_height));

            int padding = 6;
            textView.setPadding(padding, padding, padding, padding);
        } else {
            textView = (TextView) convertView;
        }
         */

        //TRy with a button instead
        Button button;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            button = new Button(mContext);
        } else {
            button = (Button) convertView;
        }

        int row = position / 9;
        int col = position % 9;
        Log.d(Sudoku.TAG, "Setting text from position: " + row + "," + col);
        button.setText(String.valueOf(mSudoku[row][col]));
        return button;
    }

}
