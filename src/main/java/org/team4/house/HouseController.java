package org.team4.house;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.team4.App;
import org.team4.common.Settings;
import org.team4.house.components.Room;

import java.net.URL;


public class HouseController {

    private HouseView houseView;

    @FXML
    public Pane housePane;

    public Pane backgroundPane;

    public HouseController() {
        houseView = new HouseView();
    }

    public void backgroundImageInit() {
        URL url = App.class.getResource("/org/img/grass.png");
        BackgroundImage myBI= new BackgroundImage(new Image(url.toString() ,690,740,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        if(Settings.simulationStarted) {
            backgroundPane.setBackground(new Background(myBI));
        }
        else {
            backgroundPane.setBackground(null);
        }
    }

    public void drawHouseLayout() {
        backgroundImageInit();
        eraseHouseLayout();
        Room[][] roomArr = House.rooms;
        for(int i = 0; i < roomArr.length; i++) {
            for(int j = 0; j < roomArr[i].length; j++) {
                Pane roomPane = houseView.getRoomPane(roomArr[i][j], i, j);
                roomPane.setLayoutY(j*houseView.roomHeight);
                roomPane.setLayoutX(i*houseView.roomWidth);
                housePane.getChildren().add(roomPane);
            }
        }
    }

    public void eraseHouseLayout() {
        backgroundImageInit();
        housePane.getChildren().clear();
    }
}
