package edu.cui.wineapp;

public class Review {
	
	private String name;
	private float rating;
	private String body;
	private int date;
	private String lang;
	private int source;
	
	public Review(String name, float rating, String body, int date,
			String lang, int source) {
		
		super();
		this.name = name;
		this.rating = rating;
		this.body = body;
		this.date = date;
		this.lang = lang;
		this.source = source;
	}

	
	public String getName() {
		return name;
	}
	public float getRating() {
		return rating;
	}
	public String getBody() {
		return body;
	}
	public int getDate() {
		return date;
	}
	public String getLang() {
		return lang;
	}
	public int getSource() {
		return source;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public void setSource(int source) {
		this.source = source;
	}
}
