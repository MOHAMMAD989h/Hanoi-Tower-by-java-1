package com.example.solvehanoi;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class main {

    @FXML
    public void initialize() {

    }
    public void openNewWindow(String fxmlFile, String title, ActionEvent event,int H, int W) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load(), W, H);


            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();


            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void StartGame(ActionEvent actionEvent) {
        openNewWindow("hello-view.fxml","Game",actionEvent,800,800);
    }

    public void Score(ActionEvent actionEvent) {
    }

    public void Help(ActionEvent actionEvent) {
    }
}
