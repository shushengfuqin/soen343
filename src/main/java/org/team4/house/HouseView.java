package org.team4.house;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.team4.App;
import org.team4.common.Settings;
import org.team4.house.components.Room;
import org.team4.house.components.Wall;
import org.team4.user.UserService;

import java.net.URL;
import java.util.ArrayList;

public class HouseView {

    private UserService userService;

    public static int roomWidth = 600/House.roomColumn;
    public static int roomHeight = 600/House.roomRow;

    public HouseView() {
        userService = new UserService();
    }

    public void drawIndex(AnchorPane row, AnchorPane col) {
        //Draw row
        double pos = 0;
        for(int i = 0; i < House.roomColumn; i++) {
            Text tempText = new Text();
            StackPane textBox = new StackPane();
            StackPane textPane = new StackPane();

            BackgroundFill bi = new BackgroundFill(Color.rgb(41,41,41), null, null);
            textBox.setBackground(new Background(bi));
            textBox.setMaxHeight(20);
            textBox.setMaxWidth(roomWidth/4);

            AnchorPane.setLeftAnchor(textPane, pos);
            pos += roomWidth;

            tempText.setText(Integer.toString(i));
            tempText.setTextAlignment(TextAlignment.CENTER);
            tempText.setFill(Color.WHITE);
            tempText.setStyle("-fx-font: 17 arial;");
            textBox.getChildren().add(tempText);
            textBox.setAlignment(tempText, Pos.CENTER);

            textPane.getChildren().add(textBox);
            textPane.setAlignment(textBox, Pos.CENTER);
            textPane.setPrefHeight(20);
            textPane.setPrefWidth(roomWidth);

            row.getChildren().add(textPane);
        }

        pos = 0;
        for(int i = 0; i < House.roomRow; i++) {
            Text tempText = new Text();
            StackPane textBox = new StackPane();
            StackPane textPane = new StackPane();

            BackgroundFill bi = new BackgroundFill(Color.rgb(41,41,41), null, null);
            textBox.setBackground(new Background(bi));
            textBox.setMaxHeight(roomHeight/4);
            textBox.setMaxWidth(20);

            AnchorPane.setTopAnchor(textPane, pos);
            pos += roomHeight;

            tempText.setText(Integer.toString(i));
            tempText.setTextAlignment(TextAlignment.CENTER);
            tempText.setFill(Color.WHITE);
            tempText.setStyle("-fx-font: 17 arial;");
            textBox.getChildren().add(tempText);
            textBox.setAlignment(tempText, Pos.CENTER);

            textPane.getChildren().add(textBox);
            textPane.setAlignment(textBox, Pos.CENTER);
            textPane.setPrefHeight(roomHeight);
            textPane.setPrefWidth(20);

            col.getChildren().add(textPane);
        }
    }

