package org.team4.house.components;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class People {
    String name;
    Paint peopleColor;
    int x_coordinate;
    int y_coordinate;
    Circle peopleShape;
    String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Paint getPeopleColor() {
        return peopleColor;
    }

    public void setPeopleColor(Paint peopleColor) {
        this.peopleColor = peopleColor;
    }

    public int getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(int x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public int getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(int y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    public Circle getPeopleShape() {
        return peopleShape;
    }

    public void setPeopleShape(Circle peopleShape) {
        this.peopleShape = peopleShape;
    }

    public People(String name, Paint peopleColor, int x_coordinate, int y_coordinate) {
        setName(name);
        setPeopleColor(peopleColor);
        setX_coordinate(Math.max(x_coordinate, 15));
        setY_coordinate(Math.max(y_coordinate, 15));
        setPeopleShape(new Circle(getX_coordinate(), getY_coordinate(), 15));
    }
}
