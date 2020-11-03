package org.team4.simulationParameters;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import org.team4.common.Settings;
import org.team4.user.User;
import org.team4.user.UserService;



public class permissionCheckController implements Initializable
{
    @FXML
    private CheckBox lightA;

    @FXML
    private CheckBox lightG;

    @FXML
    private CheckBox lightC;

    @FXML
    private CheckBox lightS;

    @FXML
    private CheckBox windowA;

    @FXML
    private CheckBox windowC;

    @FXML
    private CheckBox windowG;

    @FXML
    private CheckBox windowS;

    @FXML
    private CheckBox doorA;

    @FXML
    private CheckBox doorG;

    @FXML
    private CheckBox doorC;

    @FXML
    private CheckBox doorS;

    @FXML
    private CheckBox lightL;

    @FXML
    private CheckBox windowL;

    @FXML
    private CheckBox doorL;


    private boolean la = lightA.isSelected();
    private boolean lc = lightC.isSelected();
    private boolean lg = lightG.isSelected();
    private boolean ls = lightS.isSelected();
    private boolean wa = windowA.isSelected();
    private boolean wc = windowC.isSelected();
    private boolean wg = windowG.isSelected();
    private boolean ws = windowS.isSelected();
    private boolean da = doorA.isSelected();
    private boolean dc = doorC.isSelected();
    private boolean dg = doorG.isSelected();
    private boolean ds = doorS.isSelected();
    private boolean ll = lightL.isSelected();
    private boolean wl = windowL.isSelected();
    private boolean dl = doorL.isSelected();

    //permission for Adults,Children,Guests,Stranger,Location
    private boolean windowPermission []  = {wa,wc,wg,ws,wl} ;
    private boolean lightPermission[] = {la,lc,lg,ls,ll};
    private boolean doorPermission [] = {da,dc,dg,ds,dl};

    UserService userService = new UserService();


    // check the permission of control window
    public boolean checkWindowPermission(int x, int y){
        String currUser = Settings.currentUser;
        if(currUser == null) return true;

        User user = userService.getSingleUser(currUser);
        int xUser = user.getX();
        int yUser = user.getY();

        if(currUser.equals("Adult")){
            if(windowPermission[0]&& xUser==x && yUser==y){
                return true;
            }
        }

        if(currUser.equals("Children")){
            if(windowPermission[1]&& xUser==x && yUser==y){
                return true;
            }
        }

        if(currUser.equals("Guest")){
            if(windowPermission[2]&& xUser==x && yUser==y){
                return true;
            }
        }

        if(currUser.equals("Stranger")){
            if(windowPermission[3]&& xUser==x && yUser==y){
                return true;
            }
        }

        if(currUser.equals("Location")){
            if(windowPermission[4]&& xUser==x && yUser==y){
                return true;
            }
        }

        return false;
    }




    //Check Permission for control light
    public boolean checkLightPermission(int x, int y){
        String currUser = Settings.currentUser;
        if(currUser == null) return true;

        User user = userService.getSingleUser(currUser);
        int xUser = user.getX();
        int yUser = user.getY();

        if(currUser.equals("Adult")){
            if(lightPermission[0]&& xUser==x && yUser==y){
                return true;
            }
        }

        if(currUser.equals("Children")){
            if(lightPermission[1]&& xUser==x && yUser==y){
                return true;
            }
        }

        if(currUser.equals("Guest")){
            if(lightPermission[2]&& xUser==x && yUser==y){
                return true;
            }
        }

        if(currUser.equals("Stranger")){
            if(lightPermission[3]&& xUser==x && yUser==y){
                return true;
            }
        }

        if(currUser.equals("Location")){
            if(lightPermission[4]&& xUser==x && yUser==y){
                return true;
            }
        }


        return false;
    }


    //Check Permission for Door
    public boolean checkDoorPermission(int x, int y){
        String currUser = Settings.currentUser;
        if(currUser == null) return true;

        User user = userService.getSingleUser(currUser);
        int xUser = user.getX();
        int yUser = user.getY();

        if(currUser.equals("Adult")){
            if(doorPermission[0]&& xUser==x && yUser==y){
                return true;
            }
        }

        if(currUser.equals("Children")){
            if(doorPermission[1]&& xUser==x && yUser==y){
                return true;
            }
        }

        if(currUser.equals("Guest")){
            if(doorPermission[2]&& xUser==x && yUser==y){
                return true;
            }
        }

        if(currUser.equals("Stranger")){
            if(doorPermission[3]&& xUser==x && yUser==y){
                return true;
            }
        }

        if(currUser.equals("Location")){
            if(doorPermission[4]&& xUser==x && yUser==y){
                return true;
            }
        }


        return false;
    }


    /**
     * Read the data from a user file
     * @return a string of an array results shows the permission of user
     * @throws IOException
     */
    public static String readFromPermissionFile() throws IOException {
        File userFile = new File(permissionFileName);
        if (permissionFile.exists()) {
            Scanner fileReader = new Scanner(permissionFile);
            String data = fileReader.nextLine();
            return data;
        }
        return null;
    }
    /**
     * Writes a string to the permission file
     * @param s
     * @return
     * @throws IOException
     */
    public static boolean writeToPermissionFile(String s) throws IOException {
        try {
            FileWriter myWriter = new FileWriter(permissionFileName);
            myWriter.write(s);
            myWriter.close();
            return true;
        }
        catch (IOException e) {
            System.out.println("Error occured while writing to org.team4.permission file");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }









}
