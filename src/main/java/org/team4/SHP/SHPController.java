package org.team4.SHP;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.team4.common.Coordinate;
import org.team4.house.House;
import org.team4.user.User;

import java.util.ArrayList;

public class
SHPController {

    private ArrayList<LightPolicy> lightPolicies;
    @FXML
    //lights table
    public TableColumn<String, String> lightNameColumn;
    public TableColumn<String, String> lightStartTimeColumn;
    public TableColumn<String, String> lightStopTimeColumn;
    public TableView lightsTable;

    public SHPController() {
        lightPolicies = new ArrayList<>();
        String[] lightList = House.getAllLightsOption();
        for (String lightName : lightList) {
            lightPolicies.add(new LightPolicy(lightName));
        }
    }

    public void initialize() {
        lightNameColumn.setCellValueFactory(new PropertyValueFactory<>("lightName"));
        lightStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startAwayModeTime"));
        lightStopTimeColumn.setCellValueFactory(new PropertyValueFactory<>("stopAwayModeTime"));
        displayAllLightOptions();
    }

    private void displayAllLightOptions() {
        for (LightPolicy lightPolicy : lightPolicies) {
            lightsTable.getItems().add(lightPolicy);
        }
    }
}
