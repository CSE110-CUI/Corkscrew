package edu.cui.wineapp.models.managers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import edu.cui.wineapp.models.BAC;
import edu.cui.wineapp.models.Comment;
import edu.cui.wineapp.models.User;
import edu.cui.wineapp.models.Wine;
import edu.cui.wineapp.models.data.UserDAO;
import edu.cui.wineapp.models.data.WineDAO;

public class UserManager{
	private static Context context = null;
	private static UserDAO dao = null; 
	private static User localUser;
	private static WineDAO winedao=null;
	//private static UserManager ourInstance = new UserManager();
	private UserManager(Context context){
		this.context = context;
		dao=UserDAO.getUserDAO(context);
		winedao=WineDAO.getWineDAO(context);
	}
	
	public static UserManager getUserManager(Context context){
		return new UserManager(context);
	}
	
	public ArrayList<Comment> getComment(String q){
		return dao.getCommentsByQuery(q);
	}
	
	public void setComment(String w,String u, String c){
		try {
			Log.e("MANAGER COMMENT","!!");
		dao.setComment(w, u, c);
		} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	public User createUserOnServer(String name, int age, float weight, String email, String sex, String country, String photourl, String userid,String pass) throws UnsupportedEncodingException{
		return dao.createUserOnServer(name, age, weight, email, sex, country, photourl, userid,pass);
	}
	
	
	public User loginToServer(String userid,String pass) throws UnsupportedEncodingException{
		ArrayList<User> myArr = dao.loginServer(userid,pass);
		if(myArr.size() == 0){
			return null;
		}
		return myArr.get(0);
	}

	public static User getLocalUser() {
		return localUser;
	}
	
	public static ArrayList<Wine> fetchOnlineWines(){
		try {
			return winedao.retrieveUsersWines(UserManager.getLocalUser().getId());
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Wine>();
		}
	}

	public static void clearBAC(){
		localUser.setBAC(0);
	}
	
	public static void setLocalUser(String name, int age, float weight, String email, String sex, String country, String photourl, String id) {
		localUser = new User(name, age, weight, email, sex, country, photourl, id);
	}
	
	public static void addDrink(Wine basicWine){
		Time lastDrink = new Time();
		Time currentTime = new Time();
		currentTime.setToNow();
		
		//winedao.createWine(basicWine);

		localUser.setCurrentWine(basicWine);
		
		try {
			winedao.addWineOnline(basicWine,UserManager.getLocalUser().getId(),UserManager.getLocalUser().getCurrentWine().getCode());
			//winedao.retrieveUsersWines(UserManager.getLocalUser().getId());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
		if(localUser.getLastDrinkTime() == null){
			lastDrink.setToNow();
			localUser.setLastDrinkTime(lastDrink);
		}
		
		int hr = BAC.calculateHour(lastDrink, currentTime);
		
		localUser.setLastDrinkTime(currentTime);
		BAC.incrDrink();
		BAC.setHour(hr);
		
		// FIX THIS
		localUser.setBAC(BAC.calculateBAC((int)UserManager.getLocalUser().getWeight(), UserManager.getLocalUser().getSex()));
		
	}

	public static void clearLocalWineCellar() {
		winedao.clearAllLocalWines();
	}
	
	
	
}
