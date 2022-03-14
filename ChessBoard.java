//*******************************************************************
//
//   File: ChessBoard.java
//
//*******************************************************************

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class ChessBoard {

    // chess constants
    static final int EDGE = 10;
    static final int SIZE = 60;

    // init Panel
    static final int WIDTH = 2 * EDGE + KnightsTour.N * SIZE;
    static final int HEIGHT = 2 * EDGE + KnightsTour.N * SIZE;
    
    static DrawingPanel panel = new DrawingPanel(WIDTH, HEIGHT);
    static Graphics2D g = panel.getGraphics();

    // enable double buffering
    static BufferedImage offscreen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    static Graphics2D osg = offscreen.createGraphics();

    // knight image
    static BufferedImage knight;
    static final int kH = 50;
    static final int kW = 35; 

    // track last move to update from
    static int kRowOld;
    static int kColOld;

    public static int SLEEP;

    // draw the next knight, and a line from the last knight
    public static void updateBoard(int kR, int kC) {
        drawKnight(kR, kC);
        
        osg.setColor(Color.GREEN);
        osg.drawLine(scaleIndex(kColOld) + SIZE / 2, scaleIndex(kRowOld) + SIZE / 2, scaleIndex(kC) + SIZE / 2, scaleIndex(kR) + SIZE / 2);
        
        kRowOld = kR;
        kColOld = kC;
        
        g.drawImage(offscreen, 0, 0, null);   
        panel.sleep(SLEEP);    
    }

    // init knight image && draw the board
    public static void initBoard(int kR, int kC) throws IOException 
    {
        knight = ImageIO.read(new File("knight.png"));
        
        osg.setColor(Color.DARK_GRAY);
        osg.fillRect(0, 0, WIDTH, HEIGHT);

        drawGrid(EDGE, EDGE, KnightsTour.N / 2, SIZE);
        g.drawImage(offscreen, 0, 0, null);  
        drawKnight(kR, kC);
        
        kRowOld = kR;
        kColOld = kC;
    }

    // make checkered grid
    public static void drawGrid(int x, int y, int numPairs, int SIZE) {
        for (int i = 0; i < 2 * numPairs; i+=2) {
            drawRow(x, y + SIZE * i, Color.WHITE, Color.GRAY);
            drawRow(x, y + SIZE * (i + 1), Color.GRAY, Color.WHITE);
        }
    }

    // make alternating boxes in a row
    public static void drawRow(int x, int y, Color first, Color second) {
        for (int i = 0; i < KnightsTour.N; i+=2) {
            osg.setColor(first);
            osg.fillRect(x + SIZE * i, y, SIZE, SIZE);
            osg.setColor(second);
            osg.fillRect(x + SIZE * (i + 1), y, SIZE, SIZE);
        }
    }

    // draw a knight in the current cell
    public static void drawKnight(int row, int col) {
        osg.drawImage(knight, scaleKnight(col, kW), scaleKnight(row, kH), null);
    }


    // scale from index to CENTERED panel coordinates
    public static int scaleKnight(int index, int dimSize) {
        return scaleIndex(index) + (int) ((SIZE - dimSize) / 2.0);
    }

    // find distance from center of the board
    public static double distance(int kC, int kR) {
        return Math.sqrt(Math.pow(scaleIndex(kC) + SIZE / 2 - WIDTH / 2.0, 2) 
                        + Math.pow(scaleIndex(kR) + SIZE / 2 - HEIGHT / 2.0, 2));                         
    }

    // HELPER: scale from index to panel coordinates
    public static int scaleIndex(int index)
    {
        return index * SIZE + EDGE;
    }


    // reinit chess array with 0s
    public static void fillChess() {
        for (int i = 0; i < KnightsTour.N; i++) {
            for (int j = 0; j < KnightsTour.N; j++) {
                KnightsTour.chess[i][j] = 0;
            }
        }
    }
}   