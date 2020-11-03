module org.team4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens org.team4 to javafx.fxml, java.base;
    opens org.team4.user to javafx.fxml, javafx.base;
    opens org.team4.shsParameters to javafx.fxml, javafx.base;
    opens org.team4.house to javafx.fxml, javafx.base;
    opens org.team4.shcParameters to javafx.fxml, javafx.base;
    opens org.team4.dashboard to javafx.fxml, javafx.base;
    opens org.team4.simulationParameters to javafx.fxml, javafx.base;
}