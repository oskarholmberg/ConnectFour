package Control;


import Model.Board;
import Model.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class MainPaneController {

    private Main main;
    private Board board;

    @FXML
    void multiplayerButtonClicked(ActionEvent event) {
        main.showNameDialog();
    }

    @FXML
    void highscoreButtonClicked(ActionEvent event) {
        main.showHighscorePane();
    }

    @FXML
    void auditlogButtonClicked(ActionEvent event) {
        main.showAuditLog();
    }

    @FXML
    void newGameButton(ActionEvent event) {
        if(board.getWidth() == 3){
            board.setWinLength(4);
            board.setSize(6,7);
        }
        main.showSoloNameDialog();
    }

    @FXML
    void quitButton(ActionEvent event) {
        //Save the audit log and list of Players to file.
        board.save();
        Platform.exit();
    }

    @FXML
    void optionsButton(ActionEvent event) {
        main.showOptionsPane();
    }

    public void setMain(Main main){this.main = main;}

    public void setBoard(Board board){this.board = board;}
}
