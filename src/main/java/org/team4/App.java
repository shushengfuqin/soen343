package org.team4;

import helpers.Coordinate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws IOException {
//        launch();
        System.out.println("User test");

        Scanner key = new Scanner(System.in);
        boolean loop = true;
        while(loop) {
            System.out.println("\nHere are the options:\n1) Add new user\n2) Delete a user\n3) Modify User\n4) Get all users\n5) Get user\n6) Update user location");
            int option = key.nextInt();
            System.out.println("Option Chosen: " + option + "\n");
            switch (option) {
                case 1:
                    System.out.println("Name:" );
                    String name = key.next();
                    System.out.println("Age:" );
                    int age = key.nextInt();
                    System.out.println("Status:" );
                    String status = key.next();
                    User tempUser = new User(name, status, age);
                    User.addNewUsers(tempUser);
                    break;
                case 2:
                    System.out.println("Name:" );
                    String name2 = key.next();
                    User.deleteUser(name2);
                    break;
                case 3:
                    System.out.println("Name:" );
                    String name3 = key.next();
                    System.out.println("Age:" );
                    int age3 = key.nextInt();
                    System.out.println("Status:" );
                    String status3 = key.next();
                    System.out.println("X:" );
                    int x = key.nextInt();
                    System.out.println("Y:" );
                    int y = key.nextInt();
                    User.modifyUser(name3, status3, age3, x, y);
                    break;

                case 4:
                    ArrayList<User> allUsers = User.getAllUsers();
                    for(User u : allUsers) {
                        System.out.println(u+"\n");
                    }
                    break;
                case 5:
                    System.out.println("Name:" );
                    String name5 = key.next();
                    System.out.println(User.getUser(name5));
                    break;
                case 6:
                    System.out.println("Name:" );
                    String name6 = key.next();
                    System.out.println("X:" );
                    int x6 = key.nextInt();
                    System.out.println("Y:" );
                    int y6 = key.nextInt();
                    User.updateUserCoordinate(name6, x6, y6);
            }

        }
//        Location loc = new Location(10, 11);
//        System.out.println(loc.toJson());
    }

}