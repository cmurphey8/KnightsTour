//*******************************************************************
//
//   File: KnightsTour.java
//
//   Dependancies: ChessBoard.java, DrawingPanel.java
//
//*******************************************************************

import java.io.IOException;

public class KnightsTour {
    // declare data arrays
    public static int[][] chess;
    public static final int N = 12;

    // list of all moves... eg, xDiff[0], yDiff[0] shifts right (+1) col, down (+2) rows
    public static final int[] xDiff = {1, -1, 2, 2, 1, -1, -2, -2};
    public static final int[] yDiff = {2, 2, 1, -1, -2, -2, 1, -1};
    public static final int numMoves = xDiff.length;
    
    public static void main(String[] args) throws IOException {
        if (args.length == 1) ChessBoard.SLEEP = Integer.parseInt(args[0]);
        else ChessBoard.SLEEP = 100;

        // declare chess board array
        chess = new int[N][N];

        // run sims until we find a valid knight's tour!
        int count = 0;
        while (!runSim()){
            System.out.println("Round: " + count++);
        }
        
        System.out.println("got one!");
    }

    // run a knight's tour simulation
    public static boolean runSim() throws IOException {   
        // init arrays and graphics
        ChessBoard.fillChess();
            
        // find random initial pos to start the tour
        int knightRow = (int) Math.round(Math.random() * (N - 1));
        int knightCol = (int) Math.round(Math.random() * (N - 1));
        chess[knightRow][knightCol] = 1;

        ChessBoard.initBoard(knightRow, knightCol);

        // true at each index where we have a valid move
        int direction = findNext(knightRow, knightCol);

        // continue the tour so long as a valid move is possible
        while(direction < numMoves) {
            
            // select direction where we will have (nonzero) minimum number of possible next moves
            // int direction = findNext();
            knightCol += xDiff[direction];
            knightRow += yDiff[direction];

            // update chessboard && graphics
            chess[knightRow][knightCol] = 1;
            ChessBoard.updateBoard(knightRow, knightCol);

            // update moves array from new location
            direction = findNext(knightRow, knightCol);
        }

        // report whether the tour was completed, or stopped short
        return allOnes();
    }

    // find all possible moves from chess board index (kR, kC)
    public static int findNext(int kR, int kC)
    {   
        int bestMove = Integer.MAX_VALUE;
        double maxDist = Double.MIN_VALUE;
        // find all possible moves from index (kR, kC) in chess array
        for (int i = 0; i < numMoves; i++) {

            // if move i falls in range of our array indexes...
            boolean xCheck = kC + xDiff[i] < N && kC + xDiff[i] >= 0;
            boolean yCheck = kR + yDiff[i] < N && kR + yDiff[i] >= 0;
            if (xCheck && yCheck) {

                // separate these conditions to avoid NullPointerException!
                double compareDist = ChessBoard.distance(kR + yDiff[i], kC + xDiff[i]);

                // if move i does not revisit a cell, and is furthest from center of the board
                if (chess[kR + yDiff[i]][kC + xDiff[i]] == 0 && compareDist > maxDist) {
                    bestMove = i;
                    maxDist = compareDist;
                }
            } 
        }
        return bestMove;
    }

    // check for a complete knight's tour
    public static boolean allOnes() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (chess[i][j] == 0) return false;
            }
        }
        return true;
    }    
}   