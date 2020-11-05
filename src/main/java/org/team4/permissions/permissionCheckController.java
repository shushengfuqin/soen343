package org.team4.permissions;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

public class permissionCheckController
{

    @FXML
   public CheckBox lightA;

    @FXML
    public CheckBox lightG;

    @FXML
    public CheckBox lightF;

    @FXML
    public CheckBox lightS;

    @FXML
    public CheckBox windowA;

    @FXML
    public CheckBox windowF;

    @FXML
    public CheckBox windowG;

    @FXML
    public CheckBox windowS;

    @FXML
    public CheckBox doorA;

    @FXML
    public CheckBox doorG;

    @FXML
    public CheckBox doorF;

    @FXML
    public CheckBox doorS;

    @FXML
    public CheckBox lightL;

    @FXML
    public CheckBox windowL;

    @FXML
    public CheckBox doorL;

    @FXML
    public CheckBox awayF;
    public CheckBox awayG;
    public CheckBox awayS;
    public CheckBox awayA;
    public CheckBox awayL;

    @FXML
    private Button SavePermissionButton;

    /**
     * Save the permissions
     */
    public void save() {
        boolean wa = windowA.isSelected();
        boolean wf = windowF.isSelected();
        boolean wg = windowG.isSelected();
        boolean ws = windowS.isSelected();
        boolean wl = windowL.isSelected();
        boolean windowPermission[]  = {wf,wg,ws,wa,wl};

        boolean da = doorA.isSelected();
        boolean df = doorF.isSelected();
        boolean dg = doorG.isSelected();
        boolean ds = doorS.isSelected();
        boolean dl = doorL.isSelected();
        boolean doorPermission[]  = {df,dg,ds,da,dl};

        boolean la = lightA.isSelected();
        boolean lf = lightF.isSelected();
        boolean lg = lightG.isSelected();
        boolean ls = lightS.isSelected();
        boolean ll = lightL.isSelected();
        boolean lightPermission[]  = {lf,lg,ls,la,ll};

        boolean aa = awayA.isSelected();
        boolean af = awayF.isSelected();
        boolean ag = awayG.isSelected();
        boolean as = awayS.isSelected();
        boolean al = awayL.isSelected();
        boolean awayPermission[] = {af, ag, as, aa, al};

        Permission.saveNewPermission(windowPermission, doorPermission, lightPermission, awayPermission);

        Stage stage = (Stage) SavePermissionButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Initialize the checkboxes with the permissions
     */
    public void initialize(){
        Permission.updatePermissionsFromFile();

        boolean[] windowPermission = Permission.windowPermission;
        windowF.setSelected(windowPermission[0]);
        windowG.setSelected(windowPermission[1]);
        windowS.setSelected(windowPermission[2]);
        windowA.setSelected(windowPermission[3]);
        windowL.setSelected(windowPermission[4]);

        boolean[] doorPermission = Permission.doorPermission;
        doorF.setSelected(doorPermission[0]);
        doorG.setSelected(doorPermission[1]);
        doorS.setSelected(doorPermission[2]);
        doorA.setSelected(doorPermission[3]);
        doorL.setSelected(doorPermission[4]);

        boolean[] lightPermission = Permission.lightPermission;
        lightF.setSelected(lightPermission[0]);
        lightG.setSelected(lightPermission[1]);
        lightS.setSelected(lightPermission[2]);
        lightA.setSelected(lightPermission[3]);
        lightL.setSelected(lightPermission[4]);

        boolean[] awayPermission = Permission.awayPermission;
        awayF.setSelected(awayPermission[0]);
        awayG.setSelected(awayPermission[1]);
        awayS.setSelected(awayPermission[2]);
        awayA.setSelected(awayPermission[3]);
        awayL.setSelected(awayPermission[4]);
    }

}
