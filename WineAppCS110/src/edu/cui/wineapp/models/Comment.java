package edu.cui.wineapp.models;

public class Comment {
	private String winecode;
	private String userid;
	private String comment;
	private String date;
	// private String id;
	public Comment(String winecode,String userid,String comment,String date){
		this.winecode=winecode;
		this.userid=userid;
		this.comment=comment;
		this.date=date;
		//id=id;
	}
	public String getComment(){
		return comment;
	}
	public String getUserId(){
		return userid;
	}
	public String getWineCode(){
		return winecode;
	}
	//public String getId(){
	//return id;
	//}

}