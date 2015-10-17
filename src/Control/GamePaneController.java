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

    @FXML
    void gridClicked(MouseEvent event) {
        EventTarget target = event.getTarget();
        int column = (int) (((event.getSceneX() - 14) / 50));//calculate which column was clicked. -14 due to alignment.
        won = board.put(column);
        setTurn();
        paint();
        if (won == 1) {
            main.showWinDialog();
        }
        Player p = board.getCurrentPlayers().get(board.getPlayer());
        if (p instanceof AIPlayer) {
            won = board.put(((AIPlayer) p).getColumn(board));
            setTurn();
            paint();
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

    public void setTurn() {
        turnLabel.setText(board.getPlayerNames()[board.getPlayer()] + "'s turn!");
        if (board.getPlayer() == 0) {
            coin.setFill(Paint.valueOf("red"));
        }
        if (board.getPlayer() == 1) {
            coin.setFill(Paint.valueOf("blue"));
        }
    }

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