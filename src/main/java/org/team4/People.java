package org.team4;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class People implements Draggable {
    String name;
    Paint peopleColor;
    Coordinate coordinate;
    Circle peopleShape;
    PropertyChangeSupport support;
    final static double PEOPLE_SIZE = 5;

    public People(String name, Paint peopleColor, double x_coordinate, double y_coordinate) {

        this.name = name;
        this.peopleColor = peopleColor;
        x_coordinate = Math.max(x_coordinate, PEOPLE_SIZE);
        y_coordinate = Math.max(y_coordinate, PEOPLE_SIZE);
        this.coordinate = new Coordinate((x_coordinate + PEOPLE_SIZE)/2 , (y_coordinate + PEOPLE_SIZE)/2);
        System.out.println(this.coordinate);
        this.peopleShape = new Circle(coordinate.getX(), coordinate.getY(), PEOPLE_SIZE, peopleColor);
        support = new PropertyChangeSupport(this);
    }

    public String getName() {
        return name;
    }

    public Circle getShape() {
        return peopleShape;
    }
    
    public void setCoordinate(double x, double y) {
        Coordinate oldCoordinate = new Coordinate(coordinate.getX(), coordinate.getY());
        Coordinate newCoordinate = new Coordinate(x,y);
        coordinate.setX(x);
        coordinate.setY(y);
        support.firePropertyChange("movement", oldCoordinate, newCoordinate);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
}
