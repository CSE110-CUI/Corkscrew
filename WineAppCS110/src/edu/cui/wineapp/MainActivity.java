package edu.cui.wineapp;
import java.util.ArrayList;
import java.util.List;

import edu.cui.wineapp.DisplaySearchActivity;
import edu.cui.wineapp.R;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener{
	
	public final static String EXTRA_MESSAGE = "com.cui.wineapp.MESSAGE";
    private ListView mListView;
    private ArrayAdapter<String> myAdapter;
    ArrayList<Wine> myWineList;
	ArrayList<String> wineNames = new ArrayList<String>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		SearchView searchView = (SearchView) findViewById(R.id.searchView1);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        mListView = (ListView) findViewById(android.R.id.list);
        mListView.setAdapter(myAdapter);
		
		Log.i("Inside_OnCreate","Basic Info");

		
	//	setListAdapter(myAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void search (View view){
		
		
		//Intent intent = new Intent(this, DisplaySearchActivity.class);
		/*
		 * EditText editText = (EditText) findViewById(R.id.editText1);
	
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	    
	    */
		
		SearchView searchView = (SearchView) findViewById(R.id.searchView1);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        mListView = (ListView) findViewById(android.R.id.list);
        
        
        

	}

	@Override
	public boolean onClose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextChange(String parseText) {
		if(parseText.length() >= 10){
		
			Log.i("Inside_QTChange","Text>=10");
			
			SearchView searchView = (SearchView) findViewById(R.id.searchView1);
	        searchView.setIconifiedByDefault(false);
	        searchView.setOnQueryTextListener(this);
	        searchView.setOnCloseListener(this);
	        mListView = (ListView) findViewById(android.R.id.list);
	        
			
			WineManager wManager = new WineManager(this);
			wManager.getWineByName(parseText);
			myWineList = wManager.getWineByName(parseText);
						
			
			for(int i = 0; i < myWineList.size(); ++i){
				wineNames.add(myWineList.get(i).toString());
			}
			
			myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,wineNames);
			mListView.setAdapter(myAdapter);
	    	myAdapter.notifyDataSetChanged();
	    	
	    	
		
			
		}
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		wineNames = new ArrayList<String>();
		
		Log.i("Inside_QTChange","Text>=3");
		
		SearchView searchView = (SearchView) findViewById(R.id.searchView1);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        mListView = (ListView) findViewById(android.R.id.list);
        
		
		WineManager wManager = new WineManager(this);
		myWineList = wManager.downloadWineByName(arg0);
		
		
		for(int i = 0; i < myWineList.size(); ++i){
			Wine currWine = myWineList.get(i);
			wineNames.add(currWine.getName());
		}
		
		myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,wineNames);
		mListView.setAdapter(myAdapter);
    	myAdapter.notifyDataSetChanged();
			return false;
	}

}
