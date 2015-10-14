package Model;

import Control.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Oskar on 2015-10-09.
 */
public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Connect Four");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/rootLayoutPane.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout, 600, 400);
            this.primaryStage.setScene(scene);
            this.primaryStage.setResizable(false);
            this.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showMainPane();
    }

    public void showMainPane() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/mainPane.fxml"));
            Pane mainPane = (Pane) loader.load();
            rootLayout.setCenter(mainPane);
            MainPaneController contoller = loader.getController();
            contoller.setMain(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showGamePane(Board board){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/gamePane.fxml"));
            Pane gamePane = (Pane) loader.load();
            rootLayout.setCenter(gamePane);
            GamePaneController controller = loader.getController();
            controller.setMain(this);
            controller.setBoard(board);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNameDialog(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/nameDialog.fxml"));
            AnchorPane dialogPane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Players");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(dialogPane);
            dialogStage.setScene(scene);
            NameDialogController controller = loader.getController();
            controller.setMain(this);
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showWinDialog(Board board){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/winDialog.fxml"));
            AnchorPane dialogPane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("WINNER!");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(dialogPane);
            dialogStage.setScene(scene);
            WinDialogController controller = loader.getController();
            controller.setMain(this);
            controller.setBoard(board);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAuditLog(String log){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/auditLogPane.fxml"));
            BorderPane pane = (BorderPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("WINNER!");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            AuditLogController controller = loader.getController();
            controller.setText(log);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
