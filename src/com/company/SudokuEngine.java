package com.company;

import java.util.Scanner;

public class SudokuEngine {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] numbers = new int[9][9];

        // Enter Values...
        System.out.println("Enter Values, substituting blank spaces with zeros:\n");
        for (int i = 0; i < 9; i++) {
            String data = scanner.nextLine();
            for (int j = 0; j < data.length(); j++) {
                numbers[i][j] = Character.getNumericValue(data.charAt(j));
            }
        }
        int passes = 0;
        boolean changed = false;

        int basicAlgo = 0, complexAlgo = 0;

        long time = System.nanoTime();

        do {
            changed = false;
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (numbers[i][j] == 0) {
                        int candidate = 0;
                        for (int k = 1; k < 10; k++) {
                            if (!searchRows(numbers, i, k) && !searchCols(numbers, j, k) && !searchBox(numbers, i, j, k)) {
                                if (candidate == 0) {
                                    candidate = k;
                                } else {
                                    candidate = -1;
                                    break;
                                }
                            }
                        }
                        if (candidate != -1) {
                            numbers[i][j] = candidate;
                            changed = true;
                            basicAlgo++;
                        }

                    }
                }
            }

            //Use other algorithm

            if (!changed) {
                /*
            for each number
                is the number in this box
                otherwise
                at each open square
                does the column not contain this number
                    does the row not contain this number
                        has no other square passed the tests
                            record this square
                            mark as passing tests
                change square

     */
                for (int num = 1; num < 10; num++) {
                    for (int x = 0; x < 3; x++) {
                        for (int y = 0; y < 3; y++) {
                            if (!searchBox(numbers, x * 3, y * 3, num)) {

                                boolean foundCandidate = false;
                                int candidateX = -1, candidateY = -1;

                                for (int xSub = 0; xSub < 3; xSub++) {
                                    for (int ySub = 0; ySub < 3; ySub++) {

                                        int locX = x * 3 + xSub;
                                        int locY = y * 3 + ySub;
                                        // if square is clear
                                        if (numbers[locX][locY] == 0) {
                                            if (!searchCols(numbers, locY, num) && !searchRows(numbers, locX, num)) {
                                                if (foundCandidate) {
                                                    candidateX = -1;
                                                    candidateY = -1;
                                                } else {
                                                    foundCandidate = true;
                                                    candidateX = locX;
                                                    candidateY = locY;
                                                }
                                            }
                                        }
                                    }
                                }

                                if (candidateX != -1) {
                                    numbers[candidateX][candidateY] = num;
                                    changed = true;
                                    complexAlgo++;
                                }
                            }
                        }
                    }
                }
            }
            passes++;
        } while (changed);

        long timeCompleted = System.nanoTime() - time;

        System.out.println("This puzzle was attempted in " + passes + " passes over " + timeCompleted / 1000000f + " milliseconds.");
        System.out.println("Complex: " + complexAlgo + "  Simple: " + basicAlgo);
        for (
                int i = 0;
                i < 9; i++) {
            System.out.println();
            for (int j = 0; j < 9; j++) {
                System.out.print(numbers[i][j]);

            }
        }

    }

    static boolean searchRows(int[][] numbers, int row, int query) {
        for (int i = 0; i < 9; i++) {
            if (numbers[row][i] == query) {
                return true;
            }
        }
        return false;
    }

    static boolean searchCols(int[][] numbers, int col, int query) {
        for (int i = 0; i < 9; i++) {
            if (numbers[i][col] == query) {
                return true;
            }
        }
        return false;
    }

    static boolean searchBox(int[][] numbers, int row, int col, int query) {
        int boxX = (row / 3) * 3;
        int boxY = (col / 3) * 3;

        for (int i = boxX; i < boxX + 3; i++) {
            for (int j = boxY; j < boxY + 3; j++) {
                if (numbers[i][j] == query) {
                    return true;
                }
            }
        }
        return false;
    }
}