package org.team4;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LayoutController extends Application {

    static Pane canvas;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

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

        House house = new House(rooms,occupants);

        canvas = new Pane();

        Shape person = house.getOccupants().get(0).getPeopleShape();

        Text status = new Text(0,0,"status");

        final Delta dragDelta = new Delta();
        person.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = person.getLayoutX() - mouseEvent.getSceneX();
                dragDelta.y = person.getLayoutY() - mouseEvent.getSceneY();
            }
        });

        person.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {

                //Sets the drag boundaries limit
                double newX = mouseEvent.getSceneX() + dragDelta.x;
                double newY = mouseEvent.getSceneY() + dragDelta.y;
                if (outsideParentBounds(person.getLayoutBounds(), newX, newY))
                    return;

                person.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                person.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
            }
        });

        person.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                System.out.println("onMouseReleased");
                if (house.getFloorplan().contains(person.getBoundsInParent().getCenterX(), person.getBoundsInParent().getCenterY()))
                    status.setText("inside!!");
                else
                    status.setText("outside!!");
                mouseEvent.consume();
            }
        });

        canvas.getChildren().addAll(house.getHousePane(), person, status);
        canvas.setPrefSize(500, 500);
        stage.setTitle("test");
        stage.setScene(new Scene(canvas));
        stage.show();


    }

    private static boolean outsideParentBounds(Bounds childBounds, double newX, double newY){
        Bounds parentBounds = canvas.getBoundsInLocal();

        //check if too left
        if( parentBounds.getMaxX() <= (newX + childBounds.getMaxX()) ) {
            return true ;
        }

        //check if too right
        if( parentBounds.getMinX() >= (newX + childBounds.getMinX()) ) {
            return true ;
        }

        //check if too down
        if( parentBounds.getMaxY() <= (newY + childBounds.getMaxY()) ) {
            return true ;
        }

        //check if too up
        return parentBounds.getMinY() >= (newY + childBounds.getMinY());
    }

    // records relative x and y co-ordinates.
    static class Delta { double x, y; }
}
