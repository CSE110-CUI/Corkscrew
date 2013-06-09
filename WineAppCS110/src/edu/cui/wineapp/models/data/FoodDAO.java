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
import edu.cui.wineapp.models.Comment;
import edu.cui.wineapp.models.DetailedWine;
import edu.cui.wineapp.models.Food;
import edu.cui.wineapp.models.Review;
import edu.cui.wineapp.models.User;
import edu.cui.wineapp.models.Wine;
import edu.cui.wineapp.models.managers.UserManager;

public class FoodDAO {
    private WineSQLiteHelper wineDBHelper = null;
    private UserSQLiteHelper userDBHelper = null;
    
    public static final String TAG_CODE = "code";
    private JSONArray foodsJSON = null;
    private String apiKey = "ra4c57ui7tkz3knjur913q2ubeekm9dnoulmu9j40lmrehjy";


    private FoodDAO(Context context) {
    }
    

    public void close() {
        wineDBHelper.close();
        userDBHelper.close();
    }


    public static FoodDAO getDAO(Context context) {
        return new FoodDAO(context);
    }

    //LOCAL wineDataBase/////////////
    
    public ArrayList<Food> downloadFoodPairings(String wineID) {
    	
        String urlPreTerm = "http://api.snooth.com/wine/?akey=";
        String urlPostTerm = "&id=" + wineID + "&food=1";
        String stringUrl = urlPreTerm + apiKey + urlPostTerm;

        Log.i("DAO.java/downloadFoodPairings", "preFoodReturn");

        return parseFoodXML(new DownloadWebpageText().execute(stringUrl));
    }
    
    @SuppressWarnings("finally")
	public ArrayList<Food> parseFoodXML(String preParsed) {
    	ArrayList<Food> foods = new ArrayList<Food>();
    	
        try {
            JSONObject myJSON = new JSONObject(preParsed);
            foodsJSON = myJSON.getJSONArray("wines");
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
}
