package org.team4.dashboard;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.team4.common.Settings;
import org.team4.common.logger.Log;
import org.team4.house.House;
import org.team4.house.HouseController;
import org.team4.shcParameters.ShcParameterController;
import org.team4.user.User;
import org.team4.user.UserController;
import org.team4.user.UserService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class DashboardController {

    private UserService userService;
    private DashboardView dashboardView;

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
    public Text currentMultiplier;
    public Slider multiplierSlider;

    @FXML
    //Current user dashboard
    public Label currentUserLabel;
    public Text currentUserStatus;
    public Text currentUserAge;
    public Text currentUserLocation;

    private Timer clockTimer;

    //Output console
    @FXML
    public ListView<Pane> outputList;

    public DashboardController() {
        userService = new UserService();
        dashboardView = new DashboardView();
    }

    @FXML
    public void initialize() {
        outputList.setStyle("-fx-control-inner-background: #292929;");
        updateInfo();
        addListenerToMultiplierSlider();
        if(Settings.simulationStarted) startButton.setText("Stop");
    }

    private void addListenerToMultiplierSlider() {
        multiplierSlider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    double multiplier = Math.round((Double) newValue);
                    Settings.simulationTime.setMultiplier(multiplier);
                    currentMultiplier.setText(String.valueOf(multiplier));
                    changeAnimatedTime();
                }
        );
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
            startSimulation();
        } else {
            stopSimulation();
        }
    }

    private void startSimulation() {
        Settings.simulationTime.startTime();
        Settings.simulationStarted = true;
        startAnimatedTime();
        House.getHouseLayout();
        House.indexHouseWindowAndDoor();
        userController.initializeShsParametersSimStart();
        shcParameterController.windowAndDoorChoiceBoxInit();
        drawHouseLayout();
        startButton.setText("Stop");
    }

    private void stopSimulation() {
        Settings.simulationTime.stopTime();
        Settings.simulationStarted = false;
        stopAnimatedTime();
        houseController.eraseHouseLayout();
        House.saveHouseLayout();
        House.resetParams();
        userController.initializeShsParametersSimStart();
        shcParameterController.windowAndDoorChoiceBoxInit();
        startButton.setText("Start");
    }

    private void startAnimatedTime() {
        clockTimer = new Timer();
        clockTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateTime(Settings.simulationTime.getDate()));
            }
        },0, (long) (Duration.ofSeconds(1).toMillis()/Settings.simulationTime.getMultiplier()));
    }

    private void stopAnimatedTime() {
        clockTimer.cancel();
    }

    private void changeAnimatedTime() {
        stopAnimatedTime();
        startAnimatedTime();
    }

    /**
     * Closes the app
     * @param event
     */
    public void closeButtonAction(ActionEvent event) {
        if(Settings.simulationStarted) {
            stopSimulation();
        }
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
     * @param calendar
     */
    public void updateTime(Calendar calendar) {
        Date date = calendar.getTime();
        DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        currentTime.setText(timeFormat.format(date));
        currentDate.setText(dateFormat.format(date));
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

    /**
     * Displays a log to the dashboard
     * @param log
     */
    public void displayLog(Log log) {
        Pane logPane = dashboardView.generateLogPane(log);
        outputList.getItems().add(logPane);
        outputList.scrollTo(outputList.getItems().size());
    }
}
