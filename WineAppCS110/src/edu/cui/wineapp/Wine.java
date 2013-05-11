package edu.cui.wineapp;

import java.io.Serializable;

public class Wine{
	private long id;
	//private String avin;
	private String name;
	private String code;
	//private String country;
	private String region;
	private String price;
	private String winery;
	private String varietal;
	private String vintage;
	private String type;
	private String label_url;
	private String rank;
	
	public Wine(String name,String code, String region, String winery,
		String varietal,String price,String vintage,String type, String label_url, String rating,long id) {
		this.id = id;
		//this.avin = avin;
		this.name = name;
		//this.country = country;
		this.code=code;
		this.region = region;
		this.winery=winery;
		this.type=type;
		this.price=price;
		this.vintage=vintage;
		//this.producer = producer;
		this.varietal = varietal;
		this.label_url = label_url;
		this.rank = rating;
	}
	public long getId(){
		return id;
	}
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	public String getVintage() {
		return vintage;
	}
	
	public void setVintage(String vintage) {
		this.code = vintage;
	}
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	public String getWinery() {
		return winery;
	}
	
	public void setWinery(String winery) {
		this.winery = winery;
	}
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public String getRegion() {
		return "Region:     "+region;
	}
	public void setRegion(String region) {
		this.region = region;
	}

	public String getImage_URL() {
		return label_url;
	}
	public void setLabel_URL(String label_url) {
		this.label_url = label_url;
	}

	public String getRank() {
		return rank;
	}
	public void setRank(String rating) {
		this.rank = rating;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getVarietal() {
		return varietal;
	}
	public void setVarietal(String varietal) {
		this.varietal = varietal;
	}
	

	public String toString(){
		return stripName(this.name);
	}
	
	public String stripName(String name){
		String s = name;
		s = s.replace("\"name\":", "");
		s = s.replace("\"", "");
		s = s.replace(",", "");
		
		if(s.contains("&quot;") == true){
			s = s.replace("&quot;", "\"");
		}
		
		if(s.contains("&amp;") == true){
			s = s.replace("&amp;", "&");
		}
		s = s.trim();
		return s;
	}
	
	public String stripProducer(String producer){
		String s = producer;
		

		if(s.contains("&quot;") == true){
			s = s.replace("&quot;", "\"");
		}
		
		if(s.contains("&amp;") == true){
			s = s.replace("&amp;", "&");
		}
		
		return s;
	}

	public String stripCountry(String country){
		String s = country;

		
		if(s.contains("&quot;") == true){
			s = s.replace("&quot;", "\"");
		}
		
		if(s.contains("&amp;") == true){
			s = s.replace("&amp;", "&");
		}
		
		return s;
	}
	
	public String stripVarietals(String varietals){
		
		if(varietals.contains("null"))
			return "Varietal:    N/A";
		
		String s = varietals;
		
	
		
		if(s.contains("&quot;") == true){
			s = s.replace("&quot;", "\"");
		}
		
		if(s.contains("&amp;") == true){
			s = s.replace("&amp;", "&");
		}
		
		return s;
	}
	
	public String stripURL(String label_url){

	    return label_url;
		
	}
	
	public String stripRating(String rating){
		
		String s = rating;
		s = s.replace("{\"adegga\":", "");
		s = s.replace("}", "");
			
			return s;
		}
		
//	public void stripJSON(){
		
	//	this.setName(stripName(this.name));
		//this.setVarietal(stripVarietals(this.varietal));
		//this.setLabel_URL(stripURL(this.label_url));
		//this.setRating(stripRating(this.rating));
	//}
}
