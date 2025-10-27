package com.charisdimop.finance.model;


public class UserAccount {
	
	private int id;
	private String username;
	private String password;
	
	//constructor i ll call from DAO
	public UserAccount (int id, String username, String password) {
		this.id=id;
		this.username=username;
		this.password=password;
	}
	
	//constructor i ll call from service class (Personal Finance Manager)
	public UserAccount (String username, String password) {
		this.id=-1;   //id=-1 , database is gonna put the correct id
		this.username=username;
		this.password=password;
	}
	
	//getters
	public int getID() {
		return this.id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
	//setters
	public void setID(int id) {
		this.id=id;
	}
	public void setUsername(String username) {
		this.username=username;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	
	
	@Override
	public String toString() {
	    return "UserAccount{id=" + id + ", username='" + username + "'}";
	}

}
