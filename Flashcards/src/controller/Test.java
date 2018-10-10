package controller;

import model.User;

public class Test 
{
	public static void main(String[] args) {
		
		User u=new User("test");
		u.setPassword("e2fc714c4727ee9395f324cd2e7f331f");
		u.save();
		u.delete();
	}
	
}
