package edu.cui.bac;
import java.text.*;
import java.math.*;

/*
 * 
 * @Author: 	Arash Vatanpoor
 * @Company:	C.U.I
 * @Date: 		April/May of 2013
 * 
 * Final Resources used in calculation:
 * 1. U.S Departmet of Transportation BAC paper.
 * 2. Widmark, E. Paper/Formula.
 * 
 * 
 * Inital Testing was Successful.
 * Needs more testing.
 */

/*
 *  IGNORE BUT LEAVE HERE
 *
 *final static double MALE_CONSTANT = 0.68;			// unit -> L/Kg
 *final static double FEMALE_CONSTANT = 0.55;		//unit -> L/Kg
 *final static double WIDEMARK_CONSTANT = 0.8; 
 * Constant in Widemark's Equation
 * 
 * alcohol amount in  a regular glass considering
 *  12% alcohol (avg in wines) unit -> ounce 
 *
 *private double alcohol_per_drink = 0.71; 			// fluid ounces of alcohol per drink
 *private double alcohol_consumed;
 *private int wine_5oz_Glass = 5; 			  		//standard drink for wine = 5 Oz 
*/


public class BAC {

	
    final boolean DEBUG = false;
    
    private final double lbsToKg = 2.2046;
    private final double Default_Wine_Percentage = 0.12; // 12%
    
    private double Custom_Alcohol_Percentage;
    private boolean percentageSet = false;
    
    /* weight of one ounce of pure alcohol */
    private final double oneOunceOfAlcohol = 23.36; // unit -> grams

    /* alcohol elimination rate unit-> Kg/L/hr*/
	private double elimination_rate = 0.017; 		
	private boolean conservative_rate_set = false;
	
	private double WaterInBody;
	private double Alcohol_weight = 23.36;  		//unit -> grams   
	private int  drink_count = 1;
	
	/* can set different sizes using setDrink_size() */
	private int drink_size = 5;
	private String gender;
	

	private int body_weight; 						//unit -> Oz
	private double unadjustedBAC;
	private double adjustedBAC;
	private double finalBAC;

	
	/*public BAC (int drinkCount, int drinkSize, int weight){
		setDrink_count(drinkCount);
		setDrink_size (drinkSize);
		setBody_weight(weight);
	}*/
	
	
	/*
	 * Constructor of class BAC
	 * Can have other custom Constructors Depending on what will be needed
	 */
	
	public BAC (int drinkCount, int weight, String gender){
		setDrink_count(drinkCount);
		setBody_weight(weight);
		setGender(gender);
	}
	

	public void setGender(String gender) {
		this.gender = gender;
	}


	private void setDrink_count(int drink_count) {
		this.drink_count = drink_count;
	}
	
	/*
	 * if this is not called explicitly then it will use the default 5oz 
	 */
	public void setDrink_size(int drink_size) {
		this.drink_size = drink_size;
	}
	
	private void setBody_weight(int body_weight) {
		this.body_weight = body_weight;
	}
	/*
	 * custom alcohol percentage if available 
	 * e.g  the concentration of alcohol in wine is 5%
	 * if this is not set the default 12% will be considered
	 */
	public void setAlcoholPercent (double _percent){
		Custom_Alcohol_Percentage = (_percent / 100);
		percentageSet = true;
	}
	/*
	 *if set a more conservative calculation will be used 
	 */
	public void setConservative_rate() {
		this.conservative_rate_set = true;
	}
	
	/*
	 * Formating of the final BAC 
	 */
	double RoundTo2Decimals(double val) {
        DecimalFormat num = new DecimalFormat("###.###");
    return Double.valueOf(num.format(val));
}

	/*
	 * Takes the weight and the gender and 
	 * calculates the percentage of the water 
	 * in their respective water, converts Pounds to KG 
	 * and returns the amount of water in body in liters
	 */
	public Double calculateWaterInBody(int weight, String _gender) {
		
	
		if (_gender.toLowerCase().matches("male")) {
			/* have to convert the weight to KG and multiply by the body weight */
			WaterInBody = (weight/lbsToKg) * 0.58;
			return WaterInBody;
		
		}else{
			WaterInBody = (weight/lbsToKg) * 0.49;
			return WaterInBody;

		}
	}
	
