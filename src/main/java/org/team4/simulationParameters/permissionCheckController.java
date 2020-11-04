package org.team4.simulationParameters;


import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class permissionCheckController
{

    @FXML
   public CheckBox lightA;

    @FXML
    public CheckBox lightG;

    @FXML
    public CheckBox lightC;

    @FXML
    public CheckBox lightS;

    @FXML
    public CheckBox windowA;

    @FXML
    public CheckBox windowC;

    @FXML
    public CheckBox windowG;

    @FXML
    public CheckBox windowS;

    @FXML
    public CheckBox doorA;

    @FXML
    public CheckBox doorG;

    @FXML
    public CheckBox doorC;

    @FXML
    public CheckBox doorS;

    @FXML
    public CheckBox lightL;

    @FXML
    public CheckBox windowL;

    @FXML
    public CheckBox doorL;

    public void save() {
        boolean wa = windowA.isSelected();
        boolean wc = windowC.isSelected();
        boolean wg = windowG.isSelected();
        boolean ws = windowS.isSelected();
        boolean wl = windowL.isSelected();
        boolean windowPermission[]  = {wa,wc,wg,ws,wl};
        //todo: add the array for doors and light
        //method should look like this:
        // Permission.saveNewPermission(windowPermission, doorPermission, lightPermission);
        Permission.saveNewPermission(windowPermission);

        //this will close the window
        Stage stage = (Stage) windowC.getScene().getWindow();
        stage.close();
    }

    public void initialize(){
        Permission.updatePermissionsFromFile();
        boolean[] windowPermission = Permission.windowPermission;
        windowA.setSelected(windowPermission[0]);
        //todo: Set the default values for all checkbox

    }

}
