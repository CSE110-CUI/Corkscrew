package edu.cui.wineapp;

import java.util.ArrayList;

import android.content.Context;

public class User {
    //Okay
	private String name=null;
	private int age=-1;
	private float weight=-1;
	private String email=null;
	private String sex=null;
	private String country=null;
	private String photourl=null;
	private ArrayList<Wine> drinkedWines=null;
	private ArrayList<String> comments=null;
	private long id;
	
	public User(String name, int age, float weight, String email, String sex, String country, String photourl, long id){
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.email = email;
		this.sex = sex;
		this.country = country;
		this.photourl = photourl;
		this.id = id;
	}
	public long getId(){
		return id;
	}
	public String getName(){
		return name;
	}
	public int getAge(){
		return age;
	}
	public float getWeight(){
		return weight;
	}
	public String getEmail(){
		return email;
	}
	public String getSex(){
		return sex;
	}
	public String getCountry(){
		return country;
	}
	public String getPhotoUrl(){
		return photourl;
	}
	public ArrayList<Wine> getDrinkedWines(){
		return drinkedWines;
	}
	public ArrayList<String> getComments(){
		return comments;
	}
/**	
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
**/	
}
