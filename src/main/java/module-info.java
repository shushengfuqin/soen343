module org.team4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens org.team4 to javafx.fxml, java.base;
    opens org.team4.user to javafx.fxml, javafx.base;
    exports org.team4;
}