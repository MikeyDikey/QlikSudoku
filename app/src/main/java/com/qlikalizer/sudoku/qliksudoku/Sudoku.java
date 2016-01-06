package com.qlikalizer.sudoku.qliksudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.qlikalizer.sudoku.view.ViewAdapter;

public class Sudoku extends AppCompatActivity {

    public final static String TAG = Sudoku.class.getSimpleName();

    private char[][] mSudokuMatrix = new char[9][9];

    protected Button view[][] ;

    private TextView mUnsolvedSudokuTextView;
    private TextView mSolvedSudokuTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        mUnsolvedSudokuTextView = (TextView) findViewById(R.id.readSudokuTextView);
        mSolvedSudokuTextView = (TextView) findViewById(R.id.solvedSudokuTextView);

        mSudokuMatrix = FileReader.readFromFile(this);
        //Log.d(TAG, mSudokuMatrix.toString());
        String sudokuString = FileReader.printSudoku(mSudokuMatrix);
        mUnsolvedSudokuTextView.setText(sudokuString);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ViewAdapter(this, mSudokuMatrix));

        String solvedSudoku = FileReader.printSudoku(Solver.solveSudoku(mSudokuMatrix));
        mSolvedSudokuTextView.setText(solvedSudoku);
    }
}
