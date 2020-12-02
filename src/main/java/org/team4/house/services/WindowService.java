package org.team4.house.services;

import org.team4.common.logger.Logger;
import org.team4.house.HouseService;
import org.team4.house.components.Room;
import org.team4.house.components.Wall;
import org.team4.permissions.Permission;

public class WindowService {
    private HouseService houseService;

    public WindowService() {
        houseService = new HouseService();
    }

    /**
     * Checks if a window in a room is blocked
     * @param x coordinate of the room
     * @param y coordinate of the room
     * @return true if there's a blocked window
     */
    public boolean checkWindowBlock(int x, int y){
        for(Wall wall : houseService.getRooms()[x][y].walls)
            if(wall.type.equals("window") && wall.blocked) return true;
        return false;
    }

    /**
     * Check if a room contains windows
     * @param x
     * @param y
     * @return
     */
    public boolean hasWindow(int x, int y) {
        for(Wall wall : houseService.getRooms()[x][y].walls)
            if(wall.type.equals("window")) return true;
        return false;
    }

    /**
     * Open or close windows in a room
     * @param x
     * @param y
     * @param open
     */
    public  void toggleWindowsInRoom(int x, int y, boolean open) {
        for(Wall wall : houseService.getRooms()[x][y].walls)
            if(wall.type.equals("window")) wall.open = open;
    }

    /**
     * Get the status of a window
     * @param s
     * @return a boolean representing if a window is open or not
     */
    public boolean getWindowStatusOpen(String s) {
        int[] windowLocation = houseService.getRoomLocation(s);
        return houseService.getRooms()[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].open;
    }


    /**
     * get the status of if a window is closed or not
     * @param s position of window
     * @return boolean
     */
    public boolean getWindowStatusBlock(String s) {
        int[] windowLocation = houseService.getRoomLocation(s);
        return houseService.getRooms()[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].blocked;
    }

    /**
     * Open or close a window
     * @param s the location of a window
     */
    public boolean toggleWindowOpen(String s, boolean byPassPermission) {
        int[] windowLocation = houseService.getRoomLocation(s);
        if(!byPassPermission && !Permission.checkUserWindowPermission(windowLocation[0], windowLocation[1])) return false;

        if(houseService.getRooms()[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].blocked) {
            Logger.warning("Can't open window. Blocked");
            return false;
        }
        boolean windowStatus = houseService.getRooms()[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].open;
        houseService.getRooms()[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].open = !windowStatus;
        String location = "(" + windowLocation[0] + ", " + windowLocation[1] + ") " + Room.wallSideMapper(windowLocation[2]);
        String action = windowStatus ? "closed" : "opened";
        Logger.info("Window " + action + " at location " + location);
        return true;
    }

    /**
     * Open or close a window
     * @param s window position
     */
    public void toggleWindowBlock(String s) {
        int[] windowLocation = houseService.getRoomLocation(s);
        boolean windowStatus = houseService.getRooms()[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].blocked;
        houseService.getRooms()[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].blocked = !windowStatus;
        String location = "(" + windowLocation[0] + ", " + windowLocation[1] + ") " + Room.wallSideMapper(windowLocation[2]);
        String action = windowStatus ? "unblocked" : "blocked";
        Logger.info("Window " + action + " at location " + location);
    }

    /**
     *
     * @return an array containing the list of all the windows
     */
    public String[] getAllWindowsOption() {
        String[] windowOption = new String[houseService.house.windows.size()];
        for(int i = 0; i < houseService.house.windows.size(); i++) {
            int[] tempWindow = houseService.house.windows.get(i);
            windowOption[i] = "(" + tempWindow[0] + ", " + tempWindow[1] + ") - " + Room.wallSideMapper(tempWindow[2]);
        }
        return windowOption;
    }
}
