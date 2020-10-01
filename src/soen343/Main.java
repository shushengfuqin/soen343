package soen343;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
	    //start the problem
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Home Page");

        //Create different button needs
        Button button1 = new Button();
        button1.setText("Light");
        Button button2 = new Button();
        button2.setText("Heat");

        //This class will handle the light button
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //open of close the light
                int i = 0;
                if(i == 0){
                    System.out.println("You opened the light");
                    i = 1;
                } else {
                    System.out.println("You closed the light");
                    i = 0;
                }
            }
        });

        //This class will handle the heating button
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("You opened the heating system page");
            }
        });


        StackPane layout = new StackPane();
        layout.getChildren().add(button1);
        layout.getChildren().add(button2);

        Scene scene = new Scene(layout,1000,750);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
