package com.charisdimop.finance.model;

public class Budget {
	
	private int id;
	private int userID;
	private int catID;
	private double limitAmount;
	
	//constructor i ll call from DAO
	public Budget(int id , int userID, int catID, double limitAmount) {
		this.id=id;
		this.userID=userID;
		this.catID=catID;
		this.limitAmount=limitAmount;	
	}
	
	//constructor i ll call from service class (Personal Finance Manager)
	public Budget(int userID, int catID, double limitAmount) {
		this.id=-1;
		this.userID=userID;
		this.catID=catID;
		this.limitAmount=limitAmount;	
	}
	
	//getters
	public int getID() {
		return this.id;
	}
	public int getUserID() {
		return this.userID;
	}
	public int getCatID() {
		return this.catID;
	}
	public double getLimitAmount() {
		return this.limitAmount;
	}
	
	//setters for id, limitAmount
	public void setID(int id) {
		this.id=id;
	}
	public void setLimitAmount(double limitAmount) {
	    this.limitAmount = limitAmount;
	}
	
	@Override
	public String toString() {
	    return "Category{id=" + id + ", userID" + userID +", catID"+catID+", limitAmount"+limitAmount+ "}";
	}

}
