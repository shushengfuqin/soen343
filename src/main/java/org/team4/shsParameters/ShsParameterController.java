package org.team4.shsParameters;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.team4.App;
import org.team4.DashboardController;
import org.team4.common.Helper;
import org.team4.common.Settings;
import org.team4.house.House;
import java.time.LocalDate;

public class ShsParameterController {

    private ShsParameterService shsParameterService;

    @FXML
    //outside temperature
    public TextField tempText;
    public Text tempError;
    public Button setTempButton;

    //Set date and time
    public DatePicker dateField;
    public TextField timeField;
    public Button setDateButton;
    public Text dateError;

    //Windows control
    public ChoiceBox<String> windowChoiceBox;
    public Button windowSetButton;

    //Door control
    public ChoiceBox<String> doorsChoiceBox;
    public Button doorSetButton;

    //House Location controls
    public TextField houseLocationText;
    public Text locationError;
    public Button setHouseLocationButton;

    public ShsParameterController() {
        shsParameterService = new ShsParameterService();
    }

    public void initialize() {
        temperatureInit();
        dateTimeInit();
        houseLocationInit();
        windowsAndDoorInit();
    }

    public void windowsAndDoorInit() {
        windowSetButton.setDisable(!Settings.simulationStarted);
        doorSetButton.setDisable(!Settings.simulationStarted);
        windowChoiceBox.getItems().clear();
        doorsChoiceBox.getItems().clear();
        windowAndDoorChoiceBoxInit();
    }

    public void houseLocationInit() {
        houseLocationText.setText(House.houseLayoutFileName);
        locationError.setText("");
        setHouseLocationButton.setDisable(Settings.simulationStarted);
    }

    public void windowAndDoorChoiceBoxInit() {
        if(!Settings.simulationStarted) return;
        String[] windowList = House.getAllWindowsOption();
        windowChoiceBox.getItems().clear();
        for(int i = 0; i < windowList.length; i++) {
            windowChoiceBox.getItems().add(windowList[i]);
        }

        String[] doorList = House.getAllDoorsOption();
        doorsChoiceBox.getItems().clear();
        for(int i = 0; i < doorList.length; i++) {
            doorsChoiceBox.getItems().add(doorList[i]);
        }
        if(windowList.length > 0) windowChoiceBox.setValue(windowList[0]);
        if(doorList.length > 0) doorsChoiceBox.setValue(doorList[0]);
    }

    public void toggleWindowAction() {
        if(windowChoiceBox.getValue() != null) {
            House.toggleWindow(windowChoiceBox.getValue());
            windowAndDoorChoiceBoxInit();
            DashboardController dashboardController = App.fxmlLoader.getController();
            dashboardController.drawHouseLayout();
        }
    }

    public void getWindowStatus() {
        if(windowChoiceBox.getValue() != null) {
            boolean isOpen = House.getWindowStatus(windowChoiceBox.getValue());
            windowSetButton.setText(isOpen ? "Close" : "Open");
        }
    }

    public void toggleDoorAction() {
        if(doorsChoiceBox.getValue() != null) {
            House.toggleDoor(doorsChoiceBox.getValue());
            windowAndDoorChoiceBoxInit();
            DashboardController dashboardController = App.fxmlLoader.getController();
            dashboardController.drawHouseLayout();
        }
    }

    public void getDoorStatus() {
        if(doorsChoiceBox.getValue() != null) {
            boolean isOpen = House.getDoorStatus(doorsChoiceBox.getValue());
            doorSetButton.setText(isOpen ? "Close" : "Open");
        }
    }

    public void dateTimeInit() {
        dateError.setText("");
    }

    public void temperatureInit() {
        tempError.setText("");
        setTempButton.setDisable(true);
        tempText.setText(Integer.toString(Settings.outsideTemperature));
        DashboardController dashboardController = App.fxmlLoader.getController();
        dashboardController.updateInfo();
    }

    public void handleSetDateAction() {
        String time = timeField.getText();
        LocalDate localDate = dateField.getValue();
        boolean success = shsParameterService.setDateTime(localDate, time);
        if(success) {
            dateError.setText("");
            dateField.setValue(null);
            timeField.setText("");
        }
        else {
            dateError.setText("X");
        }
    }

    public void handleTempTextUpdate() {
        String temp = tempText.getText();
        try {
            int num = Integer.parseInt(temp);
            tempError.setText("");
            setTempButton.setDisable(num == Settings.outsideTemperature);
        }
        catch (NumberFormatException  e) {
            tempError.setText("Integer Only");
            setTempButton.setDisable(true);
        }
    }

    public void handleUpdateOutsideTemp() {
        String temp = tempText.getText();
        try {
            int num = Integer.parseInt(temp);
            if(num != Settings.outsideTemperature) {
                Settings.outsideTemperature = num;
                temperatureInit();
            }
        }
        catch (NumberFormatException  e) {
            tempError.setText("Integer Only");
        }
        setTempButton.setDisable(true);
    }

    public void setHouseLocationAction() {
        System.out.println("ere");
        String houseLocation = houseLocationText.getText();
        if(houseLocation == null || houseLocation.length() <= 0 || !Helper.isAlphanumeric(houseLocation)) {
            locationError.setText("Letters only");
            return;
        }
        House.houseLayoutFileName = houseLocation;
        initialize();
    }

}
