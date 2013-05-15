package edu.cui.wineapp;
import java.util.ArrayList;
import java.util.List;

import edu.cui.wineapp.DisplaySearchActivity;
import edu.cui.wineapp.R;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    SlidingMenu menu;

	@Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
		Wine wineToPass = myWineList.get(position);
		Intent i = new Intent(this,WineInfo.class);
		Bundle bundle2 = new Bundle();
	    bundle2.putLong("passedWine", wineToPass.getId());
		i.putExtras(bundle2);
		startActivity(i);
	}

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
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindWidth(300);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);        
        
		
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
	public boolean onQueryTextChange(String parseText) throws SQLiteException{
		if(parseText.length() >= 3){
			
			myWineList = new ArrayList<Wine>();
			wineNames = new ArrayList<String>();
			
		
			Log.i("Inside_QTChange","Text>=3");
			
			SearchView searchView = (SearchView) findViewById(R.id.searchView1);
	        searchView.setIconifiedByDefault(false);
	        searchView.setOnQueryTextListener(this);
	        searchView.setOnCloseListener(this);
	        mListView = (ListView) findViewById(android.R.id.list);
	        
			
			WineManager wManager = new WineManager(this);
			
			try{
				Log.i("Inside_QTChange","TRY");	
				myWineList = wManager.getWineByName(parseText);
				
				for(Wine currWine : myWineList){
					Log.i("PARSINGWINES",currWine.getName());
					wineNames.add(currWine.getName());
                }
				
				for(String currWine : wineNames){
					Log.i("PARSNG_WINSZ",currWine);
				}

				myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,wineNames);
				mListView.setAdapter(myAdapter);
		    	myAdapter.notifyDataSetChanged();
				
			}catch (SQLiteException e){
				Log.i("Inside_QTChange","CATCH");
				myWineList = wManager.downloadWineByName(parseText);
				
				for(Wine currWine: myWineList){
                    wineNames.add(currWine.getName());
                }

				myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,wineNames);
				mListView.setAdapter(myAdapter);
		    	myAdapter.notifyDataSetChanged();
			}
			return true;
		}
		
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		wineNames = new ArrayList<String>();
		myWineList = new ArrayList<Wine>();
		
		
		Log.i("Inside_QTChange","Text>=3");
		
		SearchView searchView = (SearchView) findViewById(R.id.searchView1);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        mListView = (ListView) findViewById(android.R.id.list);
        
		
		WineManager wManager = new WineManager(this);
		myWineList = wManager.downloadWineByName(arg0);
		
		
		for(Wine currWine : myWineList){
			if(currWine!=null) wineNames.add(currWine.getName());
		}
		
		myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,wineNames);
		mListView.setAdapter(myAdapter);
    	myAdapter.notifyDataSetChanged();
			return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			menu.toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
