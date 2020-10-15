package user;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserController {

    public UserService userService;
    public UserController() throws IOException {
        userService = new UserService();
    }

    @FXML
    public Button doneButton;
    public Button createButton;
    public Button selectButton;
    public Button editButton;
    public Button deleteButton;
    public Button searchButton;
    public ChoiceBox<String> statusChoiceBox;
    public ChoiceBox<String> userChoiceBox;
    public ChoiceBox<String> editUserChoiceBox;
    public TextField nameField;
    public TextField ageField;
    public TextField nameField1;
    public TextField ageField1;
    public ChoiceBox<String> statusChoiceBox1;
    public Text invalidText;
    public Text selectMessage;
    public Text editMessage;

    @FXML
    public void initialize() throws IOException {
        statusChoiceBoxInit();
        currentChoiceBoxInit();
        initEditSection();
    }

    public void resetText() {
        selectMessage.setText("");
        editMessage.setText("");
        invalidText.setText("");
    }

    public void initEditSection() {
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        editUserChoiceBox.setValue(null);
        nameField1.setText("");
        ageField1.setText("");
        statusChoiceBox1.setValue(null);
    }

    public void statusChoiceBoxInit() {
        statusChoiceBox1.setItems(FXCollections.observableArrayList(
                "family",
                "guest",
                "stranger"
        ));
        statusChoiceBox.setItems(FXCollections.observableArrayList(
                "family",
                "guest",
                "stranger"
        ));
        statusChoiceBox.getSelectionModel().select(0);
    }

    public void currentChoiceBoxInit() throws IOException {
        ArrayList<String> allUser = userService.getAllUsers();

        if(allUser.isEmpty()) return;

        userChoiceBox.setItems(FXCollections.observableArrayList(allUser));
        editUserChoiceBox.setItems(FXCollections.observableArrayList(allUser));
        userChoiceBox.getSelectionModel().select(0);
    }

    public void closeUserOptions() {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }

    public void displayMessage(String msg, boolean good) {
        invalidText.setFill(good ? Color.GREEN : Color.RED);
        invalidText.setText(msg);
    }

    public void selectUserAction(ActionEvent event) {
        resetText();
        if(userChoiceBox.getValue() == null) return;
        UserService.currentUser = userChoiceBox.getValue();
        selectMessage.setFill(Color.GREEN);
        selectMessage.setText("User successfully selected");
    }

    public void searchUserAction(ActionEvent event) throws IOException {
        resetText();
        if(editUserChoiceBox.getValue() == null) return;
        UserModel chosenUser = userService.getUser(editUserChoiceBox.getValue());
        nameField1.setText(chosenUser.getName());
        String status = chosenUser.getStatus();
        statusChoiceBox1.setValue(status);
        ageField1.setText(Integer.toString(chosenUser.getAge()));
        deleteButton.setDisable(false);
        editButton.setDisable(false);
    }

    public void displayEditMessage(String msg, boolean good) {
        editMessage.setFill(good ? Color.GREEN : Color.RED);
        editMessage.setText(msg);
    }

    public void editUserAction(ActionEvent event) throws  IOException {
        resetText();
        String name = nameField1.getText();
        String age = ageField1.getText();
        String status = statusChoiceBox1.getValue();
        String msg = userService.editUser(name, status, age);
        initialize();
        if(msg != null) displayEditMessage(msg, false);
        else displayEditMessage("Successfully edited user", true);
    }

    public void deleteUserAction(ActionEvent event) throws  IOException {
        resetText();
        String name = nameField1.getText();
        String msg = userService.deleteUser(name);
        initialize();
        if(msg != null) displayEditMessage(msg, false);
        else displayEditMessage("Successfully deleted user", true);
    }

    public void createNewUserAction(ActionEvent event) throws IOException {
        resetText();
        String name = nameField.getText();
        String age = ageField.getText();
        String status = statusChoiceBox.getValue();

        String message = userService.createNewUser(name, status, age);
        if (message == null) {
            displayMessage("Successfully added user", true);
            nameField.setText("");
            ageField.setText("");
            statusChoiceBox.getSelectionModel().select(0);
            initialize();
        } else {
            displayMessage(message, false);
        }

    }
}
