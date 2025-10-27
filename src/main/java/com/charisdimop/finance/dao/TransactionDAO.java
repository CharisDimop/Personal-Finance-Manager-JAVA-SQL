package com.charisdimop.finance.dao;

import java.time.LocalDate;
import java.util.ArrayList;

import com.charisdimop.finance.model.Transaction;
import com.charisdimop.finance.model.TransactionType;

public class TransactionDAO {
	
	private int nextID = 1;
	
	private ArrayList <Transaction> transactionList = new ArrayList <Transaction>();
	
	public Transaction addTransaction(Transaction aTransaction) {
		
		if (aTransaction.getID()==-1) {
			aTransaction.setID(nextID);
			nextID++;
		}
		
		transactionList.add(aTransaction); 
		return aTransaction;
	}
	
	public ArrayList<Transaction> getTransactionList() {
		return transactionList;
	}
	
	//*************** find ALL transactions by userID *****************************
	
	//find transactions by userID
	
	public ArrayList<Transaction> getTransactionsByUserID(int userID) {
		
		ArrayList<Transaction> userTransactions = new ArrayList<>();
		
		for (Transaction transaction : transactionList) {
			
			if (transaction.getUserID() == userID) {
				userTransactions.add(transaction);
			}
		}
		return userTransactions;
	}
	
	//find EXPENSES by userID
	public ArrayList<Transaction> getExpensesByUserId(int userId) {
		
		ArrayList<Transaction> userExpenses = new ArrayList<>();
		
		for (Transaction transaction : transactionList) {
			if (transaction.getUserID() == userId 
					&& transaction.getType() == TransactionType.EXPENSE) {
				
				userExpenses.add(transaction);
			}
		}
		return userExpenses;
	}
	
	//find INCOME by userID
	public ArrayList<Transaction> getIncomeByUserId(int userId) {
		
		ArrayList<Transaction> userIncome = new ArrayList<>();
		
		for (Transaction transaction : transactionList) {
			if (transaction.getUserID() == userId
					&& transaction.getType() == TransactionType.INCOME) {
				
				userIncome.add(transaction);
			}
		}
		return userIncome;
	}
	
	
	//************* find transactions for specific date range (for userID) ***********************
	
	public ArrayList<Transaction> findTransactionsByUserIdAndDateRange(int userId, LocalDate startDate, LocalDate endDate) {
	    
		ArrayList<Transaction> filteredTransactions = new ArrayList<>();
	    
		for (Transaction transaction : transactionList) {
	        if (transaction.getUserID() == userId) {
	        	
	            LocalDate txDate = transaction.getDate();
	            // check if transaction is after OR EQUAL to startDate
	            // and if transaction is before OR EQUAL to endDate
	            if (!txDate.isBefore(startDate) && !txDate.isAfter(endDate)) {
	                filteredTransactions.add(transaction);
	            }
	        }
	    }
	    return filteredTransactions;
	}
	
	
	//method that finds the sum of ALL EXPENSES in a CATEGORY for a specific MONTH of the year
	public double sumExpensesByUserIdAndCategoryForMonth(int userId, int categoryId, int month, int year) {
		
	    double total = 0.0;
	    //for every transaction on the list
	    for (Transaction t : transactionList) {
	        // filter for the correct userID , category and if its an EXPENSE only
	        if (t.getUserID() == userId && t.getCategoryID() == categoryId && t.getType() == TransactionType.EXPENSE) {
	            // check for the right month and year
	            if (t.getDate().getMonthValue() == month && t.getDate().getYear() == year) {
	                total += t.getAmount();
	            }
	        }
	    }
	    return total;
	}
	
	
	
	
	
	

}
