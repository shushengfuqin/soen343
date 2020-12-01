package org.team4.house.services;

import org.team4.common.logger.Logger;
import org.team4.house.HouseService;
import org.team4.house.components.Room;
import org.team4.permissions.Permission;

public class DoorService {
    private HouseService houseService;

    public DoorService() {
        houseService = new HouseService();
    }

    /**
     * Toggles all the locked doors
     * @param s
     */
    public void toggleDoorLock(String s){
        int[] doorLocation = houseService.getRoomLocation(s);
        if(!Permission.checkUserDoorPermission(doorLocation[0], doorLocation[1])) return;
        boolean doorLockStatus = houseService.getRooms()[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].blocked;
        if(houseService.getRooms()[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open && !doorLockStatus){
            houseService.getRooms()[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open = false;
        }
        houseService.getRooms()[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].blocked = !doorLockStatus;
        String location = "(" + doorLocation[0] + ", " + doorLocation[1] + ") " + Room.wallSideMapper(doorLocation[2]);
        String action = doorLockStatus ? "unlocked" : "locked";
        Logger.info("Door " + action + " at location " + location);
    }

    /**
     * Function lock all doors during away mode
     */
    public void lockAllDoor(){
        for(int doorLocation[]:houseService.house.lockDoor){
            boolean doorStatus = houseService.getRooms()[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].blocked;
            if(houseService.getRooms()[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open && !doorStatus){
                houseService.getRooms()[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open = false;
            }
            houseService.getRooms()[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].blocked = true;
            String location = "(" + doorLocation[0] + ", " + doorLocation[1] + ") " + Room.wallSideMapper(doorLocation[2]);
            Logger.info("Door locked at location " + location);
        }
    }

    /**
     * Get the status of a door
     * @param s
     * @return a boolean representing if a door is closed or not
     */
    public boolean getDoorStatus(String s) {
        int[] doorLocation = houseService.getRoomLocation(s);
        return houseService.getRooms()[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open;
    }

    /**
     * Get the status of the door is lock or not
     */
    public boolean getLockDoorStatus(String s){
        int[] lockDoorLocation = houseService.getRoomLocation(s);
        return houseService.getRooms()[lockDoorLocation[0]][lockDoorLocation[1]].walls[lockDoorLocation[2]].blocked;
    }

    /**
     * Open or close a door
     * @param s the location of the door
     */
    public boolean toggleDoor(String s, boolean byPassPermission) {
        int[] doorLocation = houseService.getRoomLocation(s);
        Room[][] rooms = houseService.getRooms();
        if(!byPassPermission && !Permission.checkUserDoorPermission(doorLocation[0], doorLocation[1])) return false;
        String location = "(" + doorLocation[0] + ", " + doorLocation[1] + ") " + Room.wallSideMapper(doorLocation[2]);

        boolean doorLocked = rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].blocked;
        if(doorLocked) {
            Logger.warning("Unable to open door at location " + location + ". Door is locked");
            return false;
        }

        boolean doorStatus = rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open;
        rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open = !doorStatus;

        switch (doorLocation[2]) {
            case 0:
                if(doorLocation[0] - 1 < 0) return true;
                if(rooms[doorLocation[0]-1][doorLocation[1]].walls[2].type.equals("door"))
                    rooms[doorLocation[0]-1][doorLocation[1]].walls[2].open = !doorStatus;
                break;
            case 1:
                if(doorLocation[1] - 1 < 0) return true;
                if(rooms[doorLocation[0]][doorLocation[1]-1].walls[3].type.equals("door")) {
                    rooms[doorLocation[0]][doorLocation[1] - 1].walls[3].open = !doorStatus;
                }
                break;
            case 2:
                if(doorLocation[0] + 1 >= houseService.house.roomColumn) return true;
                if(rooms[doorLocation[0]+1][doorLocation[1]].walls[0].type.equals("door")) {
                    rooms[doorLocation[0] + 1][doorLocation[1]].walls[0].open = !doorStatus;
                }
                break;
            case 3:
                if(doorLocation[1] + 1 >= houseService.house.roomRow) return true;
                if(rooms[doorLocation[0]][doorLocation[1]+1].walls[1].type.equals("door"))
                    rooms[doorLocation[0]][doorLocation[1]+1].walls[1].open = !doorStatus;
                break;
        }
        String action = doorStatus ? "closed" : "opened";
        Logger.info("Door " + action + " at location " + location);
        return true;
    }

    /**
     * Get all locked doors
     * @return string array with lock door locations
     */
    public String[] getAllLockDoor(){
        String[] lockDoorOption = new String[houseService.house.lockDoor.size()];
        for(int i = 0; i < houseService.house.lockDoor.size(); i++){
            int[] tempDoor = houseService.house.lockDoor.get(i);
            lockDoorOption[i] = "(" + tempDoor[0] + ", " + tempDoor[1] + ") - " + org.team4.house.components.Room.wallSideMapper(tempDoor[2]);
        }
        return lockDoorOption;
    }

    /**
     *
     * @return an array containing the list of all the windows
     */
    public String[] getAllDoorsOption() {
        String[] doorOption = new String[houseService.house.doors.size()];
        for(int i = 0; i < houseService.house.doors.size(); i++) {
            int[] tempDoor = houseService.house.doors.get(i);
            doorOption[i] = "(" + tempDoor[0] + ", " + tempDoor[1] + ") - " + org.team4.house.components.Room.wallSideMapper(tempDoor[2]);
        }
        return doorOption;
    }
}
