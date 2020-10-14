package org.team4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

public class House {

    Shape floorplan;
    ArrayList<Room> rooms;
    ArrayList<People> occupants;

    public void House(Shape floorplan, ArrayList<Room> rooms, ArrayList<People> occupants) {
        this.floorplan = floorplan;
        this.rooms = rooms;
        this.occupants = occupants;
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

    public void saveHouseConfig() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(this);
        System.out.println(jsonString);
    }
}
