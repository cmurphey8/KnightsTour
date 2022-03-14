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
    public static boolean[][] chess;
    public static final int N = 8;

    // list of all moves... eg, xDiff[0], yDiff[0] shifts right (+1) col, down (+2) rows
    // MAKE THIS A 2D ARRAY
    public static final int[] xDiff = {1, -1, 2, 2, 1, -1, -2, -2};
    public static final int[] yDiff = {2, 2, 1, -1, -2, -2, 1, -1};
    public static final int numMoves = xDiff.length;
    
    public static void main(String[] args) throws IOException {
        if (args.length == 1) ChessBoard.SLEEP = Integer.parseInt(args[0]);
        else ChessBoard.SLEEP = 100;

        // declare chess board array
        chess = new boolean[N][N];

        // run sims until we find a valid knight's tour!
        int count = 0;
        do {
            System.out.println("Round: " + count++);
        } while (!runSim());
        
        System.out.println("got one!");
    }

    // run a knight's tour simulation
    public static boolean runSim() throws IOException {   
        // init array
        fillChess();
            
        // find random initial pos to start the tour
        int knightRow = (int) Math.round(Math.random() * (N - 1));
        int knightCol = (int) Math.round(Math.random() * (N - 1));

        // update chessboard && init graphics
        chess[knightRow][knightCol] = true;
        ChessBoard.initBoard(knightRow, knightCol);

        // true at each index where we have a valid move
        int direction = findNext(knightRow, knightCol);

        // continue the tour so long as a valid move is possible
        while(direction < numMoves) {
            
            // select direction where we will have (nonzero) minimum number of possible next moves
            knightRow += yDiff[direction];
            knightCol += xDiff[direction];

            // update chessboard && graphics
            chess[knightRow][knightCol] = true;
            ChessBoard.updateBoard(knightRow, knightCol);

            // update moves array from new location
            direction = findNext(knightRow, knightCol);
        }

        // report whether the tour was completed, or stopped short
        return allOnes();
    }

    // FINDNEXT VS FINDNEXTRANDOM
    // RENAME KR KC TO KROW, KCOL
    // YOU DO: find all possible moves from chess board index (kR, kC)
    public static int findNext(int kRow, int kCol)
    {   
        int bestMove = Integer.MAX_VALUE;
        double maxDist = Double.MIN_VALUE;
        
        // YOU DO:  find best move from index (kR, kC) in chess array such that:
            // 1)   move is in bounds
            // 2a)  move does not revisit a cell
            // 2b)  move has smaller distance from center than maxDist

        for (int i = 0; i < numMoves; i++) {
            int kRowNew = kRow + yDiff[i];
            int kColNew = kCol + xDiff[i];

            // MAKE VARS FOR KC+XDIFF, KR+YDIFF -> NEWKrOW, NEWKCOL
            // 1) if move is in bounds...
            boolean xInBounds = kColNew < N && kColNew >= 0;
            boolean yInBounds = kRowNew < N && kRowNew >= 0;
            if (xInBounds && yInBounds) {

                // START WITH RANDOM ASSIGNMENT, SO LONG AS VALID
                // find distance from new location to center of the board
                double compareDist = distToCenter(kRowNew, kColNew);

                // 2a) if move does not revisit a cell && 
                // 2b) move has smaller distance from center than maxDist...
                if (!chess[kRowNew][kColNew] && compareDist > maxDist) {
                    bestMove = i;
                    maxDist = compareDist;
                }
            } 
        }
        return bestMove;
    }

    // HELPER: find distance from row, col to center of board
    public static double distToCenter(int row, int col)
    {
        return Math.sqrt((row + 0.5 - N / 2.0) * (row + 0.5 - N / 2.0)  + (col + 0.5 - N / 2.0)*(col + 0.5 - N / 2.0));
    }

    // YOU DO: reinit chess array with 0s
    public static void fillChess() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                chess[i][j] = false;
            }
        }
    }

    // YOU DO: check for a complete knight's tour
    public static boolean allOnes() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!chess[i][j]) return false;
            }
        }
        return true;
    }    
}   