	/*
	 * Precondition: calculateWaterInBody is called.
	 * returns  water in body in Ml mul weigth of one ounce of alcohol
	 **/
	public double CalAlcoholInBody (double _WaterInBody){
		
		return (oneOunceOfAlcohol/(_WaterInBody * 1000));
	}
	/*
	 *  
	 * asumming that on average blood is composed of 80.6 % water
	 * calculates Alcohol concentration in blood 
	 * returns the result  as of X grams alcohol per 100 mililitresof blood
	 * pass the result of CalAlcoholInBody as a pareameter 
	 * 
	 */
	public double alcoholConcInBlood (double _alcohol){
		unadjustedBAC = ((_alcohol * 0.806)*100);
		return unadjustedBAC;
	}
	
	/*
	 *PreCondition: UnadjustedBAC is calculated 
	 *returns the global BAC without considering the metabolized alcohol
	 */
	
	public void adjustForDrinks(){
		
		
		if (percentageSet) {
			double totalAlcohol = (drink_count * drink_size *
										Custom_Alcohol_Percentage);
			adjustedBAC=  totalAlcohol * unadjustedBAC;
			//return adjustedBAC;
		}else {
			double totalAlcohol = (drink_count * drink_size * 
										Default_Wine_Percentage);
			adjustedBAC=  totalAlcohol * unadjustedBAC;
			//return adjustedBAC;	
		}
			
	}
	
	/*
	 *PreCondition: adjustedBAC is calculated 
	 *returns the final BAC after considering the metabolized alcohol
	 */
	public double adjustForMetabolism(double _hour){
		
		//DecimalFormat f = new DecimalFormat("#.###");
		if (conservative_rate_set)	{
			finalBAC = ((adjustedBAC) - (_hour * 0.012));
			if (finalBAC <= 0.00000) {
				finalBAC = 0;
				return (finalBAC);
			}else {return (finalBAC);}
		}else {
			finalBAC = ((adjustedBAC) - (_hour * elimination_rate));
			if (finalBAC <= 0.00000) {
				finalBAC = 0;
			    return (finalBAC);
			} else { return (finalBAC);}
		}
		
		
	}

/*
 * 
 * JUNK-Revision_[-1]
 * 
 */
	
	/* public double estimateBAC(){
		 if (DEBUG) System.out.println(gender);
		
		if (gender.toLowerCase().matches("female")) {
			if (DEBUG)	System.out.println("female here");
			 // weight in ounces times the female constant 
			femaleProduct = (getBody_weight() * 16) * FEMALE_CONSTANT;
			System.out.println (femaleProduct
					);
			double step1 =  (WIDEMARK_CONSTANT * (alcohol_per_drink)* getDrink_count());
			double step2 = (femaleProduct * metabolizedAlcohol);
			double step3 = step1 - step2;
			if (DEBUG)	System.out.println("step1: " + step1 + ", step2: " + step2 + ", step3:" + step3);
			double currentBAC = step3 / femaleProduct;
			if(currentBAC > 0)
				return currentBAC;
			else return 0;	
		}else {
			 System.out.println("male here");
			 // weight in ounces times the male constant 
			maleProduct = (getBody_weight() * 16)  * MALE_CONSTANT;
			double step1 =  (WIDEMARK_CONSTANT * (alcohol_per_drink)* getDrink_count());
			double step2 = (maleProduct * metabolizedAlcohol);
			double step3 = step1 - step2;
			if (DEBUG)	System.out.println("step1: " + step1 + ", step2: " + step2 + ", step3:" + step3);
			double currentBAC = step3 / maleProduct;
			if(currentBAC > 0)
				return currentBAC;
			else return 0;	
		}
	}
	*/

	
			

}
