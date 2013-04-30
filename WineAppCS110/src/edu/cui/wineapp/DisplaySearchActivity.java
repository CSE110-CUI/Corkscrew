package edu.cui.wineapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplaySearchActivity extends ListActivity {
	
	private static final String DEBUG_TAG = "HttpExample";
	
    private String[] tags 	 = 
    	{"\"avin\":",		"\"name\":",		"\"country\":",
    	 "\"region\":",		"\"producer\":",	"\"varietals\":",
    	 "\"label_url\":",	"\"rating\":"};
    
    private String[] tagVals = new String[tags.length];
    private Boolean[] edited = new Boolean[tags.length];
    private ArrayList<Wine> myProducts = new ArrayList<Wine>();

    private String[] prodInfo = 
    	{"nah","nah","nah","nah","nah","nah","nah","nah","nah",
         "nah","nah","nah","nah","nah","nah","nah","nah","nah",
         "nah","nah","nah","nah","nah","nah","nah"};
    
	ArrayAdapter<String> myAdapter;// = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, prodInfo);


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		for(int i = 0; i < tags.length; ++i){edited[i] = false;}
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
		 //BEGIN CHECKING NET CONNECTION
		
		    
	    String apiKey =  "588eeca229b7895ff55a";
	    String urlPreTerm = "http://api.adegga.com/rest/v1.0/GetWinesByName/";
	    String urlPostTerm = "/&format=json&key=";
	    String searchTerm = message;
	    String stringUrl = urlPreTerm + searchTerm + urlPostTerm + apiKey;
		 
	     ConnectivityManager connMgr = (ConnectivityManager) 
    			getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
	            new DownloadWebpageText().execute(stringUrl);
			} else {
				//textView.setText("No network connection available.");    
				Log.e("DEBUG","No network connection available.");
			}
			
		myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, prodInfo);
		setListAdapter(myAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_search, menu);
		return true;
	}
	
	@Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
		Wine wineToPass = (Wine) myProducts.get(position);
		Intent i = new Intent(this,WineInfo.class);
		i.putExtra("passedWine",wineToPass);
		startActivity(i);
	}

	
    // Uses AsyncTask to create a task away from the main UI thread. This task takes a 
    // URL string and uses it to create an HttpUrlConnection. Once the connection
    // has been established, the AsyncTask downloads the contents of the webpage as
    // an InputStream. Finally, the InputStream is converted into a string, which is
    // displayed in the UI by the AsyncTask's onPostExecute method.
    private class DownloadWebpageText extends AsyncTask<String, String, String>{
    	

    @Override  
	protected String doInBackground(String... urls) {
		 // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
	}
	
	 // onPostExecute displays the results of the AsyncTask.
    @Override
	protected void onPostExecute(String result) {
    	ArrayList<Wine> pr0ds = parseXML(result, tags);
    	for(int i = 0; i < 25; ++i){prodInfo[i] = pr0ds.get(i).toString();}
    	myAdapter.notifyDataSetChanged();
   }
    
   public ArrayList<Wine> parseXML(String preParsed, String[] tags){
	   //name country producer varietals
	   preParsed = preParsed + "\nENDOFFILEREACHCED";
	   Log.i("entireString",preParsed);
	   
	   Scanner myScan = new Scanner(preParsed);
	   
	   while(myScan.findInLine("ENDOFFILE") == null){
		   String scannedLine = myScan.nextLine();
		   
		   for(int j = 0; j < tags.length; ++j){
			   if(scannedLine.contains(tags[j])){
				   tagVals[j] = scannedLine;
				   edited[j] = true;
				   Log.i("tagFound",scannedLine);
			   }
		   }
		   
		   if(!(Arrays.asList(edited).contains(false))){
			   Wine myProd = new Wine(tagVals);
			   myProducts.add(myProd);
			   for(int k = 0; k < edited.length; ++k)
				   edited[k] = false;
		   }

		   
	   }
	   
	   return myProducts;
   }

    
 // Given a URL, establishes an HttpUrlConnection and retrieves
 // the web page content as a InputStream, which it returns as
 // a string.
 private String downloadUrl(String myurl) throws IOException {
     InputStream is = null;
     // Only display the first 500 characters of the retrieved
     // web page content.
     try {
         URL url = new URL(myurl);
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();
         conn.setReadTimeout(10000 /* milliseconds */);
         conn.setConnectTimeout(15000 /* milliseconds */);
         conn.setRequestMethod("GET");
         conn.setDoInput(true);
         // Starts the query
         conn.connect();
         int response = conn.getResponseCode();
         Log.d(DEBUG_TAG, "The response is: " + response);
         is = conn.getInputStream();

         // Convert the InputStream into a string
         String contentAsString = readIt(is);
         return contentAsString;
         
     // Makes sure that the InputStream is closed after the app is
     // finished using it.
     } finally {
         if (is != null) {
             is.close();
         } 
     }
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
