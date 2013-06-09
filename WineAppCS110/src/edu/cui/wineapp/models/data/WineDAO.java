package edu.cui.wineapp.models.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import edu.cui.wineapp.models.DetailedWine;
import edu.cui.wineapp.models.Food;
import edu.cui.wineapp.models.Review;
import edu.cui.wineapp.models.Wine;

public class WineDAO {

	public static final String TAG_WINES = "wines";
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
	public static final String TAG_AVAILABILITY = "available";
	public static final String TAG_NUMMERCHANTS = "num_merchants";
	public static final String TAG_NUMREVIEWS = "num_reviews";

	private JSONArray winesJSON = null;
	private WineSQLiteHelper wineDBHelper = null;
	private static SQLiteDatabase wineDataBase = null;	
	private static Context context = null;
	private JSONArray foodsJSON = null;
	private JSONArray detailedWinesJSON = null;
	private String apiKey = "ra4c57ui7tkz3knjur913q2ubeekm9dnoulmu9j40lmrehjy";


	private WineDAO(Context context) {
		this.context = context;
		wineDBHelper = new WineSQLiteHelper(context);
		open();
	}
	private void open() throws SQLException {
		wineDataBase = wineDBHelper.getWritableDatabase();
	}
	public void close() {
		wineDBHelper.close();
	}
	public static WineDAO getWineDAO(Context context) {
		return new WineDAO(context);
	}

