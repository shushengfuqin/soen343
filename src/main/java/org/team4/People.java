package org.team4;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class People {
    String name;
    Paint peopleColor;
    int x_coordinate;
    int y_coordinate;
    Circle peopleShape;

    public People(String name, Paint peopleColor, int x_coordinate, int y_coordinate) {
        this.name = name;
        this.peopleColor = peopleColor;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
        this.peopleShape = new Circle(15,15,15);
        this.peopleShape.setFill(peopleColor);
    }
}
