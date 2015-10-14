package Control;

import Model.Board;
import Model.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WinDialogController {

    private Main main;
    private Board board;

    @FXML
    private Label winningPlayerLabel;

    @FXML
    void newGameClicked(ActionEvent event) {

    }

    @FXML
    void mainMenuClicked(ActionEvent event) {

    }

    @FXML
    void auditLogClicked(ActionEvent event) {
        main.showAuditLog(board.getAuditLog());
    }

    public void setWinner(String s){
        winningPlayerLabel.setText(s);
    }

    public void setMain(Main main){this.main = main;}

    public void setBoard(Board board){
        this.board = board;
        winningPlayerLabel.setText(board.getPlayerNames().get(board.getPlayer())+ " won!");
    }

}