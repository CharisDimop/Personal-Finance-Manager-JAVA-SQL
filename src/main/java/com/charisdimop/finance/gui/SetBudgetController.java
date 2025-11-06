package com.charisdimop.finance.gui;

import com.charisdimop.finance.model.Budget;
import com.charisdimop.finance.model.Category;
import com.charisdimop.finance.model.TransactionType;
import com.charisdimop.finance.model.UserAccount;
import com.charisdimop.finance.service.PersonalFinanceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import java.util.ArrayList;

public class SetBudgetController {

    @FXML private ComboBox<Category> categoryComboBox;
    @FXML private TextField amountField;
    @FXML private Button saveBudgetButton;
    @FXML private Label errorLabel;
    @FXML private ListView<String> currentBudgetsListView;

    private PersonalFinanceManager financeManager = PersonalFinanceManager.instance;
    private UserAccount currentUser;
    private ObservableList<String> budgetStrings = FXCollections.observableArrayList();

    /**
     * Καλείται από τον MainDashboardController για να δώσει τον χρήστη.
     */
    public void initializeData(UserAccount user) {
        this.currentUser = user;
        currentBudgetsListView.setItems(budgetStrings);
        loadCategoryComboBox();
        loadCurrentBudgetsList();
    }

    // Φορτώνει το dropdown με τις κατηγορίες ΕΞΟΔΩΝ
    private void loadCategoryComboBox() {
        ArrayList<Category> expenseCategories = financeManager.categoryDAO.findCategoriesByType(TransactionType.EXPENSE);
        categoryComboBox.getItems().addAll(expenseCategories);

        // Αυτό διορθώνει το πρόβλημα που είχες, για να δείχνει μόνο το όνομα
        categoryComboBox.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                return (category == null) ? null : category.getName();
            }
            @Override
            public Category fromString(String string) { return null; }
        });
    }

    // Φορτώνει τη λίστα με τα budgets που έχεις ήδη ορίσει
    private void loadCurrentBudgetsList() {
        budgetStrings.clear();
        // Βεβαιώσου ότι έχεις τη μέθοδο findBudgetsByUserId στο BudgetDAO!
        ArrayList<Budget> budgets = financeManager.budgetDAO.findBudgetsByUserId(currentUser.getID());
        for (Budget budget : budgets) {
            Category cat = financeManager.categoryDAO.findCategoryByID(budget.getCatID());
            String line = String.format("%s: %.2f €", cat.getName(), budget.getLimitAmount());
            budgetStrings.add(line);
        }
    }

    @FXML
    private void handleSaveBudgetAction() {
        Category selectedCategory = categoryComboBox.getValue();
        double amount;

        if (selectedCategory == null) {
            errorLabel.setText("Please select a category.");
            return;
        }

        try {
            amount = Double.parseDouble(amountField.getText());
            if (amount < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            errorLabel.setText("Please enter a valid amount.");
            return;
        }

        try {
            // Κάλεσε τον Manager για να σώσει/αλλάξει το budget
            // Βεβαιώσου ότι έχεις τη μέθοδο setBudget στον Manager!
            financeManager.setBudget(currentUser.getID(), selectedCategory.getID(), amount);
            
            // Επιτυχία! Καθάρισε τα πεδία και ανανέωσε τη λίστα
            errorLabel.setText("");
            amountField.clear();
            categoryComboBox.setValue(null);
            loadCurrentBudgetsList(); // Φρεσκάρισμα

        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }
}