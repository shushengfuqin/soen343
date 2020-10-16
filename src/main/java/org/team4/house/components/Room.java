package org.team4.house.components;

import javafx.scene.shape.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;

public class Room {

    String name;
    ArrayList<Door> doors;
    ArrayList<Window> windows;

    @JsonIgnore
    Shape roomShape;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shape getRoomShape() {
        return roomShape;
    }

    public void setRoomShape(Shape roomShape) {
        this.roomShape = roomShape;
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public void setDoors(ArrayList<Door> doors) {
        this.doors = doors;
    }

    public ArrayList<Window> getWindows() {
        return windows;
    }

    public void setWindows(ArrayList<Window> windows) {
        this.windows = windows;
    }

    public void addDoor(Door door) {
        this.doors.add(door);
    }

    public void addWindow(Window window) {
        this.windows.add(window);
    }

    public Room(String name, Shape roomShape, ArrayList<Door> doors, ArrayList<Window> windows) {
        setName(name);
        setRoomShape(roomShape);
        setDoors(doors);
        setWindows(windows);
    }

    public Room(String name, ArrayList<Door> doors, ArrayList<Window> windows) {
        setName(name);
        setDoors(doors);
        setWindows(windows);
    }
}
