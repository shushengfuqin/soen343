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
        //
        Label label1 = new Label("Home");

        //Profile Button
        Button profile = new Button("Profile");
        profile.setOnAction(event -> Profile.display("Title","Good"));

        //Light Button
        Button light = new Button("Light");
        light.setOnAction(event ->  Light.display("Light","Light page"));

        //Layout for home page
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1,profile,light);
        home = new Scene(layout1,400,400);


        window.setScene(home);
        window.setTitle("SHS");
        window.show();
    }
}
