package org.team4;

import javafx.scene.shape.Shape;

public interface Draggable {
    public Shape getShape();
    public void setCoordinate(double x, double y);
}
