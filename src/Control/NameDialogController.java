package Control;

import Model.Board;
import Model.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NameDialogController {

    private Main main;
    private Stage dialogStage;

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
        dialogStage.close();
        Board board = new Board();
        board.addPlayer(player1.getText());
        board.addPlayer(player2.getText());
        main.showGamePane(board);
    }

    public void setDialogStage(Stage stage){
        dialogStage = stage;
    }

    public void setMain(Main main){this.main = main;}

}
