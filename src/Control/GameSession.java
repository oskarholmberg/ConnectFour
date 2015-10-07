package Control;

import Model.Board;
import Model.Player;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Oskar on 2015-10-07.
 */
public class GameSession {
    private ArrayList<Player> players;
    private int width = 7;
    private int height = 6;
    private Board board;
    private Scanner scanInput = new Scanner(System.in);

    public GameSession(ArrayList<Player> players) {
        this.players = players;
        board = new Board();
    }

    public void run() {
        boolean winner = false;
        while (!winner) {
            for (Player p : players) {
                System.out.println(p.getName() + "'s turn.");
                System.out.println(board.toString());
                while (!board.put(getColumn(), p.getToken())) {

                }
                if (board.winningMove()) {
                    System.out.println("Congratulations " + p.getName() + " you won!");
                    p.won();
                    winner = true;
                    break;
                }
            }
        }
        scanInput.close();
    }

    public int getColumn() {
        String column = "";
        int col = -1;
        while (col < 0 || col >= width) {
            System.out.println("Enter the column in which you wish to place your token.");
            column = scanInput.nextLine();
            col = Integer.parseInt(column);
        }
        return col;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
