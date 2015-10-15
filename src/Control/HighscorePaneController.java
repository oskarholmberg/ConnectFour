package Control;

import Model.Board;
import Model.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class HighscorePaneController {

    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;
    @FXML
    private Label label8;
    @FXML
    private Label label9;
    @FXML
    private Label label10;
    @FXML
    private Main main;
    private Board board;



    @FXML
    void backButton(ActionEvent event) {
        main.showMainPane();
    }

    public void setMain(Main main){
        this.main = main;
    }

    public void setBoard(Board board){this.board = board;}

    public void setText(){
        String[] names = board.getTop10();
        label1.setText(names[0]);
        label2.setText(names[1]);
        label3.setText(names[2]);
        label4.setText(names[3]);
        label5.setText(names[4]);
        label6.setText(names[5]);
        label7.setText(names[6]);
        label8.setText(names[7]);
        label9.setText(names[8]);
        label10.setText(names[9]);

    }
}