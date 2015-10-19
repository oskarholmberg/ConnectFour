package Model;

/**
 * Created by oskar on 2015-10-16.
 */
public class AIPlayer extends Player implements Comparable<Player> {

    /**
     * The AI calculates difficulty moves ahead.
     */
    private int difficulty = Board.MEDIUM;
    /**
     * Value given to trail leading to win.
     */
    static final float WIN = 1f;
    /**
     * Value given to trail leading to loss.
     */
    static final float LOSE = -1f;
    /**
     * Value given to trail leading to inevitable
     * loss the next turn (providing that the other
     * player is rational).
     */
    static final float BAD = 0f;

    private AIBoard board;

    public AIPlayer(String name) {
        super(name);
    }

    /**
     * Calculates the most favourable column
     * to place tile.
     *
     * @param b, board to evaluate
     * @return, column of best placement starting
     * index 0.
     */
    public int getColumn(Board b) {
        board = new AIBoard(b);
        double maxValue = 2. * Integer.MIN_VALUE;
        int move = 0;

        //Look through every column to find the best
        //possible move. If value = WIN that means that
        //if you do the move you win. So search can stop there
        for (int column = 0; column < board.getWidth(); column++) {
            if (board.nextAvailableSlot(column) != -1) {
                // Compare the previous best
                // move with the new one and
                // swap if the new move is better.
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

    private double moveValue(int column) {
        // In order to calculate the best move,
        // the move is done, given a value and
        // removed.
        board.makeMoveAI(column);
        double val = alphabeta(difficulty, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
        board.undoMoveAI(column);
        return val;
    }

    // Recursive call using the minmax algorithm and alpha beta pruning.
    // Since the AIPlayer wants to win, it will try to maximize the value
    // of it's moves and at the same time minimizing the value of the
    // opponents moves. So by building a tree where the children to each
    // node are all possible moves in that state and taking turns in choosing the
    // highest(max) value and lowest(min) value and let these values bubble upwards.
    // The best possible move can be found.
    // Video description: https://www.youtube.com/watch?v=2xsXEpdyDUg
    private double alphabeta(int depth, double alpha, double beta, boolean maximizingPlayer) {
        boolean hasWinner = board.winningMove();
        // If there is a winner or the
        // depth is 0 (the number of predicting
        // stages have been completed) we're done!
        if (depth == 0 || hasWinner) {
            double score = 0;
            if (hasWinner) {
                score = board.isWinner(this) ? WIN : LOSE;
            } else {
                score = BAD;
            }
            // Small tweak in order to give a
            // higher score to good moves that
            // are higher up in the recursive call.
            // For instance: It is better to
            // prevent a bad thing from happening
            // the next turn rather than in five turns.
            // And vice versa for good things.
            return score / (difficulty - depth + 1);
        }

        if (maximizingPlayer) {
            for (int column = 0; column < board
                    .getWidth(); column++) {
                if (board.nextAvailableSlot(column) != -1) {
                    board.makeMoveAI(column);
                    alpha = Math.max(alpha, alphabeta(depth - 1, alpha, beta, false));
                    board.undoMoveAI(column);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return alpha;
        } else {
            for (int column = 0; column < board
                    .getWidth(); column++) {
                if (board.nextAvailableSlot(column) != -1) {
                    board.makeMoveOpponent(column);
                    beta = Math.min(beta, alphabeta(depth - 1, alpha, beta, true));
                    board.undoMoveOpponent(column);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return beta;
        }
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AIPlayer) {
            Player p = (AIPlayer) o;
            return p.getName().equals(name);
        }
        return false;
    }
}

