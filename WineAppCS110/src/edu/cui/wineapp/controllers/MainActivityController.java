package edu.cui.wineapp.controllers;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.SearchView;

import com.fima.cardsui.views.CardUI;

import edu.cui.wineapp.DisplaySearchActivity;
import edu.cui.wineapp.MainActivity;
import edu.cui.wineapp.R;
import edu.cui.wineapp.WineInfoActivity;
import edu.cui.wineapp.adapters.CustomAdapter;
import edu.cui.wineapp.models.CurrentlyDrinkingCard;
import edu.cui.wineapp.models.DisplaySearchCard;
import edu.cui.wineapp.models.MyCard;
import edu.cui.wineapp.models.Wine;
import edu.cui.wineapp.models.managers.MainActivityManager;
import edu.cui.wineapp.models.managers.UserManager;
import edu.cui.wineapp.views.MainActivityView;

public class MainActivityController implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener {
	MainActivityView currView;
	MainActivityManager currManager;


	public MainActivityController(Activity activity, MainActivityView currView, MainActivityManager currManager){

		this.currView = currView;
		this.currManager = currManager;

		/*** testing ***/
		//initUser();
		initSearchView(currView.getmSearchView());
		initTitleView(currView.getmCardName());
		initCards(currView.getmCardView());
		initDrawerNames(currView.getDrawerNames());
		initDrawerView(currView.getmDrawerList());
		initAcionBar();
		initDrawerToggle();
	}

	public void initUser()
	{
		UserManager uManager = UserManager.getUserManager(MainActivity.mainActivity.getApplication().getApplicationContext());//.setLocalUser("1", 1, 1, "1", "1", "1", "1", 1));

	}

	private void initDrawerToggle() {
		ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
				MainActivity.mainActivity,                  /* host Activity */
				currView.getmDrawerLayout(),         /* DrawerLayout object */
				R.drawable.ic_launcher,  /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description for accessibility */
				R.string.drawer_close  /* "close drawer" description for accessibility */
				);
	}


	private void initAcionBar() {
	
	}

	private void initDrawerView(ListView mDrawerList) {

		mDrawerList.setAdapter(new CustomAdapter(MainActivity.mainActivity.getApplicationContext(), 
				currView.getDrawerNames()));
		mDrawerList.setOnItemClickListener(new DrawerItemController());
	}

	private void initDrawerNames(ArrayList<String> drawerNames) {
		drawerNames.add("User Profile");
		drawerNames.add("Wine Cellar");
		drawerNames.add("Top Wines");
		drawerNames.add("Get Random Wine");
		drawerNames.add("Current BAC");
		drawerNames.add("Clear BAC");
		drawerNames.add("Clear Wine");
		drawerNames.add("DO NOT TOUCH");
		drawerNames.add("Logout");
	}

	private void initCards(CardUI mCardView) {
		mCardView.setSwipeable(false);
		mCardView.refresh();		
	}

	private void initSearchView(SearchView searchView) {
		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);
	}


	public void initTitleView(CardUI name) {
		name.clearCards();

		if(UserManager.getUserManager(MainActivity.mainActivity.getApplicationContext()).getLocalUser() == null){
			name.addCard(new MyCard("Welcome. ","To Get Started, Search for a Wine Below"));
		}

		else if(UserManager.getUserManager(MainActivity.mainActivity.getApplicationContext()).getLocalUser().getCurrentWine() == null){
			name.addCard(new MyCard("Welcome, "+UserManager.getLocalUser().getName(),"To Get Started, Search for a Wine Below"));
		}

		else{
			final Wine curWine = UserManager.getUserManager(MainActivity.mainActivity.getApplicationContext()).getLocalUser().getCurrentWine();
			CurrentlyDrinkingCard currWine = new CurrentlyDrinkingCard(curWine);
			currWine.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Bundle bundle2 = new Bundle();
					Intent i = new Intent(MainActivity.mainActivity.getApplication().getApplicationContext(), WineInfoActivity.class);
					bundle2.putString("passedWine", curWine.getCode());
					i.putExtras(bundle2);
					MainActivity.mainActivity.startActivity(i);
				}
			});	
			name.addCard(currWine);
		}

		initCards(name);
	}

	@Override
	public boolean onClose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextChange(String parseText) throws SQLiteException {
		CardUI mCardView = currView.getmCardView();
		if (parseText.length() >= 3) {
			return currManager.fetchLocalWines(parseText, mCardView);
		}

		if (parseText.length() == 0) {
			mCardView.clearCards();
			mCardView.refresh();
		}

		return false;
	}


	@Override
	public boolean onQueryTextSubmit(String parseText) {
		Bundle bundle2 = new Bundle();
		Intent i = new Intent(MainActivity.mainActivity.getBaseContext(), DisplaySearchActivity.class);
		bundle2.putString("passedSearchTerm", parseText);
		i.putExtras(bundle2);
		MainActivity.mainActivity.startActivity(i);

		return true;


	}
}
