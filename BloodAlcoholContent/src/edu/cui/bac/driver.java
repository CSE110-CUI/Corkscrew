package edu.cui.bac;
import java.util.*;

/*
* @Author: 	Arash Vatanpoor
* @Company:	C.U.I
* @Date: 	April/May of 2013
* Tester, and also shows the WorkFlow og how to use the method calls  
*
*/

public class driver {
	public static void main(String[] args) {
		
		int weight;
		int count;
		String gender;
		
		Scanner input = new Scanner(System.in); 
		
		System.out.print ("Please Enter your weight:");
		weight = input.nextInt();

		
		System.out.print ("Female/Male:");
		gender = input.next();
		
		System.out.print ("What is the size of the glass (Container) in ounces: ");
		int size = input.nextInt();

		
		System.out.print ("How Many of them (size) you consumed since the start:");
		count = input.nextInt();
		
		BAC alcLevel = new BAC (count, weight, gender);
		alcLevel.setDrink_size(size);
		double WaterInBody = alcLevel.calculateWaterInBody(weight, gender);
		double AlcoholInBody = alcLevel.CalAlcoholInBody(WaterInBody);
		double alcoholCons = alcLevel.alcoholConcInBlood(AlcoholInBody);
		
		System.out.println ("Percentage of the alcohol in the beverage.");
		System.out.println("Example: 4.5 meaning %4.5");
		System.out.print("Or enter a negative number, to use the default %12:");
		double percent = input.nextDouble();
		if (percent < 0){ System.out.println("Default 12% will be used");}
		else {
			alcLevel.setAlcoholPercent(percent);
		}
		System.out.print ("Are you a conservative Person (y/n)");
		String answer = input.next();
		
		if (answer.toLowerCase().matches("y")) {
			alcLevel.setConservative_rate();
		}
	    alcLevel.adjustForDrinks();
	    System.out.print("How many hours has passed since your first Drink:");
	     double hours = (int) input.nextDouble();
	     System.out.println("Your BAC is: " + alcLevel.RoundTo2Decimals
	    		 								(alcLevel.adjustForMetabolism(hours)));
	     
		
		
		
	}
}