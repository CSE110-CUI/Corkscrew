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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class DAO{
	private static DAO ourInstance = new DAO();
	private ArrayList<Wine> wines=new ArrayList<Wine>();
	private boolean finish=false;
	private DAO(){}
	public static DAO getInstance(){
		return ourInstance;
	}
	
	
	public ArrayList<Wine> getWineByName(String name){
	    String apiKey 		= "588eeca229b7895ff55a";
	    String urlPreTerm 	= "http://api.adegga.com/rest/v1.0/GetWinesByName/";
	    String urlPostTerm 	= "/&format=json&key=";
	    String searchTerm 	= name;
	    String stringUrl 	= urlPreTerm + searchTerm + urlPostTerm + apiKey;
	    Log.e("DEBUG","url has been built");
	   // ConnectivityManager connMgr = 
	   // 		(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
	    Log.e("DEBUG","Manager has been built");
	//	NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		
	//	if (networkInfo != null && networkInfo.isConnected()) {
	//		return null;//
	    new DownloadWebpageText().execute(stringUrl);
		//} 
	//	else {
	//		Log.e("DEBUG","No network connection available.");
	//	}
	    Log.e("DEBUG","Wine has been built");
	    if(wines.size()==0){
	    	Log.e("DEBUG","error");
	    }else{
	    	Log.e("DEBUG",wines.get(0).toString());
	    }
		return wines;
	}
	public Wine getWineById(int wineId){
		return new Wine("that", "1", "", null, null, null, null, null);
	}
	public boolean createUser(String name, int age, float weight){
		return false;
	}
	public boolean deleteUser(String name){
		return false;
	}
	public User getUserByName(String userName){
		return null;
	}
	public boolean setUserName(String userName){
		return false;
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
	public boolean setUserEmail(String email){
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
    	
    	
        private static final String TAG_WINES = "wine"; 
        private static final String TAG_AVIN = "avin";
        private static final String TAG_NAME = "name";
        private static final String TAG_COUNTRY = "country";
        private static final String TAG_REGION = "region";
        private static final String TAG_PRODUCER = "producer";
        private static final String TAG_VARIETALS = "varietals";
        private static final String TAG_LABEL = "label_url";
        private static final String TAG_RATING = "rating";
        JSONArray winesJSON = null; 
		protected void execute(String... urls) {
			String result="";
	        try {result = downloadUrl(urls[0]);} catch (IOException e) {
	        	Log.e("DEBUG","Unable to retrieve web page. URL may be invalid.");}
	        onPostExecute(result);
		}

		protected void onPostExecute(String result) {
	    	parseXML(result);
	    	finish=true;
	    	Log.e("DEBUG",wines.get(0).toString());
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
							currentWine.getString(TAG_AVIN),
							currentWine.getString(TAG_NAME),
							currentWine.getString(TAG_COUNTRY),
							currentWine.getString(TAG_REGION),
							currentWine.getString(TAG_PRODUCER),
							currentWine.getString(TAG_VARIETALS),
							currentWine.getString(TAG_LABEL),
							currentWine.getString(TAG_RATING)
					);
					//Log.i("DEBUG",newWine.toString());
					wines.add(newWine);
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
