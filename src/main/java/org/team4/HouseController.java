package org.team4;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import org.team4.controller.PeopleController;
import org.team4.view.HouseView;

import java.util.ArrayList;

public class HouseController {

    private final House house;
    private final ArrayList<People> peopleList;
    private final HouseView view;
    private static int nextInfo_Y = 10;
    private static int i = 0;

    public HouseController(House house) {
        this.house = house;
        peopleList = new ArrayList<People>();
        view = new HouseView(house, this);
    }

    public Stage getStage() {
        return view.getStage();
    }

    public void addRandomOccupant() {
        String[] names = {"alice", "bob", "charlie", "doug"};
        addOccupant(new People(names[i++%4], Color.AQUAMARINE,20, 20));
    }

    public void addOccupant(People people) {
        makeDraggable(people);
        peopleList.add(people);
        Shape person = people.getShape();
        Text status = new Text(250, 10+nextInfo_Y , "status");
        PeopleController peopleController = new PeopleController(people, status, this);
        people.addPropertyChangeListener(peopleController);
        nextInfo_Y += 30;
        view.addToPane(person);
        view.addToPane(status);
        for (Room room : house.getRooms()) {
            people.addPropertyChangeListener(room);
        }
    }

    public void openDoor(Door door) {
        if (!door.open) {
            Shape shape = door.getShape();
            shape.getTransforms().add(new Rotate(45,shape.getBoundsInLocal().getMinX(),shape.getBoundsInLocal().getMinY()));
            door.open = true;
        }
    }

    public void closeDoor(Door door) {
        if (door.open) {
            Shape shape = door.getShape();
            shape.getTransforms().add(new Rotate(-45, shape.getBoundsInLocal().getMinX(),shape.getBoundsInLocal().getMinY()));;
            door.open = false;
        }
    }

    public void makeDraggable(Draggable obj) {
        
        final Delta dragDelta = new Delta();
        Shape draggable = obj.getShape();
        
        draggable.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = draggable.getLayoutX() - mouseEvent.getSceneX();
                dragDelta.y = draggable.getLayoutY() - mouseEvent.getSceneY();
            }
        });

        draggable.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {

                //Sets the drag boundaries limit
                double newX = mouseEvent.getSceneX() + dragDelta.x;
                double newY = mouseEvent.getSceneY() + dragDelta.y;
                if (outsideParentBounds(draggable, draggable.getLayoutBounds(), newX, newY))
                    return;

                draggable.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
                draggable.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
            }
        });

        draggable.setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                obj.setCoordinate(draggable.getBoundsInParent().getCenterX(), draggable.getBoundsInParent().getCenterY());
                mouseEvent.consume();
            }
        });
    }

    private boolean outsideParentBounds(Shape shape, Bounds childBounds, double newX, double newY) {
        Bounds parentBounds = shape.getParent().getBoundsInParent();

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

    public String getRoomName(Coordinate coordinate) {
        for (Room room : house.getRooms()) {
            if (room.getShape().getBoundsInLocal().contains(coordinate.getX(), coordinate.getY())) {
                return room.getName();
            }
        }
        return "not in house";
    }

}
