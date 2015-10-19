package Model;

import java.util.ArrayList;

/**
 * Used by AIPlayer to calculate next move
 */
public class AIBoard extends Board {


    public AIBoard(Board board){
        this.matrix = board.getMatrix();
        this.currentPlayers = board.getCurrentPlayers();
        this.height = board.getHeight();
        this.width = board.getWidth();
        this.winLength = board.getWinLength();
        this.nbrOfPlayers = currentPlayers.size();
    }
    /**
     * Tries to place an opponents tile in given column.
     * If not possible board remains unchanged.
     *
     * @param column, column in which to place tile.
     */
    public void makeMoveOpponent(int column) {
        makeMove(column, 0);
    }

    /**
     * Tries to place a AI tile in given column
     * If not possible board remains unchanged.
     *
     * @param column, column in which to place tile.
     */
    public void makeMoveAI(int column) {
                makeMove(column, 1);
    }

    /**
     * Tries to remove an opponents tile from given column.
     * (Fails if the column is empty or if the top tile
     * does not belong to a player).
     * If not possible board remains the same.
     * @param column, column in which to place tile.
     */
    public void undoMoveOpponent(int column) {
        undoMove(column, 0);
    }

    /**
     * Tries to remove a player tile from given column.
     * (Fails if the column is empty or if the top tile
     * does not belong to a player).
     * If not possible board remains the same.
     * @param column
     */
    public void undoMoveAI(int column) {
        undoMove(column, 1);
    }

    private void makeMove(int column, int player) {
        if (nextAvailableSlot(column) != -1) {
            matrix[column][nextAvailableSlot(column)] = player;
        }
        //Change turn.
        this.player = (this.player+1)%nbrOfPlayers;
        System.out.println(this.player);
    }

    private void undoMove(int column, int player) {
        try {
            if (matrix[column][nextAvailableSlot(column) + 1] == player)
                matrix[column][nextAvailableSlot(column) + 1] = -1;
        } catch (IndexOutOfBoundsException e) {

        }
    }
}