package org.team4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class Window {

    String name;
    Boolean open;

    @JsonIgnore
    Shape windowShape;

    public Shape getWindowShape() {
        return windowShape;
    }

    public void setWindowShape(Shape windowShape) {
        this.windowShape = windowShape;
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

    public Window(String name, double startX, double startY, double endX, double endY ) {
        setName(name);
        setWindowShape(new Line(startX, startY, endX, endY));
        setOpen(true);
    }

    public Window(String name) {
        setName(name);
    }
}
