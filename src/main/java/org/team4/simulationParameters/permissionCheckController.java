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

import org.json.JSONObject;
import org.team4.common.Settings;
import org.team4.user.User;
import org.team4.user.UserService;



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


    public boolean la = lightA.isSelected();
    public boolean lc = lightC.isSelected();
    public boolean lg = lightG.isSelected();
    public boolean ls = lightS.isSelected();
    public boolean wa = windowA.isSelected();
    public boolean wc = windowC.isSelected();
    public boolean wg = windowG.isSelected();
    public boolean ws = windowS.isSelected();
    public boolean da = doorA.isSelected();
    public boolean dc = doorC.isSelected();
    public boolean dg = doorG.isSelected();
    public boolean ds = doorS.isSelected();
    public boolean ll = lightL.isSelected();
    public boolean wl = windowL.isSelected();
    public boolean dl = doorL.isSelected();

    //permission for Adults,Children,Guests,Stranger,Location
    public boolean windowPermission []  = {wa,wc,wg,ws,wl} ;
    public boolean lightPermission[] = {la,lc,lg,ls,ll};
    public boolean doorPermission [] = {da,dc,dg,ds,dl};

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



    int x;
    int y;

   public void initialize(){
    this.checkWindowPermission(x,y);
    this.checkLightPermission(x,y);
    this.checkDoorPermission(x,y);
}

    private JSONObject toJson() {
        JSONObject jo = new JSONObject();
        jo.put("windowPermission", this.windowPermission);
        jo.put("lightPermission", this.lightPermission);
        jo.put("doorPermission", this.doorPermission);

        return jo;
    }

    public String toString() {
        return "windowPermission " + this.windowPermission + "\nlightPermission: " + this.lightPermission + "\ndoorPermission: " + this.doorPermission ;
    }





    /**
     * Read the data from a user file
     * @return a string of an array results shows the permission of user
     * @throws IOException
     */
    public static String readFromPermissionFile() throws IOException {
        File userFile = new File("permissionFileName");
        if (userFile.exists()) {
            Scanner fileReader = new Scanner(userFile);
            String data = fileReader.nextLine();
            return data;
        }
        return null;
    }
    /**
     * Writes a string to the permission file
     * @param
     * @return
     * @throws IOException
     */
    public static boolean writeToPermissionFile(String s) throws IOException {
        try {
            FileWriter myWriter = new FileWriter("permissionFileName.json");
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






}
