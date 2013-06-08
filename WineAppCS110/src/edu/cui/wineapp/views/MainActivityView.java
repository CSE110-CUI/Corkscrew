package edu.cui.wineapp.views;

import java.util.ArrayList;

import com.fima.cardsui.views.CardUI;

import edu.cui.wineapp.MainActivity;
import edu.cui.wineapp.R;
import edu.cui.wineapp.R.id;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;


public class MainActivityView extends View{
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private CardUI mCardView;
	private CardUI mCardName;
	private SearchView mSearchView;
	private ArrayList<String> drawerNames = new ArrayList<String>();
	
	public MainActivityView(Activity activity){
		super(activity,null);
		
		View view = MainActivity.mainActivity.getWindow().getDecorView().getRootView();
		
		
		mDrawerLayout = (DrawerLayout)view.findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) view.findViewById(R.id.left_drawer);
		mSearchView = (SearchView) view.findViewById(R.id.searchView1);
		mCardName = (CardUI) view.findViewById(R.id.cardName);
		mCardView = (CardUI) view.findViewById(R.id.cardsview);
		
		
		
	}

	public DrawerLayout getmDrawerLayout() {
		return mDrawerLayout;
	}

	public ListView getmDrawerList() {
		return mDrawerList;
	}

	public ActionBarDrawerToggle getmDrawerToggle() {
		return mDrawerToggle;
	}

	public CharSequence getmDrawerTitle() {
		return mDrawerTitle;
	}

	public CharSequence getmTitle() {
		return mTitle;
	}

	public CardUI getmCardView() {
		return mCardView;
	}

	public CardUI getmCardName() {
		return mCardName;
	}

	public SearchView getmSearchView() {
		return mSearchView;
	}

	public void setmDrawerLayout(DrawerLayout mDrawerLayout) {
		this.mDrawerLayout = mDrawerLayout;
	}

	public void setmDrawerList(ListView mDrawerList) {
		this.mDrawerList = mDrawerList;
	}

	public void setmDrawerToggle(ActionBarDrawerToggle mDrawerToggle) {
		this.mDrawerToggle = mDrawerToggle;
	}

	public void setmDrawerTitle(CharSequence mDrawerTitle) {
		this.mDrawerTitle = mDrawerTitle;
	}

	public void setmTitle(CharSequence mTitle) {
		this.mTitle = mTitle;
	}

	public void setmCardView(CardUI mCardView) {
		this.mCardView = mCardView;
	}

	public void setmCardName(CardUI mCardName) {
		this.mCardName = mCardName;
	}

	public void setmSearchView(SearchView mSearchView) {
		this.mSearchView = mSearchView;
	}

	public ArrayList<String> getDrawerNames() {
		return drawerNames;
	}

	public void setDrawerNames(ArrayList<String> drawerNames) {
		this.drawerNames = drawerNames;
	}
}
