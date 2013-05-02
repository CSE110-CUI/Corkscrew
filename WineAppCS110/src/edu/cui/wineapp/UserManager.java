package edu.cui.wineapp;

public class UserManager {
	private static DAO dao = DAO.getInstance();
	private static UserManager ourInstance = new UserManager();
	private UserManager(){}
	
	public static UserManager getUserManager(){
		return ourInstance;
	}
	
	public static User getUserByName(String name){
		return dao.getUserByName(name);
	}
	
	public static boolean createUser(String name, int age, float weight){
		return dao.createUser(name, age, weight);
	}

	public static boolean deleteUser(String name){
		return dao.deleteUser(name);
	}
}
