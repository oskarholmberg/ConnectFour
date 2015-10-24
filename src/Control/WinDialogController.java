package Control;

import Model.Board;
import Model.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WinDialogController {

    private Main main;
    private Board board;
    private Stage window;

    @FXML
    private Label winningPlayerLabel;

    @FXML
    void newGameClicked(ActionEvent event) {
        board.reset();
        main.showGamePane();
        window.close();
    }

    @FXML
    void mainMenuClicked(ActionEvent event) {
        main.showMainPane();
        window.close();
    }

    @FXML
    void auditLogClicked(ActionEvent event) {
        main.showAuditLog();
        window.close();
    }

    public void setWinner(String s){
        winningPlayerLabel.setText(s);
    }

    public void setMain(Main main){this.main = main;}

    public void setBoard(Board board){
        this.board = board;
        winningPlayerLabel.setText(board.getCurrentPlayers().get(board.getPlayer()).getName()+ " won!");
    }

    public void setDialog(Stage dialogStage){
        window = dialogStage;
    }

}