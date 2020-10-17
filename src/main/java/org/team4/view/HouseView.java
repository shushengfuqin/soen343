package org.team4.view;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
//import javafx.scene.text.Font;
//import javafx.scene.text.Text;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.team4.*;


import java.util.Collection;

public class HouseView {

    private House house;
    private Group occupants;
    private Pane pane;
    private Scene scene;
    private Stage stage;
    private HouseController houseController;
    private DoorController doorController;
    private WindowController windowController;
    private static final double LINE_WIDTH = 5;


    public HouseView(House house, HouseController houseController) {
        this.houseController = houseController;
        this.house = house;

        stage = new Stage();
        stage.setTitle("House Layout");

        pane = new Pane();
        pane.setPrefSize(500,500);


        /*For testing and demo purposes only */
        Button btn = new Button("Add more people");
        btn.relocate(350, 0);
        btn.setOnMouseClicked((mouseEvent -> {
            houseController.addRandomOccupant();
        }));
        addToPane(btn);

        Text coordinates = new Text("test");
        coordinates.relocate(250,400);
        pane.setOnMouseMoved((mouseEvent -> {
            coordinates.setText("x:" + mouseEvent.getX() + " y:" + mouseEvent.getY());
        }));

        addToPane(coordinates);
//
//        Button btn1 = new Button("open door1");
//        btn1.relocate(350, 25);
//        btn1.setOnMouseClicked((mouseEvent -> {
//            houseController.openDoor(house.getRooms().get(0).getDoors().get(0));
//        }));
//        addToPane(btn1);
//
//        Button btn2 = new Button("close door1");
//        btn2.relocate(350, 50);
//        btn2.setOnMouseClicked((mouseEvent -> {
//            houseController.closeDoor(house.getRooms().get(0).getDoors().get(0));
//        }));
//        addToPane(btn2);
//
//        Button btn3 = new Button("open door2");
//        btn3.relocate(350, 75);
//        btn3.setOnMouseClicked((mouseEvent -> {
//            houseController.openDoor(house.getRooms().get(1).getDoors().get(0));
//        }));
//        addToPane(btn3);
//
//        Button btn4 = new Button("close door2");
//        btn4.relocate(350, 100);
//        btn4.setOnMouseClicked((mouseEvent -> {
//            houseController.closeDoor(house.getRooms().get(1).getDoors().get(0));
//        }));
//        addToPane(btn4);

        for (Room room : house.getRooms()) {
            Shape roomShape = drawItem(room);
            addToPane(roomShape);
            for (Window window : room.getWindows()) {
                System.out.println("\t \t window name : " + window.getName() );
                Shape windowShape = drawItem(window);
                addToPane(windowShape);
            }
            for (Door door : room.getDoors()){
                System.out.println("\t \t door name : " + door.getName() );
                Shape doorShape = drawItem(door);
                addToPane(doorShape);
            }
        }
        scene = new Scene(pane);
        stage.setScene(scene);
    }

    public Shape drawItem(Drawable item) {
        Shape shape = item.getShape();
        shape.setStroke(item.getColor());
        shape.setStrokeWidth(LINE_WIDTH);
        shape.setFill(Color.TRANSPARENT);
        return shape;
    }

    public Stage getStage() {
        return stage;
    }

    public void addToPane(Node node) {
        pane.getChildren().add(node);
    }

    public void addAllToPane(Collection<Node> nodes) {
        pane.getChildren().addAll(nodes);
    }

}
