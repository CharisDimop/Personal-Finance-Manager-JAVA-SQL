package com.charisdimop.finance.model;

public class Category {
	
	private int id;
	private final String name;
	private final TransactionType type;
	
	//maybe i should add user too in category so i show in each user his custom categories too
	//and also use it for deletion when the user wants but also for
	
	//constructor i ll call from DAO
	public Category (int id , String name, TransactionType type) {
		this.id=id;
		this.name=name;
		this.type=type;
	}
	
	//constructor i ll call from service class (Personal Finance Manager)
	public Category (String name, TransactionType type) {
		this.id= -1;    //database is gonna put the right id
		this.name= name;
		this.type=type;
	}
	
	//setter for category ID 
	public void setID(int id) {
	        this.id = id;
	}
	
	//getters
	public int getID() {
		return id;
	} 
	public String getName() {
		return name; 
	}
	public TransactionType getType() {
		return type;
	}
	
	
	//TODO:give default categories but also the user to be able to add his own
	
	
	
	@Override
	public String toString() {
	    return "Category{id=" + id + ", name='" + name +", type=" + type + "'}";
	}
	
	
	public void printInfo() {
		System.out.println("CategoryInfo: ( id=" + id 
									  + ", name=" + name 
									  +", type=" + type+")");
		
	}
	
	

}
