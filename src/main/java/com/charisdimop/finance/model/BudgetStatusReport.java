package com.charisdimop.finance.model;

public class BudgetStatusReport {
	
	private String categoryName;
    private double limitAmount;
    private double amountSpent;

    public BudgetStatusReport(String categoryName, double limitAmount, double amountSpent) {
        this.categoryName = categoryName;
        this.limitAmount = limitAmount;
        this.amountSpent = amountSpent;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public double getAmountSpent() {
        return amountSpent;
    }

    public double getAmountRemaining() {
        return limitAmount - amountSpent;
    }

    public double getPercentageSpent() {
        if (limitAmount == 0) return 0; // to avoid division by zero
        return (amountSpent / limitAmount) * 100.0;
    }

}
