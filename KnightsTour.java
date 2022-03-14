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
    public static double[] moves;
    
    public static void main(String[] args) throws IOException {
        if (args.length == 1) ChessBoard.SLEEP = Integer.parseInt(args[0]);
        else ChessBoard.SLEEP = 100;

        // declare chess board and move scoring arrays
        chess = new int[N][N];
        moves = new double[numMoves];

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
        allMoves(knightRow, knightCol);

        // continue the tour so long as a valid move is possible
        while(canMove()) {
            
            // select direction where we will have (nonzero) minimum number of possible next moves
            int direction = findNext();
            knightCol += xDiff[direction];
            knightRow += yDiff[direction];

            // update chessboard && graphics
            chess[knightRow][knightCol] = 1;
            ChessBoard.updateBoard(knightRow, knightCol);

            // update moves array from new location
            allMoves(knightRow, knightCol);
        }

        // report whether the tour was completed, or stopped short
        return allOnes();
    }

    // find all possible moves from chess board index (kR, kC)
    public static void allMoves(int kR, int kC)
    {   
        // find all possible moves from index (kR, kC) in chess array
        for (int i = 0; i < numMoves; i++) {

            // if move i falls in range of our array indexes...
            boolean xCheck = kC + xDiff[i] < N && kC + xDiff[i] >= 0;
            boolean yCheck = kR + yDiff[i] < N && kR + yDiff[i] >= 0;

            // separate this condition to avoid NullPointerException!
            // && if move i does not revisit an index...
            if (xCheck && yCheck && chess[kR + yDiff[i]][kC + xDiff[i]] == 0) {

                // calcuate distance from center of the board
                moves[i] = ChessBoard.distance(kR + yDiff[i], kC + xDiff[i]);
            } 

            // else set distance to minimum
            else moves[i] = Double.MIN_VALUE;
        }
    }

    // find the knight's next move: lands furthest from the center of the board
    public static int findNext()
    {   
        int maxIndex = Integer.MAX_VALUE;
        double maxDist = Double.MIN_VALUE;
        for (int i = 0; i < numMoves; i++) {
            if (moves[i] > maxDist) {
                maxIndex = i;
                maxDist = moves[i];
            }
        }
        return maxIndex;
    }

    // confirm the knight still has at least one valid move
    public static boolean canMove()
    {   
        // if a move has distance > Double.MIN_VALUE, it must be valid
        for (int i = 0; i < moves.length; i++) {
            if (moves[i] > Double.MIN_VALUE) {
                return true;
            }
        }
        return false;
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