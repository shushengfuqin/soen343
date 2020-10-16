package org.team4;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class PrimaryController {

    @FXML
    private Label label;
    public Button closeButton;

    //set the time and date
    public void clock(ActionEvent event){

        Thread clock= new Thread()
        {
            public void run()
            {
                try {
                    for(;;) {
                        Calendar cal = new GregorianCalendar();
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH);
                        int year = cal.get(Calendar.YEAR);

                        int second = cal.get(Calendar.SECOND);
                        int minute = cal.get(Calendar.MINUTE);
                        int hour = cal.get(Calendar.HOUR);

                        label.setText("Time  " + hour + " : " + minute + " : " + second + "  Date  " + year + "/" + month + "/" + day);
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        clock.start();

    }

    public void showTime(ActionEvent event){
        Date date=java.util.Calendar.getInstance().getTime();
        label.setText(date.toString());
    }
    public void closeTime(ActionEvent event){
        label.setText("");
    }

    public void closeButtonAction(ActionEvent event){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
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
