package org.team4.user;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.team4.common.Settings;

public class CurrentUserController {

    private UserService userService;

    @FXML
    //Current user dashboard
    public Label currentUserLabel;
    public Text currentUserStatus;
    public Text currentUserAge;
    public Text currentUserLocation;

    public CurrentUserController() {
        userService = new UserService();
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
