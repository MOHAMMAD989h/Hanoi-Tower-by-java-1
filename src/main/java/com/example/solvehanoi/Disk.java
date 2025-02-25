package com.example.solvehanoi;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class Disk extends StackPane {
    private int size;
    private Rectangle rectangle;
    private Label label;
    private boolean isDraggable = false;


    public Disk(int size, Color color) {
        this.size = size;

        double width = 20 + (size * 20);
        double height = 20;

        rectangle = new Rectangle(width, height, color);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);

        label = new Label(String.valueOf(size));
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-font-weight: bold;");

        getChildren().addAll(rectangle, label);
        setupDrag();
    }

    public int getSize() {
        return size;
    }


    private void setupDrag() {
        setOnDragDetected(event -> {
            if (isDraggable && isTopDisk()) {
                Dragboard db = startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(String.valueOf(size));
                db.setContent(content);

                // نمایش یک تصویر از دیسک هنگام کشیدن
                SnapshotParameters snapshotParams = new SnapshotParameters();
                snapshotParams.setFill(Color.TRANSPARENT);
                WritableImage snapshotImage = new WritableImage((int) getWidth(), (int) getHeight());
                snapshot(snapshotParams, snapshotImage); // گرفتن تصویر از دیسک
                db.setDragView(snapshotImage, getWidth() / 2, getHeight() / 2);

                setOpacity(0.5); // تغییر شفافیت هنگام کشیدن
            }
            event.consume();
        });

        setOnDragDone(event -> {
            setOpacity(1.0); // بازگرداندن شفافیت دیسک بعد از درگ
            event.consume();
        });

    }
    private boolean isTopDisk() {
        if (getParent() instanceof VBox) {
            VBox parentPeg = (VBox) getParent();
            return parentPeg.getChildren().get(0) == this;  // بررسی اینکه آیا این دیسک در بالای لیست قرار دارد؟
        }
        return false;
    }



    public void setDraggable(boolean draggable) {
        this.isDraggable = draggable;
    }

}

