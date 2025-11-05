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
        errorLabel.setText(""); // Καθαρίζει το μήνυμα λάθους
    }

    @FXML
    private void handleSignUpButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // 1. Έλεγχος αν τα πεδία είναι κενά
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        // 2. Έλεγχος αν οι κωδικοί ταιριάζουν
        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        // 3. Προσπάθεια εγγραφής καλώντας τον Manager!
        try {
            financeManager.signUp(username, password);
            
            // Επιτυχία!
            errorLabel.setText("Account created successfully! Please log in.");
            
            // Εδώ θα μπορούσαμε να κλείσουμε αυτόματα το παράθυρο
            // handleLoginLinkAction(); 

        } catch (IllegalArgumentException e) {
            // Αποτυχία! (π.χ. το username υπάρχει ήδη)
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void handleLoginLinkAction() {
        // Αυτός είναι ο κώδικας για να κλείσεις το τρέχον παράθυρο
        Stage stage = (Stage) loginLink.getScene().getWindow();
        stage.close();
        
        // Στο μέλλον, εδώ θα ξανα-ανοίγουμε το LoginView
    }
}
