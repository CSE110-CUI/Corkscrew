package edu.cui.wineapp;
/*
import java.util.ArrayList;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.fima.cardsui.views.CardUI;

import edu.cui.wineapp.Controllers.UserManager;
import edu.cui.wineapp.Models.CurrentlyDrinkingCard;
import edu.cui.wineapp.Models.DisplaySearchCard;
import edu.cui.wineapp.Models.MyCard;
import edu.cui.wineapp.Models.Wine;
*/
public class MainActivityBU
//extends Activity 
{
/*
	public final static String EXTRA_MESSAGE = "com.cui.wineapp.MESSAGE";
	ArrayList<Wine> myWineList = null;
	ArrayList<String> wineNames = new ArrayList<String>();
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private CardUI mCardView;
	private ArrayList<String> sideMenu = new ArrayList<String>();
	String wineName; 
	TextView name;
	static MainActivity mainActivity;


	Bundle bundle2 = new Bundle();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainActivity = this;
		
		final MainActivityView mainView = new MainActivityView(this);
		final MainActivityController mainController = new MainActivityController(this, mainView);
	/*	
		CardUI name = (CardUI) findViewById(R.id.cardName);
		name.clearCards();
		name.addCard(new MyCard("Welcome.","To Get Started, Search for a Wine Below"));
		name.refresh();
		name.setSwipeable(false);
		Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "HERE", Toast.LENGTH_LONG).show();
	*/
		//In MainActivityView
		//mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		//mDrawerList = (ListView) findViewById(R.id.left_drawer);
		//SearchView searchView = (SearchView) findViewById(R.id.searchView1);
		//CardUI name = (CardUI) findViewById(R.id.cardName);

		//init search view
		//searchView.setIconifiedByDefault(false);
		//searchView.setOnQueryTextListener(this);
		//searchView.setOnCloseListener(this);
		/*
		if(UserManager.getUserManager(getApplicationContext()).getLocalUser() == null){
			name.clearCards();
			name.addCard(new MyCard("Welcome.","To Get Started, Search for a Wine Below"));
			name.refresh();
			name.setSwipeable(false);
		}
		
		else{
			name.clearCards();
			Wine curWine = UserManager.getUserManager(getApplicationContext()).getLocalUser().getCurrentWine();
			name.addCard(new CurrentlyDrinkingCard(curWine));
			name.refresh();
			name.setSwipeable(false);
		}
		*/
		//mTitle = mDrawerTitle = getTitle();
		
		//sideMenu.add("Choose-a-Wine");
		//sideMenu.add("Build-a-Wine");
		//sideMenu.add("Pair-a-Wine");
		//sideMenu.add("Wine History");

		// set a custom shadow that overlays the main content when the drawer opens
		// set up the drawer's list view with items and click listener
		//mDrawerList.setAdapter(new CustomAdapter(this, sideMenu));



		//In MAV
	//	mCardView = (CardUI) findViewById(R.id.cardsview);
		
		//mCardView.setSwipeable(false);
		//mCardView.refresh();

		//getActionBar().setDisplayHomeAsUpEnabled(true);
		//getActionBar().setHomeButtonEnabled(true);

		//mDrawerToggle = new ActionBarDrawerToggle(
			//	this,                  /* host Activity */
				//mDrawerLayout,         /* DrawerLayout object */
				//R.drawable.ic_launcher,  /* nav drawer image to replace 'Up' caret */
				//R.string.drawer_open,  /* "open drawer" description for accessibility */
				//R.string.drawer_close  /* "close drawer" description for accessibility */
				//) {
			//public void onDrawerClosed(View view) {
				//getActionBar().setTitle(mTitle);
				//invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			//}

			//public void onDrawerOpened(View drawerView) {
				//getActionBar().setTitle(mDrawerTitle);
				//invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			//}
		//};
		
		//mDrawerLayout.setDrawerListener(mDrawerToggle);
		//mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


		//if (savedInstanceState == null) {
			//selectItem(0);
		//}

		//Log.i("Inside_OnCreate", "Basic Info");


	}
/*
	private void determineTitleView(CardUI name) {
		if(UserManager.getUserManager(getApplicationContext()).getLocalUser() == null){
			name.clearCards();
			name.addCard(new MyCard("Welcome.","To Get Started, Search for a Wine Below"));
			name.refresh();
			name.setSwipeable(false);
		}
		
		else{
			name.clearCards();
			Wine curWine = UserManager.getUserManager(getApplicationContext()).getLocalUser().getCurrentWine();
			name.addCard(new CurrentlyDrinkingCard(curWine));
			name.refresh();
			name.setSwipeable(false);
		}
	}

	private void setUpSearchView(SearchView searchView) {
		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);
	}

	private void populateDrawerItems() {
		mTitle = mDrawerTitle = getTitle();
		sideMenu.add("Choose-a-Wine");
		sideMenu.add("Build-a-Wine");
		sideMenu.add("Pair-a-Wine");
		sideMenu.add("Wine History");
	}

	private void updateTitleCard(CardUI name) {
		if(UserManager.getUserManager(getApplicationContext()).getLocalUser() == null){
			name.clearCards();
			name.addCard(new MyCard("Welcome.","To Get Started, Search for a Wine Below"));
			name.refresh();
			name.setSwipeable(false);
		}

		else{
			name.clearCards();
			Wine curWine = UserManager.getUserManager(getApplicationContext()).getLocalUser().getCurrentWine();
			name.addCard(new CurrentlyDrinkingCard(curWine));
			name.refresh();
			name.setSwipeable(false);
		}
	}
*//*
	@Override
	protected void onResume(){
		super.onResume();
		CardUI name = (CardUI) findViewById(R.id.cardName);
		//updateTitleCard(name);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id) {
			switch(position){
			case 3:
				Intent i = new Intent(MainActivity.this,WineCellar.class);
				startActivity(i);
				break;

			}
		}
	}


}
*/

