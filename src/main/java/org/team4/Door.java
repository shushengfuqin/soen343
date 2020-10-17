package org.team4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Door implements Drawable {

    String name;
    Boolean open;

    @JsonIgnore
    Shape doorShape;

    public Door(String name, double startX, double startY, double endX, double endY ) {
        this.name = name;
        doorShape = new Line(startX, startY, endX, endY);
        open = false;
    }

    @Override
    public Shape getShape() {
        return doorShape;
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    public String getName() {
        return name;
    }
}
