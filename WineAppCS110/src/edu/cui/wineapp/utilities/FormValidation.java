package edu.cui.wineapp.utilities;

import java.util.regex.*;





public class FormValidation {
	private final static String REGEX_EMAIL = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
	static Pattern emailPattern = Pattern.compile(REGEX_EMAIL);
	
	

	public static boolean isValidEmail(String email) {

	Matcher m = emailPattern.matcher(email);
	return m.matches();

	}
	
	//check no digit 
	public static boolean checkNoDigit(String name) {
		if(name.matches(".*\\d.*")){
			return false;	
		} else{
			return true;
		}
	}
	public static boolean checkWeight(float weight){
		if  ( 65 < weight && weight < 1000){
			return true;
		}
		return false;
	}
	
	public static boolean checkAge(int age){
		if  ( 20 < age && age < 121){
			return true;
		}
		return false;
	}
	
	public static boolean confirmPassword(String pass1, String pass2)
	{
		return pass1.equals(pass2);
	}
}