package edu.cui.wineapp;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.util.Log;

public class UserManager{
	private static Context context = null;
	private static DAO dao = null; 
	//private static UserManager ourInstance = new UserManager();
	private UserManager(Context context){
		this.context = context;
		dao=DAO.getDAO(context);
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
			Log.e("ERROR","ACCOUNT CREATED");
			dao.createUser(user,password);
			return true;
		}
	}
	public static boolean setUserEmail(String username, String newEmail){
		return dao.setUserEmail(username, newEmail);
	}
	public static boolean deleteUser(String name){
		return dao.deleteUser(name);
	}
	
	
	public void testpost(String w,String u, String c){
		try {
		dao.setComment(w, u, c);
		} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
}
