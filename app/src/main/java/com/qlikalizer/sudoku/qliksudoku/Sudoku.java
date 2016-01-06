package com.qlikalizer.sudoku.qliksudoku;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import view.ViewAdapter;

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

        mSudokuMatrix = readFromFile();
        //Log.d(TAG, mSudokuMatrix.toString());
        String sudokuString = printSudoku(mSudokuMatrix);
        mUnsolvedSudokuTextView.setText(sudokuString);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ViewAdapter(this, mSudokuMatrix));

        String solvedSudoku = printSudoku(Solver.solveSudoku(mSudokuMatrix));
        mSolvedSudokuTextView.setText(solvedSudoku);


    }

    private char[][] readFromFile() {
        char[][] sudokuMatrix = new char[9][9];
        InputStream is;

        is  = this.getResources().openRawResource(R.raw.easy);

        char ch;
        for (int k = 0; k < 9; k++) {
            for (int i = 0; i < 9; i++) {
                sudokuMatrix[k][i] = getNextValidCharacter(is);
            }
        }
        return sudokuMatrix;
    }

    private char getNextValidCharacter(InputStream is) {
        Log.d(TAG, "getNextValidCharacter");
        char ch;

        try {
            ch = (char)is.read();
            while (ch != 0 && ch != '\uFFFF') {
                if (ch >= '1' && ch <= '9' || ch == '.') {
                    return ch;
                }
                ch = (char)is.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String printSudoku(char[][] sudoku) {
        StringBuffer buffer = new StringBuffer();
        Log.d(TAG, "Printing sudoku \n");
        //buffer.append("Printing sudoku \n");
        for (int k = 0; k < 9; k++) {
            for (int i = 0; i < 9; i++) {
                Log.d(TAG, "k = " + k + " i = " + i + " Matrix on [i,k] = " +
                        String.valueOf(sudoku[k][i]));
                buffer.append(sudoku[k][i]);
            }
            buffer.append("\n");
        }
        String sudokuString = buffer.toString();
        Log.d(TAG, sudokuString);
        return sudokuString;
    }
}
