package org.team4.house;

import org.team4.house.components.House;
import org.team4.house.components.Room;

public class HouseService {
    public static House house = new House();

    public Room[][] getHouseLayout() {
        Room[][] rooms = house.getHouseLayout();
        house.indexAllIndoorRooms();
        house.indexAllLights();
        house.indexHouseWindowAndDoor();
        return rooms;
    }

    public Room[][] getRooms() {
        return house.rooms;
    }

    public void resetParams() {
        house.resetParams();
    }

    public void saveHouseLayout() {
        house.saveHouseLayout();
    }

    /**
     * Get the room location from a string
     * @param s the location of a room
     * @return an array representing the row column and side
     */
    public int[] getRoomLocation(String s) {
        s = s.replace(" ", "");
        int indexSideChar = s.indexOf("-");
        String roomRow = s.substring(1,2);
        String roomColumn = s.substring(3,4);
        String roomSide = s.substring(indexSideChar+1);
        int intRoomRow = Integer.parseInt(roomRow);
        int intRoomColumn = Integer.parseInt(roomColumn);
        int intRoomSide = Room.wallSideMapper(roomSide);

        int[] windowLocation = {intRoomRow, intRoomColumn, intRoomSide};
        return windowLocation;
    }
}
