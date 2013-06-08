package edu.cui.wineapp.Controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.Context;
import android.text.format.Time;
import edu.cui.wineapp.BAC;
import edu.cui.wineapp.DAOBU;
import edu.cui.wineapp.WineDAO;
import edu.cui.wineapp.Models.Comment;
import edu.cui.wineapp.Models.User;
import edu.cui.wineapp.Models.Wine;

public class UserManager{
	private static Context context = null;
	private static WineDAO WineDao = null; 
	private static DAOBU dao = null;
	private static User localUser;
	//private static UserManager ourInstance = new UserManager();
	private UserManager(Context context){
		this.context = context;
		dao=DAOBU.getDAO(context);
	}
	
	public static UserManager getUserManager(Context context){
		return new UserManager(context);
	}

	public static boolean deleteUser(String name){
		return dao.deleteUser(name);
	}
	public ArrayList<Comment> getComment(String q){
		return dao.getCommentsByQuery(q);
	}
	
	public void setComment(String w,String u, String c){
		try {
		dao.setComment(w, u, c);
		} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	public User createUserOnServer(String name, int age, float weight, String email, String sex, String country, String photourl, long userid) throws UnsupportedEncodingException{
		return dao.createUserOnServer(name, age, weight, email, sex, country, photourl, userid);
	}
	
	public User testCreateUser(){
		try {
			return createUserOnServer("9", 9, 9, "9", "9", "9", "9", 22);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public User loginToServer(long userid) throws UnsupportedEncodingException{
		ArrayList<User> myArr = dao.loginServer(Long.toString(userid));
		if(myArr.size() == 0){
			return null;
		}
		return myArr.get(0);
	}

	public static User getLocalUser() {
		return localUser;
	}

	public static void setLocalUser(String name, int age, float weight, String email, String sex, String country, String photourl, long id) {
		localUser = new User(name, age, weight, email, sex, country, photourl, id);
	}
	
	public static void addDrink(Wine basicWine){
		Time lastDrink = new Time();
		Time currentTime = new Time();
		currentTime.setToNow();
		
		WineDao.getDAO(context).createWine(basicWine);
		
		localUser.setCurrentWine(basicWine);
		
		if(localUser.getLastDrinkTime() == null){
			lastDrink.setToNow();
			localUser.setLastDrinkTime(lastDrink);
		}
		
		int hr = BAC.calculateHour(lastDrink, currentTime);
		
		localUser.setLastDrinkTime(currentTime);
		BAC.incrDrink();
		BAC.setHour(hr);
		
		// FIX THIS
		localUser.setBAC(BAC.calculateBAC(150, "male"));
		
	}
	
	
	
}
