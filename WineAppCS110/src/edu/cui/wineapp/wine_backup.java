package edu.cui.wineapp;

import java.io.Serializable;

public class wine_backup implements Serializable{
	
	public wine_backup(String avin, String name, String country, String region,
		String producer, String varietal, String label_url, String rating) {
		
		this.avin = avin;
		this.name = name;
		this.country = country;
		this.region = region;
		this.producer = producer;
		this.varietal = varietal;
		this.label_url = label_url;
		this.rating = rating;
	}

	private String avin;
	private String name;
	private String country;
	private String region;
	private String producer;
	private String varietal;
	private String label_url;
	private String rating;
	
	public String getAvin() {return avin;}
	public void setAvin(String avin) {this.avin = avin;}

	public String getRegion() {return "Region:     "+region;}
	public void setRegion(String region) {this.region = region;}

	public String getProducer() {return "Producer: "+producer;}
	public void setProducer(String producer) {this.producer = producer;}

	public String getLabel_URL() {return label_url;}
	public void setLabel_URL(String label_url) {this.label_url = label_url;}

	public String getRating() {return rating;}
	public void setRating(String rating) {this.rating = rating;}

	public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	public String getCountry() {return "Country:   "+country;}
	public void setCountry(String country) {this.country = country;}

	public String getVarietal() {return varietal;}
	public void setVarietal(String varietal) {this.varietal = varietal;}
	
	public wine_backup(String[] tagArray){
		this.avin = tagArray[0];
		this.name = tagArray[1];
		this.country = tagArray[2];
		this.region = tagArray[3];
		this.producer = tagArray[4];
		this.varietal = tagArray[5];
		this.label_url = tagArray[6];
		this.rating = tagArray[7];
	}
	
	public String toString(){return stripName(this.name);}
	
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
		
	public void stripJSON(){
		
		this.setName(stripName(this.name));
		this.setCountry(stripCountry(this.country));
		this.setProducer(stripProducer(this.producer));
		this.setVarietal(stripVarietals(this.varietal));
		this.setLabel_URL(stripURL(this.label_url));
		this.setRating(stripRating(this.rating));
	}
}
