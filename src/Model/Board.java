package Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by Oskar on 2015-10-06.
 */
public class Board {

    private int height = 6;
    private int width = 7;
    private int[][] matrix;
    private int player = 0;
    private ArrayList<String> players = new ArrayList<>();
    private StringBuilder auditlog;
    private Calendar cal;

    public Board() {
        cal = Calendar.getInstance();
        auditlog = new StringBuilder();
        matrix = new int[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[j][i] = -1;
            }
        }
    }

    /**
     * Attempts to place a color in selected column and checks if that placement rendered a win condition.
     *
     * @param column, the selected column for placing color.
     * @return 1 if placement caused win condition, 0 if successful placement, -1 if column is full.
     */
    public int put(int column) {
        if (column < width) {
            int slot = nextAvailableSlot(column);
            if (slot != -1) {
                matrix[column][slot] = player;
                auditlog.append(cal.getTime().toString() + ":" + players.get(player) + " placed a tile in column " + column + "].\n");
                if (winningMove()) {
                    auditlog.append(players.get(player) + " won!\n");
                    return 1;
                }
                player = (player + 1) % 2;
                return 0;
            }
        }
        auditlog.append(cal.getTime().toString() + ":" + players.get(player) + " tried to place a tile in column: +" + column + ".\n");
        return -1;
    }


    /**
     * Checks if there is a win condition.
     *
     * @return true if a player has won, false otherwise.
     */
    public boolean winningMove() {
        for (int i = height - 1; i >= 0; i--) { // -3 since if you have not gotten 4 in a row yet you can't it due to board limitations.
            for (int j = 0; j < width; j++) {
                if (matrix[j][i] != -1) { // No point in starting at an empty position
                    if (checkRight(j, i, 1)) return true;
                    if (checkUp(j, i, 1)) return true;
                    if (checkDiagonalRight(j, i, 1)) return true;
                    if (checkDiagonalLeft(j, i, 1)) return true;
                }
            }
        }
        return false;
    }

    /**
     * Clears board
     */
    public void reset() {
        auditlog.append("Board was reset.\n");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[j][i] = -1;
            }
        }
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void addPlayer(String name) {
        players.add(name);
    }

    public ArrayList<String> getPlayerNames() {
        return players;
    }

    public int getPlayer() {
        return player;
    }

    public String getAuditLog() {
        return auditlog.toString();
    }

    /**
     * Finds the next available slot in selected column.
     *
     * @param column, column to be evaluated.
     * @return index for next available slot in that column. -1 if full.
     */
    private int nextAvailableSlot(int column) {
        for (int i = height - 1; i >= 0; i--) {
            if (matrix[column][i] == -1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Checks through recursion if a winning condition is met horizontally and returns true if so.
     *
     * @param h,      start position height
     * @param w,      start position width
     * @param inARow, number of same color found in a row so far.
     * @return true if winning condition met, false otherwise.
     */
    private boolean checkRight(int w, int h, int inARow) {
        try {
            if (inARow == 4) {
                return true;
            }
            if (matrix[w][h] == matrix[w + 1][h]) {
                return checkRight(w + 1, h, inARow + 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    /**
     * Checks through recursion if a winning condition is met up wards and returns true if so.
     *
     * @param h,      start position height
     * @param w,      start position width
     * @param inARow, number of same color found in a row so far.
     * @return true if winning condition met, false otherwise.
     */
    private boolean checkUp(int w, int h, int inARow) {
        try {
            if (inARow == 4) {
                return true;
            }
            if (matrix[w][h] == matrix[w][h - 1]) {
                return checkUp(w, h - 1, inARow + 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    /**
     * Checks through recursion if a winning condition is met diagonally going right and returns true if so.
     *
     * @param h,      start position height
     * @param w,      start position width
     * @param inARow, number of same color found in a row so far.
     * @return true if winning condition met, false otherwise.
     */
    private boolean checkDiagonalRight(int w, int h, int inARow) {
        try {
            if (inARow == 4) {
                System.out.println("DiaRight");
                return true;
            }
            if (matrix[w][h] == matrix[w + 1][h - 1]) {
                return checkDiagonalRight(w + 1, h - 1, inARow + 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    /**
     * Checks through recursion if a winning condition is met diagonally going left and returns true if so.
     *
     * @param h,      start position height
     * @param w,      start position width
     * @param inARow, number of same color found in a row so far.
     * @return true if winning condition met, false otherwise.
     */
    private boolean checkDiagonalLeft(int w, int h, int inARow) {
        try {
            if (inARow == 4) {

                System.out.println("DiaLeft");
                return true;
            }
            if (matrix[w][h] == matrix[w + 1][h - 1]) {
                return checkDiagonalRight(w - 1, h - 1, inARow + 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }
}
