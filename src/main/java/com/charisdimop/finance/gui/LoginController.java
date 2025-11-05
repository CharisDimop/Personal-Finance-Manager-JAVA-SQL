package com.charisdimop.finance.gui;

import java.io.IOException;

import com.charisdimop.finance.model.UserAccount;
import com.charisdimop.finance.service.PersonalFinanceManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginController {
	
	// i use the instance of my service class
	private PersonalFinanceManager financeManager = PersonalFinanceManager.instance;

    // I ll connect the fxml components with the Java code
    // the names (e.g. "usernameField") should match exactly with the fx:id in the FXML.
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink signUpLink;

    @FXML
    private Label errorLabel;

    /**
     * this method is called automatically when the FXML is loaded.
     * its ideal for setting initial values .
     */
    @FXML
    public void initialize() {
        // clear error text in the beginning
        errorLabel.setText("");
    }

    /**
     * this method is called when the user presses the "Login" button.
     * the name matches with the "onAction" in the FXML.
     */
    @FXML
    private void handleLoginButtonAction() {
        // Take values from the fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // check if they 're empty
        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter username and password.");
            return; // stop here
        } 
        
        try {
            // try to login
           UserAccount loggedInUser = financeManager.login(username, password);

           
           
           // open the main window
           openMainDashboard(loggedInUser);

         } catch (IllegalArgumentException e) {
           // if manager throws an error catch it
           errorLabel.setText(e.getMessage());
         }
    }
	
    @FXML
    private void handleSignUpLinkAction() {
        try {
            // load the FXML of Sign Up
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignUpView.fxml"));
            Parent root = loader.load();

            // create a new stage
            Stage signUpStage = new Stage();
            signUpStage.setTitle("Create New Account");
            signUpStage.setScene(new Scene(root, 400, 500));
            
            signUpStage.initModality(Modality.APPLICATION_MODAL); 

            signUpStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading sign up page.");
        }
    }

    private void openMainDashboard(UserAccount user) {
        try {
            // load the FXML of Dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainDashboardView.fxml"));
            Parent root = loader.load();

            // take the Dashboard controller
            MainDashboardController controller = loader.getController();
            
            // transfer the user data to the next controller
            controller.initializeUser(user);

            // create a new scene
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("My Finance Dashboard");
            dashboardStage.setScene(new Scene(root, 800, 600));

            // close login window
            Stage loginStage = (Stage) loginButton.getScene().getWindow();
            loginStage.close();

            // show Dashboard
            dashboardStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("Error loading dashboard.");
        }
    }
	
}
	
	