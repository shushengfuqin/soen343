package org.team4.shsParameters;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.team4.App;
import org.team4.dashboard.DashboardController;
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

    //House Location controls
    public TextField houseLocationText;
    public Text locationError;
    public Button setHouseLocationButton;

    //Window block
    public ChoiceBox<String> windowChoices;
    public Button blockWindowButton;


    public ShsParameterController() {
        shsParameterService = new ShsParameterService();
    }

    public void initialize() {
        temperatureInit();
        dateTimeInit();
        houseLocationInit();
        initWindowChoices();
    }

    public void initWindowChoices() {
        windowChoices.getItems().clear();
        if(!Settings.simulationStarted) {
           blockWindowButton.setDisable(true);
           return;
        }
        blockWindowButton.setDisable(false);
        String[] windowList = House.getAllWindowsOption();
        for(int i = 0; i < windowList.length; i++) {
            windowChoices.getItems().add(windowList[i]);
        }
        if(windowList.length > 0) windowChoices.setValue(windowList[0]);
    }

    public void getWindowStatus() {
        if(windowChoices.getValue() != null) {
            boolean isBlocked = House.getWindowStatusBlock(windowChoices.getValue());
            blockWindowButton.setText(isBlocked ? "Open" : "Block");
        }
    }

    public void toggleWindowBlock() {
        if(windowChoices.getValue() != null) {
            House.toggleWindowBlock(windowChoices.getValue());
            initWindowChoices();
            DashboardController dashboardController = App.fxmlLoader.getController();
            dashboardController.drawHouseLayout();
        }
    }

    /**
     * Prepare the text field to get the house layout location
     */
    public void houseLocationInit() {
        houseLocationText.setText(House.houseLayoutFileName);
        locationError.setText("");
        setHouseLocationButton.setDisable(Settings.simulationStarted);
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

    /**
     * Set the date and time
     */
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

    /**
     * Check whether the new temperature is valid
     */
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

    /**
     * Set the new temperature
     */
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

    /**
     * Set a house location
     */
    public void setHouseLocationAction() {
        String houseLocation = houseLocationText.getText();
        if(houseLocation == null || houseLocation.length() <= 0 || !Helper.isAlphanumeric(houseLocation)) {
            locationError.setText("Letters only");
            return;
        }
        House.houseLayoutFileName = houseLocation;
        initialize();
    }

}
