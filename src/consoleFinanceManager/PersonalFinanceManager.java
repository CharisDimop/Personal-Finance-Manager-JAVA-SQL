package consoleFinanceManager;

import java.time.LocalDate;
import java.util.ArrayList;

//*******************************   SERVICE CLASS   ********************************

public class PersonalFinanceManager {
	
	//******************************  Transactions   ************************************
	
	// create a new transaction from the user
	
	public TransactionDAO transactionDAO = new TransactionDAO();
	
	
	//general method i ll use later for createEXPENSE and createINCOME
	
	public Transaction createTransaction(int userID, TransactionType type, double amount, int categoryID, String description) {
		 if (amount <= 0) {
		        throw new IllegalArgumentException("The amount in a transaction must be positive!");
		    }
		
		//id = 1 
		Transaction newTransaction = new Transaction (userID,type,amount,categoryID,description);
		
		return transactionDAO.addTransaction(newTransaction); //id given by the database
		
	}
	
	
	//create EXPENSE
	public void createExpense(int userID, double amount, int categoryID, String description) {
		
		createTransaction(userID, TransactionType.EXPENSE, amount, categoryID, description);
	}
	
	//create INCOME
	public void createIncome(int userID, double amount, int categoryID, String description) {
		
		createTransaction(userID, TransactionType.INCOME, amount, categoryID, description);
	}
	
	
	//***************************  FIND TRANSACTIONS METHODS  ***************************************
	
	//methods that return ALL the transactions the user made
	
	//find transactions by userID
	public ArrayList <Transaction> findALLTransactionsByUserID( int userID) {
		
		return transactionDAO.getTransactionsByUserID(userID);
	}
	
	
	//find expenses by userID
	public ArrayList<Transaction> findALLExpensesByUserID(int userID) {
		return transactionDAO.getExpensesByUserId(userID);
	}
	
	
	//find income by userID
	public ArrayList<Transaction> findALLIncomeByUserID(int userID) {
		return transactionDAO.getIncomeByUserId(userID);
	}
	
	//methods that return the transactions the user made for a specific period of time
	
	public ArrayList<Transaction> findTransactionsByDateRange(int userId, LocalDate startDate, LocalDate endDate) {
	    
		
		if (startDate.isAfter(endDate)) {
	        throw new IllegalArgumentException("Start date cannot be after end date.");
	    }
		
		return transactionDAO.findTransactionsByUserIdAndDateRange(userId, startDate, endDate);
	}
	
	 //*************************** UserAccounts *********************************************
	
		public UserAccountDAO  userAccountDAO = new UserAccountDAO();
		
		// create UserAccount
		
		public UserAccount createUserAccount (String username , String password) {
			
			if (username == null || username.trim().isEmpty()) {
	            throw new IllegalArgumentException("Username cannot be empty.");
	        }
			if (password == null || password.trim().isEmpty()) {
	            throw new IllegalArgumentException("Password cannot be empty.");
	        }
			
			UserAccount newUserAccount = new UserAccount(username, password); //id =-1
			
			return userAccountDAO.addAccount(newUserAccount); //id given by the database
			
		}
	
	
	//******************************  User SIGN UP & LOGIN  ****************************************
	
	//user Sign up
	
	//checks if username already exists ----> then create new UserAccount
	public void signUp(String username, String password) {
		
	    if (userAccountDAO.findByUsername(username) != null) {
	    	
	    	throw new IllegalArgumentException("Username already exists");
	    }
	    
	    createUserAccount(username, password);
	    System.out.println("Your account creation has completed successfully!");
	    System.out.println("You are ready to login to your account.");
	}
	
	// user login
	
