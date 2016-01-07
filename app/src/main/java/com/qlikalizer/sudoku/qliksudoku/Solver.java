package com.qlikalizer.sudoku.qliksudoku;

import android.util.Log;

/**
 * Created by admin on 2016-01-03.
 */
public class Solver {

    private static int mCurrentTriedNumber = 1;

    private static final String WRONG_DIMENSIONS_ERROR =
            "The Sudoku supplied has dimensions larger than 9x9";

    public static boolean solve(char[][] sudoku, Sudoku.DataChangedListener dataChangeListener) {

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (isNumberOneToNineChar(sudoku[row][col])) {
                    continue;
                }
                for (char c = '1'; c <= '9'; c++) {
                    if (allowedInCol(sudoku, col, c) && allowedInRow(sudoku, row, c) &&
                            allowedInSquare(sudoku, row, col, c)) {
                        sudoku[row][col] = c;
                        dataChangeListener.onDataChanged(sudoku);

                        if (solve(sudoku, dataChangeListener)) {
                            return true;
                        } else {
                            sudoku[row][col] = '.';
                            dataChangeListener.onDataChanged(sudoku);
                        }
                    }
                }
                return false;
            }
        }
        return true;
    }



    static boolean allowedInRow(char[][] sudoku, final int row, char value) {
        for (int col = 0; col < 9; col++) {
            if (sudoku[row][col] == value) {
                //Log.d(Sudoku.TAG, value + " allowedInRow: false");
                return false;
            }
        }
        //Log.d(Sudoku.TAG, value + " allowedInRow: true");
        return true;
    }

    static boolean allowedInCol(char[][] sudoku, final int col, char value) {
        for (int row = 0; row < 9; row++) {
            if (sudoku[row][col] == value) {
                //Log.d(Sudoku.TAG, value + " allowedInCol: false");
                return false;
            }
        }
        //Log.d(Sudoku.TAG, value + " allowedInCol: true");
        return true;
    }

    static boolean allowedInSquare(char[][] sudoku, final int row, final int col, char value) {
        boolean allowed = true;
        int quadrant = calculateQuadrant(row, col);
        //TODO: Use / and % for less code below
        switch (quadrant) {
            case 0:
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        if (sudoku[r][c] == value) {
                            allowed = false;
                        }
                    }
                }
                break;
            case 1:
                for (int r = 0; r < 3; r++) {
                    for (int c = 3; c < 6; c++) {
                        if (sudoku[r][c] == value) {
                            allowed = false;
                        }
                    }
                }
                break;
            case 2:
                for (int r = 0; r < 3; r++) {
                    for (int c = 6; c < 9; c++) {
                        if (sudoku[r][c] == value) {
                            allowed = false;
                        }
                    }
                }
                break;
            case 3:
                for (int r = 3; r < 6; r++) {
                    for (int c = 0; c < 3; c++) {
                        if (sudoku[r][c] == value) {
                            allowed = false;
                        }
                    }
                }
                break;
            case 4:
                for (int r = 3; r < 6; r++) {
                    for (int c = 3; c < 6; c++) {
                        if (sudoku[r][c] == value) {
                            allowed = false;
                        }
                    }
                }
                break;
            case 5:
                for (int r = 3; r < 6; r++) {
                    for (int c = 6; c < 9; c++) {
                        if (sudoku[r][c] == value) {
                            allowed = false;
                        }
                    }
                }
                break;
            case 6:
                for (int r = 6; r < 9; r++) {
                    for (int c = 0; c < 3; c++) {
                        if (sudoku[r][c] == value) {
                            allowed = false;
                        }
                    }
                }
                break;
            case 7:
                for (int r = 6; r < 9; r++) {
                    for (int c = 3; c < 6; c++) {
                        if (sudoku[r][c] == value) {
                            allowed = false;
                        }
                    }
                }
                break;
            case 8:
                for (int r = 6; r < 9; r++) {
                    for (int c = 6; c < 9; c++) {
                        if (sudoku[r][c] == value) {
                            allowed = false;
                        }
                    }
                }
                break;
            default:
                Log.e(Sudoku.TAG, "Error: Quadrant not valid: " + quadrant);
        }
        //Log.d(Sudoku.TAG, value + " allowedInSquare: " + allowed);
        return allowed;
    }

    private static int calculateQuadrant(final int row, final int col)
            throws IllegalArgumentException{
        int quadrant = 0;
        if (row < 3) {
            if (col < 3) {
                quadrant = 0;
            } else if (col < 6) {
                quadrant = 1;
            } else if (col < 9) {
                quadrant = 2;
            } else {
                throw new IllegalArgumentException(WRONG_DIMENSIONS_ERROR);
            }
        } else if (row < 6) {
            if (col < 3) {
                quadrant = 3;
            } else if (col < 6) {
                quadrant = 4;
            } else if (col < 9) {
                quadrant = 5;
            } else {
                throw new IllegalArgumentException(WRONG_DIMENSIONS_ERROR);
            }
        } else if (row < 9) {
            if (col < 3) {
                quadrant = 6;
            } else if (col < 6) {
                quadrant = 7;
            } else if (col < 9) {
                quadrant = 8;
            } else {
                throw new IllegalArgumentException(WRONG_DIMENSIONS_ERROR);
            }
        } else {
            throw new IllegalArgumentException(WRONG_DIMENSIONS_ERROR);
        }
        Log.d(Sudoku.TAG, "Calculated quadrant: " + quadrant);
        return quadrant;
    }

    static boolean isSolved(char[][] sudoku){
        for(int row = 0; row<9; row++){
            for(int col = 0; col<9; col++){
                if(!isNumberOneToNineChar(sudoku[row][col])){
                    return false;
                }
            }
        }
        return true;
    }

    static private boolean isNumberOneToNineChar(char c) {
        return c >= '1' && c <= '9';
    }

}

