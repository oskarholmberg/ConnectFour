package Tests;

import Model.Board;
import junit.framework.Test;

import static org.junit.Assert.*;

/**
 * Created by Oskar on 2015-10-06.
 */
public class BoardTest {

    private Board board;
    private int[][] matrix;

    public void init(){
        board = new Board();
        board.addPlayer("Oskar");
        board.addPlayer("Kevin");
        matrix = new int[7][6];
        for(int i = 0; i < 6; i ++){
            for(int j = 0; j < 7; j++){
                matrix[j][i] = -1;
            }
        }
    }
    @org.junit.Test
    public void testGetMatrix(){
        for(int i = 0; i < 6; i ++){
            for(int j = 0; j < 7; j++){
               assertEquals(board.getMatrix()[j][i], matrix[j][i]);
            }
        }
    }
    @org.junit.Test
    public void testPut(){
        board.put(0);
        board.put(1);
        matrix[0][5] = 0;
        matrix[1][5] = 1;
        testGetMatrix();
        board.reset();
    }

    public void testWinningMove(){
        testCheckRight();
        testCheckUp();
        testCheckDiagonalRight();
        testCheckDiagonalLeft();
    }

    private void testCheckRight(){
        board.put(0);
        assertEquals(board.put(0), 0);
        board.put(1);
        assertEquals(board.put(1), 0);
        board.put(2);
        assertEquals(board.put(2), 0);
        assertEquals(board.put(3), 1);
        board.reset();
    }

    private void testCheckUp(){
        board.put(0);
        assertEquals(board.put(1), 0);
        board.put(0);
        assertEquals(board.put(1), 0);
        board.put(0);
        assertEquals(board.put(1), 0);
        assertEquals(board.put(0), 1);
        board.reset();
    }

    public void testCheckDiagonalRight(){
        assertEquals(0, board.put(0));
        assertEquals(0, board.put(1));
        assertEquals(0, board.put(1));
        assertEquals(0, board.put(0));
        assertEquals(0, board.put(2));
        assertEquals(0, board.put(2));
        assertEquals(0, board.put(2));
        assertEquals(0, board.put(3));
        assertEquals(0, board.put(3));
        assertEquals(0, board.put(3));
        assertEquals(1, board.put(3));
        board.reset();
    }

    public void testCheckDiagonalLeft(){
        assertEquals(0, board.put(0));
        assertEquals(0, board.put(1));
        assertEquals(0, board.put(1));
        assertEquals(0, board.put(0));
        assertEquals(0, board.put(2));
        assertEquals(0, board.put(2));
        assertEquals(0, board.put(2));
        assertEquals(0, board.put(3));
        assertEquals(0, board.put(3));
        assertEquals(0, board.put(3));
        assertEquals(0, board.put(4));
        assertEquals(0, board.put(1));
        assertEquals(0, board.put(0));
        assertEquals(1, board.put(0));
        board.reset();
    }

}