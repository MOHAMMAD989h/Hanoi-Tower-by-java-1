package com.example.solvehanoi;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

public class HelloController {
    @FXML
    private TextField inputId;
    @FXML private VBox peg1, peg2, peg3;
    @FXML private Peg[] pegs;
    @FXML private HanoiGame game;
    @FXML private Queue<int[]> moveQueue = new LinkedList<>();
    @FXML private Timeline timeline;
    @FXML
    private Label inforLabel;

    @FXML
    private Label timeLbl;

    private int counter = 0;

    private NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH); // نمایش اعداد به انگلیسی




    @FXML
    public void initialize() {
        Peg pegA = new Peg(peg1);
        Peg pegB = new Peg(peg2);
        Peg pegC = new Peg(peg3);

        pegs = new Peg[]{pegA, pegB, pegC};
        game = new HanoiGame(pegs);

        setupDropTarget(peg1, pegs[0]);
        setupDropTarget(peg2, pegs[1]);
        setupDropTarget(peg3, pegs[2]);

        addDisksToPeg(1); // تعداد دیسک‌ها

        /*Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1000), event -> checkGameCompletion())
        );
        timeline.setCycleCount(Animation.INDEFINITE); // اجرای بی‌نهایت
        timeline.play();*/
    }
    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            counter++;
            int minutes = counter / 60; // محاسبه دقیقه
            int seconds = counter % 60; // محاسبه ثانیه
            timeLbl.setText(String.format("time : %s:%s",numberFormat.format(minutes), numberFormat.format(seconds)));
        }));

        timeline.setCycleCount(Timeline.INDEFINITE); // اجرای نامحدود تایمر
        timeline.play(); // شروع تایمر
    }

    private void stopTimer() {
        if (timeline != null) {
            timeline.stop();
            counter = 0;
            timeLbl.setText("");
        }
    }
    int bestScore = Integer.MAX_VALUE;

    @FXML
    private void resetGame() {
        peg1.getChildren().clear();
        peg2.getChildren().clear();
        peg3.getChildren().clear();

        pegs[0].clear();
        pegs[1].clear();
        pegs[2].clear();

        disableDragAndDrop();
        stopTimer();
    }

        private void addDisksToPeg(int numDisks) {
        for (int i = numDisks; i > 0; i--) {
            Disk disk = new Disk(i, Color.BLUE);
            pegs[0].pushDisk(disk);  // اضافه کردن دیسک‌ها به اولین ستون
        }
    }
    Alert alert = new Alert(Alert.AlertType.ERROR);


    @FXML
    public void solveGame() {
        try {
            int n = Integer.parseInt(inputId.getText());
            if ((n > 16) || (n < 1)) {
                System.out.println("Please enter a valid number of disks.");
                inforLabel.setText("must at least 12");
                // ایجاد تایمر برای حذف متن بعد از چند ثانیه
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event1 -> {
                    inforLabel.setText(""); // حذف متن
                }));
                timeline.setCycleCount(1); // فقط یک بار اجرا شود
                timeline.play();

                return;
            }
            for (int i = n; i > 0; i--) {
                Disk disk = new Disk(i, Color.BLUE);
                pegs[0].pushDisk(disk);  // اضافه کردن دیسک‌ها به اولین ستون
            }
            resetGame();
            addDisksToPeg(n);
            moveQueue.clear();
            game.solve(n, 0, 2, 1, moveQueue);
            executeMoves();
            startTimer();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
        }
    }



    private void executeMoves() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.75), e -> {
            if (!moveQueue.isEmpty()) {
                int[] move = moveQueue.poll();
                game.moveDisk(move[0], move[1]);
            }
        }));
        timeline.setCycleCount(moveQueue.size());
        timeline.play();
    }

    public void resetHanoi(ActionEvent actionEvent) {
        resetGame();
        checkGameCompletion();
    }

    main cont = new main();

    public void backToStart(ActionEvent actionEvent) {cont.openNewWindow("main.fxml","Home",actionEvent,800,800);}

    public void checkGameCompletion() {
        if (pegs[0].isEmpty() && pegs[1].isEmpty()) {
            disableDragAndDrop();
            stopTimer();
            if(bestScore > counter){bestScore = counter;}
            counter = 0;
            inforLabel.setText("\uD83C\uDF89 بازی تمام شد!  ");
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event1 -> {
                inforLabel.setText(""); // حذف متن
            }));
            timeline.setCycleCount(1); // فقط یک بار اجرا شود
            timeline.play();
        }
    }

    private void disableDragAndDrop() {
        for (Peg peg : pegs) {
            for (Disk disk : peg.getDisks()) {
                disk.setDraggable(false);
            }
        }
    }

    public void solveUser(ActionEvent actionEvent) {
        try{
            int n = Integer.parseInt(inputId.getText());
            if ((n > 12) || (n < 1)) {
                System.out.println("Please enter a valid number of disks.");
                inforLabel.setText("must at least 12");
                // ایجاد تایمر برای حذف متن بعد از چند ثانیه
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event1 -> {
                    inforLabel.setText(""); // حذف متن
                }));
                timeline.setCycleCount(1); // فقط یک بار اجرا شود
                timeline.play();

                return;
            }
            resetGame();
            for (int i = n; i > 0; i--) {
                Disk disk = new Disk(i, Color.BLUE);
                pegs[0].pushDisk(disk);  // اضافه کردن دیسک‌ها به اولین ستون
            }
            resetGame();
            startTimer();
            addDisksToPeg(n);
            enableDragAndDrop();
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
        }
    }

    private void setupDropTarget(VBox pegBox, Peg peg) {
        pegBox.setOnDragOver(event -> {
            if (event.getGestureSource() instanceof Disk) {
                Disk draggedDisk = (Disk) event.getGestureSource();
                if (peg.isEmpty() || peg.peekDisk().getSize() > draggedDisk.getSize()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
            }
            event.consume();
        });

        pegBox.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasString()) {
                int diskSize = Integer.parseInt(db.getString());
                Disk draggedDisk = (Disk) event.getGestureSource();
                VBox oldPegBox = (VBox) draggedDisk.getParent();

                // بررسی قوانین بازی: فقط دیسک بزرگتر می‌تواند روی دیسک کوچکتر قرار بگیرد
                if (!peg.getDisks().isEmpty() && peg.peekDisk().getSize() < diskSize) {
                    event.setDropCompleted(false);
                    return;
                }

                // حذف از ستون قبلی
                Peg sourcePeg = findPegContainingDisk(draggedDisk);
                if (sourcePeg != null) {
                    sourcePeg.popDisk();
                }

                // اضافه کردن به ستون جدید
                peg.pushDisk(draggedDisk);
                pegBox.getChildren().add(0, draggedDisk); // قرار گرفتن در بالاترین سطح ستون جدید

                event.setDropCompleted(true);
            }
            event.consume();
        });
    }

    // پیدا کردن ستونی که دیسک در آن بوده
    private Peg findPegContainingDisk(Disk disk) {
        for (Peg peg : pegs) {
            if (peg.getDisks().contains(disk)) {
                return peg;
            }
        }
        return null;
    }



    public void bestScore(ActionEvent actionEvent) {
        int minutes = bestScore / 60;
        int seconds = bestScore % 60;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Best Score");
        alert.setHeaderText(null);
        alert.setContentText("Best Score is :" + String.format(" %s:%s",numberFormat.format(minutes), numberFormat.format(seconds)));
        alert.showAndWait();
    }
    private void enableDragAndDrop() {
        for (Peg peg : pegs) {
            for (Disk disk : peg.getDisks()) {
                disk.setDraggable(true);
                //peg.peekDisk().setDraggable(true);
            }
        }
    }

}
