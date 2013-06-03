package edu.cui.wineapp;
import java.text.*;
import java.math.*;
/*
 * 
 * @Author: 	Arash Vatanpoor
 * @Company:	C.U.I
 * @Date: 		April/May of 2013
 * 				
 * 
 * Final Resources used in calculation:
 * 1. U.S Departmet of Transportation BAC paper.
 * 2. Widmark, E. Paper/Formula.
 * 
 * Log:
 * 1.Inital Testing was Successful.
 * 2.Needs more testing.
 * 3.Converted to static a single method
 */

import android.text.format.Time;

public class BAC {

	/* weight of one ounce of pure alcohol */
    private final static double oneOunceOfAlcohol = 23.36; // unit -> grams
	private final static double lbsToKg = 2.2046;
   	private final static double Default_Wine_Percentage = 0.12; // 12%
	private final static double elimination_rate = 0.017; 
	private final static int drink_size = 5; // as in 5 oz		
	private static boolean conservative_rate_set = false; //can have this form setting
	private static boolean percentageSet = false; // if we manage to get the percent
	private static int drink_count = 0; //the globalcounter every time a button is pressed
	public static  double hour = 1; //should be updated before calculating the BAC 
	
	private static double Custom_Alcohol_Percentage; //using 12% right now
	
	/***************************************NOTE*******************************************/
	/* if we can get he alcohol content percentage just uncommentting the setter
	 * method below is all it needs to be done, the rest is handled through BAC 
	 * algorithm.
	 */
	/***************************************NOTE*******************************************/



	/* the timer should calculate the difference between the 
	 * ending of drinking session and start and set it
	/* using this setter below     **/
	public static void setHour(double _hour){
		hour = _hour;
	}/**
	private double getHour() {
		return this.hour;
	}*/ 
	/*
	 * Formating of the final BAC 
		public void setAlcoholPercent (double _percent){
			Custom_Alcohol_Percentage = (_percent / 100);
			percentageSet = true;
		}
	 */
	
	/* if you need to set the conservative rate,
	 * probably set in the setting
	 */ 	
	
	public static void incrDrink(){
		drink_count++;
	}
	public static void decDrink(){
		if (drink_count >= 1){
			drink_count--;
		}
	}
	public void setConservative_rate() {
		this.conservative_rate_set = true;
	}
	private static double RoundTo2Decimals(double val) {
       		 DecimalFormat num = new DecimalFormat("###.###");
    		return Double.valueOf(num.format(val));
	}
	
	public static int calculateHour(Time lastDrink, Time currentTime){
		return 24*(currentTime.yearDay-lastDrink.yearDay)+currentTime.hour-lastDrink.hour;
	}
	
	public static double calculateBAC(int weight, String gender) {
		double WaterInBody;

		if (gender.toLowerCase().matches("male")) {
		/* have to convert the weight to KG and
		 multiply by the body weight */
			WaterInBody = (weight/lbsToKg) * 0.58;
		}else{
			WaterInBody = (weight/lbsToKg) * 0.49;
		}
		double alc_in_body = (oneOunceOfAlcohol /(WaterInBody * 1000));
		/* 
	 	 * asumming that on average blood is composed of 80.6 % water
	 	 * calculates Alcohol concentration in blood 
	 	 * returns the result  as of X grams alcohol per 100 ml of blood
	 	 * pass the result of CalAlcoholInBody as a parameter 
	 	 */
		double unadjustedBAC = ((alc_in_body * 0.806)*100);
		double adjustedBAC;

		/*returns the global BAC without 
		 *considering the metabolized alcohol
		 */
		if (percentageSet) {
			double totalAlcohol = (drink_count * drink_size * Custom_Alcohol_Percentage);
			adjustedBAC=  totalAlcohol * unadjustedBAC;
		}else {
			double totalAlcohol = (drink_count * drink_size * Default_Wine_Percentage);
			adjustedBAC =  totalAlcohol * unadjustedBAC;	
		}

		/*returns the final BAC after considering
		 * the metabolized alcohol
		 */	
		double finalBAC;
		if (conservative_rate_set)	{
			finalBAC = ((adjustedBAC) - (hour * 0.012));
			if (finalBAC <= 0.00000) { finalBAC = 0; }
		}else {
			finalBAC = ((adjustedBAC) - (hour * elimination_rate));
			if (finalBAC <= 0.00000) { finalBAC = 0; }
		}
		return RoundTo2Decimals(finalBAC);
	}
}
		
	

