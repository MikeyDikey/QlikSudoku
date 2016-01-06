package com.qlikalizer.sudoku.qliksudoku;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 2016-01-06.
 */
public class FileReader {

    private Context mContext;

    public FileReader(Context c) {
        mContext = c;
    }

    static char[][] readFromFile(Context context) {
        char[][] sudokuMatrix = new char[9][9];
        InputStream is;

        is  = context.getResources().openRawResource(R.raw.easy);

        char ch;
        for (int k = 0; k < 9; k++) {
            for (int i = 0; i < 9; i++) {
                sudokuMatrix[k][i] = getNextValidCharacter(is);
            }
        }
        return sudokuMatrix;
    }

    private static char getNextValidCharacter(InputStream is) {
        Log.d(Sudoku.TAG, "getNextValidCharacter");
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

    static String printSudoku(char[][] sudoku) {
        StringBuffer buffer = new StringBuffer();
        Log.d(Sudoku.TAG, "Printing sudoku \n");
        //buffer.append("Printing sudoku \n");
        for (int k = 0; k < 9; k++) {
            for (int i = 0; i < 9; i++) {
                Log.d(Sudoku.TAG, "k = " + k + " i = " + i + " Matrix on [i,k] = " +
                        String.valueOf(sudoku[k][i]));
                buffer.append(sudoku[k][i]);
            }
            buffer.append("\n");
        }
        String sudokuString = buffer.toString();
        Log.d(Sudoku.TAG, sudokuString);
        return sudokuString;
    }
}
