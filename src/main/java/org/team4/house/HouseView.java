package org.team4.house;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.team4.house.components.House;
import org.team4.house.components.People;
import org.team4.house.components.Room;

public class HouseView {

    private static House house;
    private static Group occupants;
    private static Pane housePane;
    private static int nextInfo_Y = 0;

    public HouseView(House house) {
        setHouse(house);
        housePane = new Pane();
        housePane.setPrefSize(500,500);
    }

    public static House getHouse() {
        return house;
    }

    public static void setHouse(House house) {
        HouseView.house = house;
    }

    public static Group getOccupants() {
        return occupants;
    }

    public static void setOccupants(Group occupants) {
        HouseView.occupants = occupants;
    }

    public static void addOccupant(People people) {

        Shape person = people.getPeopleShape();

        Text status = new Text(300, 10+nextInfo_Y , "status");
        nextInfo_Y += 30;

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

        HouseView.housePane.getChildren().addAll(person, status);

    }

    private static boolean outsideParentBounds(Bounds childBounds, double newX, double newY) {
        Bounds parentBounds = housePane.getBoundsInLocal();

        //check if too left
        if (parentBounds.getMaxX() <= (newX + childBounds.getMaxX())) {
            return true;
        }

        //check if too right
        if (parentBounds.getMinX() >= (newX + childBounds.getMinX())) {
            return true;
        }

        //check if too down
        if (parentBounds.getMaxY() <= (newY + childBounds.getMaxY())) {
            return true;
        }

        //check if too up
        return parentBounds.getMinY() >= (newY + childBounds.getMinY());
    }

    // records relative x and y co-ordinates.
    static class Delta {
        double x, y;
    }

    public Pane getHousePane() {

        Text roomInfo = new Text();
        roomInfo.setFont(new Font(18));
        roomInfo.relocate(100, 100);
        roomInfo.setText("test");
        Group houseRooms = new Group();
        for (Room room : house.getRooms()) {
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
        housePane.getChildren().addAll(houseRooms);
        housePane.getChildren().add(roomInfo);

        return housePane;
    }


}
