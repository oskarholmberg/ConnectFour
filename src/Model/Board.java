package Model;

import java.io.*;
import java.util.*;

/**
 * Created by Oskar on 2015-10-06.
 */
public class Board {

    private int height = 6;
    private int width = 7;
    private int[][] matrix;
    private int player = 0;
    private ArrayList<Player> players = new ArrayList<>();
    private int nbrOfPlayers = 2;
    private Player[] currentPlayers = new Player[nbrOfPlayers];
    private StringBuilder auditlog;
    private Calendar cal;
    private boolean locked = false;

    public Board() {
        auditlog = new StringBuilder();
        load();
        cal = Calendar.getInstance();
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
        if (column < width && !locked) {
            int slot = nextAvailableSlot(column);
            if (slot != -1) {
                matrix[column][slot] = player;
                log(cal.getTime().toString() + ": " + currentPlayers[player].getName() + " placed a tile in column " + column + "].\n");
                if (winningMove()) {
                    log(cal.getTime().toString() + ": " + currentPlayers[player].getName() + " won!\n");
                    currentPlayers[player].won();
                    sortList();
                    locked = true;
                    return 1;
                }
                player = (player + 1) % nbrOfPlayers;
                return 0;
            }
        }
        log(cal.getTime().toString() + ": " + currentPlayers[player].getName() + " tried to place a tile in column: " + column + " but it was full.\n");
        return -1;
    }


    /**
     * Checks if there is a win condition.
     *
     * @return true if a player has won, false otherwise.
     */
    private boolean winningMove() {
        for (int i = 0; i < height; i++) {
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
        locked = false;
        log(cal.getTime().toString() + ": Board was reset.\n");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[j][i] = -1;
            }
        }
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void addPlayer(String name, int playerNbr) {
        //Make sure that playerNbr is not out of bounds.
        if (playerNbr < nbrOfPlayers) {
            if (players.contains(new Player(name))) {
                for (Player p : players) {
                    if (p.getName().equals(name)) {
                        currentPlayers[playerNbr] = p;
                    }
                }
            } else {
                Player p = new Player(name);
                players.add(p);
                currentPlayers[playerNbr] = p;
            }
        }
    }

    public String[] getPlayerNames() {
        String[] names = new String[2];
        names[0] = currentPlayers[0].getName();
        names[1] = currentPlayers[1].getName();
        return names;
    }

    public int getPlayer() {
        return player;
    }

    public String getAuditLog() {
        return auditlog.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sb.append(matrix[j][i] + "\t");
            }
            sb.append("\n");
        }
        return sb.toString();
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
                return true;
            }
            if (matrix[w][h] == matrix[w - 1][h - 1]) {
                return checkDiagonalLeft(w - 1, h - 1, inARow + 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    private void log(String s) {
        auditlog.append(s);
    }

    /**
     * Gets the 10 players with most wins.
     *
     * @return, Sorted String[] with best player at index 0 and worst index 9.
     */
    public String[] getTop10() {
        String[] top10 = new String[10];
        int i = 0;
        //While there are entries AND 10 entries havnt been selected.
        while (players.size() > i && i < 10) {
            top10[i] = players.get(i).getName();
            i++;
        }
        // Fill in empty slots.
        while (i < 10) {
            top10[i] = "Up for taking!";
            i++;
        }
        return top10;
    }

    /**
     * Sort list depending on highest number of wins.
     */
    private void sortList() {
        Collections.sort(players);
    }

    /**
     * Loads the persistent data for the audit log and players.
     * Uses a trick with java.util.Scanner to access an java.io.InputStream from it.
     * Scanner iterates over tokens in the stream, by using the "beginning of input" (\A) as a delimiter
     * only one token is given for the entire content of the stream.
     */
    private void load() {
        try {
            Scanner auditScan = new Scanner(new File("auditlog.txt"));
            Scanner playerScan = new Scanner(new File("players.txt"));
            while (auditScan.hasNext()) {
                auditlog.append(auditScan.nextLine()+"\n");
            }
            String name;
            int wins;
            while (playerScan.hasNext()) {
                String[] line = playerScan.nextLine().split(":");
                name = line[0];
                wins = Integer.parseInt(line[1]);
                Player p = new Player(name);
                p.setNbrWins(wins);
                players.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        BufferedWriter auditWriter = null;
        BufferedWriter playerWriter = null;
        try {
            auditWriter = new BufferedWriter(new FileWriter("auditlog.txt"));
            playerWriter = new BufferedWriter(new FileWriter("players.txt"));
            auditWriter.write(getAuditLog());
            for(Player p:players){
                playerWriter.write(p.getName()+":"+p.getNbrWins()+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (auditWriter != null)
                    auditWriter.close();
                if (playerWriter != null)
                    playerWriter.close();
            } catch (IOException e) {
            }
        }
    }
}
