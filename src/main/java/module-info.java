module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens org.team4 to javafx.fxml;
    opens user to javafx.fxml;
    exports org.team4;
}