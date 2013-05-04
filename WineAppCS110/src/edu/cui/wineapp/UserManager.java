package edu.cui.wineapp;

import android.content.Context;

public class UserManager {
	private static Context context = null;
	private static DAO dao = DAO.getDAO(context);
	//private static UserManager ourInstance = new UserManager();
	private UserManager(Context context){
		this.context = context;
	}
	
	public static UserManager getUserManager(Context context){
		return new UserManager(context);
	}
	
	public static User getUserByName(String name){
		return dao.getUserByName(name);
	}
	
	public static boolean createUser(User user, String password){
		if(dao.getUserByName(user.getName())!=null){
			return false;
		}else{
			dao.createUser(user,password);
			return true;
		}
	}

	public static boolean deleteUser(String name){
		return dao.deleteUser(name);
	}
}
