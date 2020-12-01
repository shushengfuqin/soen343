package org.team4.house;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.team4.App;
import org.team4.common.Settings;
import org.team4.house.components.House;
import org.team4.house.components.Room;

import java.net.URL;


public class HouseController {

    private HouseView houseView;
    private HouseService houseService;
    public int roomWidth;
    public int roomHeight;

    @FXML
    public Pane housePane;
    public AnchorPane rowIndex;
    public AnchorPane columnIndex;

    public Pane backgroundPane;

    public HouseController() {
        houseService = new HouseService();
        roomWidth = 600/ houseService.house.roomColumn;
        roomHeight = 600/ houseService.house.roomRow;
        houseView = new HouseView(roomWidth, roomHeight);
    }

    /**
     * Initialize the background of the house layout
     */
    public void backgroundImageInit() {
        URL url = App.class.getResource("/org/img/grass.png");
        BackgroundImage myBI= new BackgroundImage(new Image(url.toString() , roomWidth, roomHeight,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        if(Settings.simulationStarted) {
            backgroundPane.setBackground(new Background(myBI));
        }
        else {
            backgroundPane.setBackground(null);
        }
    }

    /**
     * Draw the house layout
     */
    public void drawHouseLayout() {
        backgroundImageInit();
        eraseHouseLayout();
        houseView.drawIndex(rowIndex, columnIndex);
        Room[][] roomArr = houseService.getRooms();
        for(int i = 0; i < roomArr.length; i++) {
            for(int j = 0; j < roomArr[i].length; j++) {
                AnchorPane roomPane = houseView.getRoomPane(roomArr[i][j], i, j);
                roomPane.setLayoutY(j*houseView.roomHeight);
                roomPane.setLayoutX(i*houseView.roomWidth);
                housePane.getChildren().add(roomPane);
            }
        }
    }

    /**
     * Erase the house layout
     */
    public void eraseHouseLayout() {
        backgroundImageInit();
        housePane.getChildren().clear();
        rowIndex.getChildren().clear();
        columnIndex.getChildren().clear();
    }
}
