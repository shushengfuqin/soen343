package org.team4.shpParameters;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.team4.App;
import org.team4.common.Coordinate;
import org.team4.common.Settings;
import org.team4.dashboard.DashboardController;
import org.team4.house.House;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class
ShpParameterController {

    private ShpService shpService;

    @FXML
    //lights table
    public TableColumn<String, String> lightXColumn;
    public TableColumn<String, String> lightYColumn;
    public TableView lightsTable;

    //Light away mode control
    @FXML
    public ChoiceBox<String> lightsawayChoiceBox;
    public Button lightawaySetButton;

    //Toggle away mode button
    @FXML
    public Button toggleAwayMode;

    //Notify input fields
    @FXML
    public TextField amountTimeNotify;
    public Button updateAwayModeNotify;


    public Text invalidNotifText;

    public Text currentNotifTime;

    //On off time away light
    public Button lightOnTimeSet;
    public TextField awayModeOnTime;
    public TextField awayModeOffTime;
    public Text onTimeError;
    public Text offTimeError;

    public Text currentOnTime;
    public Text currentOffTime;

    public ShpParameterController() {
        shpService = new ShpService();
    }

    /**
     * Disable the buttons if in away mode
     */
    public void toggleSHPButtons() {
        boolean disable = Settings.awayMode;
        updateAwayModeNotify.setDisable(disable);
        lightawaySetButton.setDisable(disable);
        lightOnTimeSet.setDisable(disable);
    }

    /**
     * Display the away mode button
     */
    public void displayAwayModeButton() {
        toggleAwayMode.setText("Enable");
        if(!Settings.simulationStarted) {
            toggleAwayMode.setDisable(true);
            return;
        }
        toggleAwayMode.setDisable(false);
        toggleAwayMode.setText(Settings.awayMode ? "Disable" : "Enable");
    }

    /**
     * Display all the lights in away mode in the table
     */
    public void displayLightsInAwayMode() {
        lightsTable.getItems().clear();
        lightsawayChoiceBox.getItems().clear();

        if (!Settings.simulationStarted) {
            lightawaySetButton.setDisable(true);
            return;
        }

        lightawaySetButton.setDisable(false);

        ArrayList<Coordinate> allLightAway = House.lightsAway;
        String[] lightList = House.getAllLightsOption();
        for(Coordinate c : allLightAway) {
            lightsTable.getItems().add(c);
        }
        for (int i = 0; i < lightList.length; i++) {
            lightsawayChoiceBox.getItems().add(lightList[i]);
        }
        if (lightList.length > 0) lightsawayChoiceBox.setValue(lightList[0]);
    }

    /**
     * Update the current notification time in the ui
     */
    public void updateCurrentNotifTime() {
        currentNotifTime.setText(Integer.toString(Settings.timeBeforeAlerting));
    }

    /**
     * Resets all the invalid texts
     */
    public void resetInvalidText() {
        invalidNotifText.setText("");
        invalidNotifText.setFill(Color.RED);
        onTimeError.setText("");
        onTimeError.setFill(Color.RED);
        offTimeError.setText("");
        offTimeError.setFill(Color.RED);
    }

    public void initialize() {
        resetInvalidText();
        updateCurrentNotifTime();
        lightXColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        lightYColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        displayAwayModeButton();
        displayLightsInAwayMode();
        updateCurrentOnOffTimes();
    }

    /**
     * Get whether a light is on or off
     */
    public void getLightAwayStatus() {
        resetInvalidText();
        if (lightsawayChoiceBox.getValue() != null) {
            boolean isOn = House.getLightAwayStatus(lightsawayChoiceBox.getValue());
            lightawaySetButton.setText(isOn ? "Remove" : "Add");
        }
    }

    /**
     * Add or remove light from away list
     */
    public void toggleLightsAwayAction() {
        resetInvalidText();
        if (lightsawayChoiceBox.getValue() != null) {
            House.toggleLightsAway(lightsawayChoiceBox.getValue());
            displayLightsInAwayMode();
        }
    }

    /**
     * Turn on or off away mode
     */
    public void toggleAwayMode() {
        resetInvalidText();
        shpService.toggleAwayMode();
        displayAwayModeButton();
        toggleSHPButtons();
        DashboardController dashboardController = App.fxmlLoader.getController();
        dashboardController.drawHouseLayout();
        dashboardController.toggleAwayShcButtons();
    }

    /**
     * Set the time before notifying authorities
     */
    public void setTimeToNotify() {
        String amountTime = amountTimeNotify.getText();
        try {
            int seconds = Integer.parseInt(amountTime);
            if(seconds <= 10) throw new Exception();
            shpService.setAwayModeNotifTime(seconds);
            amountTimeNotify.setText("");
            resetInvalidText();
        }
        catch (Exception e) {
            invalidNotifText.setText("Invalid input should be int greater than 10");
        }
        updateCurrentNotifTime();
    }

    /**
     * Update the time between when the lights stays on
     */
    public void updateCurrentOnOffTimes() {
        Date onTime = Settings.awayLightOnTime;
        Date offTime = Settings.awayLightOffTime;
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        if(onTime != null && offTime != null) {
            currentOnTime.setText(timeFormat.format(onTime));
            currentOffTime.setText(timeFormat.format(offTime));
            return;
        }
        currentOnTime.setText("None");
        currentOffTime.setText("None");
    }

    /**
     * Set the new time between which a light stay on
     */
    public void setNewOnOffTime() {
        resetInvalidText();
        String onTime = awayModeOnTime.getText();
        String offTime = awayModeOffTime.getText();

        boolean onTimeValid = shpService.validateTime(onTime);
        boolean offTimeValid = shpService.validateTime(offTime);
        if(!onTimeValid || !offTimeValid) {
            onTimeError.setText(onTimeValid ? "" : "Invalid input");
            offTimeError.setText(offTimeValid ? "" : "Invalid input");
            return;
        }

        awayModeOnTime.setText("");
        awayModeOffTime.setText("");
        shpService.setOnOffTime(onTime, offTime);
        updateCurrentOnOffTimes();
    }
}
