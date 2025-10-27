package com.charisdimop.finance.dao;

import java.util.ArrayList;

import com.charisdimop.finance.model.UserAccount;

public class UserAccountDAO {
	
	private int nextID = 1;
	
	private ArrayList <UserAccount> userAccountList = new ArrayList <UserAccount>();
	
	public UserAccount addAccount(UserAccount anAccount) {
		
		if(anAccount.getID()== -1) {
			anAccount.setID(nextID);
			nextID++;
		}
		
		userAccountList.add(anAccount); 
		
		return anAccount;
	}
	
	public ArrayList<UserAccount> getAccountList() {
        return userAccountList;
    }
	
	//find user by his id
	public UserAccount findbyID(int id) {
		for (UserAccount i : userAccountList) {
			if(i.getID()==id) {
				return i;
			}
		}
		
		return null;
	}
	
	// find account by the username (used for login and sign up)
	public UserAccount findByUsername(String username) {
	    for (UserAccount user : userAccountList) {
	    	//for better user experience i decided to be case insensitive for usernames
	        if (user.getUsername().equalsIgnoreCase(username)) {
	            return user;
	        }
	    }
	    return null; // if username is not found
	}

}
