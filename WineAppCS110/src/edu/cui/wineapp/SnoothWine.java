package edu.cui.wineapp;

public class SnoothWine {
	private String name;
	private String code;
	private String region;
	private String winery;
	private String winery_id;
	private String varietal;
	private float  price;
	private String vintage;
	private String type;
	private String link;
	private String tags;
	private String image;
	private float  snoothrank;
	private String availability;
	private String num_merchants;
	private String num_reviews;
	
	public SnoothWine(String name, String code, String region, String winery,
			String winery_id, String varietal, float price, String vintage,
			String type, String link, String tags, String image,
			float snoothrank, String availability, String num_merchants,
			String num_reviews) {
		
		super();
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
	}
	
	public SnoothWine(String string, String string2, String string3,
			String string4, String string5, String string6, String string7,
			String string8, String string9, String string10, String string11,
			String string12, String string13, String string14, String string15,
			String string16) {
		// TODO Auto-generated constructor stub
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
	

}
