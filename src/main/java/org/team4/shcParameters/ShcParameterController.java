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

    //Lock Door control
    public ChoiceBox<String> lockDoorChoiceBox;
    public Button lockDoorSetButton;
    /**
     * Display all the windows and doors options
     */
    public void windowAndDoorChoiceBoxInit() {
        windowChoiceBox.getItems().clear();
        doorsChoiceBox.getItems().clear();
        lockDoorChoiceBox.getItems().clear();

        if(!Settings.simulationStarted) {
            windowSetButton.setDisable(true);
            doorSetButton.setDisable(true);
            lockDoorChoiceBox.setDisable(true);
            return;
        }

        windowSetButton.setDisable(false);
        doorSetButton.setDisable(false);
        lockDoorChoiceBox.setDisable(false);

        String[] windowList = House.getAllWindowsOption();
        for(int i = 0; i < windowList.length; i++) {
            windowChoiceBox.getItems().add(windowList[i]);
        }

        String[] doorList = House.getAllDoorsOption();
        for(int i = 0; i < doorList.length; i++) {
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
     * Open/close a window
     */
    public void toggleWindowAction() {
        if(windowChoiceBox.getValue() != null) {
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
        if(windowChoiceBox.getValue() != null) {
            boolean isOpen = House.getWindowStatusOpen(windowChoiceBox.getValue());
            windowSetButton.setText(isOpen ? "Close" : "Open");
        }
    }

    /**
     * Open/Close a door
     */
    public void toggleDoorAction() {
        if(doorsChoiceBox.getValue() != null) {
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
        if(doorsChoiceBox.getValue() != null) {
            boolean isOpen = House.getDoorStatus(doorsChoiceBox.getValue());
            doorSetButton.setText(isOpen ? "Close" : "Open");
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
     * get whether a door is locked or not
     */
    public void getDoorLockStatus(){
        if(lockDoorChoiceBox.getValue() != null){
            boolean isOpen = House.getDoorStatus(lockDoorChoiceBox.getValue());
            lockDoorSetButton.setText(isOpen ? "Lock" : "Unlock");
        }
    }
}
