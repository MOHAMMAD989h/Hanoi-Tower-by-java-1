package com.example.solvehanoi;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.ls.LSOutput;

import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;


import static com.example.solvehanoi.main.NumberDisk;

public class HelloController {
    @FXML private VBox peg1, peg2, peg3;
    @FXML private Peg[] pegs;
    @FXML private HanoiGame game;
    @FXML private Queue<int[]> moveQueue = new LinkedList<>();
    @FXML private Timeline timeline;

    @FXML
    private Label timeLbl;

    @FXML
    private Label moveLbl;

    @FXML
    private Button backToStart;

    private static int counter;

    private final NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH); // نمایش اعداد به انگلیسی

    AtomicBoolean isReset = new AtomicBoolean(true);

    boolean isBestScore = false;

    private static int moves = 0 ;

    private static int saveTime = 0 ;
    private static int saveMove= 0;

    public static int bestMove = Integer.MAX_VALUE ;
    public static boolean isOpeneduser = false;
    public static boolean isAtLeastCheckGameComp = false;


    @FXML
    public void initialize() {
        Peg pegA = new Peg(peg1,this);
        Peg pegB = new Peg(peg2,this);
        Peg pegC = new Peg(peg3,this);

        pegs = new Peg[]{pegA, pegB, pegC};
        game = new HanoiGame(pegs);

        setupDropTarget(peg1, pegs[0]);
        setupDropTarget(peg2, pegs[1]);
        setupDropTarget(peg3, pegs[2]);

        addDisksToPeg(3); // تعداد دیسک‌ها




        final Timeline[] timeline1 = new Timeline[1]; // آرایه‌ای برای نگه‌داشتن مقدار
        timeline1[0] = new Timeline(
                new KeyFrame(Duration.millis(500), event -> {
                    if(isReset.get()) {
                        checkGameCompletion();
                    }
                })
        );

        timeline1[0].setCycleCount(Animation.INDEFINITE);
        timeline1[0].play();
    }
    private void startTimer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            counter++;
            int minutes = counter / 60; // محاسبه دقیقه
            int seconds = counter % 60; // محاسبه ثانیه
            timeLbl.setText(String.format("time : %s:%s",numberFormat.format(minutes), numberFormat.format(seconds)));
            timeLbl.setStyle("    -fx-background-color: transparent;\n" +
                    "    -fx-border-color: linear-gradient(to bottom right , #4c507b, #d3005f );\n" +
                    "    -fx-background-radius: 5px;\n" +
                    "    -fx-border-width: 2px;\n" +
                    "    -fx-border-radius: 30px;");
        }));

        timeline.setCycleCount(Timeline.INDEFINITE); // اجرای نامحدود تایمر
        timeline.play(); // شروع تایمر
    }

    private void stopTimer() {
        if (timeline != null) {
            timeline.stop();
            saveTime = counter;
            timeLbl.setText("");
        }
    }
    public static int bestScore = Integer.MAX_VALUE;

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
        moveQueue.clear();
        moves = 0;
        moveLbl.setText("");
        timeLbl.setStyle("");
        moveLbl.setStyle("");
    }
    private void clearBoard() {
        peg1.getChildren().clear();
        peg2.getChildren().clear();
        peg3.getChildren().clear();

        pegs[0].clear();
        pegs[1].clear();
        pegs[2].clear();
        disableDragAndDrop();
        moveQueue.clear();
        moveLbl.setText("");
        timeLbl.setStyle("");
        moveLbl.setStyle("");
    }

    public static Color getRandomPredefinedColor() {
        Color[] colors = {
                Color.RED,
                Color.BLUE,
                Color.GREEN,
                Color.YELLOW,
                Color.ORANGE,
                Color.PURPLE,
                Color.CYAN,
                Color.MAGENTA
        };
        Random random = new Random();
        int randomIndex = random.nextInt(colors.length); // اندیس تصادفی بین 0 و length-1
        return colors[randomIndex];
    }

    private void addDisksToPeg(int numDisks) {
        for (int i = numDisks; i > 0; i--) {
            Disk disk = new Disk(i, getRandomPredefinedColor(),this);
            pegs[0].pushDisk(disk);  // اضافه کردن دیسک‌ها به اولین ستون
        }
    }
    Alert alert = new Alert(Alert.AlertType.ERROR);


    @FXML
    public void solveGame() {
        try {
            int n = Integer.parseInt(String.valueOf(NumberDisk));
            if ((n > 16) || (n < 3)) {
                System.out.println("Please enter a valid number of disks.");

                return;
            }
            for (int i = n; i > 0; i--) {
                Disk disk = new Disk(i, Color.BLUE,this);
                pegs[0].pushDisk(disk);  // اضافه کردن دیسک‌ها به اولین ستون
            }
            isBestScore =false;
            resetGame();
            addDisksToPeg(n);
            moveQueue.clear();
            game.solve(n, 0, 2, 1, moveQueue);
            executeMoves();
            counter = 0;
            startTimer();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
        }
    }
    private void addDisksToPegContinue() {
        for (int i = 0; i <= savepeg0.length -1; i++) {
            if(savepeg0[i] != 0) {
                Disk disk = new Disk(savepeg0[i], getRandomPredefinedColor(), this);
                pegs[0].pushDisk(disk);
            }
        }
        for (int i = 0; i <= savepeg1.length -1; i++) {
            if(savepeg1[i] != 0) {
                Disk disk = new Disk(savepeg1[i], getRandomPredefinedColor(), this);
                pegs[1].pushDisk(disk);
            }
        }
        for (int i = 0; i <= savepeg2.length - 1; i++) {
            if(savepeg2[i] != 0) {
                Disk disk = new Disk(savepeg2[i], getRandomPredefinedColor(), this);
                pegs[2].pushDisk(disk);
            }
        }
    }

    public void ContinueSolution() {
        try{
            clearBoard();
            addDisksToPegContinue();
            enableDragAndDrop();
            counter = saveTime;
            moves = saveMove;
            startTimer();
            isBestScore = true;
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
        }
    }

    private void executeMoves() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.75), e -> {
            if (!moveQueue.isEmpty()) {
                int[] move = moveQueue.poll();
                game.moveDisk(move[0], move[1]);
                moves++;
                moveLbl.setText(String.valueOf("moves : " +moves));
                moveLbl.setStyle("    -fx-background-color: transparent;\n" +
                        "    -fx-border-color: linear-gradient(to bottom right , #4c507b, #d3005f );\n" +
                        "    -fx-background-radius: 5px;\n" +
                        "    -fx-border-width: 2px;\n" +
                        "    -fx-border-radius: 30px;");
            }
        }));
        timeline.setCycleCount(moveQueue.size());
        timeline.play();
    }
    main cont = new main();

    public void backToStart(ActionEvent actionEvent) {
        cont.openNewWindow("main.fxml","Hanoi",actionEvent);
        main.isSolvedUser = false;
        resetGame();
        NumberDisk = 3;
    }

    public void checkGameCompletion() {
        int n = Integer.parseInt(String.valueOf(NumberDisk));
        if (peg3.getChildren().size() == n) {
            disableDragAndDrop();
            if(isBestScore) {
                if (bestScore > counter) {
                    bestScore = counter;
                }
                if (bestMove > moves) {
                    bestMove = moves;
                }
                if(n*5 > counter && n*4 > moves){main.isStar3 = true;}
                else if(n*7 > counter && n*6 > moves){main.isStar2 = true;}

                main.isStarPage = true;
            }
            cont.openNewWindow("main.fxml", "Hanoi", null);

            Stage stage = (Stage) backToStart.getScene().getWindow();
            stage.close();

            stopTimer();
            counter = 0;
            isReset.set(false);
            isAtLeastCheckGameComp = true;
        }
    }

    private void disableDragAndDrop() {
        for (Peg peg : pegs) {
            for (Disk disk : peg.getDisks()) {
                disk.setDraggable(false);
            }
        }
    }

    public void solveUser() {
        try{
            int n = Integer.parseInt(String.valueOf(NumberDisk));
            if ((n > 12) || (n < 1)) {
                System.out.println("Please enter a valid number of disks.");;

                return;
            }
            for (int i = n; i > 0; i--) {
                Disk disk = new Disk(i, Color.BLUE,this);
                pegs[0].pushDisk(disk);  // اضافه کردن دیسک‌ها به اولین ستون
            }
            resetGame();
            isBestScore = true;
            counter = 0;
            startTimer();
            addDisksToPeg(n);
            enableDragAndDrop();
            System.out.println("Solved User");
            MoveCount();
            isOpeneduser = true;
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
        }
    }

    public void MoveCount() {
        moveLbl.setText(String.valueOf("moves : " +moves));
        moves++;
        saveMove = moves;
        moveLbl.setStyle("    -fx-background-color: transparent;\n" +
                    "    -fx-border-color: linear-gradient(to bottom right , #4c507b, #d3005f );\n" +
                    "    -fx-background-radius: 5px;\n" +
                    "    -fx-border-width: 2px;\n" +
                    "    -fx-border-radius: 30px;");
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

                System.out.println("setup drop target %%%% ");

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

    private void enableDragAndDrop() {
        for (Peg peg : pegs) {
            for (Disk disk : peg.getDisks()) {
                disk.setDraggable(true);
            }
        }
    }

    static int[] savepeg0 = new int[20];
    static int[] savepeg1 = new int[20];
    static int[] savepeg2 = new int[20];
    @FXML
    public void saveGame(ActionEvent actionEvent) {
        Arrays.fill(savepeg0, 0);
        Arrays.fill(savepeg1, 0);
        Arrays.fill(savepeg2, 0);

        int i = 0;
        for(Disk disk : pegs[0].getDisks()) {
            savepeg0[i] = disk.getSize();
            i++;
        }
        i = 0;
        for(Disk disk : pegs[1].getDisks()) {
            savepeg1[i] = disk.getSize();
            i++;
        }
        i = 0;
        for(Disk disk : pegs[2].getDisks()) {
            savepeg2[i] = disk.getSize();
            i++;
        }
        stopTimer();
        backToStart(actionEvent);
    }
}
