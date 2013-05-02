package edu.cui.wineapp;

public class User {
	private static DAO dao = DAO.getInstance();
	private static String name=null;
	private static int age=-1;
	private static float weight=-1;
	private static String email=null;
	private static String sex=null;
	private static String country=null;
	private static String photourl=null;
	private static Wine[] drinkedWines=null;
	private static String[] comments=null;
	
	
	public User(String name, int age, float weight, Object... p){}
	
	public static String getName(){
		return name;
	}
	public static int getAge(){
		return age;
	}
	public static float getWeight(){
		return weight;
	}
	public static String getEmail(){
		return email;
	}
	public static String getSex(){
		return sex;
	}
	public static String getCountry(){
		return country;
	}
	public static String getPhotoUrl(){
		return photourl;
	}
	public static Wine[] getDrinkedWines(){
		return drinkedWines;
	}
	public static String[] getComments(){
		return comments;
	}
	
	//setter!!!!!
	public static boolean setName(String name){
		return dao.setUserName(name);
	}
	public static boolean setAge(int age){
		return dao.setUserAge(age);
	}
	public static boolean setWeight(float weight){
		return dao.setUserWeight(weight);
	}
	public static boolean setEmail(String email){
		return dao.setUserEmail(email);
	}
	public static boolean setSex(String sex){
		return dao.setUserSex(sex);
	}
	public static boolean setCountry(String country){
		return dao.setUserCountry(country);
	}
	public static boolean setPhotoUrl(String url){
		return dao.setUserPhoto(url);
	}
	public static boolean drink(Wine wine){
		return false;
	}
	public static boolean setComments(String comment){
		return dao.setUserComments(comment);
	}
	
}
