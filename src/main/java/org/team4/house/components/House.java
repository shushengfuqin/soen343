package org.team4.house.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class House {

    Shape floorplan;
    ArrayList<Room> rooms;
    ArrayList<People> occupants;

    public House(ArrayList<Room> rooms, ArrayList<People> occupants) {
        setRooms(rooms);
        setOccupants(occupants);
        setFloorplan(makeFloorPlan(rooms));
    }

    private Shape makeFloorPlan(ArrayList<Room> rooms) {

        ArrayList<Shape> shapeList = new ArrayList<Shape>();

        for (Room room : rooms) {
            shapeList.add(room.getRoomShape());
        }

        Shape finalShape = shapeList.get(0);

        for(int i=0; i < shapeList.size(); i++) {
            if (i+1 < shapeList.size()) {
                finalShape = Shape.union(finalShape,shapeList.get(i+1));
            }
        }
        return finalShape;
    }

    public Shape getFloorplan() {
        return floorplan;
    }

    public void setFloorplan(Shape floorplan) {
        this.floorplan = floorplan;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<People> getOccupants() {
        return occupants;
    }

    public void setOccupants(ArrayList<People> occupants) {
        this.occupants = occupants;
    }

    public static House getDefaultHouse() {
        Shape room1Shape = new Rectangle(104, 46,127, 100);

        ArrayList<Window> room1_windows = new ArrayList<Window>();
        room1_windows.add(new Window("room1_window1", 231, 75, 231, 105));

        ArrayList<Door> room1_doors = new ArrayList<Door>();
        room1_doors.add(new Door("room1_door1", 130, 46, 200, 46));


        Shape room2Shape = new Rectangle(104, 146,127, 124);

        ArrayList<Window> room2_windows = new ArrayList<Window>();
        room2_windows.add(new Window("room2_window1", 231, 75, 231, 105));
        room1_windows.add(new Window("room2_window2", 143, 270, 180, 270));

        ArrayList<Door> room2_doors = new ArrayList<Door>();
        room1_doors.add(new Door("room2_door1", 130, 146, 180, 146));


        Shape room3Shape = new Rectangle(23, 114,81, 156);

        ArrayList<Window> room3_windows = new ArrayList<Window>();
        room1_windows.add(new Window("room3_window1", 23, 130, 23, 180));

        ArrayList<Door> room3_doors = new ArrayList<Door>();
        room3_doors.add(new Door("room3_door1", 104, 130, 104, 180));


        Room room1 = new Room("room1", room1Shape, room1_doors, room1_windows);
        Room room2 = new Room("room2", room2Shape, room2_doors, room2_windows);
        Room room3 = new Room("room3", room3Shape, room3_doors, room3_windows);

        ArrayList<Room> rooms = new ArrayList<Room>();
        rooms.add(room1);
        rooms.add(room2);
        rooms.add(room3);

        ArrayList<People> occupants = new ArrayList<People>();
        occupants.add(new People("bob", Color.AQUA, 20,30));

        return new House(rooms,occupants);
    }

    public void saveHouseConfig() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(this);
        System.out.println(jsonString);
    }
}
