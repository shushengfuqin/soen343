package org.team4.controller;

import javafx.scene.text.Text;
import org.team4.Coordinate;
import org.team4.HouseController;
import org.team4.People;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PeopleController implements PropertyChangeListener {
    People person;
    Text status;
    HouseController houseController;

    /* For testing and demo purposes only */
    public PeopleController(People person, Text status, HouseController houseController) {
        this.person = person;
        this.status = status;
        this.houseController = houseController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        People eventPerson = (People) evt.getSource();
        if (eventPerson == person) {
            status.setText(houseController.getRoomName((Coordinate) evt.getNewValue()));
        }
    }
}
