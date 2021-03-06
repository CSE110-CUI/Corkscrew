
package edu.cui.wineapp.models;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;

public class DetailedWine implements Serializable{

	private long id;
	private String name;
	private String code;
	private String region;
	private String winery;
	private String winery_id;
	private String varietal;
	private float price;
	private String vintage;
	private String type;
	private String link;
	private String tags;
	private String image;
	private float snoothrank;
	private String availability;
	private String num_merchants;
	private String num_reviews;
	private String wm_notes;
	private String winery_tasting_notes;
	private	String sugar;
	private float alcohol;
	private float pH;
	private String acidity;
	private ArrayList<Review> reviews;
	private ArrayList<Food> recipes;

	public DetailedWine(){
		this.id = -1;
		this.name = "";
		this.code = "";
		this.region = "";
		this.winery = "";
		this.winery_id = "";
		this.varietal = "";
		this.price = -1;
		this.vintage = "";
		this.type = "";
		this.link = "";
		this.tags = "";
		this.image = "";
		this.snoothrank = -1;
		this.availability = "";
		this.num_merchants = "";
		this.num_reviews = "";
		this.wm_notes = "";
		this.winery_tasting_notes = "";
		this.sugar = "";
		this.alcohol = -1;
		this.pH = -1;
		this.acidity = "";
		this.setReviews(null);
		this.setRecipes(null);
	}

	public DetailedWine(long id, String name, String code, String region,
			String winery, String winery_id, String varietal, float price,
			String vintage, String type, String link, String tags,
			String image, float snoothrank, String availability,
			String num_merchants, String num_reviews, String wm_notes,
			String winery_tasting_notes, String sugar, float alcohol, float pH,
			String acidity, ArrayList<Review> reviews, ArrayList<Food> recipes) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.region = region;
		this.winery = winery;
		this.winery_id = winery_id;
		this.varietal = varietal;
		this.price = price;
		this.vintage = vintage;
		this.type = type;
		this.link = link;
		this.tags = tags;
		this.image = image;
		this.snoothrank = snoothrank;
		this.availability = availability;
		this.num_merchants = num_merchants;
		this.num_reviews = num_reviews;
		this.wm_notes = wm_notes;
		this.winery_tasting_notes = winery_tasting_notes;
		this.sugar = sugar;
		this.alcohol = alcohol;
		this.pH = pH;
		this.acidity = acidity;
		this.reviews = reviews;
		this.recipes = recipes;
	}

	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	public String getRegion() {
		return region;
	}
	public String getWinery() {
		return winery;
	}
	public String getWinery_id() {
		return winery_id;
	}
	public String getVarietal() {
		return varietal;
	}
	public float getPrice() {
		return price;
	}
	public String getVintage() {
		return vintage;
	}
	public String getType() {
		return type;
	}
	public String getLink() {
		return link;
	}
	public String getTags() {
		return tags;
	}
	public String getImage() {
		return image;
	}
	public float getSnoothrank() {
		return snoothrank;
	}
	public String getAvailability() {
		return availability;
	}
	public String getNum_merchants() {
		return num_merchants;
	}
	public String getNum_reviews() {
		return num_reviews;
	}
	public String getWm_notes() {
		return wm_notes;
	}
	public String getWinery_tasting_notes() {
		return winery_tasting_notes;
	}
	public String getSugar() {
		return sugar;
	}
	public float getAlcohol() {
		return alcohol;
	}
	public float getpH() {
		return pH;
	}
	public String getAcidity() {
		return acidity;
	}
	public ArrayList<Review> getReviews() {
		return reviews;
	}
	public ArrayList<Food> getRecipes() {
		return recipes;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setWinery(String winery) {
		this.winery = winery;
	}
	public void setWinery_id(String winery_id) {
		this.winery_id = winery_id;
	}
	public void setVarietal(String varietal) {
		this.varietal = varietal;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public void setVintage(String vintage) {
		this.vintage = vintage;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setSnoothrank(float snoothrank) {
		this.snoothrank = snoothrank;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public void setNum_merchants(String num_merchants) {
		this.num_merchants = num_merchants;
	}
	public void setNum_reviews(String num_reviews) {
		this.num_reviews = num_reviews;
	}
	public void setWm_notes(String wm_notes) {
		this.wm_notes = wm_notes;
	}
	public void setWinery_tasting_notes(String winery_tasting_notes) {
		this.winery_tasting_notes = winery_tasting_notes;
	}
	public void setSugar(String sugar) {
		this.sugar = sugar;
	}
	public void setAlcohol(float alcohol) {
		this.alcohol = alcohol;
	}
	public void setpH(float pH) {
		this.pH = pH;
	}
	public void setAcidity(String acidity) {
		this.acidity = acidity;
	}
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
	public void setRecipes(ArrayList<Food> recipes) {
		this.recipes = recipes;
	}

	public ArrayList<Food> getFoodPairings(Context context){
		return recipes;
	}



}
