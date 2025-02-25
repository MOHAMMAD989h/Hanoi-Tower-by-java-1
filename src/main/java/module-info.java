module com.example.solvehanoi {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.solvehanoi to javafx.fxml;
    exports com.example.solvehanoi;
}