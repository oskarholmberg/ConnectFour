package Model;

import java.util.ArrayList;

/**
 * Used by AI to calculate next move
 */
public class AIBoard extends Board {

    public AIBoard(int [][] matrix, ArrayList<Player> currentPlayers){
        this.matrix = matrix;
        this.currentPlayers = currentPlayers;
    }


    public void makeMove(int column, int player) {
        if (column < width && player <= nbrOfPlayers) {
            int slot = nextAvailableSlot(column);
            if (slot != -1) {
                matrix[column][slot] = player;
            }
        }
    }
    public void undoMove(int column, int player){
        if (column < width && player <= nbrOfPlayers) {
            int slot = nextAvailableSlot(column);
            try{
            if (matrix[column][slot+1] == player) {
                matrix[column][slot+1] = -1;
            }
            }catch (IndexOutOfBoundsException e){

            }
        }
    }
}
