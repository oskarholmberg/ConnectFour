package Model;

import java.io.*;
import java.util.*;

/**
 * Created by Oskar on 2015-10-06.
 */
public class Board {

    protected int height = 6;
    protected int width = 7;
    protected int[][] matrix;
    protected int player = 0;
    private ArrayList<Player> players = new ArrayList<>();
    protected int nbrOfPlayers = 2;
    private int winLength = 4;
    protected ArrayList<Player> currentPlayers = new ArrayList<>();
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
     * Attempts to place a tile in selected column and checks if that placement rendered a win condition.
     * If column is full, board remains the same. If win condition is caused board is locked.
     *
     * @param column, the selected column for placing tile.
     * @return 1 if placement caused win condition, 0 if successful placement, -1 if column is full.
     */
    public int put(int column) {
        if (column < width && !locked) {
            int slot = nextAvailableSlot(column);
            if (slot != -1) {
                Player p = currentPlayers.get(player);
                matrix[column][slot] = player;
                auditlog.append(cal.getTime().toString() + ": " + p.getName() + " placed a tile in column " + column + "].\n");
                if (winningMove()) {
                    auditlog.append(cal.getTime().toString() + ": " + p.getName() + " won!\n");
                    p.won();
                    sortList();
                    locked = true;
                    return 1;
                }
                player = (player + 1) % nbrOfPlayers;
                return 0;
            }
        }
        auditlog.append(cal.getTime().toString() + ": " + currentPlayers.get(player).getName() + " tried to place a tile in column: " + column + " but it was full.\n");
        return -1;
    }


    /**
     * Checks if there is a win condition.
     *
     * @return true if a player has won, false otherwise.
     */
    public boolean winningMove() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[j][i] != -1) { // No point in starting at an empty position
                    if (checkRight(j, i, winLength - 1)) return true; // -1 since we already have found 1.
                    if (checkUp(j, i, winLength - 1)) return true;
                    if (checkDiagonalRight(j, i, winLength - 1)) return true;
                    if (checkDiagonalLeft(j, i, winLength - 1)) return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the parameter player is the winner of the game.
     *
     * @param p, player to evaluate.
     * @return, true if player is the winner, false if not the winner or no winner exists.
     */

    public boolean isWinner(Player p) {
        if(winningMove()) {
            return p.equals(currentPlayers.get(player));
        }
        return false;
    }

    /**
     * Clears board of all tiles and unlocks it.
     */
    public void reset() {
        locked = false;
        auditlog.append(cal.getTime().toString() + ": Board was reset.\n");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[j][i] = -1;
            }
        }
    }


    /**
     * Returns the matrix representation of the board.
     *
     * @return, matrix representation of the board.
     */

    public int[][] getMatrix() {
        return matrix;
    }

    /**
     * Searches through existing players for player with corresponding name and
     * adds that player to the current players on the board.
     * If that player is not found a new player will be created and added.
     * If parameter name is "Bot" a ME bot will be added instead.
     *
     * @param name, Name of player to be added.
     */

    public void addPlayer(String name) {
        //Make sure that playerNbr is not out of bounds.
        if (currentPlayers.size() >= nbrOfPlayers) {
            currentPlayers.clear();
        }
        if (players.contains(new HumanPlayer(name))) {
            for (Player p : players) {
                if (p.getName().equals(name)) {
                    currentPlayers.add(p);
                }
            }
        } else {
            Player p = null;
            if (name.equals("Bot")) {
                p = new AIPlayer("Jarvis");
            } else {
                p = new HumanPlayer(name);
                players.add(p);
            }
            currentPlayers.add(p);
        }
    }

    /**
     * Returns an integer representation of the current player.
     * Player 1 will be 0, Player 2 will be 1 and so on.
     *
     * @return, Integer representation of current player.
     */

    public int getPlayer() {
        return player;
    }

    /**
     * Returns String representation of the audit log.
     *
     * @return, String representation of audit log.
     */

    public String getAuditLog() {
        return auditlog.toString();
    }

    public int getWidth() {
        return width;
    }
    public int getHeight(){
        return height;
    }
    public int getWinLength(){
        return winLength;
    }


    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        for (int x = 0; x < width; x++) {
            result.append((x + 1) + " ");
        }
        result.append(System.lineSeparator());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix[x][y] == 0) {
                    result.append("R ");
                } else if (matrix[x][y] == 1) {
                    result.append("B ");
                } else {
                    result.append(". ");
                }
            }
            result.append(System.lineSeparator());
        }
        return result.toString();
    }

    /**
     * Finds the next available slot in selected column.
     *
     * @param column, column to be evaluated.
     * @return index for next available slot in that column. -1 if full.
     */
    public int nextAvailableSlot(int column) {
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
            if (inARow == 0) {
                return true;
            }
            if (matrix[w][h] == matrix[w + 1][h]) {
                return checkRight(w + 1, h, inARow - 1);
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
            if (inARow == 0) {
                return true;
            }
            if (matrix[w][h] == matrix[w][h - 1]) {
                return checkUp(w, h - 1, inARow - 1);
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
            if (inARow == 0) {
                return true;
            }
            if (matrix[w][h] == matrix[w + 1][h - 1]) {
                return checkDiagonalRight(w + 1, h - 1, inARow - 1);
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
            if (inARow == 0) {
                return true;
            }
            if (matrix[w][h] == matrix[w - 1][h - 1]) {
                return checkDiagonalLeft(w - 1, h - 1, inARow - 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    /**
     * Gets the 10 players with most wins, each element contains the name of the
     * player and that players number of wins.
     *
     * @return, Sorted String[] with best player at index 0 and worst index 9.
     */
    public String[] getTop10() {
        String[] top10 = new String[10];
        int i = 0;
        //While there are entries AND 10 entries havnt been selected.
        while (players.size() > i && i < 10) {
            Player p = players.get(i);
            top10[i] = (p.getName() + " \t\t with " + p.getNbrWins() + " wins");
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
     * Returns the players engaged in the current game.
     *
     * @return, ArrayList<Player>, Players currently playing on this board.
     */

    public ArrayList<Player> getCurrentPlayers() {
        return currentPlayers;
    }

    /**
     * Sort list depending on highest number of wins.
     */
    private void sortList() {
        Collections.sort(players);
    }

    /**
     * Tries to load the persistent data for the audit log and players.
     * Searches for files auditlog.txt and players.txt in application file path.
     * If these do not exist nothing will happen.
     */
    private void load() {
        try {
            Scanner auditScan = new Scanner(new File("auditlog.txt"));
            Scanner playerScan = new Scanner(new File("players.txt"));
            while (auditScan.hasNext()) {
                auditlog.append(auditScan.nextLine() + "\n");
            }
            String name;
            int wins;
            while (playerScan.hasNext()) {
                String[] line = playerScan.nextLine().split(":");
                name = line[0];
                wins = Integer.parseInt(line[1]);
                Player p = new HumanPlayer(name);
                p.setNbrWins(wins);
                players.add(p);
            }
        } catch (IOException e) {
        }
    }

    /**
     * Saves the audit log and all players that have played to file in application file path.
     * If these do not exist they will be created.
     */

    public void save() {
        BufferedWriter auditWriter = null;
        BufferedWriter playerWriter = null;
        try {
            auditWriter = new BufferedWriter(new FileWriter("auditlog.txt"));
            playerWriter = new BufferedWriter(new FileWriter("players.txt"));
            auditWriter.write(getAuditLog());
            for (Player p : players) {
                playerWriter.write(p.getName() + ":" + p.getNbrWins() + "\n");
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
