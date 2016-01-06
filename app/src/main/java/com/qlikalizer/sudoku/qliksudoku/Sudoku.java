package com.qlikalizer.sudoku.qliksudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.qlikalizer.sudoku.view.ViewAdapter;

public class Sudoku extends AppCompatActivity {

    public final static String TAG = Sudoku.class.getSimpleName();

    private char[][] mSudokuMatrix = new char[9][9];

    private Button mSolveSudokuButton;

    interface DataChangedListener {
        void onDataChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        mSudokuMatrix = FileReader.readFromFile(this);

        final GridView gridView = (GridView) findViewById(R.id.gridview);
        final ViewAdapter viewAdapter = new ViewAdapter(this, mSudokuMatrix);
        gridView.setAdapter(viewAdapter);

        final DataChangedListener dataChangeListener = new DataChangedListener() {
            @Override
            public void onDataChanged() {
                Log.d(TAG, "onDataChanged");
                viewAdapter.notifyDataSetChanged();
                gridView.invalidateViews();
                gridView.setAdapter(viewAdapter);
            }
        };

        mSolveSudokuButton = (Button) findViewById(R.id.solveSudokuButton);
        mSolveSudokuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Solve Sudoku Button onClick");
                Solver.solve(mSudokuMatrix, dataChangeListener);
            }
        });
    }
}
