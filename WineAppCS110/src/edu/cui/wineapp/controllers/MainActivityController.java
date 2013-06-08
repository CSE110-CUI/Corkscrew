package edu.cui.wineapp.controllers;


import java.util.ArrayList;

import com.fima.cardsui.views.CardUI;

import edu.cui.wineapp.DisplaySearchActivity;
import edu.cui.wineapp.MainActivity;
import edu.cui.wineapp.R;
import edu.cui.wineapp.R.drawable;
import edu.cui.wineapp.R.string;
import edu.cui.wineapp.adapters.CustomAdapter;
import edu.cui.wineapp.models.CurrentlyDrinkingCard;
import edu.cui.wineapp.models.DisplaySearchCard;
import edu.cui.wineapp.models.MyCard;
import edu.cui.wineapp.models.Wine;
import edu.cui.wineapp.models.managers.MainActivityManager;
import edu.cui.wineapp.models.managers.UserManager;
import edu.cui.wineapp.views.MainActivityView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivityController implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener {
	MainActivityView currView;
	MainActivityManager currManager;


	public MainActivityController(Activity activity, MainActivityView currView, MainActivityManager currManager){

		this.currView = currView;
		this.currManager = currManager;

		initSearchView(currView.getmSearchView());
		initTitleView(currView.getmCardName());
		initCards(currView.getmCardView());
		initDrawerNames(currView.getDrawerNames());
		initDrawerView(currView.getmDrawerList());
		initAcionBar();
		initDrawerToggle();
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
		MainActivity.mainActivity.getActionBar().setDisplayHomeAsUpEnabled(true);
		MainActivity.mainActivity.getActionBar().setHomeButtonEnabled(true);		
	}

	private void initDrawerView(ListView mDrawerList) {
		
		mDrawerList.setAdapter(new CustomAdapter(MainActivity.mainActivity.getApplicationContext(), 
				currView.getDrawerNames()));
		mDrawerList.setOnItemClickListener(new DrawerItemController());
	}

	private void initDrawerNames(ArrayList<String> drawerNames) {
		drawerNames.add("Wine Cellar");
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
			name.addCard(new MyCard("Welcome.","To Get Started, Search for a Wine Below"));
		}

		else{
			Wine curWine = UserManager.getUserManager(MainActivity.mainActivity.getApplicationContext()).getLocalUser().getCurrentWine();
			name.addCard(new CurrentlyDrinkingCard(curWine));
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
