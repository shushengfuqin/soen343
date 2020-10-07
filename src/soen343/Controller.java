package soen343;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private Label label;
    public void showTime(ActionEvent event){
        Date date=java.util.Calendar.getInstance().getTime();
        label.setText(date.toString());
    }
    public void closeTime(ActionEvent event){
        label.setText("");
    }

    public void heat(ActionEvent event){
        Stage window = new Stage();

        window.setMinWidth(250);

        Label label = new Label();
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

    public void profile(ActionEvent event){
        Stage window = new Stage();

        window.setMinWidth(250);

        Label label = new Label();
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

    public void light(ActionEvent event){
        Stage window = new Stage();

        window.setMinWidth(250);

        Label label = new Label();
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }
}
