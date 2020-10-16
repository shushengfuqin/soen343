package org.team4.house;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.team4.house.components.House;
import org.team4.house.components.People;

public class HouseController {
    @FXML
    public AnchorPane houseLayout;

    public void initialize() {
        House house = House.getDefaultHouse();
        HouseView houseView = new HouseView(house);
        Pane housePane = new Pane();
        Button btn = new Button("Add more people");
        btn.relocate(350, 10);
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                HouseView.addOccupant(new People("some random name", Color.BROWN,20, 20));
            }
        });

        housePane.getChildren().addAll(houseView.getHousePane(), btn);
        housePane.setPrefSize(500, 500);
        houseLayout.getChildren().add(housePane);
    }
}
