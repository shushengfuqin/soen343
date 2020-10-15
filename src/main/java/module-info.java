module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens org.team4 to javafx.fxml;
    opens org.team4.user to javafx.fxml;
    opens org.team4.settings to javafx.fxml;
    exports org.team4;
}