package org.team4.shcParameters;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.team4.App;
import org.team4.common.Coordinate;
import org.team4.dashboard.DashboardController;
import org.team4.common.Settings;
import org.team4.house.House;

public class ShcParameterController {
    @FXML
    //Windows control
    public ChoiceBox<String> windowChoiceBox;
    public Button windowSetButton;

    //Door control
    public ChoiceBox<String> doorsChoiceBox;
    public Button doorSetButton;

    //Lights control
    @FXML
    private ChoiceBox<String> lightsChoiceBox;
    @FXML
    private Button lightSetButton;

    //Light away mode control
    @FXML
    private ChoiceBox<String> lightsawayChoiceBox;
    @FXML
    private Button lightawaySetButton;

    /**
     * Display all the windows and doors options
     */
    public void windowAndDoorChoiceBoxInit() {
        windowChoiceBox.getItems().clear();
        doorsChoiceBox.getItems().clear();

        if (!Settings.simulationStarted) {
            windowSetButton.setDisable(true);
            doorSetButton.setDisable(true);
            return;
        }

        windowSetButton.setDisable(false);
        doorSetButton.setDisable(false);

        String[] windowList = House.getAllWindowsOption();
        for (int i = 0; i < windowList.length; i++) {
            windowChoiceBox.getItems().add(windowList[i]);
        }

        String[] doorList = House.getAllDoorsOption();
        for (int i = 0; i < doorList.length; i++) {
            doorsChoiceBox.getItems().add(doorList[i]);
        }
        if (windowList.length > 0) windowChoiceBox.setValue(windowList[0]);
        if (doorList.length > 0) doorsChoiceBox.setValue(doorList[0]);
    }

    /**
     * Display all the lights and lightsAway mode options
     */
    public void lightsAndLightAwayChoiceBoxInit() {
        lightsChoiceBox.getItems().clear();
        lightsawayChoiceBox.getItems().clear();

        if (!Settings.simulationStarted) {
            lightSetButton.setDisable(true);
            lightawaySetButton.setDisable(true);
            return;
        }

        lightSetButton.setDisable(false);
        lightawaySetButton.setDisable(false);

        String[] lightList = House.getAllLightsOption();
        for (int i = 0; i < lightList.length; i++) {
            lightsChoiceBox.getItems().add(lightList[i]);
            lightsawayChoiceBox.getItems().add(lightList[i]);
        }

        if (lightList.length > 0) lightsChoiceBox.setValue(lightList[0]);
        if (lightList.length > 0) lightsawayChoiceBox.setValue(lightList[0]);

    }

    /**
     * Open/close a window
     */
    public void toggleWindowAction() {
        if (windowChoiceBox.getValue() != null) {
            House.toggleWindowOpen(windowChoiceBox.getValue());
            windowAndDoorChoiceBoxInit();
            DashboardController dashboardController = App.fxmlLoader.getController();
            dashboardController.drawHouseLayout();
        }
    }

    /**
     * Get whether a window is closed or not
     */
    public void getWindowStatus() {
        if (windowChoiceBox.getValue() != null) {
            boolean isOpen = House.getWindowStatusOpen(windowChoiceBox.getValue());
            windowSetButton.setText(isOpen ? "Close" : "Open");
        }
    }

    /**
     * Open/Close a door
     */
    public void toggleDoorAction() {
        if (doorsChoiceBox.getValue() != null) {
            House.toggleDoor(doorsChoiceBox.getValue());
            windowAndDoorChoiceBoxInit();
            DashboardController dashboardController = App.fxmlLoader.getController();
            dashboardController.drawHouseLayout();
        }
    }

    /**
     * Get whether a door is closed or not
     */
    public void getDoorStatus() {
        if (doorsChoiceBox.getValue() != null) {
            boolean isOpen = House.getDoorStatus(doorsChoiceBox.getValue());
            doorSetButton.setText(isOpen ? "Close" : "Open");
        }
    }

    /**
     * On/Off a light
     */
    public void toggleLightsAction() {
        if (lightsChoiceBox.getValue() != null) {
            House.toggleLights(lightsChoiceBox.getValue());
            lightsAndLightAwayChoiceBoxInit();
            DashboardController dashboardController = App.fxmlLoader.getController();
            dashboardController.drawHouseLayout();
        }
    }

    /**
     * Get whether a light is on or off
     */
    public void getLightAwayStatus() {
        if (lightsawayChoiceBox.getValue() != null) {
            boolean isOn = House.getLightAwayStatus(lightsawayChoiceBox.getValue());
            lightawaySetButton.setText(isOn ? "Remove" : "Add");
        }
    }

    /**
     * Add or remove light from away list
     */
    public void toggleLightsAwayAction() {
        if (lightsawayChoiceBox.getValue() != null) {
            House.toggleLightsAway(lightsawayChoiceBox.getValue());
            lightsAndLightAwayChoiceBoxInit();
            DashboardController dashboardController = App.fxmlLoader.getController();
            dashboardController.drawHouseLayout();
        }
    }

    /**
     * Get whether a light is on or off
     */
    public void getLightStatus() {
        if (lightsChoiceBox.getValue() != null) {
            boolean isOn = House.getLightStatus(lightsChoiceBox.getValue());
            lightSetButton.setText(isOn ? "Off" : "On");
        }

    }
}

