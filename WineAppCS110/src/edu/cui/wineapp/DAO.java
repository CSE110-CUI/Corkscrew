package edu.cui.wineapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class DAO{
	private static Context context = null;
	private SQLiteDatabase wineDataBase=null;
	private WineSQLiteHelper wineDBHelper=null;
	private SQLiteDatabase userDataBase = null;
	private UserSQLiteHelper userDBHelper = null;
	private String[] allColumnsforuser = {
									UserSQLiteHelper.COLUMN_ID,
									UserSQLiteHelper.COLUMN_NAME,
									UserSQLiteHelper.COLUMN_AGE,
									UserSQLiteHelper.COLUMN_WEIGHT,
									UserSQLiteHelper.COLUMN_EMAIL,
									UserSQLiteHelper.COLUMN_SEX,
									UserSQLiteHelper.COLUMN_COUNTRY,
									UserSQLiteHelper.COLUMN_PHOTOURL,
									UserSQLiteHelper.COLUMN_PASSWORD};
	private ArrayList<Wine> wines=new ArrayList<Wine>();
	
	
	private DAO(Context context){
		this.context = context;
		wineDBHelper = new WineSQLiteHelper(context);
		userDBHelper = new UserSQLiteHelper(context);
		open();
	}
	public void open() throws SQLException {
		wineDataBase = wineDBHelper.getWritableDatabase();
		userDataBase = userDBHelper.getWritableDatabase();
	}

	public void close() {
		wineDBHelper.close();
		userDBHelper.close();
	}
	/**public void openUserData() throws SQLException {
		//wineDataBase = wineDBHelper.getWritableDatabase();
		userDataBase = userDBHelper.getWritableDatabase();
	}
	public void closeUserData() {
	//	wineDBHelper.close();
		userDBHelper.close();
	}
**/
//	public static DAO getInstance(){
	//	return ourInstance;
//	}
	public static DAO getDAO(Context context){
		return new DAO(context);
	}

	//LOCAL wineDataBase/////////////
	public boolean deleteWine(Wine wine) {
	    long id = wine.getId();
	    if (id == -1){
	    	return false;
	    }
	    System.out.println("Comment deleted with id: " + id);
	    wineDataBase.delete(WineSQLiteHelper.TABLE_WINES, WineSQLiteHelper.COLUMN_ID
	        + " = " + id, null);
	    return true;
	  }

	public ArrayList<Wine> getAllWinesInwineDataBase() {
	    ArrayList<Wine> winesInData = new ArrayList<Wine>();

	    Cursor cursor = wineDataBase.query(WineSQLiteHelper.TABLE_WINES,
	        null, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Wine wine = cursorToWine(cursor);
	      winesInData.add(wine);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return winesInData;
	  }
	private Wine cursorToWine(Cursor cursor) {
	    Wine wine = new Wine(
	    						cursor.getLong(0), 
	    						cursor.getString(1),
	    						cursor.getString(2),
	    						cursor.getString(3),
	    						cursor.getString(4),
	    						cursor.getString(5),
	    						cursor.getString(6),
	    						cursor.getFloat(7), 
	    						cursor.getString(8),
	    						cursor.getString(9),
	    						cursor.getString(10),
	    						cursor.getString(11),
	    						cursor.getString(12),
	    						cursor.getFloat(13), 
	    						cursor.getString(14),
	    						cursor.getString(15),
	    						cursor.getString(16)
	    												);
	    return wine;
	  }
	
	private User cursorToUser(Cursor cursor) {
		int myAge=-1;
		float myWeight=-1;
		try {
		    myAge = Integer.parseInt(cursor.getString(2));
		} catch(NumberFormatException nfe) {
			Log.e("DEBUG","can not convert age for user");
		} 
		try {
		    myWeight = Float.parseFloat(cursor.getString(3));
		} catch(NumberFormatException nfe) {
			Log.e("DEBUG","can not convert weight for user");
		} 
		//ArrayList<Wine> drinkedWines = null;//cursor.getString(8);
		//ArrayList<String> comments = null;//cursor.getString(9);
	    User user = new User(cursor.getString(1),myAge,myWeight,cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getLong(0));
	    return user;
	  }
	  
	public ArrayList<Wine> getWineByName(String name){
		 ArrayList<Wine> winesInData = new ArrayList<Wine>();

		    Cursor cursor = wineDataBase.query(WineSQLiteHelper.TABLE_WINES, 
		    		//allColumns
		    		null
		    		, 
		    		WineSQLiteHelper.COLUMN_NAME + " LIKE '%" + name +"%'"
		    		//"name = " + name
		    		, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
			      Wine wine = cursorToWine(cursor);
			      winesInData.add(wine);
			      cursor.moveToNext();
			    }
		    return winesInData;
	}
	
	
	
	//GETTER AND SETTER FROM ONLINE
	public ArrayList<Wine> downloadWineByName(String name){
	    String apiKey 		= "588eeca229b7895ff55a";
	    String urlPreTerm 	= "http://api.adegga.com/rest/v1.0/GetWinesByName/";
	    String urlPostTerm 	= "/&format=json&key=";
	    String searchTerm 	= name;
	    String stringUrl 	= urlPreTerm + searchTerm + urlPostTerm + apiKey;
	    
	    Log.e("DEBUG","url has been built");
	    Log.e("DEBUG","Manager has been built");
	    
	    new DownloadWebpageText().execute(stringUrl);

	    Log.e("DEBUG","Wine has been built");

	    return wines;
    }
	
	public Wine getWineById(long wineId){

	    Cursor cursor = wineDataBase.query(WineSQLiteHelper.TABLE_WINES,
	        null, WineSQLiteHelper.COLUMN_ID + " = " + wineId, null, null, null, null);
	    if(cursor.moveToFirst()){
	    	Wine newWine = cursorToWine(cursor);
	    	cursor.close();
	    	return newWine;
	    }else{
	    	return null;
	    }
	}
	public User createUser(User user, String password){
		 ContentValues values = new ContentValues();
		 values.put(UserSQLiteHelper.COLUMN_NAME, user.getName());
		 values.put(UserSQLiteHelper.COLUMN_AGE, Integer.toString(user.getAge()));
		 values.put(UserSQLiteHelper.COLUMN_WEIGHT, Float.toString(user.getWeight()));
		 values.put(UserSQLiteHelper.COLUMN_EMAIL, user.getEmail());
		 values.put(UserSQLiteHelper.COLUMN_SEX, user.getSex());
		 values.put(UserSQLiteHelper.COLUMN_COUNTRY, user.getCountry());
		 values.put(UserSQLiteHelper.COLUMN_PHOTOURL, user.getPhotoUrl());
		 //values.put(UserSQLiteHelper.COLUMN_DRINKEDWINES,user.getDrinkedWines().toString());
		 //values.put(UserSQLiteHelper.COLUMN_COMMENTS, user.getComments().toString());
		 values.put(UserSQLiteHelper.COLUMN_PASSWORD,password);
		    long insertId = userDataBase.insert(UserSQLiteHelper.TABLE_USERS, null,
		        values);
		    Cursor cursor = userDataBase.query(UserSQLiteHelper.TABLE_USERS,
		        allColumnsforuser, UserSQLiteHelper.COLUMN_ID + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    User newUser = cursorToUser(cursor);
		    cursor.close();
		    return newUser;
	}
	public Wine createWine(Wine wine) {
		//openWineData();
	    ContentValues values = new ContentValues();
	    values.put(WineSQLiteHelper.COLUMN_NAME, wine.getName());
	    values.put(WineSQLiteHelper.COLUMN_REGION, wine.getRegion());
	    values.put(WineSQLiteHelper.COLUMN_VARIETAL, wine.getVarietal());
	    
	    
	    
	    long insertId = wineDataBase.insert(WineSQLiteHelper.TABLE_WINES, null,
	        values);
	    Cursor cursor = wineDataBase.query(WineSQLiteHelper.TABLE_WINES,
	        null, WineSQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Wine newWine = cursorToWine(cursor);
	    cursor.close();
	  //  closeWineData();
	    return newWine;
	  }
	public boolean deleteUser(String name){
		return false;
	}
	public User getUserByName(String userName){

	    Cursor cursor = userDataBase.query(UserSQLiteHelper.TABLE_USERS,
	        allColumnsforuser, UserSQLiteHelper.COLUMN_NAME + " = \"" + userName+ "\"", null, null, null, null);
	    Log.e("ERROR",userName);
	    if(cursor.moveToFirst()){
	    	User newUser = cursorToUser(cursor);
	    	cursor.close();
	    	Log.e("ERROR","NOT NULL");
	    	return newUser;
	    }else{
	    	Log.e("ERROR","null");
	    	return null;
	    }
	}
	public boolean setUserEmail(String userName, String newEmail){
	    /**Cursor cursor = userDataBase.query(UserSQLiteHelper.TABLE_USERS,
		        allColumnsforuser, UserSQLiteHelper.COLUMN_NAME + " = \"" + userName+ "\"", null, null, null, null);

		    if(cursor.moveToFirst()){
		    	User newUser = cursorToUser(cursor);
		    	cursor.close();
		    	return newUser;
		    }else{
		    	return null;
		    }**/
		ContentValues arg = new ContentValues();
		arg.put("email", newEmail);
		int num =userDataBase.update(UserSQLiteHelper.TABLE_USERS, arg, "name=?", new String[]{userName});
		if (num==0){
			return false;
		}
		else{
			return true;
		}
	}
	public boolean setUserPassWord(String password){
		return false;
	}
	public boolean setUserWeight(float weight){
		return false;
	}
	public boolean setUserAge(int age){
		return false;
	}
	public boolean setUserSex(String sex){
		return false;
	}
	public boolean setUserCountry(String country){
		return false;
	}
	public boolean setUserPhoto(String url){
		return false;
	}
	public boolean setUserName(String email){
		return false;
	}
	public boolean setUserComments(String comment){
		return false;
	}
	public boolean verify(String username,String password){
		return false;
	}
	
	//private classs!!!
    private class DownloadWebpageText{   	
    	private static final String DEBUG_TAG = "HttpExample";	
    	
    	
  	  public static final String TAG_WINES = "Wines";
    	  public static final String TAG_ID = "_id";
    	  public static final String TAG_NAME = "name";
    	  public static final String TAG_CODE = "code";
    	  public static final String TAG_REGION = "region";
    	  public static final String TAG_WINERY = "winery";
    	  public static final String TAG_WINERYID = "winery_id";
    	  public static final String TAG_VARIETAL = "varietal";
    	  public static final String TAG_PRICE = "price";
    	  public static final String TAG_VINTAGE = "vintage";
    	  public static final String TAG_TYPE = "type";
    	  public static final String TAG_LINK = "link";
    	  public static final String TAG_TAGS = "tags";
    	  public static final String TAG_IMAGE = "image";
    	  public static final String TAG_SNOOTHRANK = "snoothrank";
    	  public static final String TAG_AVAILABILITY = "availability";
    	  public static final String TAG_NUMMERCHANTS = "num_merchants";
    	  public static final String TAG_NUMREVIEWS = "num_reviews";
    	  
        JSONArray winesJSON = null; 
		protected void execute(String... urls) {
			String result="";
	        try {result = downloadUrl(urls[0]);} catch (IOException e) {
	        	Log.e("DEBUG","Unable to retrieve web page. URL may be invalid.");}
	        onPostExecute(result);
		}

		protected void onPostExecute(String result) {
	    	parseXML(result);
	    	//Log.e("DEBUG",wines.get(0).toString());
	   }
	    
	   public void parseXML(String preParsed){
		   try {
				JSONObject myJSON = new JSONObject(preParsed);
				myJSON = myJSON.getJSONObject("response");
				myJSON = myJSON.getJSONObject("aml");
				myJSON = myJSON.getJSONObject("wines");
				winesJSON = myJSON.getJSONArray(TAG_WINES);
				Log.i("TRY","made it past myJSON");
				//Log.i("DEBUG",winesJSON.toString());
				for(int i = 0; i < winesJSON.length(); i++){
					JSONObject currentWine = winesJSON.getJSONObject(i);
					
					Wine newWine = new Wine(
							-1,
							currentWine.getString(TAG_NAME),
							currentWine.getString(TAG_CODE),
							currentWine.getString(TAG_REGION),
							currentWine.getString(TAG_WINERY),
							currentWine.getString(TAG_WINERYID),
							currentWine.getString(TAG_VARIETAL),
							currentWine.getLong(TAG_PRICE),
							currentWine.getString(TAG_VINTAGE),
							currentWine.getString(TAG_TYPE),
							currentWine.getString(TAG_LINK),
							currentWine.getString(TAG_TAGS),
							currentWine.getString(TAG_IMAGE),
							currentWine.getLong(TAG_SNOOTHRANK),
							currentWine.getString(TAG_AVAILABILITY),
							currentWine.getString(TAG_NUMMERCHANTS),
							currentWine.getString(TAG_NUMREVIEWS)
					);
					//Log.i("DEBUG",newWine.toString());
					wines.add(createWine(newWine));
				}
		   } catch (JSONException e) {e.printStackTrace();}  
	   }
	
	 private String downloadUrl(String myurl) throws IOException {
	     InputStream is = null;
	     try {
		         URL url = new URL(myurl);
		         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		         conn.setReadTimeout(10000 /* milliseconds */);
		         conn.setConnectTimeout(15000 /* milliseconds */);
		         conn.setRequestMethod("GET");
		         conn.setDoInput(true);
		         conn.connect();
		         int response = conn.getResponseCode();
		         Log.d(DEBUG_TAG, "The response is: " + response);
		         is = conn.getInputStream();
		
		         String contentAsString = readIt(is);
		         return contentAsString;
		
	     } finally {if (is != null) {is.close();} }
	 }
	 
	 public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
		   
		 	BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));  
		    StringBuilder sb = new StringBuilder();
		    String line;
		    
		    while((line = reader.readLine()) != null){
		    	sb.append(line+"\n");
		    }
		    reader.close();
		    return sb.toString();
		}
	

    }
}
