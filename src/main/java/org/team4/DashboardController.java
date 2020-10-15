package org.team4;

import java.io.IOException;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.team4.settings.SettingsService;
import org.team4.user.User;

public class DashboardController {

    @FXML
    private Label label;
    public Button closeButton;
    public Button refreshUserButton;
    public Text nameText;
    public Text statusText;
    public Text ageText;

    @FXML
    public void initialize() {
        currentUserBoxInit();
    }

    public void currentUserBoxInit() {
        User curr = SettingsService.getCurrentUser();
        nameText.setText(curr.getName());
        statusText.setText(curr.getStatus());
        ageText.setText(Integer.toString(curr.getAge()));
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

    private void openPopUp(String name) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(name+".fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle(name);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openUserOptions(ActionEvent event){
        this.openPopUp("userOptions");
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
