package com.charisdimop.finance.gui;

import com.charisdimop.finance.service.PersonalFinanceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Button signUpButton;
    @FXML private Hyperlink loginLink;
    @FXML private Label errorLabel;

    // i use the instance of my service class
 	private PersonalFinanceManager financeManager = PersonalFinanceManager.instance;

    @FXML
    public void initialize() {
        errorLabel.setText(""); // clear error text
    }

    @FXML
    private void handleSignUpButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // check if fields are empty
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        // check if passwords match
        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        // try to sign up with the manager object
        try {
            financeManager.signUp(username, password);
            
            // success
            errorLabel.setText("Account created successfully! Please log in.");
            
            //close window
            handleLoginLinkAction(); 

        } catch (IllegalArgumentException e) {
            // fail (e.g. username already exists)
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void handleLoginLinkAction() {
        
        Stage stage = (Stage) loginLink.getScene().getWindow();
        stage.close();
        
        // Στο μέλλον, εδώ θα ξανα-ανοίγουμε το LoginView
    }
}
