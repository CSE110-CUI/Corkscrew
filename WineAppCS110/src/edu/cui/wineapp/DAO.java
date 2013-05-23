package edu.cui.wineapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Currency;

public class DAO {
    private static Context context = null;
    private static SQLiteDatabase wineDataBase = null;	//If it doesn't work, remove static.
    private WineSQLiteHelper wineDBHelper = null;
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
    private JSONArray foodsJSON = null;
    private JSONArray detailedWinesJSON = null;
    private String apiKey = "ra4c57ui7tkz3knjur913q2ubeekm9dnoulmu9j40lmrehjy";


    private DAO(Context context) {
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

    /**
     * public void openUserData() throws SQLException {
     * //wineDataBase = wineDBHelper.getWritableDatabase();
     * userDataBase = userDBHelper.getWritableDatabase();
     * }
     * public void closeUserData() {
     * //	wineDBHelper.close();
     * userDBHelper.close();
     * }
     */
//	public static DAO getInstance(){
    //	return ourInstance;
//	}
    public static DAO getDAO(Context context) {
        return new DAO(context);
    }

    //LOCAL wineDataBase/////////////
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

    private User cursorToUser(Cursor cursor) {
        int myAge = -1;
        float myWeight = -1;
        try {
            myAge = Integer.parseInt(cursor.getString(2));
        } catch (NumberFormatException nfe) {
            Log.e("DEBUG", "can not convert age for user");
        }
        try {
            myWeight = Float.parseFloat(cursor.getString(3));
        } catch (NumberFormatException nfe) {
            Log.e("DEBUG", "can not convert weight for user");
        }
        //ArrayList<Wine> drinkedWines = null;//cursor.getString(8);
        //ArrayList<String> comments = null;//cursor.getString(9);
        User user = new User(cursor.getString(1), myAge, myWeight, cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getLong(0));
        return user;
    }

    public ArrayList<Wine> getWineByName(String name) {
        ArrayList<Wine> winesInData = new ArrayList<Wine>();

        Cursor cursor = wineDataBase.query(WineSQLiteHelper.TABLE_WINES, null,
                WineSQLiteHelper.COLUMN_NAME + " LIKE '%" + name + "%'", null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Wine wine = cursorToWine(cursor);
            winesInData.add(wine);
            cursor.moveToNext();
        }
        cursor.close();
        return winesInData;
    }

    public ArrayList<Food> downloadFoodPairings(String wineID) {
    	
        String urlPreTerm = "http://api.snooth.com/wine/?akey=";
        String urlPostTerm = "&id=" + wineID + "&food=1";
        String stringUrl = urlPreTerm + apiKey + urlPostTerm;

        Log.i("DAO.java/downloadFoodPairings", "preFoodReturn");

        return parseFoodXML(new DownloadWebpageText().execute(stringUrl));
    }
    
    public DetailedWine downloadDetailedWine(String name) {
        String searchTerm = name;
        String urlPreTerm = "http://api.snooth.com/wine/";
        String urlPostTerm = "?akey=" + apiKey + "&id=" + searchTerm + "&food=1&i=1&photos=1";
        String stringUrl = urlPreTerm + urlPostTerm;

        return parseDetailedWineXML(new DownloadWebpageText().execute(stringUrl));
    }
    
    public DetailedWine parseDetailedWineXML(String preParsed) {
    	DetailedWine detailedWine = new DetailedWine();
        long wineRank = 0;

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
            
           detailedWine = new DetailedWine(
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
	
	            Review myReview = new Review(
	                    currentReview.getString("name"),
	                    Float.parseFloat(currentReview.getString("rating")),
	                    currentReview.getString("body"),
	                    currentReview.getInt("date"),
	                    currentReview.getString("lang"),
	                    currentReview.getInt("source")
	            );
	//NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE //NOTADDEDINDATABASE
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
        String urlPostTerm = "?akey=" + apiKey + "&q=" + searchTerm;
        String stringUrl = urlPreTerm + urlPostTerm;

        Log.i("DEBUG", "url has been built");
        Log.i("DEBUG", "Manager has been built");

        return parseWineXML(new DownloadWebpageText().execute(stringUrl));
    }
    
    public ArrayList<Wine> parseWineXML(String preParsed) {
        ArrayList<Wine> wines = new ArrayList<Wine>();
    	
        long wineRank = 0;
        try {
            JSONObject myJSON = new JSONObject(preParsed);
            winesJSON = myJSON.getJSONArray(TAG_WINES);
            for (int i = 0; i < winesJSON.length(); i++) {
                JSONObject currentWine = winesJSON.getJSONObject(i);

                try {
                    wineRank = currentWine.getLong(TAG_SNOOTHRANK);
                } catch (JSONException e) {
                    wineRank = 0;
                }

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
                        wineRank,
                        currentWine.getString(TAG_AVAILABILITY),
                        currentWine.getString(TAG_NUMMERCHANTS),
                        currentWine.getString(TAG_NUMREVIEWS)
                );
                //Log.i("DEBUG",newWine.toString());
                wines.add(createWine(newWine));
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

    public User createUser(User user, String password) {
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
        values.put(UserSQLiteHelper.COLUMN_PASSWORD, password);
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
        long insertId = -1;
        Wine newWine = null;

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
            insertId = wineDataBase.insertOrThrow(WineSQLiteHelper.TABLE_WINES, null, values);
            Cursor cursor = wineDataBase.query(WineSQLiteHelper.TABLE_WINES,
                    null, WineSQLiteHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            newWine = cursorToWine(cursor);
            cursor.close();
            //  closeWineData();

        } catch (SQLiteConstraintException e) {

        }
        return newWine;
    }

    public boolean deleteUser(String name) {
        return false;
    }

    public User getUserByName(String userName) {

        Cursor cursor = userDataBase.query(UserSQLiteHelper.TABLE_USERS,
                allColumnsforuser, UserSQLiteHelper.COLUMN_NAME + " = \"" + userName + "\"", null, null, null, null);
        Log.e("ERROR", userName);
        if (cursor.moveToFirst()) {
            User newUser = cursorToUser(cursor);
            cursor.close();
            Log.e("ERROR", "NOT NULL");
            return newUser;
        } else {
            Log.e("ERROR", "null");
            return null;
        }
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
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}

    	ArrayList<Wine> thiswines = parseWineXML(response); //MAY CAUSE PROBLEM

    	return thiswines;
    }

    public boolean setUserEmail(String userName, String newEmail) {
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
        int num = userDataBase.update(UserSQLiteHelper.TABLE_USERS, arg, "name=?", new String[]{userName});
        if (num == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean setUserPassWord(String password) {
        return false;
    }

    public boolean setUserWeight(float weight) {
        return false;
    }

    public boolean setUserAge(int age) {
        return false;
    }

    public boolean setUserSex(String sex) {
        return false;
    }

    public boolean setUserCountry(String country) {
        return false;
    }

    public boolean setUserPhoto(String url) {
        return false;
    }

    public boolean setUserName(String email) {
        return false;
    }

    public boolean setUserComments(String comment) {
        return false;
    }

    public boolean verify(String username, String password) {
        return false;
    }
    
//    "WHERE winecode = 'wine' LIMIT 5"
//     How to use rawQuery
//     TABLE COLUMNS : winecode userid comment date

    //String 
    //WHERE winecode = currWine.getCode()
    //
    //
    //
    
    public void setComment(String winecode, String user_id,String comment) throws UnsupportedEncodingException{
    	String url="http://hello-zhaoyang-udacity.appspot.com/comments";
    	url+="?wine="+URLEncoder.encode(winecode,"UTF-8");
    	url+="&user="+URLEncoder.encode(user_id,"UTF-8");
    	url+="&comment="+URLEncoder.encode(comment,"UTF-8");
    	String response = "ERROR";
    	try {
    		response = uploadUrl(url);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public ArrayList<Comment> getCommentsByQuery(String q){
    	String url="http://hello-zhaoyang-udacity.appspot.com/comments";
    	url=url+"?q="+q;
    	String response="";
		//response=new DownloadWebpageText().execute(url);		//May cause issues
    	try {
			response = new DownloadWebpageText().downloadUrl(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return parseComments(response);
    }
    
    //.getCommentsByQuery("WHERE winecode = '"+currentWine.getCode()+"');
    // GAE Database Query
    // Google Database
    
    private ArrayList<Comment> parseComments(String response) {
    	// TODO Auto-generated method stub
    	JSONArray winesJSON = null;
    	ArrayList<Comment> thisComments=new ArrayList<Comment>();
    	// Log.i("TRY",preParsed);
    	try {
    		JSONObject myJSON = new JSONObject(response);
    		winesJSON = myJSON.getJSONArray("comments");
    		//Log.i("TRY","made it past myJSON");
    		//Log.i("DEBUG",winesJSON.toString());
    		for(int i = 0; i < winesJSON.length(); i++){
    			JSONObject currentWine = winesJSON.getJSONObject(i);

    			Comment newComment;
    			//Log.e("DEBUG",currentWine.getString("comment"));
    			newComment = new Comment(
    					//currentWine.getString(TAG_AVIN),
    					currentWine.getString("winecode"),
    					currentWine.getString("userid"),
    					currentWine.getString("comment"),
    					currentWine.getString("date")
    					//currentWine.getString("id")
    					);

    			// Log.i("DEBUG",newWine.toString());
    			thisComments.add(newComment);
    		}
    	} catch (JSONException e) {e.printStackTrace();}
    	return thisComments;
    }


    
    private String uploadUrl(String myurl) throws IOException {
    	InputStream is = null;
    	try {
    		URL url = new URL(myurl);
    		URI uri = null;
    		try {
    			uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
    		} catch (URISyntaxException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		url = uri.toURL();
    		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    		conn.setReadTimeout(10000 /* milliseconds */);
    		conn.setConnectTimeout(15000 /* milliseconds */);
    		conn.setRequestMethod("POST");
    		conn.setDoInput(true);
    		conn.connect();
    		int response = conn.getResponseCode();
    		// Log.e("DEBUG", "The response is: " + response);
    		is = conn.getInputStream();

    		String contentAsString = new DownloadWebpageText().readIt(is);
    		return contentAsString;

    	} finally {if (is != null) {is.close();} }
    }
    

    private class DownloadWebpageText {
        private static final String DEBUG_TAG = "HttpExample";

        protected String execute(String... urls) {
        	String fetchResult = "";
            try {
                fetchResult = downloadUrl(urls[0]);
            	//return fetchResult;

            } catch (IOException e) {
                Log.e("DEBUG", "Unable to retrieve web page. URL may be invalid.");
                fetchResult = "Unable to retrieve web page. URL may be invalid.";
            }
            finally{
            	return fetchResult;
            }
        }

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
                if (is != null) {
                    is.close();
                }
                
                return fetchResult;
            }
        }

        public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            reader.close();
            String result = sb.toString();
            return result;
        }


    }
}
