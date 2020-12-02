package org.team4.shcParameters;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.team4.common.Settings;
import org.team4.house.services.DoorService;
import org.team4.house.services.LightService;
import org.team4.house.services.WindowService;

public class ShcParameterController {
    private LightService lightService;
    private WindowService windowService;
    private DoorService doorService;

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

    public ShcParameterController() {
        lightService = new LightService();
        windowService = new WindowService();
        doorService = new DoorService();
    }

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

        String[] windowList = windowService.getAllWindowsOption();
        for (int i = 0; i < windowList.length; i++) {
            windowChoiceBox.getItems().add(windowList[i]);
        }

        String[] doorList = doorService.getAllDoorsOption();
        for (int i = 0; i < doorList.length; i++) {
            doorsChoiceBox.getItems().add(doorList[i]);
        }

        String[] lockDoorList = doorService.getAllLockDoor();
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

        String[] lightList = lightService.getAllLightsOption();
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
            windowService.toggleWindowOpen(windowChoiceBox.getValue(), false);
            windowAndDoorChoiceBoxInit();
        }
    }

    /**
     * Get whether a window is closed or not
     */
    public void getWindowStatus() {
        if (windowChoiceBox.getValue() != null) {
            boolean isOpen = windowService.getWindowStatusOpen(windowChoiceBox.getValue());
            windowSetButton.setText(isOpen ? "Close" : "Open");
        }
    }

    /**
     * Open/Close a door
     */
    public void toggleDoorAction() {
        if (doorsChoiceBox.getValue() != null) {
            doorService.toggleDoor(doorsChoiceBox.getValue(), false);
            windowAndDoorChoiceBoxInit();
        }
    }

    /**
     * Get whether a door is closed or not
     */
    public void getDoorStatus() {
        if (doorsChoiceBox.getValue() != null) {
            boolean isOpen = doorService.getDoorStatus(doorsChoiceBox.getValue());
            doorSetButton.setText(isOpen ? "Close" : "Open");
        }
    }

    /**
     * On/Off a light
     */
    public void toggleLightsAction() {
        if (lightsChoiceBox.getValue() != null) {
            lightService.toggleLights(lightsChoiceBox.getValue());
            lightsChoiceBoxInit();
        }
    }

    /**
     *
     */
    public void toggleLockDoorAction(){
        if(lockDoorChoiceBox.getValue() != null){
            doorService.toggleDoorLock(lockDoorChoiceBox.getValue());
            windowAndDoorChoiceBoxInit();
        }
    }

    /**
     * Get whether a light is on or off
     */
    public void getLightStatus() {
        if (lightsChoiceBox.getValue() != null) {
            boolean isOn = lightService.getLightStatus(lightsChoiceBox.getValue());
            lightSetButton.setText(isOn ? "Off" : "On");
        }

    }

    /**
     * get whether a door is locked or not
     */
    public void getDoorLockStatus(){
        if(lockDoorChoiceBox.getValue() != null){
            boolean isLocked = doorService.getLockDoorStatus(lockDoorChoiceBox.getValue());
            lockDoorSetButton.setText(isLocked ? "Unlock" : "Lock");
        }
    }

    /**
     * Enable or disable light auto mode
     */
    public void toggleLightAutomaticMode() {
        lightService.toggleLightAuto();
        lightAutomaticModeInit();
    }

    /**
     * Disable or enable buttons depending on away mode
     */
    public void toggleAwayShcButtons() {
        boolean disable = Settings.awayMode;
        setLightAutoButton.setDisable(disable);
        lockDoorSetButton.setDisable(disable);
        lightSetButton.setDisable(disable);
        doorSetButton.setDisable(disable);
        windowSetButton.setDisable(disable);
    }
}
