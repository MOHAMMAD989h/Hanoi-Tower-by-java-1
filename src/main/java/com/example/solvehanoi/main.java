package com.example.solvehanoi;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;

public class main {



    public boolean isLoggedIn = false;

    @FXML
    private VBox vboxFirst;

    @FXML
    private VBox vbox2;

    @FXML
    private VBox vbox3;

    @FXML
    public Slider numberSlider;

    public static int NumberDisk = 3;

    private Timeline timeline;

    static boolean isSolvedUser = false;
    boolean isOpenContinue = false;

    @FXML
    public void initialize() {
        numberSlider.setMin(3);
        numberSlider.setMax(16);
        numberSlider.setValue(3);
        numberSlider.setBlockIncrement(1);

        // تنظیم اسلایدر برای انتخاب تنها اعداد صحیح
        numberSlider.setMajorTickUnit(1);
        numberSlider.setMinorTickCount(0);
        numberSlider.setSnapToTicks(true);
        numberSlider.setShowTickMarks(true);
        numberSlider.setShowTickLabels(true);
        timeline = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            NumberDisk = (int) Math.round(numberSlider.getValue());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        // فعال‌سازی انیمیشن هنگام کشیدن اسلایدر
        numberSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            timeline.playFromStart(); // اجرای انیمیشن هنگام تغییر مقدار
        });

        // به‌روز رسانی برچسب با استفاده از lambda expression هنگام تغییر مقدار اسلایدر
        numberSlider.valueProperty().addListener((observable, oldValue, newValue) ->{
                    NumberDisk = newValue.intValue();
                }
        );
    }
    public void openNewWindow(String fxmlFile, String title, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load(), 1535, 790);
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
        vboxFirst.setVisible(false);
        vbox2.setVisible(true);
        /*if(isLoggedIn) {
            vboxFirst.setVisible(false);
            vbox2.setVisible(true);
        }
        else {
            openNewWindow("loginpage.fxml", "Login", actionEvent, 1535, 790);
        }*/
    }
    @FXML
    void ContinueGame(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 1535, 790);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Game");
            stage.show();

            // مقداردهی صحیح کنترلر
            HelloController controller = loader.getController();
            controller.ContinueSolution(); // اجرای حل خودکار

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    void Profile(ActionEvent event) {
        if(isLoggedIn) {
            openNewWindow("profile1.fxml", "Profile", event);
        }
        else {
            openNewWindow("loginpage.fxml", "Login", event);
        }
    }
    @FXML
    void openSolveAuto(ActionEvent event) {
        isOpenContinue = false;
        isSolvedUser = false;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 1535, 790);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Game");
            stage.show();

            // مقداردهی صحیح کنترلر
            HelloController controller = loader.getController();
            controller.solveGame(); // اجرای حل خودکار

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void OpenGame(ActionEvent actionEvent) {
        isOpenContinue = true;
        isSolvedUser = true;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 1535, 790);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Game");
            stage.show();

            HelloController controller = loader.getController();
            controller.solveUser(); // اجرای حالت دستی
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backToMain(MouseEvent mouseEvent) {
        vbox2.setVisible(false);
        vboxFirst.setVisible(true);
    }
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void showBestScore(ActionEvent actionEvent) {
        alert.setTitle("Best Score");
        alert.setHeaderText(null);
        alert.setContentText("best score : " + HelloController.bestScore);
        alert.showAndWait();
    }

    public void backTo1(ActionEvent actionEvent) {
        vbox2.setVisible(false);
        vboxFirst.setVisible(true);
    }

    public void gameExit(ActionEvent actionEvent) {
        vbox3.setVisible(false);
        vboxFirst.setVisible(true);
        vbox2.setVisible(false);
    }
    public VBox getVboxFirst() {
        return vboxFirst;
    }
    public VBox getVbox2() {
        return vbox2;
    }
    public VBox getVbox3() {
        return vbox3;
    }
}
