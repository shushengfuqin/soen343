module ca.concordia {
    requires javafx.controls;
    requires javafx.fxml;

    opens ca.concordia to javafx.fxml;
    exports ca.concordia;
}