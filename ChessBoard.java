//*******************************************************************
//
//   File: KnightsTour.java
//
//   Dependancies: ChessBoard.java, DrawingPanel.java
//
//   YOU DO: 1)  complete fillChess() to init chess array
//           2)  complete allOnes() to check chess array for complete tour
//           3a) complete findRandom() to choose a valid random next move
//           3b) complete xyInBounds() helper method for findRandom()
//           4)  complete findDistMethod() to choose next move by max dist from center
//
//*******************************************************************

import java.io.IOException;

public class KnightsTour {
    // declare data arrays
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

        // 1) YOU DO: complete the method to choose next knight randomly
        int direction = findRandom(knightRow, knightCol);

        // 2) YOU DO: complete the method to choose by max distance from center
        // int direction = findDistMethod(knightRow, knightCol);

        // continue the tour so long as a valid move is possible
        while(direction < numMoves) {

            // select direction where we will have (nonzero) minimum number of possible next moves
            knightRow += yDiff[direction];
            knightCol += xDiff[direction];

            // update chessboard && graphics
            chess[knightRow][knightCol] = true;
            ChessBoard.updateBoard(knightRow, knightCol);

            // 1) YOU DO: complete the method to choose next knight randomly
            direction = findRandom(knightRow, knightCol);

            // 2) YOU DO: complete the method to choose by max distance from center
            // direction = findDistMethod(knightRow, knightCol);
        }

        // report whether the tour was completed, or stopped short
        return allOnes();
    }  

    // YOU DO: reinit boolean chess array with all false
    public static void fillChess() {
    }  

    // YOU DO: is this a complete knight's tour? update from false
    public static boolean allOnes() {
        return false;
    }

    // YOU DO: choose next knight by random update
    public static int findRandom(int kRow, int kCol)
    {
        int rIndex;
        boolean isValid;
        int count = 0;

        do {
            // choose a random index
            rIndex = (int) Math.round(Math.random() * (numMoves - 1));

            // new row, col after move i update
            int kRowNew = kRow + yDiff[rIndex];
            int kColNew = kCol + xDiff[rIndex];

            // update counter
            count++;

            // YOU DO: is move in bounds && is this move unvisited? Update from false
            isValid = xyInBounds(kRowNew, kColNew) && false;

        } while (count < 20 && !isValid);

        if (isValid) return rIndex;
        return Integer.MAX_VALUE;
    }

    // YOU DO: check for a complete knight's tour
    public static boolean xyInBounds(int rowIndex, int colIndex) {
        // YOU DO: are row, col valid indexes for the chess array? Update from false
        boolean xInBounds = false;
        boolean yInBounds = false;

        return (xInBounds && yInBounds);
    }

    // YOU DO: choose next knight by max distance from center of the board
    public static int findDistMethod(int kRow, int kCol)
    {
        int bestMove = Integer.MAX_VALUE;
        double maxDist = Double.MIN_VALUE;

        for (int i = 0; i < numMoves; i++) {
            // new row, col after move i update
            int kRowNew = kRow + yDiff[i];
            int kColNew = kCol + xDiff[i];

            // 1) if new row, col are in bounds...
            if (xyInBounds(kRowNew, kColNew)) {

                // find distance from new location to center of the board
                double compareDist = distToCenter(kRowNew, kColNew);

                // YOU DO: update from false
                // 2a) if move does not revisit a cell && 
                // 2b) move has smaller distance from center than maxDist...
                if (false) {
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
