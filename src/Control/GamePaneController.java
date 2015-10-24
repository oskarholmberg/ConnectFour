package Control;

import Model.AIPlayer;
import Model.Board;
import Model.Main;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

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
     *
     * @param event, MouseEvent from click.
     */
    @FXML
    void gridClicked(MouseEvent event) {
        EventTarget target = event.getTarget();
        int column = (int) ((event.getX() / (350/board.getWidth())));
        int row = (int) ((event.getY()) / (300/board.getHeight()));
        won = board.put(column, row);
        paint();
        if (won == 1) {
            main.showWinDialog();
        }
        //Checks if next player in line is an AIPlayer.
        //If so that AIPlayers turn is performed
        // unless the HumanPlayer just won.
        Player p = board.getCurrentPlayers().get(board.getPlayer());
        if (p instanceof AIPlayer && won != 1) {
            won = board.put(((AIPlayer) p).getColumn(board), row);
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
        setTurn();
    }

    /**
     * Changes the color of the circle showing next tile color and
     * the name displayed above it to the currently active player
     * on the Board.
     */

    private void setTurn() {
        turnLabel.setText(board.getCurrentPlayers().get(board.getPlayer()).getName() + "'s turn!");
        if (board.getPlayer() == 0) {
            coin.setFill(Paint.valueOf("red"));
        }
        if (board.getPlayer() == 1) {
            coin.setFill(Paint.valueOf("blue"));
        }
        if (board.getPlayer() == 2) {
            coin.setFill(Paint.valueOf("green"));
        }
    }

    /**
     * Paints all tiles in the view to correspond with the
     * board.
     */

    private void paint() {
        matrix = board.getMatrix();
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = board.getHeight()-1; j >= 0; j--) {
                if (matrix[i][j] == 0) {
                    boardGrid.add(new Circle(boardGrid.getColumnConstraints().get(0).getPrefWidth() / 2, Paint.valueOf("red")), i, j);
                }
                if (matrix[i][j] == 1) {
                    boardGrid.add(new Circle(boardGrid.getColumnConstraints().get(0).getPrefWidth() / 2, Paint.valueOf("blue")), i, j);
                }
                if (matrix[i][j] == 2) {
                    boardGrid.add(new Circle(boardGrid.getColumnConstraints().get(0).getPrefWidth() / 2, Paint.valueOf("green")), i, j);
                }
            }
        }
        setTurn();
    }

    /**
     * Reformats the grid displaying the game session to
     * concur with the board.
     */

    public void reformatGrid() {
        int width = boardGrid.getColumnConstraints().size();
        int height = boardGrid.getRowConstraints().size();
        //If grid width is smaller than board width add columns
        if (width < board.getWidth()) {
            for (int i = width; i < board.getWidth(); i++) {
                boardGrid.getColumnConstraints().add(new ColumnConstraints());
            }
        }
        //If grid width is larger than board width remove columns
        if (width > board.getWidth()) {
            for (int i = width-1; i > board.getWidth()-1; i--) {
                boardGrid.getColumnConstraints().remove(i);
            }
        }
        //If grid height is lower than board width add rows
        if (height < board.getHeight()) {
            for (int i = height; i < board.getHeight(); i++) {
                boardGrid.getRowConstraints().add(new RowConstraints());
            }
        }
        //If grid height is higher than board width remove rows
        if (height > board.getHeight()) {
            for (int i = height-1; i > board.getHeight()-1; i--) {
                boardGrid.getRowConstraints().remove(i);
            }
        }
        //Set constraints (size) of rows and columns to accurate
        //proportions, i.e. grid width is 350 so each column should be
        // 350 / (number of columns) wide. Similar for the height.
        // With the exception with TicTacToe where the board is squared
        //instead of rectangular.
        for (int i = 0; i < board.getWidth(); i++) {
            if(board.getWidth()!=3) {
                boardGrid.getColumnConstraints().get(i).setPrefWidth(350 / board.getWidth());
            }else{
                boardGrid.getColumnConstraints().get(i).setPrefWidth(300 / board.getWidth());
            }
        }
        for (int i = 0; i < board.getHeight(); i++) {
            boardGrid.getRowConstraints().get(i).setPrefHeight(300 / board.getHeight());
        }
    }
}