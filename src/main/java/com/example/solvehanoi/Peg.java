package com.example.solvehanoi;
import java.util.Stack;

import javafx.geometry.Pos;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Peg extends VBox {
    private VBox vbox;
    private Stack<Disk> disks;

    public Peg(VBox vbox) {
        this.vbox = vbox;
        this.disks = new Stack<>();
        this.vbox.setAlignment(Pos.BOTTOM_CENTER);
        setSpacing(5);
        setupDrop();


    }
    HelloController hello = new HelloController();

    private void setupDrop() {
        setOnDragOver(event -> {
            if (event.getGestureSource() instanceof Disk && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            if (event.getGestureSource() instanceof Disk) {
                Disk draggedDisk = (Disk) event.getGestureSource();
                Peg sourcePeg = (Peg) draggedDisk.getParent().getParent();

                if (isValidMove(draggedDisk)) {
                    sourcePeg.popDisk();
                    pushDisk(draggedDisk);
                    event.setDropCompleted(true);

                    hello.MoveCount();


                } else {
                    event.setDropCompleted(false);
                }
            }
            event.consume();
        });
    }

    public boolean isValidMove(Disk disk) {
        if (disks.isEmpty()) return true; // اگر میله خالی است، هر دیسکی می‌تواند روی آن قرار گیرد
        return disks.peek().getSize() > disk.getSize(); // فقط دیسک کوچکتر می‌تواند روی دیسک بزرگتر قرار گیرد
    }

    public void pushDisk(Disk disk) {
        disks.push(disk);
        vbox.getChildren().add(0, disk);  // نمایش دیسک‌ها از پایین به بالا
    }

    public Disk popDisk() {
        if (!disks.isEmpty()) {
            Disk disk = disks.pop();
            vbox.getChildren().remove(disk);
            return disk;
        }
        return null;
    }
    public Stack<Disk> getDisks() {
        return disks;
    }

    public boolean isEmpty() {
        return disks.isEmpty();
    }

    public Disk peekDisk() {
        return disks.isEmpty() ? null : disks.peek();
    }
    public void clear() {
        disks.clear();
        vbox.getChildren().clear();
    }


}
