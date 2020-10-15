package org.team4.settings;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.team4.App;
import org.team4.user.User;
import org.team4.user.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;

public class SettingsController {

    private UserService userService;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public Button doneButton;
    public ChoiceBox<String> selectUserChoiceBox;
    public Text userErrorText;
    public Button editUserButton;
    public Button refreshUsers;
    public Button exitSetup;

    public SettingsController() throws IOException {
        userService = new UserService();
    }

    @FXML
    public void initialize() throws IOException {
        selectUserChoiceBoxInit();
    }

    public void closeSetupAction() {
        Stage stageSetup = (Stage) doneButton.getScene().getWindow();
        stageSetup.close();
    }

    public void selectUserChoiceBoxInit() throws IOException {
        ArrayList<String> allUser = userService.getAllUsersName();

        if(!allUser.isEmpty()) {
            selectUserChoiceBox.setItems(FXCollections.observableArrayList(allUser));
            selectUserChoiceBox.getSelectionModel().select(0);
        }

        if(selectUserChoiceBox.getValue() == null) {
            doneButton.setDisable(true);
            userErrorText.setText("A current user must be selected");
        }
        else {
            doneButton.setDisable(false);
            userErrorText.setText("");
        }
    }

    public void refreshUsersChoiceBox() throws IOException {
        selectUserChoiceBoxInit();
    }

    public void openEditUserAction() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("userOptions.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("User Options");
            Scene scene = new Scene(root1);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDashboardAction() throws IOException {
        if(selectUserChoiceBox.getValue() == null) {
            doneButton.setDisable(true);
            userErrorText.setText("A current user must be selected");
            return;
        }
        String currentUser = selectUserChoiceBox.getValue();
        SettingsService.setCurrentUser(userService.getUser(currentUser));

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("dashboard.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Dashboard");
            Scene scene = new Scene(root1);
            stage.setScene(scene);
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
            stage.show();
            closeSetupAction();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
