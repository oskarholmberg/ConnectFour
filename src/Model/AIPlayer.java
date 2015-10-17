package Model;

/**
 * Created by oskar on 2015-10-16.
 */
public class AIPlayer extends Player implements Comparable<Player> {

    private int difficulty = 6;
    //will cause win
    static final float WIN = 1f;
    //will cause loss
    static final float LOSE = -1f;
    //will cause loss in next move
    static final float BAD = 0f;
    static final int HUMAN = 0, AI = 1;

    private AIBoard board;

    public AIPlayer(String name) {
        super(name);
    }

    public int compareTo(Player p) {
        return -1;
    }

    public int getColumn(Board b) {
        board = new AIBoard(b.getMatrix(), b.getCurrentPlayers());
        double maxValue = Integer.MIN_VALUE;
        int move = 0;
        //Search the board for best move.
        //Stops search if WIN has been found since that will
        //cause a win condition in next move.
        for (int column = 0; column < board.getWidth(); column++) {
            if (board.nextAvailableSlot(column) != -1) {
                //Compare current score to the next and change if higher.
                double value = moveValue(column);
                if (value > maxValue) {
                    maxValue = value;
                    move = column;
                    if (value == WIN) {
                        break;
                    }
                }
            }
        }
        return move;
    }

    double moveValue(int column) {
        // To determine the value of a move, first
        // make the move, estimate that state and
        // then undo the move again.
        board.makeMove(column, AI);
        double val = alphabeta(difficulty, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
        board.undoMove(column, AI);
        return val;
    }

    double alphabeta(int depth, double alpha, double beta, boolean maximizingPlayer) {
        boolean hasWinner = board.winningMove();
        // All these conditions lead to a
        // termination of the recursion
        if (depth == 0 || hasWinner) {
            double score = 0;
            if (hasWinner) {
                score = board.getPlayerNames()[board.getPlayer()].equals(name) ? LOSE : WIN; // compare this name to the name of the current player.
            } else {
                score = BAD;
            }
            //Since the depth here will start at a higher value
            //and decrease with each recursion.
            //The score is calculated like this to enforce that
            //something good will happen the next move it gets a higher
            //score than if it would happen after 5 turns.
            System.out.println(score / (difficulty - depth + 1));
            return score / (difficulty - depth + 1);
        }
        if (maximizingPlayer) {
            for (int column = 0; column < board.getWidth(); column++) {
                if (board.nextAvailableSlot(column) != -1) {
                    board.makeMove(column, AI);
                    alpha = Math.max(alpha, alphabeta(depth - 1, alpha, beta, false));
                    board.undoMove(column, AI);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return alpha;
        } else {
            for (int column = 0; column < board.getWidth(); column++) {
                if (board.nextAvailableSlot(column) != -1) {
                    board.makeMove(column, HUMAN);
                    beta = Math.min(beta, alphabeta(depth - 1, alpha, beta, true));
                    board.undoMove(column, HUMAN);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return beta;
        }
    }
}

