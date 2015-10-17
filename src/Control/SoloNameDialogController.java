package Control;

import Model.Board;
import Model.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SoloNameDialogController {

    private Main main;
    private Board board;
    private Stage dialogStage;

    @FXML
    private TextField name;

    @FXML
    void okClicked(ActionEvent event) {
        dialogStage.close();
        board.addPlayer(name.getText());
        board.addPlayer("Bot");
        board.reset();
        main.showGamePane();
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        dialogStage.close();
    }

    public void setDialogStage(Stage stage){
        dialogStage = stage;
    }

    public void setMain(Main main){this.main = main;}

    public void setBoard(Board board){this.board = board;}

}