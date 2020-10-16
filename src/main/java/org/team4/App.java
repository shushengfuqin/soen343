package org.team4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
<<<<<<< HEAD
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
=======
>>>>>>> 75efca8... Add implemenation of house layout in dashboard
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
<<<<<<< HEAD
    private double xOffset = 0;
    private double yOffset = 0;
=======
>>>>>>> 75efca8... Add implemenation of house layout in dashboard
    public static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {
        fxmlLoader = new FXMLLoader(App.class.getResource("dashboard.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Dashboard");
<<<<<<< HEAD
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
=======
        stage.setScene(scene);
        stage.show();
>>>>>>> 75efca8... Add implemenation of house layout in dashboard
    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}