	public boolean deleteWine(Wine wine) {
		long id = wine.getId();
		if (id == -1) {
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

	public ArrayList<Wine> getWineByName(String name) {
		ArrayList<Wine> winesInData = new ArrayList<Wine>();
		//TODO: FIX THIS SERIOUSLY DO
		if(name.contains("'")){
			name = name.replace('\'','|');
		}

		Cursor cursor = wineDataBase.query(WineSQLiteHelper.TABLE_WINES, null,
				WineSQLiteHelper.COLUMN_NAME + " LIKE '%" + name + "%'", null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Wine wine = cursorToWine(cursor);
			winesInData.add(wine);
			cursor.moveToNext();
		}
		cursor.close();
		/*
	        if(winesInData.get(0).getName().contains("|")){
	            	 winesInData.get(0).setName(winesInData.get(0).getName().replace('|', '\''));
	        }
		 */
		return winesInData;
	}


	public DetailedWine downloadDetailedWine(String name) {
		String searchTerm = name;
		String urlPreTerm = "http://api.snooth.com/wine/";
		String urlPostTerm = "?akey=" + apiKey + "&id=" + searchTerm + "&food=1&i=1&photos=1";
		String stringUrl = urlPreTerm + urlPostTerm;

		return parseDetailedWineXML(new DownloadWebpageText().execute(stringUrl));
	}
	public DetailedWine parseDetailedWineXML(String preParsed) {
		Log.e("Winexml",preParsed);
		DetailedWine detailedWine = new DetailedWine();
		long wineRank = 0;
		long winePrice = 0;

		try {
			JSONObject myJSON = new JSONObject(preParsed);
			foodsJSON = myJSON.getJSONArray(TAG_WINES);
			myJSON = foodsJSON.getJSONObject(0);
			JSONObject currentWine = myJSON;

			try {
				wineRank = myJSON.getLong(TAG_SNOOTHRANK);
			} catch (JSONException e) {
				wineRank = 0;
			}            
			try {
				winePrice = myJSON.getLong(TAG_PRICE);
			} catch (JSONException e) {
				winePrice = -1;
			}

			detailedWine = new DetailedWine(
					-1,
					currentWine.getString(TAG_NAME),
					currentWine.getString(TAG_CODE),
					currentWine.getString(TAG_REGION),
					currentWine.getString(TAG_WINERY),
					currentWine.getString(TAG_WINERYID),
					currentWine.getString(TAG_VARIETAL),
					winePrice,
					currentWine.getString(TAG_VINTAGE),
					currentWine.getString(TAG_TYPE),
					currentWine.getString(TAG_LINK),
					"",
					currentWine.getString(TAG_IMAGE),
					wineRank,
					currentWine.getString(TAG_AVAILABILITY),
					currentWine.getString(TAG_NUMMERCHANTS),
					currentWine.getString(TAG_NUMREVIEWS),
					currentWine.getString("wm_notes"),
					currentWine.getString("winery_tasting_notes"),
					currentWine.getString("sugar"),
					Float.parseFloat(currentWine.getString("alcohol")),
					Float.parseFloat(currentWine.getString("ph")),
					currentWine.getString("acidity"),
					getReviewArrayListFromJSON(myJSON),
					getRecipeArrayListFromJSON(myJSON)
					);
			Log.i("DAO.java/parseDetailedWineXML","Sugar: "+ currentWine.getString("sugar") +" Alcohol: " + Float.parseFloat(currentWine.getString("alcohol")));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		finally{
			return detailedWine;
		}
	}

	private ArrayList<Review> getReviewArrayListFromJSON(JSONObject myJSON){
		ArrayList<Review> helperReviews = new ArrayList<Review>();

		try{
			JSONArray myJSONArr = myJSON.getJSONArray("reviews");
			for(int i = 0; i < myJSONArr.length(); ++i){
				JSONObject currentReview = myJSONArr.getJSONObject(i);
				Log.i("DAO.java/getReviewArrayListFromJSON", "Current Review Name: "+currentReview.getString("name"));

				int source = -1;

				try{
					source = currentReview.getInt("source");
				}catch(JSONException e){
					Log.i("DAO.java/getReviewArrayListFromJSON","Catch: JSON Exception. No source provided");
					source = -1;
				}

				Review myReview = new Review(
						currentReview.getString("name"),
						Float.parseFloat(currentReview.getString("rating")),
						currentReview.getString("body").trim(),
						currentReview.getInt("date"),
						currentReview.getString("lang"),
						source
						);
				//NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE
				if(myReview.getBody().length() > 3)
					helperReviews.add(myReview);
				//NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE

			}

			Log.i("DAO.java/getReviewArrayListFromJSON","helperReviews.size() "+helperReviews.size());
		}catch(JSONException e){
			e.printStackTrace();
		}
		finally{
			return helperReviews;
		}
	}

	@SuppressWarnings("finally")
	private ArrayList<Food> getRecipeArrayListFromJSON(JSONObject myJSON){

		ArrayList<Food> helperFoods = new ArrayList<Food>();

		try{
			JSONArray myJSONArr = myJSON.getJSONArray("recipes");
			for (int i = 0; i < myJSONArr.length(); i++) {
				JSONObject currentFood = myJSONArr.getJSONObject(i);
				Log.i("Current Food Name", currentFood.getString("name"));

				Food newFood = new Food(
						currentFood.getString("name"),
						currentFood.getString("link"),
						currentFood.getString("source_link"),
						currentFood.getInt("source_id"),
						currentFood.getString("image")
						);
				//NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE
				helperFoods.add(newFood);
				//NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE

			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		finally{
			return helperFoods;
		}
	}


	@SuppressWarnings("finally")
	public ArrayList<Food> parseFoodXML(String preParsed) {
		ArrayList<Food> foods = new ArrayList<Food>();

		try {
			JSONObject myJSON = new JSONObject(preParsed);
			foodsJSON = myJSON.getJSONArray(TAG_WINES);
			myJSON = foodsJSON.getJSONObject(0);
			foodsJSON = myJSON.getJSONArray("recipes");

			for (int i = 0; i < foodsJSON.length(); i++) {
				JSONObject currentFood = foodsJSON.getJSONObject(i);
				Log.i("Current Food Name", currentFood.getString("name"));

				Food newFood = new Food(
						currentFood.getString("name"),
						currentFood.getString("link"),
						currentFood.getString("source_link"),
						currentFood.getInt("source_id"),
						currentFood.getString("image")
						);
				//NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE
				foods.add(newFood);
				//NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		finally{
			return foods;
		}
	}

	//GETTER AND SETTER FROM ONLINE
	public ArrayList<Wine> downloadWineByName(String name) {


		String searchTerm = name;
		String urlPreTerm = "http://api.snooth.com/wines/";
		String urlPostTerm = "?akey=" + apiKey + "&q=" + searchTerm  + "&n=99"+"&s=sr";
		String stringUrl = urlPreTerm + urlPostTerm;

		Log.i("DEBUG", "url has been built");
		Log.i("DEBUG", "Manager has been built");

		return parseWineXML(new DownloadWebpageText().execute(stringUrl));
	}

	@SuppressWarnings("finally")
	public ArrayList<Wine> parseWineXML(String preParsed) {
		ArrayList<Wine> wines = new ArrayList<Wine>();

		long wineRank = 0;
		long winePrice = 0;
		String winery = "NA";
		try {
			JSONObject myJSON = new JSONObject(preParsed);
			winesJSON = myJSON.getJSONArray(TAG_WINES);
			for (int i = 0; i < winesJSON.length(); i++) {
				JSONObject currentWine = winesJSON.getJSONObject(i);

				try {
					wineRank = currentWine.getLong(TAG_SNOOTHRANK);
				} catch (JSONException e) {
					wineRank = 0;
					Log.e("DAO.java.parseWineXML()","Catch: wineRank set to 0");
				}


				try {
					winePrice = currentWine.getLong(TAG_PRICE);
				} catch (JSONException e) {
					winePrice = -1;
					Log.e("DAO.java.parseWineXML()","Catch: winePrice set to -1");
				}

				try{
					winery = currentWine.getString(TAG_WINERY);
				} catch(JSONException e){
					Log.e("DAO.java.parseWineXML()","Catch: winery set to NA");
				}

				Wine newWine = new Wine(
						-1,
						currentWine.getString(TAG_NAME),
						currentWine.getString(TAG_CODE),
						currentWine.getString(TAG_REGION),
						winery,
						currentWine.getString(TAG_WINERYID),
						currentWine.getString(TAG_VARIETAL),
						winePrice,
						currentWine.getString(TAG_VINTAGE),
						currentWine.getString(TAG_TYPE),
						currentWine.getString(TAG_LINK),
						currentWine.getString(TAG_TAGS),
						currentWine.getString(TAG_IMAGE),
						wineRank,
						currentWine.getString(TAG_AVAILABILITY),
						currentWine.getString(TAG_NUMMERCHANTS),
						currentWine.getString(TAG_NUMREVIEWS)
						);

				if(newWine.getName()!=null)
					Log.e("DAO.java/parseWineXML","Wine creation succeeded: "+i);

				//wines.add(createWine(newWine));
				wines.add(newWine);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		finally{
			return wines;
		}
	}

	public Wine getWineById(long wineId) {

		Cursor cursor = wineDataBase.query(WineSQLiteHelper.TABLE_WINES,
				null, WineSQLiteHelper.COLUMN_ID + " = " + wineId, null, null, null, null);
		if (cursor.moveToFirst()) {
			Wine newWine = cursorToWine(cursor);
			cursor.close();
			return newWine;
		} else {
			return null;
		}
	}

	public Wine getWineByNameFromDB(String wineName){
		Log.e("DAO.java/createWine","Wine name: "+ wineName);
		Cursor cursor = 
				//wineDataBase.query(WineSQLiteHelper.TABLE_WINES, 
				//null, WineSQLiteHelper.COLUMN_NAME + " = " + wineName, null, null, null, null);
				wineDataBase.rawQuery("SELECT * FROM "+WineSQLiteHelper.TABLE_WINES+" WHERE "+ WineSQLiteHelper.COLUMN_NAME+" = "+wineName, null);
		if(cursor.moveToFirst()){
			Wine newWine = cursorToWine(cursor);
			cursor.close();
			return newWine;
		} else{
			return null;
		}
	}

	public Wine createWine(Wine wine) {
		//openWineData();
		long insertId = -1;
		Wine newWine = wine;

		Log.i("DAO.java/createWine","Trying Wine: "+wine.getName());

		ContentValues values = new ContentValues();
		values.put(WineSQLiteHelper.COLUMN_NAME, wine.getName());
		values.put(WineSQLiteHelper.COLUMN_CODE, wine.getCode());
		values.put(WineSQLiteHelper.COLUMN_REGION, wine.getRegion());
		values.put(WineSQLiteHelper.COLUMN_WINERY, wine.getWinery());
		values.put(WineSQLiteHelper.COLUMN_WINERYID, wine.getWinery_id());
		values.put(WineSQLiteHelper.COLUMN_VARIETAL, wine.getVarietal());
		values.put(WineSQLiteHelper.COLUMN_PRICE, wine.getPrice());
		values.put(WineSQLiteHelper.COLUMN_VINTAGE, wine.getVintage());
		values.put(WineSQLiteHelper.COLUMN_TYPE, wine.getType());
		values.put(WineSQLiteHelper.COLUMN_LINK, wine.getLink());
		values.put(WineSQLiteHelper.COLUMN_TAGS, wine.getTags());
		values.put(WineSQLiteHelper.COLUMN_IMAGE, wine.getImage());
		values.put(WineSQLiteHelper.COLUMN_SNOOTHRANK, wine.getSnoothrank());
		values.put(WineSQLiteHelper.COLUMN_AVAILABILITY, wine.getAvailability());
		values.put(WineSQLiteHelper.COLUMN_NUMMERCHANTS, wine.getNum_merchants());
		values.put(WineSQLiteHelper.COLUMN_NUMREVIEWS, wine.getNum_reviews());


		try {
			//	        	Log.e("DAO.java/createWine","ID: "+wineDataBase.insertOrThrow(WineSQLiteHelper.TABLE_WINES, null, values));
			insertId = wineDataBase.insertOrThrow(WineSQLiteHelper.TABLE_WINES, null, values);
			Cursor cursor = wineDataBase.query(WineSQLiteHelper.TABLE_WINES,
					null, WineSQLiteHelper.COLUMN_ID + " = " + insertId, null,
					null, null, null);
			cursor.moveToFirst();
			newWine = cursorToWine(cursor);
			cursor.close();
			//  closeWineData();

		} catch (SQLiteConstraintException e) {
			Log.e("DAO.java/createWine","Wine already in database, ignoring");
			Log.e("DAO.java/createWine","Wine name: "+ wine.getName());
			//newWine = getWineByNameFromDB(wine.getName());
		}
		return newWine;
	}


	public ArrayList<Wine> getWineByQuery(String query){			//Tomas method
		String apiKey = "ra4c57ui7tkz3knjur913q2ubeekm9dnoulmu9j40lmrehjy";
		String searchTerm = query;
		String urlPreTerm = "http://api.snooth.com/wines/";
		String urlPostTerm = "?akey="+apiKey+"&format=json&"+searchTerm;
		String snoothUrl = urlPreTerm + urlPostTerm;

		String response="";
		try {
			response=new DownloadWebpageText().downloadUrl(snoothUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<Wine> thiswines = parseWineXML(response); //MAY CAUSE PROBLEM

		return thiswines;
	}



	//private cause error
	private class DownloadWebpageText {
		private static final String DEBUG_TAG = "HttpExample";

		@SuppressWarnings("finally")
		protected String execute(String... urls) {
			String fetchResult = "";
			try {
				fetchResult = downloadUrl(urls[0]);
			} catch (IOException e) {
				fetchResult = "Unable to retrieve web page. URL may be invalid.";
			}
			finally{
				return fetchResult;
			}
		}

		@SuppressWarnings("finally")
		private String downloadUrl(String myurl) throws IOException {
			InputStream is = null;
			String fetchResult = "";
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

				fetchResult = readIt(is);

			} finally {
				if (is != null) is.close();
				return fetchResult;
			}
		}

		public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {

			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				Log.e("DEBUG_DAO",line);
				sb.append(line + "\n");
			}
			reader.close();
			String result = sb.toString();
			return result;
		}


	}



	public void addWineOnline(Wine basicWine, String userid, String winecode) throws UnsupportedEncodingException {
		String url="http://hello-zhaoyang-udacity.appspot.com/users";
		url+="?command=drink";
		url+="&userid="+URLEncoder.encode(userid,"UTF-8");
		url+="&winecode="+URLEncoder.encode(winecode,"UTF-8");

		try {
			uploadUrl(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		createWine(basicWine);

	}

	public ArrayList<Wine> retrieveUsersWines(String userid) throws UnsupportedEncodingException{
		String url="http://hello-zhaoyang-udacity.appspot.com/users";
		url+="?command=winehistory";
		url+="&userid="+URLEncoder.encode(userid,"UTF-8");

		String response = "";
		try {
			response=uploadUrl(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Wine> myArrWines = parseWinesReturned(response);
		//Log.e("WineDAO.java/retrieveUsersWines",myArrWines.get(0).getName());
		return myArrWines;

	}

	private ArrayList<Wine> parseWinesReturned(String response) {
		ArrayList<Wine> wines = new ArrayList<Wine>();

		long wineRank = 0;
		long winePrice = 0;
		String winery = "NA";
		try {
			JSONObject myJSON = new JSONObject(response);
			winesJSON = myJSON.getJSONArray("winehistory");
			for (int i = 0; i < winesJSON.length(); i++) {
				JSONObject currentWine = winesJSON.getJSONObject(i);
				DetailedWine dWine = downloadDetailedWine(currentWine.getString("winecode"));
				try {
					wineRank = currentWine.getLong(TAG_SNOOTHRANK);
				} catch (JSONException e) {
					wineRank = 0;
					Log.e("DAO.java.parseWineXML()","Catch: wineRank set to 0");
				}


				try {
					winePrice = currentWine.getLong(TAG_PRICE);
				} catch (JSONException e) {
					winePrice = -1;
					Log.e("DAO.java.parseWineXML()","Catch: winePrice set to -1");
				}

				try{
					winery = currentWine.getString(TAG_WINERY);
				} catch(JSONException e){
					Log.e("DAO.java.parseWineXML()","Catch: winery set to NA");
				}
				Wine newWine = new Wine(
						-1,
						dWine.getName(),
						dWine.getCode(),
						dWine.getRegion(),
						winery,
						dWine.getWinery_id(),
						dWine.getVarietal(),
						winePrice,
						dWine.getVintage(),
						dWine.getType(),
						dWine.getLink(),
						dWine.getTags(),
						dWine.getImage(),
						wineRank,
						dWine.getAvailability(),
						dWine.getNum_merchants(),
						dWine.getNum_reviews()
						);

				if(newWine.getName()!=null)
					Log.e("DAO.java/parseWineXML","Wine creation succeeded: "+i);

				//wines.add(createWine(newWine));
				wines.add(newWine);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		finally{
			return wines;
		}
	}
	private String uploadUrl(String myurl) throws IOException {
		InputStream is = null;
		try {
			URL url = new URL(myurl);
			URI uri = null;



			try {
				uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}


			url = uri.toURL();


			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.connect();
			// Log.e("DEBUG", "The response is: " + response);



			is = conn.getInputStream();

			Log.e("INSIDE","HERE");	


			String contentAsString =readIt(is);
			return contentAsString;

		} finally {if (is != null) {is.close();} }
	}

	public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = reader.readLine()) != null) {
			Log.e("DEBUG_DAO",line);
			sb.append(line + "\n");
		}
		reader.close();
		String result = sb.toString();
		return result;
	}
	public void clearAllLocalWines() {
		ArrayList<Wine> winesInDB = getAllWinesInwineDataBase();
		for(Wine w:winesInDB) deleteWine(w);
	}

}