    /**
     * Apply a background to a room depending on it's type
     * @param pane
     * @param name
     */
    public void updateBackgroundImage(AnchorPane pane, String name) {
        URL url = App.class.getResource("/org/img/wood.png");
        if(name.equals("outside") || name.equals("backyard"))
            url = App.class.getResource("/org/img/grass.png");
        if(name.equals("garage"))
            url = App.class.getResource("/org/img/stone.png");
        if(name.equals("entrance"))
            url = App.class.getResource("/org/img/wool.png");

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

    public AnchorPane drawWindow(String side, boolean blocked, boolean opened) {
        AnchorPane windowPane = new AnchorPane();
        int backgroundDim = 0;
        if(side.equals("left") || side.equals("right")) {
            windowPane.setPrefHeight(roomHeight/2);
            windowPane.setPrefWidth(roomWidth/10);
            backgroundDim = roomWidth/10;
        }
        else {
            windowPane.setPrefWidth(roomWidth/2);
            windowPane.setPrefHeight(roomHeight/10);
            backgroundDim = roomHeight/10;
        }

        URL url = App.class.getResource("/org/img/wood.png");

        BackgroundImage bi = new BackgroundImage(new Image(url.toString() ,backgroundDim, backgroundDim,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        windowPane.setBackground(new Background(bi));

        Pane window = new Pane();
        Pane blocker = new Pane();

        if(!opened) {
            BackgroundFill bf = new BackgroundFill(Color.LIGHTBLUE, null, null);
            window.setBackground(new Background(bf));
        }

        int windowBackgroundDim = 0;

        if(side.equals("left") || side.equals("right")) {
            window.setPrefHeight(roomHeight/2);
            window.setPrefWidth(roomWidth/20);
            blocker.setPrefHeight(roomHeight/2);
            blocker.setPrefWidth(roomWidth/20);
            windowBackgroundDim = roomWidth/20;
        }
        else {
            window.setPrefWidth(roomWidth/2);
            window.setPrefHeight(roomHeight/20);
            blocker.setPrefWidth(roomWidth/2);
            blocker.setPrefHeight(roomHeight/20);
            windowBackgroundDim = roomHeight/20;
        }

        if(blocked) {
            URL urlBlock = App.class.getResource("/org/img/obsidian.png");
            BackgroundImage blockBI = new BackgroundImage(new Image(urlBlock.toString() ,windowBackgroundDim, windowBackgroundDim,false,true),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            blocker.setBackground(new Background(blockBI));
        }

        switch (side) {
            case "left":
                AnchorPane.setRightAnchor(blocker, 0.0);
                AnchorPane.setLeftAnchor(window, 0.0);
                AnchorPane.setLeftAnchor(windowPane, 0.0);
                AnchorPane.setTopAnchor(windowPane, (double) (roomHeight/4));
                break;
            case "right":
                AnchorPane.setLeftAnchor(blocker, 0.0);
                AnchorPane.setRightAnchor(window, 0.0);
                AnchorPane.setRightAnchor(windowPane, 0.0);
                AnchorPane.setTopAnchor(windowPane, (double) (roomHeight/4));
                break;
            case "top":
                AnchorPane.setBottomAnchor(blocker, 0.0);
                AnchorPane.setTopAnchor(window, 0.0);
                AnchorPane.setTopAnchor(windowPane, 0.0);
                AnchorPane.setLeftAnchor(windowPane, (double) (roomWidth/4));
                break;
            case "bot":
                AnchorPane.setTopAnchor(blocker, 0.0);
                AnchorPane.setBottomAnchor(window, 0.0);
                AnchorPane.setBottomAnchor(windowPane, 0.0);
                AnchorPane.setLeftAnchor(windowPane, (double) (roomWidth/4));
                break;
        }

        windowPane.getChildren().addAll(window, blocker);
        return windowPane;
    }

    public AnchorPane drawDoor(String side, boolean blocked, boolean opened) {
        AnchorPane doorPane = new AnchorPane();
        int backgroundDim = 0;
        if(side.equals("left") || side.equals("right")) {
            doorPane.setPrefHeight(roomHeight/4);
            doorPane.setPrefWidth(roomWidth/10);
            backgroundDim = roomWidth/10;
        }
        else {
            doorPane.setPrefWidth(roomWidth/4);
            doorPane.setPrefHeight(roomHeight/10);
            backgroundDim = roomHeight/10;
        }

        URL url = App.class.getResource("/org/img/wood.png");
        if(blocked)
            url = App.class.getResource("/org/img/obsidian.png");

        BackgroundImage bi = new BackgroundImage(new Image(url.toString() ,backgroundDim, backgroundDim,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        doorPane.setBackground(new Background(bi));

        Pane door = new Pane();

        if(!opened) {
            BackgroundFill bf = new BackgroundFill(Color.BROWN, null, null);
            door.setBackground(new Background(bf));
        }


        if(side.equals("left") || side.equals("right")) {
            door.setPrefHeight(roomHeight/4);
            door.setPrefWidth(roomWidth/20);
        }
        else {
            door.setPrefWidth(roomWidth/4);
            door.setPrefHeight(roomHeight/20);
        }

        switch (side) {
            case "left":
                AnchorPane.setLeftAnchor(door, 0.0);
                AnchorPane.setLeftAnchor(doorPane, 0.0);
                AnchorPane.setTopAnchor(doorPane, (double) (roomHeight/4 + roomHeight/8));
                break;
            case "right":
                AnchorPane.setRightAnchor(door, 0.0);
                AnchorPane.setRightAnchor(doorPane, 0.0);
                AnchorPane.setTopAnchor(doorPane, (double) (roomHeight/4 + roomHeight/8));
                break;
            case "top":
                AnchorPane.setTopAnchor(door, 0.0);
                AnchorPane.setTopAnchor(doorPane, 0.0);
                AnchorPane.setLeftAnchor(doorPane, (double) (roomWidth/4 + roomWidth/8));
                break;
            case "bot":
                AnchorPane.setBottomAnchor(door, 0.0);
                AnchorPane.setBottomAnchor(doorPane, 0.0);
                AnchorPane.setLeftAnchor(doorPane, (double) (roomWidth/4 + roomWidth/8));
                break;
        }

        doorPane.getChildren().addAll(door);
        return doorPane;
    }

    public AnchorPane drawWall(Wall wall, String side) {
        AnchorPane wallPane = new AnchorPane();
        int backgroundDim = 0;
        if(side.equals("left") || side.equals("right")) {
            wallPane.setPrefWidth(roomWidth/10);
            wallPane.setPrefHeight(roomHeight);
            backgroundDim = roomWidth/10;
        }
        else {
            wallPane.setPrefWidth(roomWidth);
            wallPane.setPrefHeight(roomHeight/10);
            backgroundDim = roomHeight/10;
        }

        switch (side) {
            case "left":
                AnchorPane.setLeftAnchor(wallPane, 0.0);
                break;
            case "top":
                AnchorPane.setTopAnchor(wallPane, 0.0);
                break;
            case "right":
                AnchorPane.setRightAnchor(wallPane, 0.0);
                break;
            case "bot":
                AnchorPane.setBottomAnchor(wallPane, 0.0);
                break;
        }

        if(wall.type.equals("empty")) return wallPane;

        if(wall.type.equals("window")) wallPane.getChildren().add(drawWindow(side, wall.blocked, wall.open));
        if(wall.type.equals("door")) wallPane.getChildren().add(drawDoor(side, wall.blocked, wall.open));

        URL url = App.class.getResource("/org/img/brick.png");

        BackgroundImage myBI= new BackgroundImage(new Image(url.toString() ,backgroundDim,backgroundDim,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        wallPane.setBackground(new Background(myBI));

        return wallPane;
    }

    public void drawRoomWalls(AnchorPane roomPane, Room room) {
        for(int i = 0; i < room.walls.length; i++) {
            String side = Room.wallSideMapper(i);
            AnchorPane wallPane = drawWall(room.walls[i], side);
            roomPane.getChildren().add(wallPane);
        }
    }

    public void drawInformationBox(AnchorPane roomPane, Room room, ArrayList<String> allUsersInRoom) {
        Pane info = new Pane();
        info.setPrefHeight(roomHeight-(2 * roomHeight/10));
        info.setPrefWidth(roomWidth-(2 * roomWidth/10));
        AnchorPane.setTopAnchor(info, (double) roomHeight/10);
        AnchorPane.setLeftAnchor(info, (double) roomWidth/10);

        StackPane infoPane = new StackPane();
        VBox infoBox = new VBox();
        infoPane.setPrefHeight(roomHeight-(2 * roomHeight/10));
        infoPane.setPrefWidth(roomWidth-(2 * roomWidth/10));

        Text roomName = new Text();
        StackPane nameBox = new StackPane();
        roomName.setText(room.name);
        roomName.setFill(Color.BLACK);
        roomName.setStyle("-fx-font: bold 17 arial;");
        nameBox.getChildren().add(roomName);
        nameBox.setAlignment(roomName, Pos.CENTER);


        Text roomUsers = new Text();
        StackPane userBox = new StackPane();
        String users = "";
        for(String s : allUsersInRoom) {
            users += s + "\n";
        }
        roomUsers.setText(users);
        roomUsers.setFill(Color.BLACK);
        roomUsers.setStyle("-fx-font: 15 arial");
        userBox.getChildren().add(roomUsers);
        userBox.setAlignment(roomName, Pos.CENTER);

        infoBox.getChildren().addAll(nameBox, userBox);

        infoPane.getChildren().addAll(infoBox);
        infoPane.setAlignment(infoBox, Pos.CENTER);

        info.getChildren().add(infoPane);
        roomPane.getChildren().add(info);
    }

    /**
     * Generate a pane from a room
     * @param room
     * @param x
     * @param y
     * @return a pane that is the room
     */
    public AnchorPane getRoomPane(Room room, int x, int y) {
        AnchorPane roomPane = new AnchorPane();
        roomPane.setPrefWidth(roomWidth);
        roomPane.setPrefHeight(roomHeight);
        Text roomName = new Text();
        roomName.setLayoutX(roomWidth/10);
        roomName.setLayoutY(roomHeight/10);

        drawRoomWalls(roomPane, room);

        Pane lightPane = new Pane();
        lightPane.setPrefWidth(roomWidth);
        lightPane.setPrefHeight(roomHeight);
        if(!room.name.equals("outside") && !room.lightOn) {
            Color c = Color.rgb(0, 0, 0, 0.7);
            BackgroundFill bf = new BackgroundFill(c, null, null);
            lightPane.setBackground(new Background(bf));
        }

        ArrayList<String> allUserInRoom = userService.userInLocation(x,y);
        drawInformationBox(roomPane, room, allUserInRoom);
        updateBackgroundImage(roomPane, room.name);

        roomPane.getChildren().add(lightPane);

        return roomPane;
    }
}
