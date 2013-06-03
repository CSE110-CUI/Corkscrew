package edu.cui.wineapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;

import java.util.ArrayList;

import com.fima.cardsui.views.CardUI;

public class DisplaySearchActivity extends Activity {


	private ArrayList<Wine> wines;
	private ArrayList<String> wineNames;
	CardUI mCardView;
    Bundle bundle = new Bundle();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);
        
        wineNames = new ArrayList<String>();
        wines = new ArrayList<Wine>();

		String parseText = getIntent().getExtras().getString("passedSearchTerm");

		mCardView = (CardUI) findViewById(R.id.cardsview);
		mCardView.setSwipeable(false);

        //ListView mListView = (ListView) findViewById(android.R.id.list);

        WineManager wManager = new WineManager(this);
        wines = wManager.downloadWineByName(parseText);
/*
        for (Wine currWine : wines) {
			mCardView.addCard(new MyCard(currWine.getName()));
        }
  */      
        for(final Wine currWine:wines) {
			
			DisplaySearchCard currentCard = new DisplaySearchCard(currWine.getName(),"00",currWine.getRegion(),currWine.getVintage(), currWine.getType(), currWine.getWinery());
			
			//wineName = currWine.getCode();
			
			currentCard.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
			        Intent i = new Intent(getApplication().getApplicationContext(), WineInfo.class);
			        bundle.putString("passedWine", currWine.getCode());
			        i.putExtras(bundle);
			        startActivity(i);
				}
			});
			
			mCardView.addCard(currentCard);

		}
		mCardView.refresh();
        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.search_menu, menu);
	    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
	    // Configure the search info and add any event listeners
	    
	    searchView.setOnCloseListener(new OnCloseListener(){

			@Override
			public boolean onClose() {
				// TODO Auto-generated method stub
				return false;
			}
	    	
	    });

	    return super.onCreateOptionsMenu(menu);
	}

}