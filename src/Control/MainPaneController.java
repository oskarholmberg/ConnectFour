package Control;


import Model.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MainPaneController {

    private Main main;

    @FXML
    void multiplayerButtonClicked(ActionEvent event) {
        main.showNameDialog();
    }

    @FXML
    void highscoreButtonClicked(ActionEvent event) {

    }

    @FXML
    void auditlogButtonClicked(ActionEvent event) {

    }

    public void setMain(Main main){this.main = main;}

}
