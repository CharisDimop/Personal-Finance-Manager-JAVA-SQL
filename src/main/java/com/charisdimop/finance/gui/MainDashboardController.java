package com.charisdimop.finance.gui;

import com.charisdimop.finance.model.BudgetStatusReport;
import com.charisdimop.finance.model.Category;
import com.charisdimop.finance.model.Transaction;
import com.charisdimop.finance.model.TransactionType;
import com.charisdimop.finance.model.UserAccount;
import com.charisdimop.finance.service.PersonalFinanceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter; 

import java.io.IOException;
import java.util.ArrayList;

public class MainDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private Button addExpenseButton;
    @FXML private Button addIncomeButton;
    @FXML private Button dashboardButton; 
    @FXML private Button logoutButton;    
    @FXML private ListView<String> transactionsListView; 
    @FXML private ListView<String> budgetStatusListView;
    @FXML private Button setBudgetsButton; 

    private UserAccount currentUser;
    private PersonalFinanceManager financeManager = PersonalFinanceManager.instance;
    
    private ObservableList<String> transactionStrings = FXCollections.observableArrayList();
    private ObservableList<String> budgetStatusStrings = FXCollections.observableArrayList();

    //called from loginController to get the user
    public void initializeUser(UserAccount user) {
        this.currentUser = user;
        welcomeLabel.setText("Welcome, " + currentUser.getUsername() + "!");
        transactionsListView.setItems(transactionStrings);
        budgetStatusListView.setItems(budgetStatusStrings);
        
        refreshDashboard();
    }
    
    //refreshes the info on the screen
    private void refreshDashboard() {
        loadTransactionList();
        loadBudgetStatus(); 
    }


	//called when user presses 'Dashboard' (just refreshes the lists)
    @FXML
    private void handleDashboardAction() {
        System.out.println("Refreshing dashboard...");
        refreshDashboard();
    }

    //called when user presses 'Add expense'
    @FXML
    private void handleAddExpenseAction() {
        openTransactionWindow(TransactionType.EXPENSE);
    }

    //called when user presses 'Add income'
    @FXML
    private void handleAddIncomeAction() {
        openTransactionWindow(TransactionType.INCOME);
    }
    
    //called when user presses 'Set Budgets'
    @FXML
    private void handleSetBudgetsAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SetBudgetView.fxml"));
            Parent root = loader.load();

            // take the controller of set budget
            SetBudgetController controller = loader.getController();
            controller.initializeData(currentUser); // give the user

            Stage popupStage = new Stage();
            popupStage.setTitle("Set Monthly Budgets");
            popupStage.setScene(new Scene(root, 400, 400));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            
            // wait for the pop-up to close
            popupStage.showAndWait();
            
            
            refreshDashboard();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

   //called when user presses 'Logout'
    @FXML
    private void handleLogoutAction() {
        try {
            //close current window
            Stage dashboardStage = (Stage) logoutButton.getScene().getWindow();
            dashboardStage.close();

            //reload login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            Parent root = loader.load();

            // create a stage and a scene for login
            Stage loginStage = new Stage();
            loginStage.setTitle("Personal Finance Manager - Login");
            loginStage.setScene(new Scene(root, 400, 400));
            loginStage.setResizable(false);
            loginStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // a general method to open the pop up window
    private void openTransactionWindow(TransactionType type) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddTransactionView.fxml"));
            Parent root = loader.load();

            AddTransactionController controller = loader.getController();
            controller.initializeData(currentUser, type);

            Stage popupStage = new Stage();
            popupStage.setTitle(type == TransactionType.INCOME ? "Add Income" : "Add Expense");
            popupStage.setScene(new Scene(root, 350, 450));
            popupStage.initModality(Modality.APPLICATION_MODAL);
            
            popupStage.showAndWait();
            
            // when the pop up closes refresh the list
            loadTransactionList();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //this method loads data from the manager and adds them in the list
    private void loadTransactionList() {
        transactionStrings.clear();
        
        ArrayList<Transaction> transactions = financeManager.findALLTransactionsByUserID(currentUser.getID());

        for (Transaction t : transactions) {
            
            Category cat = financeManager.categoryDAO.findCategoryByID(t.getCategoryID());
            String catName = (cat != null) ? cat.getName() : "N/A";
            
            String sign = (t.getType() == TransactionType.INCOME) ? "+" : "-";
            
            String line = String.format("%s %.2f €  |  %s  (%s)", 
                                  sign, t.getAmount(), catName, t.getDescription());
            
            transactionStrings.add(line);
        }
    }
    
    private void loadBudgetStatus() {
        budgetStatusStrings.clear();
        // we ask for the reports from the manager
        ArrayList<BudgetStatusReport> reports = financeManager.getBudgetStatusReports(currentUser.getID());

        if (reports.isEmpty()) {
            budgetStatusStrings.add("No budgets set. Click 'Set Budgets'.");
            return;
        }

        for (BudgetStatusReport report : reports) {
            String line = String.format("%s (%.0f%%)",
                                    report.getCategoryName(),
                                    report.getPercentageSpent());
            budgetStatusStrings.add(line);
            
            String line2 = String.format("  Spent %.2f / %.2f €",
                                    report.getAmountSpent(),
                                    report.getLimitAmount());
            budgetStatusStrings.add(line2);
            
            if (report.getAmountRemaining() < 0) {
                 budgetStatusStrings.add(String.format("  (%.2f € over budget!)", -report.getAmountRemaining()));
            }
        }
    }

}
