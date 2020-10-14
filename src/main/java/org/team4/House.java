package org.team4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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

    public Pane getHousePane() {

        Text roomInfo = new Text();
        roomInfo.setFont(new Font(18));
        roomInfo.relocate(100,100);
        roomInfo.setText("test");
        Pane houseCanvas = new Pane();
        Group houseRooms = new Group();
        for (Room room : rooms) {
            Shape formattedRoomShape = room.getRoomShape();
            formattedRoomShape.setStroke(Color.RED);
            formattedRoomShape.setStrokeWidth(10);
            formattedRoomShape.setFill(Color.WHITE);
            formattedRoomShape.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    roomInfo.setText(room.getName());
                }
            });
            houseRooms.getChildren().add(formattedRoomShape);
        }
        houseCanvas.getChildren().addAll(houseRooms);
        houseCanvas.getChildren().add(roomInfo);

        return houseCanvas;
    }

    public void saveHouseConfig() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(this);
        System.out.println(jsonString);
    }
}
