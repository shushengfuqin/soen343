module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.team4 to javafx.fxml;
    exports org.team4;
}