package org.team4;

import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.team4.view.HouseView;

public class LayoutController extends Application {

    static Pane canvas;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        House house = House.getDefaultHouse();
        HouseView houseView = new HouseView(house);

        Button btn = new Button("Add more people");
        btn.relocate(350, 10);
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                HouseView.addOccupant(new People("some random name", Color.BROWN,20, 20));
            }
        });

        canvas = new Pane();
        canvas.getChildren().addAll(houseView.getHousePane(), btn);
        canvas.setPrefSize(500, 500);
        stage.setTitle("test");
        stage.setScene(new Scene(canvas));
        stage.show();
    }
}
