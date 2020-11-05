package org.team4.shpParameters;

import org.team4.common.Settings;
import org.team4.common.logger.Logger;
import org.team4.house.House;
import org.team4.permissions.Permission;

import java.util.Date;
import java.util.stream.Stream;

public class ShpService {

    /**
     * Either enable or disable away mode
     */
    public void toggleAwayMode() {
        if(!Permission.checkAwayModePermission()) return;

        if(Settings.awayMode) {
            turnOffAwayMode();
        }
        else {
            turnOnAwayMode();
        }
    }

    /**
     * Disable away mode
     */
    public void turnOffAwayMode() {
        Settings.awayMode = false;
        Settings.startTimeBeforeAlerting = false;
        House.turnOnAllLights();
        Logger.info("Away mode has been deactivated");
    }

    /**
     * Enable away mode
     */
    public void turnOnAwayMode() {
        if(House.userInHouse()) {
            Logger.warning("Unable to enable away mode. User is in the house");
            return;
        }

        boolean successCloseWindowAndDoor = House.closeAllWindowsAndDoors();
        if(!successCloseWindowAndDoor) {
            Logger.warning("Unable to enable away mode. Failed to close all doors and windows");
            return;
        }

        Settings.awayMode = true;
        if(Settings.lightAutoMode) House.toggleLightAuto();
        House.turnOffAllLights();
        House.lockAllDoor();
        House.turnOnAllAwayModeLights();
        Settings.awayLightOn = true;
        Logger.info("Away mode has been activated");
    }

    /**
     * Set the time until the authorities are alerted
     * @param seconds
     */
    public void setAwayModeNotifTime(int seconds) {
        Settings.timeBeforeAlerting = seconds;
        Logger.info("Time before alerting authorities has been updated");
    }

    /**
     * Verify if a time format is valid
     * @param timeInput
     * @return a boolean
     */
    public boolean validateTime(String timeInput) {
        if (timeInput == null || timeInput.equals(""))
            return true;
        if (!timeInput.matches("^(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)$"))
            return false;
        return true;
    }

    /**
     * Set the on and off time of the light during away mode
     * @param on
     * @param off
     */
    public void setOnOffTime(String on, String off) {
        Logger.info("Light away mode on/off time has been updated");

        if (on == null || on.equals("") || off == null || off.equals("")) {
            Settings.awayLightOnTime = null;
            Settings.awayLightOffTime = null;
            return;
        }

        int[] onTime = Stream.of(on.split(":")).mapToInt(Integer::parseInt).toArray();
        int[] offTime = Stream.of(off.split(":")).mapToInt(Integer::parseInt).toArray();

        Date onDate = new Date();
        onDate.setHours(onTime[0]);
        onDate.setMinutes(onTime[1]);
        onDate.setSeconds(onTime[2]);

        Date offDate = new Date();
        offDate.setHours(offTime[0]);
        offDate.setMinutes(offTime[1]);
        offDate.setSeconds(offTime[2]);

        Settings.awayLightOnTime = onDate;
        Settings.awayLightOffTime = offDate;
    }

}
