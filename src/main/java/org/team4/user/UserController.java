package org.team4.user;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.team4.App;
import org.team4.DashboardController;
import org.team4.common.Coordinate;
import org.team4.common.Settings;
import org.team4.shsParameters.ShsParameterController;

import java.util.ArrayList;

public class UserController {

    private UserService userService;

    @FXML
    public ShsParameterController shsParameterController;

    @FXML
    //Table variables
    public TableColumn<User, String>  nameColumn;
    public TableColumn<User, String> statusColumn;
    public TableColumn<User, Integer> ageColumn;
    public TableColumn<Coordinate, Integer> xColumn;
    public TableColumn<Coordinate, Integer> yColumn;
    public TableView userTable;

    //Add user variables
    public TextField nameAddField;
    public ChoiceBox<String> statusAddField;
    public TextField ageAddField;
    public Button addButton;
    public Text nameAddText;
    public Text ageAddText;
    public Text addErrorText;

    //Edit user variables
    public ChoiceBox<String> usersEditField;
    public ChoiceBox<String> statusEditField;
    public TextField ageEditField;
    public Text editErrorText;
    public Text ageEditText;
    public Button editButton;
    public Button deleteButton;

    //Select current user variables
    public ChoiceBox<String> selectUserField;
    public Button selectButton;

    //Move user variables
    public Text invalidX;
    public Text invalidY;
    public TextField xCoordinate;
    public TextField yCoordinate;

    public UserController() {
        userService = new UserService();
    }

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        xColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        yColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        editButton.setDisable(true);
        deleteButton.setDisable(true);
        selectButton.setDisable(true);
        clearInputField();
        displayAllUsers();
        setStatusValues();
        resetErrorMessages();

        DashboardController dashboardController = App.fxmlLoader.getController();
        dashboardController.setCurrentUserDashboard();
        dashboardController.drawHouseLayout();
    }

    public void clearInputField() {
        nameAddField.setText("");
        ageAddField.setText("");
        ageEditField.setText("");
        invalidX.setText("");
        invalidY.setText("");
        xCoordinate.setText("");
        yCoordinate.setText("");
    }

    public void resetErrorMessages() {
        nameAddText.setText("");
        ageAddText.setText("");
        addErrorText.setText("");
        editErrorText.setText("");
        ageEditText.setText("");
        invalidX.setText("");
        invalidY.setText("");
    }

    public void setStatusValues() {
        statusEditField.getItems().clear();
        statusAddField.getItems().clear();
        statusAddField.getItems().addAll("family", "guest", "stranger");
        statusEditField.getItems().addAll("family", "guest", "stranger");
        statusAddField.setValue("family");
        statusEditField.setValue(null);
    }

    public void displayAllUsers() {
        ArrayList<User> allUsers = userService.getAllUsersList();
        userTable.getItems().clear();
        usersEditField.getItems().clear();
        selectUserField.getItems().clear();
        for(User u : allUsers) {
            userTable.getItems().add(u);
            usersEditField.getItems().add(u.name);
            selectUserField.getItems().add(u.name);
        }
        selectUserField.setValue(Settings.currentUser);
    }

    public void handleDisplayUserEdit() {
        String name = usersEditField.getValue();
        User user = userService.getSingleUser(name);
        if(user != null) {
            statusEditField.setValue(user.status);
            ageEditField.setText(Integer.toString(user.age));
            xCoordinate.setText(Integer.toString(user.getX()));
            yCoordinate.setText(Integer.toString(user.getY()));
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }
    }

    public void handleEditUser() {
        resetErrorMessages();
        String name = usersEditField.getValue();
        String status = statusEditField.getValue();
        String age = ageEditField.getText();
        String xCoord = xCoordinate.getText();
        String yCoord = yCoordinate.getText();
        String[] coordValid = userService.validateCoordinate(xCoord, yCoord);
        String ageValid = userService.validateAge(age);

        System.out.println(coordValid[0]);
        System.out.println(coordValid[1]);

        boolean valid = true;
        if(ageValid != null) {
            ageEditText.setText(ageValid);
            ageEditText.setFill(Color.RED);
            valid = false;
        }
        if(coordValid[0] != null) {
            invalidX.setText(coordValid[0]);
            invalidX.setFill(Color.RED);
            valid = false;
        }
        if(coordValid[1] != null) {
            invalidY.setText(coordValid[1]);
            invalidY.setFill(Color.RED);
            valid = false;
        }
        if(!valid) return;

        int intAge = Integer.parseInt(age);
        int intX = Integer.parseInt(xCoord);
        int intY = Integer.parseInt(yCoord);
        boolean success = userService.editUser(name, status, intAge, intX, intY);
        if(!success) {
            editErrorText.setText("Failed");
            editErrorText.setFill(Color.RED);
            return;
        }
        initialize();
    }

    public void handleDeleteUser() {
        resetErrorMessages();
        String name = usersEditField.getValue();
        boolean success = userService.deleteSingleUser(name);
        if(!success) {
            editErrorText.setText("Failed");
            editErrorText.setFill(Color.RED);
        }
        initialize();
    }

    public void handleAddNewUser() {
        resetErrorMessages();
        String name = nameAddField.getText();
        String status = statusAddField.getValue();
        String age = ageAddField.getText();

        String nameValid = userService.validateName(name, true);
        if(nameValid != null) {
            nameAddText.setText(nameValid);
            nameAddText.setFill(Color.RED);
        }

        String ageValid = userService.validateAge(age);
        if(ageValid != null) {
            ageAddText.setText(ageValid);
            ageAddText.setFill(Color.RED);
        }

        if(ageValid != null || nameValid != null) return;

        int intAge = Integer.parseInt(age);
        boolean success = userService.addUser(name, status, intAge);

        if(!success) {
            addErrorText.setText("Failed");
            addErrorText.setFill(Color.RED);
            return;
        }

        initialize();
    }

    public void handleCurrentButton() {
        String username = selectUserField.getValue();
        if(username == null || username.equals(Settings.currentUser))
            return;
        selectButton.setDisable(false);
    }

    public void handleSelectCurrentUser() {
        String username = selectUserField.getValue();
        Settings.currentUser = username;
        initialize();
    }

    public void initializeShsParametersSimStart() {
        shsParameterController.initialize();
        shsParameterController.windowAndDoorChoiceBoxInit();
    }
}
