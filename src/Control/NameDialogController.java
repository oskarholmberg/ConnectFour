package Control;

import Model.Board;
import Model.Main;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NameDialogController {

    private Main main;
    private Stage dialogStage;
    private Board board;

    @FXML
    private TextField player1;

    @FXML
    private TextField player2;

    @FXML
    void cancelClicked(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    void okClicked(ActionEvent event) {
        board.addPlayer(player1.getText());
        board.addPlayer(player2.getText());
        board.reset();
        main.showGamePane();
        dialogStage.close();
    }

    public void setDialogStage(Stage stage) {
        dialogStage = stage;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setBoard(Board board) {
        this.board = board;
    }


    @FXML
    void keyReleased(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            okClicked(null);
        }
    }
}
