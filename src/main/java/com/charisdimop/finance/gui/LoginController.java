package com.charisdimop.finance.gui;

import com.charisdimop.finance.model.UserAccount;
import com.charisdimop.finance.service.PersonalFinanceManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	
	// i ll create an instance of my service class
    private PersonalFinanceManager financeManager = new PersonalFinanceManager();

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

        // Call my service class (PersonalFinanceManager)
        UserAccount loggedInUser = financeManager.login(username, password);

        // check results
        if (loggedInUser != null) {
            // ΕΠΙΤΥΧΙΑ!
            errorLabel.setText("Login Successful! Welcome, " + loggedInUser.getUsername());
            // Εδώ στο μέλλον, θα ανοίξεις το κυρίως παράθυρο της εφαρμογής
            // π.χ. loadMainDashboard(loggedInUser);
        } else {
            // ΑΠΟΤΥΧΙΑ!
            errorLabel.setText("Invalid username or password.");
        }
    }
	
    // Εδώ στο μέλλον θα προσθέσεις μια μέθοδο για το link "Sign Up"
    // @FXML
    // private void handleSignUpLinkAction() {
    //    // ... κώδικας για να ανοίξει το παράθυρο εγγραφής ...
    // }
	
}
	
	