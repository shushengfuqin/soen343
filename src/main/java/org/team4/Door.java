package org.team4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Door {

    String name;
    Boolean open;

    @JsonIgnore
    Shape doorShape;

    public Shape getDoorShape() {
        return doorShape;
    }

    public void setDoorShape(Shape doorShape) {
        this.doorShape = doorShape;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Door(String name, double startX, double startY, double endX, double endY ) {
        setName(name);
        setDoorShape(new Line(startX,startY,endX,endY));
        setOpen(true);
    }

    public Door(String name) {
        setName(name);
    }
}
