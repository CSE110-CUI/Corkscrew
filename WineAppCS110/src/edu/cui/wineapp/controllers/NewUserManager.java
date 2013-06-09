package edu.cui.wineapp.controllers;

import android.util.Log;
import edu.cui.wineapp.NewUserActivity;
import edu.cui.wineapp.models.User;
import edu.cui.wineapp.models.data.UserDAO;

public class NewUserManager {
	private User currUser;
	
	public NewUserManager()
	{
		this.currUser = null;
	}
	
	public User createUser(String name, int age, float weight, String email,
			String sex, String country, String photo, String id, String password) {
		currUser = null;
		try{
			currUser = UserDAO.getUserDAO(NewUserActivity.newUserAct.getApplication().getApplicationContext()).createUserOnServer(name, age, weight, email, sex, country, photo, id, password);
			Log.e("NewUserManager.java/createUser","Creation Successful?");
		} catch (Exception e){
			Log.e("NewUserManager.java/createUser","Inside Catch");
			e.printStackTrace();
		}finally{return currUser;}
	}
}
