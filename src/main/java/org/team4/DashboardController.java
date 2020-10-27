package org.team4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.team4.common.Settings;
import org.team4.house.House;
import org.team4.house.HouseController;
import org.team4.shcParameters.ShcParameterController;
import org.team4.user.User;
import org.team4.user.UserController;
import org.team4.user.UserService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardController {

    private UserService userService;

    @FXML
    public HouseController houseController;

    @FXML
    public UserController userController;

    @FXML
    public ShcParameterController shcParameterController;

    @FXML
    public Button closeButton;
    public Button startButton;


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
        if(Settings.simulationStarted) startButton.setText("Stop");
    }

    public void drawHouseLayout() {
        if(Settings.simulationStarted) houseController.drawHouseLayout();
    }

    /**
     * Generate the house layout on start and erase it on stop
     * @param event
     */
    public void startButtonAction(ActionEvent event) {
        if(startButton.getText().equals("Start")) {
            Settings.simulationStarted = true;
            House.getHouseLayout();
            House.indexHouseWindowAndDoor();
            userController.initializeShsParametersSimStart();
            shcParameterController.windowAndDoorChoiceBoxInit();
            drawHouseLayout();
            startButton.setText("Stop");
        }
        else {
            Settings.simulationStarted = false;
            houseController.eraseHouseLayout();
            House.saveHouseLayout();
            House.resetParams();
            userController.initializeShsParametersSimStart();
            shcParameterController.windowAndDoorChoiceBoxInit();
            startButton.setText("Start");
        }
    }

    /**
     * Closes the app
     * @param event
     */
    public void closeButtonAction(ActionEvent event) {
        if(Settings.simulationStarted) {
            Settings.simulationStarted = false;
            houseController.eraseHouseLayout();
            House.saveHouseLayout();
            House.resetParams();
            userController.initializeShsParametersSimStart();
            startButton.setText("Start");
        }
        Settings.simulationTime.stop = true;
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Update the current shs params in the dashboard
     */
    public void updateInfo() {
        outsideTemp.setText(Integer.toString(Settings.outsideTemperature));
    }

    /**
     * Update the current date/time in the dashboard
     * @param date
     */
    public void updateTime(Date date) {
        String timePatter = "HH:mm:ss";
        String datePattern = "MM/dd/yyyy";
        DateFormat tf = new SimpleDateFormat(timePatter);
        DateFormat df = new SimpleDateFormat(datePattern);
        currentTime.setText(tf.format(date));
        currentDate.setText(df.format(date));
    }

    /**
     * Display the current user in the dashboard
     */
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
