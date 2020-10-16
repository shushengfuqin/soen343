package org.team4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.team4.user.CurrentUserController;


public class DashboardController {

    @FXML
    public Button closeButton;

    @FXML
    private CurrentUserController currentUserController;

    @FXML
    public void initialize() {
    }

    public void closeButtonAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void setCurrentUserDashboard() {
        currentUserController.setCurrentUserDashboard();
    }
}
