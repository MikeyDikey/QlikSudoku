package com.qlikalizer.sudoku.qliksudoku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.qlikalizer.sudoku.view.ViewAdapter;

public class Sudoku extends AppCompatActivity {

    public final static String TAG = Sudoku.class.getSimpleName();

    char[][] mSudokuMatrix = new char[9][9];

    private Button mSolveSudokuButton;

    interface DataChangedListener {
        void onDataChanged();
    }

    DataChangedListener mDataChangeListener = null;

    GridView mGridView;
    ViewAdapter mViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        // Load Easy Sudoku as the default Sudoku board
        mSudokuMatrix = FileReader.readFromFile(this, R.raw.easy);

        if (mSudokuMatrix == null) {
            // Toast is notifying the user in readFromFile
            return;
        }

        initGridView();

        mDataChangeListener = new DataChangedListener() {
            @Override
            public void onDataChanged() {
                Log.d(TAG, "onDataChanged");
                mViewAdapter.setSudoku(mSudokuMatrix);
                mViewAdapter.notifyDataSetChanged();
            }
        };

        mSolveSudokuButton = (Button) findViewById(R.id.solveSudokuButton);
        mSolveSudokuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Solve Sudoku Button onClick");
                Solver.solve(mSudokuMatrix, mDataChangeListener);
            }
        });
    }

    private void initGridView() {
        mGridView = (GridView) findViewById(R.id.gridview);
        mViewAdapter = new ViewAdapter(this, mSudokuMatrix);
        mGridView.setAdapter(mViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.load_easy:
                mSudokuMatrix = FileReader.readFromFile(this, R.raw.easy);
                mDataChangeListener.onDataChanged();
                return true;
            case R.id.load_medium:
                mSudokuMatrix = FileReader.readFromFile(this, R.raw.medium);
                mDataChangeListener.onDataChanged();
                return true;
            case R.id.readme:
                Intent i = new Intent();
                i.setClass(getApplicationContext(), ReadmeActivity.class);
                this.startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
