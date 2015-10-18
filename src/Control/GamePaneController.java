package Control;

import Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class GamePaneController {

    private Main view;
    @FXML
    private GridPane boardGrid;
    @FXML
    private Label turnLabel;
    @FXML
    private Pane pane;
    @FXML
    private Circle coin;
    private Board board;
    private int[][] matrix;
    private Main main;
    private int won = 0;

    @FXML
    void newGameButton(ActionEvent event) {
        board.reset();
        main.showGamePane();
    }

    @FXML
    void mainMenuButton(ActionEvent event) {
        main.showMainPane();
    }

    /**
     * Calculates which column was clicked and tries to place a tile there.
     * If the next player in turn is an AIPlayer that AIPlayers move is calculated
     * and performed.
     * @param event, MouseEvent from click.
     */
    @FXML
    void gridClicked(MouseEvent event) {
        EventTarget target = event.getTarget();
        //Calculate which column was clicked. -14 due to alignment and 50 due to grid column width.
        int column = (int) (((event.getSceneX() - 14) / 50));
        won = board.put(column);
        setTurn();
        paint();
        if (won == 1) {
            main.showWinDialog();
        }
        //Checks if next player in line is an AIPlayer.
        //If so that AIPlayers turn is performed
        // unless the HumanPlayer just won.
        Player p = board.getCurrentPlayers().get(board.getPlayer());
        if (p instanceof AIPlayer && won != 1) {
            won = board.put(((AIPlayer) p).getColumn(board));
            paint();
            setTurn();
            if (won == 1) {
                main.showWinDialog();
            }
        }
    }


    public void setMain(Main main) {
        this.main = main;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Changes the color of the circle showing next tile color and
     * the name displayed above it to the currently active player
     * on the Board.
     */

    public void setTurn() {
        turnLabel.setText(board.getCurrentPlayers().get(board.getPlayer()).getName() + "'s turn!");
        if (board.getPlayer() == 0) {
            coin.setFill(Paint.valueOf("red"));
        }
        if (board.getPlayer() == 1) {
            coin.setFill(Paint.valueOf("blue"));
        }
    }

    /**
     * Paints all tiles in the view to correspond with the
     * board.
     */

    private void paint() {
        matrix = board.getMatrix();
        for (int i = 0; i < 7; i++) {
            for (int j = 5; j >= 0; j--) {
                if (matrix[i][j] == 0) {
                    boardGrid.add(new Circle(25, Paint.valueOf("red")), i, j);
                }
                if (matrix[i][j] == 1) {
                    boardGrid.add(new Circle(25, Paint.valueOf("blue")), i, j);
                }
            }
        }
    }
}