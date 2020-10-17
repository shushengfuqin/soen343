package org.team4;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class Room implements PropertyChangeListener, Drawable {

    String name;
    ArrayList<Door> doors;
    ArrayList<Window> windows;
    ArrayList<People> peoplePresent;

    @JsonIgnore
    Shape roomShape;

    public Room(String name, Shape roomShape, ArrayList<Door> doors, ArrayList<Window> windows) {
        this.name = name;
        this.roomShape = roomShape;
        this.doors = doors;
        this.windows = windows;
        this.peoplePresent = new ArrayList<People>();
    }


    public String getName() {
        return name;
    }

    public Shape getRoomShape() {
        return roomShape;
    }

    public ArrayList<Window> getWindows() {
        return windows;
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public ArrayList<People> getPeoplePresent() {
        return peoplePresent;
    }

    public void addPeoplePresent(People people) {
        this.peoplePresent.add(people);
    }

    public void removePeoplePresent(People people) {
        this.peoplePresent.remove(people);
    }

    public boolean isPresent(People person) {
        if (this.peoplePresent == null)
            return false;
        return getPeoplePresent().contains(person);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Room old coordinate:" + evt.getOldValue() + "\t Room new coordinate:" + evt.getNewValue());
        People person = (People) evt.getSource();
        Coordinate newCoordinate = (Coordinate) evt.getNewValue();
        if (!isPresent(person) && getRoomShape().contains(newCoordinate.getX(),newCoordinate.getY())) {
            addPeoplePresent(person);
        }

        if (isPresent(person) && !getRoomShape().contains(newCoordinate.getX(),newCoordinate.getY())) {
            removePeoplePresent(person);
        }

        System.out.println(getName() + " has these occupants : ");
        for (People people : peoplePresent) {
            System.out.println(people.getName());
        }
    }

    @Override
    public Shape getShape() {
        return roomShape;
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }
}
