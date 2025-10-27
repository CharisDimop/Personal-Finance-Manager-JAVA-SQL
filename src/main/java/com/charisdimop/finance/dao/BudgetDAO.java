package com.charisdimop.finance.dao;

import java.util.ArrayList;

import com.charisdimop.finance.model.Budget;

public class BudgetDAO {
	
	private int nextID = 1;
	
	private ArrayList <Budget> budgetList = new ArrayList <Budget>();
	
	public Budget addBudget(Budget aBudget) {
		
		if(aBudget.getID()== -1) {
			aBudget.setID(nextID);
			nextID++;
		}
		
		budgetList.add(aBudget); 
		
		return aBudget;
	}
	
	public ArrayList<Budget> getBudgetList() {
        return budgetList;
    }
	
	
	//find budget by its id
	public Budget findbyID(int id) {
		for (Budget i : budgetList) {
			if(i.getID()==id) {
				return i;
			}
		}
			
		return null;	
	}
	
	//find budget for a certain category
	public Budget findBudgetByUserAndCategory(int userId, int categoryId) {
		
        for (Budget budget : budgetList) {
            if (budget.getUserID() == userId && budget.getCatID() == categoryId) {
                return budget;
            }
        }
        return null; // budget not found
    }
	
	
	// find ALL budgets of a user
    public ArrayList<Budget> findBudgetsByUserId(int userId) {
    	
        ArrayList<Budget> userBudgets = new ArrayList<>();
        
        for (Budget budget : budgetList) {        	
        	if (budget.getUserID() == userId) {
                userBudgets.add(budget);
            }
        }
        return userBudgets;
    }

}
