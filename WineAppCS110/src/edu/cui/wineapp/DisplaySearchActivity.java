package edu.cui.wineapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import org.json.*;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplaySearchActivity extends ListActivity {
	
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
        
    JSONArray wines = null;
        
    private ArrayList<Wine> myProducts = new ArrayList<Wine>();
    private String[] prodInfo = new String[25];

	ArrayAdapter<String> myAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    for(int i = 0; i < prodInfo.length; ++i) prodInfo[i] = "Loading...";
				
		Intent intent 	= getIntent();
		String message 	= intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		    
	    String apiKey 		= "588eeca229b7895ff55a";
	    String urlPreTerm 	= "http://api.adegga.com/rest/v1.0/GetWinesByName/";
	    String urlPostTerm 	= "/&format=json&key=";
	    String searchTerm 	= message;
	    String stringUrl 	= urlPreTerm + searchTerm + urlPostTerm + apiKey;
		 
	    ConnectivityManager connMgr = 
	    		(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		
		if (networkInfo != null && networkInfo.isConnected()) {
			new DownloadWebpageText().execute(stringUrl);} 
		else {Log.e("DEBUG","No network connection available.");}
			
		myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, prodInfo);
		setListAdapter(myAdapter);
	}
	
	@Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
		Wine wineToPass = (Wine) myProducts.get(position);
		Intent i = new Intent(this,WineInfo.class);
		i.putExtra("passedWine",wineToPass);
		startActivity(i);
	}

    private class DownloadWebpageText extends AsyncTask<String, String, String>{
	
	    @Override  
		protected String doInBackground(String... urls) {
	        try {return downloadUrl(urls[0]);} catch (IOException e) {
	        	return "Unable to retrieve web page. URL may be invalid.";}
		}
		
	    @Override
		protected void onPostExecute(String result) {
	    	ArrayList<Wine> pr0ds = parseXML(result);
	    	for(int i = 0; i < 25; ++i){prodInfo[i] = pr0ds.get(i).toString();}
	    	myAdapter.notifyDataSetChanged();
	   }
	    
	   public ArrayList<Wine> parseXML(String preParsed){
		   try {
				JSONObject myJSON = new JSONObject(preParsed);
				myJSON = myJSON.getJSONObject("response");
				myJSON = myJSON.getJSONObject("aml");
				myJSON = myJSON.getJSONObject("wines");
				wines = myJSON.getJSONArray(TAG_WINES);
				Log.i("TRY","made it past myJSON");
		
				
				for(int i = 0; i < wines.length(); i++){
					JSONObject currentWine = wines.getJSONObject(i);
					
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
					
					myProducts.add(newWine);
				}
		   } catch (JSONException e) {e.printStackTrace();}  
		   return myProducts;
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
