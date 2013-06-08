
package edu.cui.wineapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;
import edu.cui.wineapp.controllers.DisplaySearchController;
import edu.cui.wineapp.views.DisplaySearchView;

public class DisplaySearchActivity extends Activity {


	//private ArrayList<Wine> wines;
	//private ArrayList<String> wineNames;
	public static DisplaySearchActivity disSearchAct;
	
	//CardUI mCardView;//
    Bundle bundle = new Bundle();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);
        disSearchAct = this;
        final DisplaySearchView dSearchView = new DisplaySearchView(this);
        final DisplaySearchController dSearchController = new DisplaySearchController(this, dSearchView);
        
        /*wineNames = new ArrayList<String>();
        wines = new ArrayList<Wine>();
      
        

		String parseText = getIntent().getExtras().getString("passedSearchTerm");

		//mCardView = (CardUI) findViewById(R.id.cardsview);
		dSearchView.getmCardView().setSwipeable(false);*/

        //ListView mListView = (ListView) findViewById(android.R.id.list);

/*        WineManager wManager = new WineManager(this);
        wines = wManager.downloadWineByName(parseText);
 */
/*
        for (Wine currWine : wines) {
			mCardView.addCard(new MyCard(currWine.getName()));
        }
  */      
/*        for(final Wine currWine:wines) {
			
			DisplaySearchCard currentCard = new DisplaySearchCard(currWine.getName(),String.valueOf(currWine.getSnoothrank()),currWine.getRegion(),currWine.getVintage(), currWine.getType(), currWine.getWinery(),String.valueOf(currWine.getPrice()));
			
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
			
			dSearchView.getmCardView().addCard(currentCard);

		}
		dSearchView.getmCardView().refresh();
*/        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.search_menu, menu);
	    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
	    // Configure the search info and add any event listeners

	    return super.onCreateOptionsMenu(menu);
	}

}