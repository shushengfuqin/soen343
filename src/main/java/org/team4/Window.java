package org.team4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Window implements Drawable {

    String name;
    Boolean open;

    @JsonIgnore
    Shape windowShape;

    public Window(String name, double startX, double startY, double endX, double endY ) {
        this.name = name;
        windowShape = new Line(startX, startY, endX, endY);
        open = true;
    }

    @Override
    public Shape getShape() {
        return windowShape;
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    public String getName() {
        return name;
    }
}
