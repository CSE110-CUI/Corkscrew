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
import android.widget.Toast;

public class DisplaySearchActivity extends ListActivity {
	
        
    private ArrayList<Wine> wines;
    private WineManager wineManager;
    private UserManager userManager;
    private String[] prodInfo = new String[25];

	ArrayAdapter<String> myAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userManager = UserManager.getUserManager(this);
		/** this code below is used for test!
		User account = new User("Zhaoyang_Zeng", 19, (float)50.0, "zeng0129@gmail.com","b","c","d",(long)-1);
	    userManager.createUser(account, "asdf");
	    Toast.makeText(this,userManager.getUserByName(account.getName()).getEmail(), Toast.LENGTH_SHORT).show();
	    **/
		for(int i = 0; i < prodInfo.length; ++i) prodInfo[i] = "Loading...";
				
		Intent intent 	= getIntent();
		String message 	= intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		wineManager = WineManager.getWineManager(this);
		new DownloadWebpageText().execute(message);
		myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, prodInfo);
		setListAdapter(myAdapter);
	}
	
	@Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
		Wine wineToPass = (Wine) wines.get(position);
		Intent i = new Intent(this,WineInfo.class);
		Bundle bundle2 = new Bundle();
	    bundle2.putLong("passedWine", wineToPass.getId());
		i.putExtras(bundle2);
		startActivity(i);
	}

private class DownloadWebpageText extends AsyncTask<String, String, String>{

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String message = arg0[0];
		wines = wineManager.downloadWineByName(message);
		return null;
	}   	
	protected void onPostExecute(String result) {
		for(int i = 0; i < 25; ++i){
			prodInfo[i] = wines.get(i).toString();
		}
		myAdapter.notifyDataSetChanged();
	}
}
}
