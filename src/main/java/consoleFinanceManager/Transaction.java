package consoleFinanceManager;

import java.time.LocalDate;

public class Transaction {
	
	private int id;
	private int userID;
	private TransactionType type; //income or expense
	private double amount;   // i can later make it BigDecimal
	private int categoryID;
	private String description;
	private LocalDate date;
	
	//constructor i ll call from DAO
	public Transaction (int id, int userID, TransactionType type, double amount, int categoryID, String description ) {
		this.id=id;
		this.userID= userID;
		this.type = type;
		this.amount = amount;
		this.categoryID = categoryID;
		this.description = description;
		this.date = LocalDate.now();
	} 
	
	
	
	//constructor i ll call from service class (Personal Finance Manager)
	public Transaction (int userID, TransactionType type, double amount, int categoryID, String description ) {
		this.id= -1; //id=-1 , database is gonna put the correct id
		this.userID= userID;
		this.type = type;
		this.amount = amount;
		this.categoryID = categoryID;
		this.description = description;
		this.date = LocalDate.now();
	} 
	
	
	//getters
	public int getID() {
		return this.id;
	}
	public int getUserID() {
		return userID;
	}
	
	public TransactionType getType() {
		return type; 
	} 
	
	public double getAmount() {
		return amount; 
	}
	
	public int getCategoryID() {
		return categoryID; 
	}
	
	public String getDescription() {
		return description; 
	}
	
	public LocalDate getDate() {
		return date; 
	} 
	
	
	//setter for id 
	public void setID(int id) {
		this.id=id;
	}
	
	
	@Override
	public String toString() {
	    return "Transaction{type=" + type
	         + ", amount=" + amount
	         + ", categoryID=" + categoryID
	         + ", description='" + description + "'"
	         + ", date=" + date + "}";
	}
	
	
	public void printInfo() {
		System.out.println("TransactionInfo: ( UserID="+ userID +", type=" + type + ", amount=" + amount + 
				", categoryID=" + categoryID + ", description=" + description + ", date =" + date +")"); 
	}
	
	

}
