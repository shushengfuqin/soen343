package org.team4.shcParameters;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.team4.App;
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
    public ChoiceBox<String> lightsChoiceBox;
    @FXML
    public Button lightSetButton;

    //Light auto mode control
    public Button setLightAutoButton;

    //Lock Door control
    public ChoiceBox<String> lockDoorChoiceBox;
    public Button lockDoorSetButton;

    public void initialize() {
        windowAndDoorChoiceBoxInit();
        lightInit();
    }

    /**
     * Display all the windows and doors options
     */
    public void windowAndDoorChoiceBoxInit() {
        windowChoiceBox.getItems().clear();
        doorsChoiceBox.getItems().clear();
        lockDoorChoiceBox.getItems().clear();

        if (!Settings.simulationStarted) {
            windowSetButton.setDisable(true);
            doorSetButton.setDisable(true);
            lockDoorSetButton.setDisable(true);
            return;
        }

        windowSetButton.setDisable(false);
        doorSetButton.setDisable(false);
        lockDoorSetButton.setDisable(false);

        String[] windowList = House.getAllWindowsOption();
        for (int i = 0; i < windowList.length; i++) {
            windowChoiceBox.getItems().add(windowList[i]);
        }

        String[] doorList = House.getAllDoorsOption();
        for (int i = 0; i < doorList.length; i++) {
            doorsChoiceBox.getItems().add(doorList[i]);
        }

        String[] lockDoorList = House.getAllLockDoor();
        for(int i = 0; i < lockDoorList.length; i++){
            lockDoorChoiceBox.getItems().add(lockDoorList[i]);
        }
        if(windowList.length > 0) windowChoiceBox.setValue(windowList[0]);
        if(doorList.length > 0) doorsChoiceBox.setValue(doorList[0]);
        if(lockDoorList.length > 0) lockDoorChoiceBox.setValue(lockDoorList[0]);
    }

    /**
     * Init all the light fields
     */
    public void lightInit() {
        lightsChoiceBoxInit();
        lightAutomaticModeInit();
    }

    /**
     * Enables the light auto mode button
     */
    public void lightAutomaticModeInit() {
        boolean auto = Settings.lightAutoMode;
        if (!Settings.simulationStarted) {
            setLightAutoButton.setDisable(true);
            return;
        }
        setLightAutoButton.setDisable(false);
        setLightAutoButton.setText(auto ? "Disable" : "Enable");
    }

    /**
     * Display all the lights and lightsAway mode options
     */
    public void lightsChoiceBoxInit() {
        lightsChoiceBox.getItems().clear();

        if (!Settings.simulationStarted) {
            lightSetButton.setDisable(true);
            return;
        }

        lightSetButton.setDisable(false);

        String[] lightList = House.getAllLightsOption();
        for (int i = 0; i < lightList.length; i++) {
            lightsChoiceBox.getItems().add(lightList[i]);
        }

        if (lightList.length > 0) lightsChoiceBox.setValue(lightList[0]);
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
            lightsChoiceBoxInit();
            DashboardController dashboardController = App.fxmlLoader.getController();
            dashboardController.drawHouseLayout();
        }
    }

    /**
     *
     */
    public void toggleLockDoorAction(){
        if(lockDoorChoiceBox.getValue() != null){
            House.toggleDoorLock(lockDoorChoiceBox.getValue());
            windowAndDoorChoiceBoxInit();
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

    /**
     * get whether a door is locked or not
     */
    public void getDoorLockStatus(){
        if(lockDoorChoiceBox.getValue() != null){
            boolean isLocked = House.getLockDoorStatus(lockDoorChoiceBox.getValue());
            lockDoorSetButton.setText(isLocked ? "Unlock" : "Lock");
        }
    }

    /**
     * Enable or disable light auto mode
     */
    public void toggleLightAutomaticMode() {
        House.toggleLightAuto();
        lightAutomaticModeInit();
        DashboardController dashboardController = App.fxmlLoader.getController();
        dashboardController.drawHouseLayout();
    }

    public void toggleAwayShcButtons() {
        boolean disable = Settings.awayMode;
        setLightAutoButton.setDisable(disable);
        lockDoorSetButton.setDisable(disable);
        lightSetButton.setDisable(disable);
        doorSetButton.setDisable(disable);
        windowSetButton.setDisable(disable);
    }
}
