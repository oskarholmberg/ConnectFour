package Control;

import Model.Board;
import Model.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;

public class OptionPaneController {

    private Main main;
    private Board board;

    @FXML
    private RadioButton mediumRadioButton;

    @FXML
    private RadioButton sixSevenRadioButton;

    @FXML
    private RadioButton hardRadioButton;

    @FXML
    private RadioButton tenElevenRadioButton;

    @FXML
    private RadioButton tictactoeRadioButton;

    @FXML
    private RadioButton easyRadioButton;

    @FXML
    void setEasy(ActionEvent event) {
        board.setDifficulty(Board.EASY);
        setDifficulty();
    }

    @FXML
    void setMedium(ActionEvent event) {
        board.setDifficulty(Board.MEDIUM);
        setDifficulty();
    }

    @FXML
    void setHard(ActionEvent event) {
        board.setDifficulty(Board.HARD);
        setDifficulty();
    }


    @FXML
    void setTicTacToe(ActionEvent event) {
        board.setSize(3, 3);
        board.setWinLength(3);
        setSize();
    }

    @FXML
    void setSixSeven(ActionEvent event) {
        board.setSize(6, 7);
        board.setWinLength(4);
        setSize();
    }

    @FXML
    void setTenEleven(ActionEvent event) {
        board.setSize(10, 11);
        board.setWinLength(4);
        setSize();
    }

    @FXML
    void backButton(ActionEvent event) {
        main.showMainPane();
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setDifficulty() {
        int difficulty = board.getDifficulty();
        if (difficulty == Board.EASY) {
            easyRadioButton.setSelected(true);
            mediumRadioButton.setSelected(false);
            hardRadioButton.setSelected(false);
        } else if (difficulty == Board.MEDIUM) {
            easyRadioButton.setSelected(false);
            mediumRadioButton.setSelected(true);
            hardRadioButton.setSelected(false);
        } else if (difficulty == Board.HARD) {
            easyRadioButton.setSelected(false);
            mediumRadioButton.setSelected(false);
            hardRadioButton.setSelected(true);
        }
    }

    public void setSize() {
        int size = board.getWidth();
        if (size == 3) {
            tictactoeRadioButton.setSelected(true);
            sixSevenRadioButton.setSelected(false);
            tenElevenRadioButton.setSelected(false);
            easyRadioButton.setDisable(true);
            mediumRadioButton.setDisable(true);
            hardRadioButton.setDisable(true);
        } else if (size == 7) {
            tictactoeRadioButton.setSelected(false);
            sixSevenRadioButton.setSelected(true);
            tenElevenRadioButton.setSelected(false);
            easyRadioButton.setDisable(false);
            mediumRadioButton.setDisable(false);
            hardRadioButton.setDisable(false);
        } else if (size == 11) {
            tictactoeRadioButton.setSelected(false);
            sixSevenRadioButton.setSelected(false);
            tenElevenRadioButton.setSelected(true);
            easyRadioButton.setDisable(false);
            mediumRadioButton.setDisable(false);
            hardRadioButton.setDisable(false);
        }
    }
}
