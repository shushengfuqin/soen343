package org.team4.shsParameters;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.team4.App;
import org.team4.DashboardController;
import org.team4.common.Settings;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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

    public ShsParameterController() {
        shsParameterService = new ShsParameterService();
    }

    public void initialize() {
        temperatureInit();
        dateTimeInit();
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


}