	public UserAccount login(String username, String password) {
	    
		//find user by his username
	    UserAccount user = userAccountDAO.findByUsername(username);

	    //if user exists and password is correct then:
	    
	    if (user != null  &&  user.getPassword().equals(password)) {
	    	
	    	System.out.println("Welcome "+ user.getUsername()+"!");
	    	System.out.println("You are logged in your account!");
	        return user;
	        
	    } else {//failed login	
	    	
	    	throw new IllegalArgumentException("Error: Invalid username or password.");
	    }
	}
	
	
	//********************************  Categories  ******************************************
	
	//Category creation
	
	public CategoryDAO categoryDAO = new CategoryDAO();
	
	//general class for category creation (EXPENSE and INCOME)
	public Category createCategory (String name, TransactionType type) {
		
		if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty.");
        }
		
		Category newCategory = new Category(name,type); //id=-1
		
		return categoryDAO.addCategory(newCategory); //id given by the database
	
	}
	
	
	
	// MAYBE IT NEEDS CHANGES................!!!!!!!!!!!

	// *************************** Budgets *********************************************

	public BudgetDAO budgetDAO = new BudgetDAO(); 

	// set budget by the user
	public Budget setMonthlyBudget(int userId, int categoryId, double limitAmount) {
		
	    if (limitAmount < 0) {
	        throw new IllegalArgumentException("Budget limit cannot be negative.");
	    }

	    // first i ll check if a budget already exists so i ll just change the limit amount
	    Budget currentBudget = budgetDAO.findBudgetByUserAndCategory(userId, categoryId);

	    if (currentBudget != null) {
	        // change limit amount
	        currentBudget.setLimitAmount(limitAmount);
	        System.out.println("Budget updated for category: " + categoryDAO.findCategoryNameByID(categoryId));
	        return currentBudget;
	    } else {
	        // i ll create a new budget object
	        Budget newBudget = new Budget(userId, categoryId, limitAmount);
	        System.out.println("New budget created for category: " + categoryDAO.findCategoryNameByID(categoryId));
	        return budgetDAO.addBudget(newBudget);
	    }
	}
	
	
	//check ALL budgets for the current month
	public void checkCurrentMonthBudgetStatus(int userId) {
		
		 int currentMonth = LocalDate.now().getMonthValue();
		 int currentYear = LocalDate.now().getYear();
		
	    System.out.println("****** BUDGET STATUS REPORT FOR USER " + userId + " (" + currentMonth +" /" + currentYear + ") ****");
	    
	    // find all budgets of the user
	    ArrayList<Budget> userBudgets = budgetDAO.findBudgetsByUserId(userId);
	    
	    if (userBudgets.isEmpty()) {
	        System.out.println("No budgets have been set for user:" + userAccountDAO.findbyID(userId));
	    }

	    for (Budget budget : userBudgets) {
	    	
	    	double spent;     //sum amount of all expenses in category
			double remaining; // if (limitAmount - spent) > 0
			double exceeded;  // if (limitAmount - spent) < 0
			
	        //get the sum of expenses in a category for the user
	        spent = transactionDAO.sumExpensesByUserIdAndCategoryForMonth(userId, budget.getCatID(), currentMonth, currentYear);
	        
	        Category category = categoryDAO.findCategoryByID(budget.getCatID());
	        
	        String categoryName;
	        if (category != null) {
	            categoryName = category.getName();
	        } else {
	        	throw new IllegalArgumentException("Error: Unknown category!");
	        }
	        
	        remaining = budget.getLimitAmount() - spent;

	        System.out.println("Category: " + categoryName);
	        System.out.println("Monthly Limit: " + budget.getLimitAmount() + " euros");
	        System.out.println("Spent this month: " + spent + " euros");
	        
	        //if still on budget
	        if (remaining >= 0) {
	        	
	            System.out.println("Remaining: " + remaining + " euros");
	            
	        } else { //if we exceeded the monthly budget
	        	
	        	exceeded=-remaining;
	            System.out.println("WARNING: You spended " + exceeded + " euros over the budget!");
	        }
	        System.out.println("***********************************");
	    }
	}
	
	

}
