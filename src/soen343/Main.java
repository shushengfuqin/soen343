package soen343;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;
    Scene home, light;

    public static void main(String[] args) {
	    //start the problem
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;

        Label label1 = new Label("Home");
        Button button1 = new Button("Light");
        button1.setOnAction(e -> window.setScene(light));

        //Profile Button
        Button profile = new Button("Profile");
        profile.setOnAction(event -> Profile.diplay("Title","Good"));

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1,button1,profile);
        home = new Scene(layout1,400,400);

        //Button2
        Button button2 = new Button("Light");
        button2.setOnAction(e -> window.setScene(home));

        //Layout 2
        VBox layout2 = new VBox(20);
        Label label2 = new Label("Light");
        layout2.getChildren().addAll(label2,button2);
        light = new Scene(layout2,500,250);

        window.setScene(home);
        window.setTitle("SHS");
        window.show();
    }
}
