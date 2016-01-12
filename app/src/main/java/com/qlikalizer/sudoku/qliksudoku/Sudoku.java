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
import android.widget.TextView;

import com.qlikalizer.sudoku.view.ViewAdapter;

public class Sudoku extends AppCompatActivity {

    public final static String TAG = Sudoku.class.getSimpleName();

    char[][] mSudokuMatrix = new char[9][9];

    private Button mSolveSudokuButton;

    interface DataChangedListener {
        void onDataChanged(char[][] sudoku);
    }

    DataChangedListener mDataChangeListener = null;

    GridView mGridView;

    ViewAdapter mViewAdapter;

    TextView mLevelTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        mLevelTextView = (TextView) findViewById(R.id.levelTextView);

        // Load Easy Sudoku as the default Sudoku board
        mSudokuMatrix = FileReader.readFromFile(this, R.raw.easy);

        if (mSudokuMatrix == null) {
            // Toast is notifying the user in readFromFile
            return;
        }

        initGridView();

        mDataChangeListener = new DataChangedListener() {
            @Override
            public void onDataChanged(char[][] sudoku) {
                //Log.d(TAG, "onDataChanged");
                mGridView.invalidateViews();
                mViewAdapter.setSudoku(sudoku);
                mViewAdapter.notifyDataSetChanged();
            }
        };

        mSolveSudokuButton = (Button) findViewById(R.id.solveSudokuButton);
        mSolveSudokuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Solve Sudoku Button onClick");
                Solver.sCalculationDepth = 0;
                Solver.solve(mSudokuMatrix, mDataChangeListener);
                mLevelTextView.setText("Level: " + Solver.getLevel(Solver.sCalculationDepth));
                Log.d(TAG, "Calculation depth: " + Solver.sCalculationDepth);
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
                mDataChangeListener.onDataChanged(mSudokuMatrix);
                resetLevelTextView();
                return true;
            case R.id.load_medium:
                mSudokuMatrix = FileReader.readFromFile(this, R.raw.medium);
                mDataChangeListener.onDataChanged(mSudokuMatrix);
                resetLevelTextView();
                return true;
            case R.id.load_hard:
                mSudokuMatrix = FileReader.readFromFile(this, R.raw.hard);
                mDataChangeListener.onDataChanged(mSudokuMatrix);
                resetLevelTextView();
                return true;
            case R.id.load_samurai:
                mSudokuMatrix = FileReader.readFromFile(this, R.raw.samurai);
                mDataChangeListener.onDataChanged(mSudokuMatrix);
                resetLevelTextView();
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

    private void resetLevelTextView() {
        mLevelTextView.setText("Level:");
    }
}
