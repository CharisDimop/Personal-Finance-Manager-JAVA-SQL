package consoleFinanceManager;

public class Main {
	
	public static void main(String[] args) { 
		
		PersonalFinanceManager pf = new PersonalFinanceManager();
		
		
		//**************************  INITIAL TESTING  *****************************
		/*
		//test category
		 
		 Category c1 = pf.createCategory("supermarket", TransactionType.EXPENSE);
	     Category c2 = pf.createCategory("rent",TransactionType.EXPENSE);
	        
	     System.out.println("C1 : id = " + c1.getID() +	", name = " + c1.getName());
	     System.out.println("C2: id = " + c2.getID() + ", name = " + c2.getName());
	     
	     //test UserAccounts
	     
	     
	     UserAccount u1 = pf.createUserAccount("Charis", "1234");
	     UserAccount u2 = pf.createUserAccount("JohnRambo", "pass*$*word1");
		
	     System.out.println("U1 : id = " + u1.getID() +	", username = " + u1.getUsername() +", username = " + u1.getPassword());
	     System.out.println("U2: id = " + u2.getID() + ", username = " + u2.getUsername()+	", username = " + u2.getPassword());
	     
	     //test Transactions
	     
	     
	     Transaction ts1 = pf.createTransaction(u1.getID(),TransactionType.INCOME, 1350, c1.getID(), "mothly salary");
	     Transaction ts2 = pf.createTransaction(u1.getID(),TransactionType.EXPENSE, 500, c2.getID(), "mothly rent");
	     
	     System.out.println("Saved transactions for user1:");
	     
	     for (Transaction t : pf.findALLTransactionsByUserID(u1.getID())) {
	            System.out.println("  " + t);
	     }
	     */
		
		
		System.out.println("****** SIGN UP AND LOGIN ****** ");
		//sign up
        pf.signUp("Charis", "pass123");
		//login
        UserAccount user1 = pf.login("charis", "pass123");
        
        //show default categories
        System.out.println(pf.categoryDAO.findCategoriesByType(TransactionType.EXPENSE));
        System.out.println(pf.categoryDAO.findCategoriesByType(TransactionType.INCOME));
        
        Category groceriesCat = pf.categoryDAO.findCategoriesByType(TransactionType.EXPENSE).get(0);
        Category rentCat = pf.categoryDAO.findCategoriesByType(TransactionType.EXPENSE).get(2);
        Category salaryCat = pf.categoryDAO.findCategoriesByType(TransactionType.INCOME).get(0);
        
        System.out.println();
        
        System.out.println("******* Create Transactions for CURRENT Month ****");
        pf.createIncome(user1.getID(), 1400, salaryCat.getID(), "October Salary");
        pf.createExpense(user1.getID(), 50, groceriesCat.getID(), "week 1 Groceries");
        pf.createExpense(user1.getID(), 650, rentCat.getID(), "October Rent");
        pf.createExpense(user1.getID(), 70, groceriesCat.getID(), "week 2 Groceries");
        System.out.println("Transactions created!");
        
        System.out.println();
        
        System.out.println("****** Set and Check Budgets *******");
        //set monthly budget for groceries
        pf.setMonthlyBudget(user1.getID(), groceriesCat.getID(), 150);
        //set monthly budget for rent
        pf.setMonthlyBudget(user1.getID(), rentCat.getID(), 650);
        
        System.out.println();
        
		//check if we are still on budget
        pf.checkCurrentMonthBudgetStatus(user1.getID());
		
        System.out.println();
        
        System.out.println("I ll update an existing budget");
        System.out.println("I ll set grocery budget from 150 to 200 euros.");
        // grocery budget updating
        pf.setMonthlyBudget(user1.getID(), groceriesCat.getID(), 200);
        
        System.out.println();

        //i ll re-check the budget status
        pf.checkCurrentMonthBudgetStatus(user1.getID());
	     
	     
	}
	
	
	
	

}
