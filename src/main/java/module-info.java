module org.team4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens org.team4 to javafx.fxml, java.base;
    opens org.team4.user to javafx.fxml, javafx.base;
    opens org.team4.house to javafx.fxml, javafx.base;
    exports org.team4;
}