package edu.cui.wineapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.cui.wineapp.Controllers.UserManager;
import edu.cui.wineapp.Models.Comment;
import edu.cui.wineapp.Models.DetailedWine;
import edu.cui.wineapp.Models.Food;
import edu.cui.wineapp.Models.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Currency;

public class DAOBU {
    private static Context context = null;
    private static SQLiteDatabase wineDataBase = null;	//If it doesn't work, remove static.
    private WineSQLiteHelper wineDBHelper = null;
    private SQLiteDatabase userDataBase = null;
    private UserSQLiteHelper userDBHelper = null;
    

    
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

    
    
    private JSONArray foodsJSON = null;
    private String apiKey = "ra4c57ui7tkz3knjur913q2ubeekm9dnoulmu9j40lmrehjy";


    private DAOBU(Context context) {
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
    public static DAOBU getDAO(Context context) {
        return new DAOBU(context);
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

    public ArrayList<Food> downloadFoodPairings(String wineID) {
    	
        String urlPreTerm = "http://api.snooth.com/wine/?akey=";
        String urlPostTerm = "&id=" + wineID + "&food=1";
        String stringUrl = urlPreTerm + apiKey + urlPostTerm;

        Log.i("DAO.java/downloadFoodPairings", "preFoodReturn");

        return parseFoodXML(new DownloadWebpageText().execute(stringUrl));
    }
    
    public ArrayList<User> parseUserXML(String preParsed){    	
    	JSONArray winesJSON = null;
    	ArrayList<User> thisComments=new ArrayList<User>();
    	// Log.i("TRY",preParsed);
    	try {
    		JSONObject myJSON = new JSONObject(preParsed);
    		winesJSON = myJSON.getJSONArray("users");
    		//Log.i("TRY","made it past myJSON");
    		//Log.i("DEBUG",winesJSON.toString());
    		for(int i = 0; i < winesJSON.length(); i++){
    			JSONObject currentWine = winesJSON.getJSONObject(i);

    			User newUser;
    			//Log.e("DEBUG",currentWine.getString("comment"));
    			newUser = new User(
    					//currentWine.getString(TAG_AVIN),
    					currentWine.getString("name"),
    					Integer.parseInt(currentWine.getString("age")),
    					Float.parseFloat(currentWine.getString("weight")),
    					currentWine.getString("email"),
    					currentWine.getString("sex"),
    					currentWine.getString("country"),
    					currentWine.getString("photourl"),
    					Long.parseLong(currentWine.getString("userid"))
    					);

    			// Log.i("DEBUG",newWine.toString());
    			thisComments.add(newUser);
    		}
    	} catch (JSONException e) {e.printStackTrace();}
    	return thisComments;
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


    public boolean deleteUser(String name) {
        return false;
    }

    
    public User createUserOnServer(String name, int age, float weight, String email, String sex, String country, String photourl, long userid) throws UnsupportedEncodingException{
    	String url="http://hello-zhaoyang-udacity.appspot.com/users";
    	url+="?command=signup";
    	url+="&userid="+URLEncoder.encode(Long.toString(userid),"UTF-8");
    	url+="&age="+URLEncoder.encode(Integer.toString(age),"UTF-8");
    	url+="&weight="+URLEncoder.encode(Integer.toString((int)weight),"UTF-8");
    	url+="&email="+URLEncoder.encode(email,"UTF-8");
    	url+="&sex="+URLEncoder.encode(sex,"UTF-8");
    	url+="&country="+URLEncoder.encode(country,"UTF-8");
    	url+="&photourl="+URLEncoder.encode(photourl,"UTF-8");
    	url+="&name="+URLEncoder.encode(name,"UTF-8");

    	try {
    		uploadUrl(url);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	return new User(name, age, weight, email, sex, country, photourl, userid);
    }
    
    public boolean changeAge(String newAge) throws UnsupportedEncodingException{
    	String url="http://hello-zhaoyang-udacity.appspot.com/users";
    	url+="?command=setting";
    	url+="&userid="+URLEncoder.encode(Long.toString(UserManager.getUserManager(context).getLocalUser().getId()),"UTF-8");
    	url+="&colum=age";
    	url+="&newvalue="+URLEncoder.encode(newAge,"UTF-8");

    	boolean isValid = true;
    	try {
    		uploadUrl(url);
    		isValid = true;
    		
    	} catch (IOException e) {
			isValid = false;
    	}
    	
    	return isValid;
    }

    public boolean changeWeight(String newWeight) throws UnsupportedEncodingException{
    	String url="http://hello-zhaoyang-udacity.appspot.com/users";
    	url+="?command=setting";
    	url+="&userid="+URLEncoder.encode(Long.toString(UserManager.getUserManager(context).getLocalUser().getId()),"UTF-8");
    	url+="&colum=weight";
    	url+="&newvalue="+URLEncoder.encode(newWeight,"UTF-8");

    	boolean isValid = true;
    	try {
    		uploadUrl(url);
    		isValid = true;
    		
    	} catch (IOException e) {
			isValid = false;
    	}
    	
    	return isValid;
    }
    
    public boolean changeSex(String newSex) throws UnsupportedEncodingException{
    	String url="http://hello-zhaoyang-udacity.appspot.com/users";
    	url+="?command=setting";
    	url+="&userid="+URLEncoder.encode(Long.toString(UserManager.getUserManager(context).getLocalUser().getId()),"UTF-8");
    	url+="&colum=sex";
    	url+="&newvalue="+URLEncoder.encode(newSex,"UTF-8");

    	boolean isValid = true;
    	try {
    		uploadUrl(url);
    		isValid = true;
    		
    	} catch (IOException e) {
			isValid = false;
    	}
    	
    	return isValid;
    }
    
    public boolean changeCountry(String newCountry) throws UnsupportedEncodingException{
    	String url="http://hello-zhaoyang-udacity.appspot.com/users";
    	url+="?command=setting";
    	url+="&userid="+URLEncoder.encode(Long.toString(UserManager.getUserManager(context).getLocalUser().getId()),"UTF-8");
    	url+="&colum=country";
    	url+="&newvalue="+URLEncoder.encode(newCountry,"UTF-8");

    	boolean isValid = true;
    	try {
    		uploadUrl(url);
    		isValid = true;
    		
    	} catch (IOException e) {
			isValid = false;
    	}
    	
    	return isValid;
    }
    
    public boolean changePhotourl(String newPhotourl) throws UnsupportedEncodingException{
    	String url="http://hello-zhaoyang-udacity.appspot.com/users";
    	url+="?command=setting";
    	url+="&userid="+URLEncoder.encode(Long.toString(UserManager.getUserManager(context).getLocalUser().getId()),"UTF-8");
    	url+="&colum=photourl";
    	url+="&newvalue="+URLEncoder.encode(newPhotourl,"UTF-8");

    	boolean isValid = true;
    	try {
    		uploadUrl(url);
    		isValid = true;
    		
    	} catch (IOException e) {
			isValid = false;
    	}
    	
    	return isValid;
    }
    
    public ArrayList<User> loginServer(String userid){
    	String url = "http://hello-zhaoyang-udacity.appspot.com/users";
    	url+="?command=login";
    	try {
			url+="&userid="+URLEncoder.encode(userid,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	String response = "ERROR";
    	try {
    		response = uploadUrl(url);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	
    	return parseUserXML(response);
    	
    }
    /*
     * COMMAND REQ- USER DB
     * 
     * command = signup
     * 		userid	-- unique
     * 		age
     * 		weight
     * 		email
     * 		sex
     * 		country
     * 		photourl
     * 		password
     * 		
     * 		I don't need to check for user exists
     * 
     * 		succesful response -- OK USER HAS BEEN CREATED
     * 
     * command = login
     * 		userid
     * 		password
     * 
     * 		returns json file
     * 
     * 
     * command = setting 
     * 
     * 		(ALLOWS FOR CHANGING ASPECTS OF EXISTING USER PROFILE)
     * 
     * 		userid
     * 		colum
     * 			age
     * 			weight
     * 			sex
     * 			country
     * 			photourl
     * 		newvalue
     * 		
     * command = winehistory
     * 		userid
     * 		
     * 		returns jsonfile
     * 
     * command = drink
     * 		userid
     * 		winecode
     * 
     * command = clearhistory
     * 		userid
     * 
     * 		clears entire wine history
     * 
     * 
     * hello-zhaoyang-udacity.appspot.com/
     * 										winehistory
     * 											GET
     * 												SUPPORTS RAW QUERY
     * 											POST
     * 										user
     * 											GET
     * 											POST
     */	
    
    

    

    
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
    	try {
    		uploadUrl(url);
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
			response= downloadUrl(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return parseComments(response);
    }
    
    public void getUserFromDB(String winecode, String user_id,String comment) throws UnsupportedEncodingException{
    	String url="http://hello-zhaoyang-udacity.appspot.com/comments";
    	url+="?wine="+URLEncoder.encode(winecode,"UTF-8");
    	url+="&user="+URLEncoder.encode(user_id,"UTF-8");
    	url+="&comment="+URLEncoder.encode(comment,"UTF-8");
    	try {
    		uploadUrl(url);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    //.getCommentsByQuery("WHERE winecode = '"+currentWine.getCode()+"');
    // GAE Database Query
    // Google Database
    
    private ArrayList<Comment> parseComments(String response) {
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

    		
    		String contentAsString = new DownloadWebpageText().readIt(is);
    		return contentAsString;

    	} finally {if (is != null) {is.close();} }
    }
    private String downloadUrl(String myurl) throws IOException {
    	InputStream is = null;
    	Log.e("DAO.java/downloadUrl","MADE IT");
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
    		conn.setRequestMethod("GET");
    		conn.setDoInput(true);
    		conn.connect();
    		// Log.e("DEBUG", "The response is: " + response);
    		is = conn.getInputStream();

    		String contentAsString = new DownloadWebpageText().readIt(is);
    		return contentAsString;

    	} finally {if (is != null) {is.close();} }
    }
    

    public class DownloadWebpageText {
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
}
