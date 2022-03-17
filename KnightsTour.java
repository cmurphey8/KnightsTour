//*******************************************************************
//
//   File: KnightsTour.java
//
//   Dependancies: ChessBoard.java, DrawingPanel.java
//
//   YOU DO: To get random tour running via findRandom()...
//           1)  complete fillChess() to init chess array
//           2)  complete allOnes() to check chess array for complete tour
//           3)  complete isValid() to check whether a move is both inbounds and not yet visited
//           
//           After random tour is up and running...
//           4)  complete findDistMethod() to choose next move by max dist from center
//
//*******************************************************************

import java.io.IOException;

public class KnightsTour {
    
    // declare chess array - true if index has been visited
    public static boolean[][] chess;
    public static final int N = 8;

    // list of all moves... eg, xDiff[0], yDiff[0] shifts right (+1) col, down (+2) rows
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

        // continue the tour so long as a valid move is possible
        while(canMove(knightRow, knightCol)) {

            // choose next knight randomly
            int direction = findRandom(knightRow, knightCol);

            // -OR- choose by max distance from center
            // int direction = findDistMethod(knightRow, knightCol);

            // update index of knight on chessboard
            knightRow += yDiff[direction];
            knightCol += xDiff[direction];

            // update chessboard && graphics
            chess[knightRow][knightCol] = true;
            ChessBoard.updateBoard(knightRow, knightCol);
        }

        // report whether the tour was completed, or stopped short
        return allOnes();
    }  

    // YOU DO: reinit chess array with boolean value false
    public static void fillChess() {

    }  

    // YOU DO: check for a complete knight's tour (update from true)
    public static boolean allOnes() {
        return true;
    }

    // check if at least one move from this cell is possible
    public static boolean canMove(int kRow, int kCol) {
        for (int i = 0; i < numMoves; i++)
            if(isValid(kRow, kCol, i)) return true;

        return false;
    }

    // YOU DO: check if update from kRow, kCol via moveIndex is valid
    public static boolean isValid(int kRow, int kCol, int moveIndex) {
        // new row, col after move i update
        int kRowNew = kRow + yDiff[moveIndex];
        int kColNew = kCol + xDiff[moveIndex];

        // YOU DO: complete the following boolean expressions (update from false)
        boolean xInBounds = false;
        boolean yInBounds = false;
        boolean cellVisited = false;

        // is move inbounds and not yet visited??
        return (xInBounds && yInBounds && !cellVisited);
    }

    // choose next knight move index randomly
    public static int findRandom(int kRow, int kCol)
    {
        int rIndex;
        do {
            // choose a random index
            rIndex = (int) Math.round(Math.random() * (numMoves - 1));

        } while (!isValid(kRow, kCol, rIndex));

        return rIndex;
    }

    // YOU DO: choose next knight move by max distance from center of the board after update
    public static int findDistMethod(int kRow, int kCol)
    {
        int bestMove = Integer.MAX_VALUE;
        double maxDist = Double.MIN_VALUE;

        for (int i = 0; i < numMoves; i++) {
            if (isValid(kRow, kCol, i)) {

                // find distance from new location to center of the board
                double compareDist = distToCenter(kRow + yDiff[i], kCol + xDiff[i]);

                // YOU DO: if move has smaller distance from center than maxDist...
                boolean isBest = compareDist > maxDist; // update from false
                if (isBest) { 
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
}   
