package com.charisdimop.finance.gui;

import com.charisdimop.finance.model.Category;
import com.charisdimop.finance.model.TransactionType;
import com.charisdimop.finance.model.UserAccount;
import com.charisdimop.finance.service.PersonalFinanceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.util.ArrayList;

public class AddTransactionController {

    @FXML private Label titleLabel;
    @FXML private TextField amountField;
    @FXML private ComboBox<Category> categoryComboBox;
    @FXML private TextField descriptionField;
    @FXML private Button saveButton;
    @FXML private Label errorLabel;

    // we take the common MANAGER
    private PersonalFinanceManager financeManager = PersonalFinanceManager.instance;

    private UserAccount currentUser;
    private TransactionType transactionType;

    
    public void initializeData(UserAccount user, TransactionType type) {
        this.currentUser = user;
        this.transactionType = type;

        // set window title
        if (type == TransactionType.INCOME) {
            titleLabel.setText("Add New Income");
        } else {
            titleLabel.setText("Add New Expense");
        }

        // LOAD COMBOBOX!
        // we take the correct default categories
        ArrayList<Category> categories = financeManager.categoryDAO.findCategoriesByType(type);
        // we add them in the dropdown
        categoryComboBox.getItems().addAll(categories);
        
        categoryComboBox.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                //if the object is not null show its name
                return (category == null) ? null : category.getName();
            }

            @Override
            public Category fromString(String string) {
                // we dont need it so we return null
                return null;
            }
        });
    }

    @FXML
    private void handleSaveButtonAction() {
        // Validation
        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            errorLabel.setText("Please enter a valid, positive amount.");
            return;
        }

        Category selectedCategory = categoryComboBox.getValue();
        if (selectedCategory == null) {
            errorLabel.setText("Please select a category.");
            return;
        }

        String description = descriptionField.getText();
        if (description == null || description.isEmpty()) {
            description = "No description"; // put a default
        }

        //  save through Manager
        try {
            financeManager.createTransaction(currentUser.getID(), transactionType, amount, selectedCategory.getID(), description);
            
            // Close the window (pop-up)
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

        } catch (Exception e) {
            errorLabel.setText("An error occurred. Please try again.");
            e.printStackTrace();
        }
    }
}