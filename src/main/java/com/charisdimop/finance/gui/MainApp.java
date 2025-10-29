package com.charisdimop.finance.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            //load the fxml file
            //Check for the route to be correct!!
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginView.fxml"));

            Scene scene = new Scene(root, 400, 400); // window size

            primaryStage.setTitle("Personal Finance Manager - Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // lock size
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // error handling if file not found
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}