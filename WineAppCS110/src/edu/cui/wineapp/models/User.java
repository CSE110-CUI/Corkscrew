package edu.cui.wineapp.models;

import java.util.ArrayList;

import android.content.Context;
import android.text.format.Time;

public class User {
	private String name=null;
	private int age=-1;
	private float weight=-1;
	private String email=null;
	private String sex=null;
	private String country=null;
	private String photourl=null;
	private ArrayList<Wine> drinkedWines=null;
	private ArrayList<String> comments=null;
	private String id;
	//private String currentWine = "To Get Started, Search a Wine Below!";
	private Wine currentWine = null;
	private Time lastDrinkTime = null;
	private double BAC;
	
	
	public User(String name, int age, float weight, String email, String sex, String country, String photourl, String id){
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.email = email;
		this.sex = sex;
		this.country = country;
		this.photourl = photourl;
		this.id = id;
	}
	public String getId(){
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

	public Wine getCurrentWine() {
		return currentWine;
	}
	public void setCurrentWine(Wine basicWine) {
		this.currentWine = basicWine;
	}
	public Time getLastDrinkTime() {
		return lastDrinkTime;
	}
	public void setLastDrinkTime(Time lastDrinkTime) {
		this.lastDrinkTime = lastDrinkTime;
	}
	public double getBAC() {
		return BAC;
	}
	public void setBAC(double bAC) {
		BAC = bAC;
	}	
}
