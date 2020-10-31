package org.team4.dashboard;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.team4.common.logger.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardView {

    /**
     * Creates a spacer of width 10
     * @return
     */
    public Pane getSpacer10() {
        Pane spacer20 = new Pane();
        spacer20.setPrefWidth(10);
        spacer20.setPrefHeight(20);
        return spacer20;
    }

    /**
     * Generate a log pane
     * @param log
     * @return a pane displaying the log
     */
    public Pane generateLogPane(Log log) {
        Pane logPane = new Pane();
        logPane.setPrefHeight(20);
        logPane.setPrefWidth(1200);

        HBox hBox = new HBox();
        hBox.setPrefHeight(20);
        hBox.setPrefWidth(1200);

        Text dateText = new Text();
        dateText.setStyle("-fx-font: 17 arial;");
        Text userText = new Text();
        userText.setStyle("-fx-font: 17 arial;");;
        Text messageText = new Text();
        messageText.setStyle("-fx-font: 17 arial;");;
        Text moneyText = new Text();
        moneyText.setStyle("-fx-font: 17 arial;");;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        Date date = log.date;
        String strDate = dateFormat.format(date);
        dateText.setText("["+strDate+"]");
        dateText.setFill(Color.PURPLE);

        String user = log.user;
        userText.setText("["+(user != null ? user : "None")+"]");
        userText.setFill(Color.YELLOW);

        String message = log.message;
        Color color = Color.WHITE;
        switch (log.level) {
            case "warning":
                color = Color.rgb(255,163,26);
                break;
            case "error":
                color = Color.RED;
        }
        messageText.setText(message);
        messageText.setFill(color);

        moneyText.setText("$");
        moneyText.setFill(Color.WHITE);

        hBox.getChildren().add(getSpacer10());
        hBox.getChildren().add(dateText);
        hBox.getChildren().add(getSpacer10());
        hBox.getChildren().add(userText);
        hBox.getChildren().add(getSpacer10());
        hBox.getChildren().add(moneyText);
        hBox.getChildren().add(getSpacer10());
        hBox.getChildren().add(messageText);

        logPane.getChildren().add(hBox);
        return logPane;
    }

}
