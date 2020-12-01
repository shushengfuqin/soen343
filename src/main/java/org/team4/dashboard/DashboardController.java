package org.team4.dashboard;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.team4.App;
import org.team4.common.logger.Logger;
import org.team4.shhParameters.ShhParameterController;
import org.team4.shpParameters.ShpParameterController;
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
    public ShpParameterController shpParameterController;

    @FXML
    public ShhParameterController shhParameterController;

    @FXML
    public Button closeButton;
    public Button startButton;


    //Info box variables
    @FXML
    public Text outsideTemp;
    public Text currentDate;
    public Text currentTime;
    public Slider multiplierSlider;
    public Text multiplierText;

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

    //tabs
    public Tab shcTab;
    public Tab shpTab;
    public Tab shhTab;

    //summer & default temp
    public Text defaultTemp;
    public Text summerBegin;
    public Text summerEnd;


    public DashboardController() {
        userService = new UserService();
        dashboardView = new DashboardView();
    }

    @FXML
    public void initialize() {
        outputList.setStyle("-fx-control-inner-background: #292929;");
        updateInfo();
        addListenerToMultiplierSlider();
        initMultiplier();
        toggleTabs();
        if(Settings.simulationStarted) startButton.setText("Stop");
    }

    /**
     * Initialize the multiplier with default values
     */
    public void initMultiplier() {
        Settings.simulationTime.setMultiplier(1);
        multiplierText.setText("1");
        multiplierSlider.setValue(1);
    }

    /**
     * Add listener to multiplier
     */
    private void addListenerToMultiplierSlider() {
        multiplierSlider.valueProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    double multiplier = Math.round((Double) newValue);
                    int mul = (int) multiplier;
                    Settings.simulationTime.setMultiplier(mul);
                    multiplierText.setText(String.valueOf(mul));
                }
        );
    }

    /**
     * Render the house layout in the ui
     */
    public void drawHouseLayout() {
        if(Settings.simulationStarted) {
            Platform.runLater(
                    () -> {
                        houseController.drawHouseLayout();
                    }
            );
        }
    }

    /**
     * Generate the house layout on start and erase it on stop
     * @param event
     */
    public void startButtonAction(ActionEvent event) {
        if(startButton.getText().equals("Start")) {
            boolean valid = true;
            if(Settings.defaultTemp == null) {
                Logger.error("Default temperature is not defined");
                valid = false;
            }

            if(Settings.summerBegin == null || Settings.summerEnd == null) {
                Logger.error("Summer months are not defined");
                valid = false;
            }

            if(!valid) return;

            startSimulation();
        } else {
            stopSimulation();
        }
    }

    /**
     * What to do when the simulation starts
     */
    private void startSimulation() {
        updateTime(Settings.simulationTime.getDate());
        Settings.simulationStarted = true;
        House.getHouseLayout();
        House.indexHouseWindowAndDoor();
        House.indexAllIndoorRooms();
        House.indexAllLights();
        House.lockAllDoor();
        shpParameterController.displayLightsInAwayMode();
        shpParameterController.displayAwayModeButton();
        shpParameterController.updateCurrentOnOffTimes();
        userController.initializeShsParametersSimStart();
        shcParameterController.initialize();
        shcParameterController.toggleAwayShcButtons();
        shhParameterController.displayAllRooms();
        toggleTabs();
        Settings.startClock();
        startButton.setText("Stop");
    }

    /**
     * What to do when the simulation stops
     */
    private void stopSimulation() {
        initMultiplier();
        updateTime(Settings.simulationTime.getDate());
        Settings.resetSettings();
        houseController.eraseHouseLayout();
        House.saveHouseLayout();
        House.resetParams();
        shpParameterController.displayLightsInAwayMode();
        shpParameterController.displayAwayModeButton();
        shpParameterController.updateCurrentOnOffTimes();
        shpParameterController.toggleSHPButtons();
        userController.initializeShsParametersSimStart();
        shcParameterController.initialize();
        startButton.setText("Start");
        shcParameterController.toggleAwayShcButtons();
        setCurrentUserDashboard();
        toggleTabs();
        updateInfo();
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
        outsideTemp.setText((Settings.outsideTemperature) + "°C");
        updateTime(Settings.simulationTime.getDate());

        defaultTemp.setText(Settings.defaultTemp == null ? ("None") : (Settings.defaultTemp.toString() + "°C"));
        summerBegin.setText(Settings.summerBegin == null ? ("None") : (Settings.summerBegin.toString()));
        summerEnd.setText(Settings.summerEnd == null ? ("None") : (Settings.summerEnd.toString()));

    }

    public void updateTime(Date date) {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
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

    /**
     * Disable or enable shc buttons depending on away mode or not
     */
    public void toggleAwayShcButtons() {
        shcParameterController.toggleAwayShcButtons();
    }

    /**
     * Disable tabs if simulation did not start
     */
    public void toggleTabs() {
        boolean started = Settings.simulationStarted;
        shcTab.setDisable(!started);
        shpTab.setDisable(!started);
        shhTab.setDisable(!started);
    }
}
