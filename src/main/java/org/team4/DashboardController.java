package org.team4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.team4.common.Settings;
import org.team4.user.User;
import org.team4.user.UserService;


public class DashboardController {

    private UserService userService;

    @FXML
    public Button closeButton;

    //Info box variables
    @FXML
    public Text outsideTemp;
    public Text currentDate;
    public Text currentTime;

    @FXML
    //Current user dashboard
    public Label currentUserLabel;
    public Text currentUserStatus;
    public Text currentUserAge;
    public Text currentUserLocation;

    public DashboardController() {
        userService = new UserService();
    }

    @FXML
    public void initialize() {
        updateInfo();
    }

    public void closeButtonAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void updateInfo() {
        outsideTemp.setText(Integer.toString(Settings.outsideTemperature));
    }

    public void setCurrentUserDashboard() {
        String name = "No current user";
        String status = "None";
        String age = "None";
        String location = "None";
        if(Settings.currentUser != null) {
            User current = userService.getSingleUser(Settings.currentUser);
            name = current.name;
            status = current.status;
            age = Integer.toString(current.age);
            location = current.coord.toString();
        }
        currentUserLabel.setText(name);
        currentUserStatus.setText(status);
        currentUserAge.setText(age);
        currentUserLocation.setText(location);
    }
}
