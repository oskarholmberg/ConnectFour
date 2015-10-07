package Model;

/**
 * Created by Oskar on 2015-10-06.
 */
public class Board {

    private int height;
    private int width;
    private int[][] matrix;

    public Board() {
        height = 6;
        width = 7;
        matrix = new int[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[j][i] = -1;
            }
        }
    }

    /**
     * Creates game matrix from input parameters.
     *
     * @param height, desired height
     * @param width,  desired width
     */
    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        matrix = new int[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[j][i] = -1;
            }
        }
    }

    /**
     * Attempts to place a token in selected column.
     *
     * @param column, the selected column for placing token.
     * @param token,  token to be placed
     * @return true if slot is available for placement, false if not.
     */
    public boolean put(int column, Token token) {
        int slot = nextAvailableSlot(column);
        if (slot != -1) {
            matrix[column][slot] = token.getShape();
            return true;
        }
        return false;
    }


    /**
     * Checks if there is a win condition.
     *
     * @return true if a player has won, false otherwise.
     */
    public boolean winningMove() {
        boolean horizon = false;
        boolean up = false;
        boolean diagR = false;
        boolean diagL = false;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[j][i] != -1) {
                    horizon = checkRight(j, i, 1);
                    up = checkUp(j, i, 1);
                    diagR = checkDiagonalRight(j, i, 1);
                    diagL = checkDiagonalLeft(j, i, 1);
                }
                if (horizon || up || diagR || diagL) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Clears board of all tokens.
     */
    public void reset() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[j][i] = -1;
            }
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sb.append(matrix[j][i]+"\t");
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
        for (int i = 0; i < height; i++) {
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
     * @param inARow, number of tokens of same shape found in a row so far.
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
     * @param inARow, number of tokens of same shape found in a row so far.
     * @return true if winning condition met, false otherwise.
     */
    private boolean checkUp(int w, int h, int inARow) {
        try {
            if (inARow == 4) {
                return true;
            }
            if (matrix[w][h] == matrix[w][h + 1]) {
                return checkUp(w, h + 1, inARow + 1);
            }
        } catch(IndexOutOfBoundsException e){
            return false;
        }
        return false;
    }

    /**
     * Checks through recursion if a winning condition is met diagonally and returns true if so.
     *
     * @param h,      start position height
     * @param w,      start position width
     * @param inARow, number of tokens of same shape found in a row so far.
     * @return true if winning condition met, false otherwise.
     */
    private boolean checkDiagonalRight(int w, int h, int inARow) {
        try {
            if (inARow == 4) {
                return true;
            }
            if (matrix[w][h] == matrix[w + 1][h + 1]) {
                return checkDiagonalRight(w + 1, h + 1, inARow + 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    private boolean checkDiagonalLeft(int w, int h, int inARow) {
        try {
            if (inARow == 4) {
                return true;
            }
            if (matrix[w][h] == matrix[w - 1][h + 1]) {
                return checkDiagonalRight(w - 1, h + 1, inARow + 1);
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }


}
