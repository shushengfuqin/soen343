package org.team4.shpParameters;

import org.team4.common.Settings;
import org.team4.common.logger.Logger;
import org.team4.house.services.DoorService;
import org.team4.house.HouseService;
import org.team4.house.services.LightService;
import org.team4.house.services.WindowService;
import org.team4.permissions.Permission;
import org.team4.user.UserService;

import java.util.Date;
import java.util.stream.Stream;

public class ShpService {

    LightService lightService;
    DoorService doorService;
    UserService userService;
    WindowService windowService;
    HouseService houseService;

    public ShpService() {
        lightService = new LightService();
        doorService = new DoorService();
        userService = new UserService();
        windowService = new WindowService();
        houseService = new HouseService();
    }

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
        lightService.turnOnAllLights();
        Logger.info("Away mode has been deactivated");
    }

    /**
     * Enable away mode
     */
    public void turnOnAwayMode() {
        if(userService.userInHouse()) {
            Logger.warning("Unable to enable away mode. User is in the house");
            return;
        }

        boolean successCloseWindowAndDoor = closeAllWindowsAndDoors();
        if(!successCloseWindowAndDoor) {
            Logger.warning("Unable to enable away mode. Failed to close all doors and windows");
            return;
        }

        Settings.awayMode = true;
        if(Settings.lightAutoMode) lightService.toggleLightAuto();
        lightService.turnOffAllLights();
        doorService.lockAllDoor();
        lightService.turnOnAllAwayModeLights();
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


    /**
     * Close all windows
     * @return a boolean if it's a success or not
     */
    public boolean closeAllWindowsAndDoors() {
        String[] allWindows = windowService.getAllWindowsOption();
        for(String window: allWindows) {
            int[] loc = houseService.getRoomLocation(window);
            if(houseService.getRooms()[loc[0]][loc[1]].walls[loc[2]].open) {
                boolean success = windowService.toggleWindowOpen(window, true);
                if(!success) return false;
            }
        }

        String[] allDoors = doorService.getAllDoorsOption();
        for(String door: allDoors) {
            int[] loc = houseService.getRoomLocation(door);
            if(houseService.getRooms()[loc[0]][loc[1]].walls[loc[2]].open) {
                boolean success = doorService.toggleDoor(door, true);
                if(!success) return false;
            }
        }
        return true;
    }
}
