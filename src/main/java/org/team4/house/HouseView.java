package org.team4.house;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.team4.App;
import org.team4.common.Settings;
import org.team4.house.components.Room;
import org.team4.user.UserService;

import java.net.URL;
import java.util.ArrayList;

public class HouseView {

    private UserService userService;

    public static int roomWidth = 600/House.roomRow;
    public static int roomHeight = 600/House.roomColumn;

    public HouseView() {
        userService = new UserService();
    }

    public void updateBackgroundImage(Pane pane, boolean isOutside) {
        URL url = App.class.getResource("/org/img/wood.png");
        if(isOutside)
            url = App.class.getResource("/org/img/grass.png");

        BackgroundImage myBI= new BackgroundImage(new Image(url.toString() ,roomWidth,roomHeight,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        if(Settings.simulationStarted) {
            pane.setBackground(new Background(myBI));
        }
        else {
            pane.setBackground(null);
        }
    }

    public Pane getRoomPane(Room room, int x, int y) {
        Pane roomPane = new Pane();
        roomPane.setPrefWidth(roomWidth);
        roomPane.setPrefHeight(roomHeight);
        roomPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Text roomName = new Text();
        roomName.setLayoutX(roomWidth/10);
        roomName.setLayoutY(roomHeight/10);
        roomName.setTextAlignment(TextAlignment.LEFT);
        String coord = "(" + x + ", " + y + ")";
        String roomDescription = coord + "\n" + room.name +"\n";
        for(int i = 0; i < room.walls.length; i++) {
            if(room.walls[i].type.equals("window") || room.walls[i].type.equals("door")) {
                roomDescription += Room.wallSideMapper(i) + "-" + room.walls[i].type + "-" + room.walls[i].open + "\n";
            }
            else {
                roomDescription += Room.wallSideMapper(i) + "-" + room.walls[i].type + "\n";
            }
        }
        ArrayList<String> allUserInRoom = userService.userInLocation(x,y);
        String userList = "";
        for(String s : allUserInRoom) {
            userList += s + " ";
        }
        roomDescription += userList;
        roomName.setText(roomDescription);
        roomName.setFill(Color.WHITE);
        roomPane.getChildren().add(roomName);
        updateBackgroundImage(roomPane, room.name.equals("outside"));
        return roomPane;
    }
}
