package Tests;

/**
 * Created by Oskar on 2015-10-15.
 */
public class TestRunner {

    public static void main(String[] args){
        BoardTest bt = new BoardTest();
        bt.init();
        bt.testGetMatrix();
        bt.testPut();
        bt.testWinningMove();
    }
}
