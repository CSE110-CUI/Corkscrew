package edu.cui.wineapp.controllers;

import java.util.ArrayList;

import android.app.Activity;

import com.fima.cardsui.views.CardUI;

import edu.cui.wineapp.DisplaySearchActivity;
import edu.cui.wineapp.models.Wine;
import edu.cui.wineapp.models.managers.DisplaySearchManager;
import edu.cui.wineapp.views.DisplaySearchView;

public class DisplaySearchController {
    private DisplaySearchView disSearchView;
    private DisplaySearchManager dSManager;
    private ArrayList<Wine> wines;
	private ArrayList<String> wineNames;
    //wineNames = new ArrayList<String>();
    //wines = new ArrayList<Wine>();
  
    public DisplaySearchController(Activity activity, DisplaySearchView dSV)
    {
    	dSManager = new DisplaySearchManager();
    	this.disSearchView = dSV;
    	
    	
    	wineNames = new ArrayList<String>();
    	wines = new ArrayList<Wine>();
    	
    	
    	/*dSV.getmCardView().setSwipeable(false);*/
    	setSwipeableCards(dSV.getmCardView(), false);

/*    	String parseText = DisplaySearchActivity.disSearchAct.getIntent().getExtras().getString("passedSearchTerm");
 */
    	this.wines = dSManager.textParser(DisplaySearchActivity.disSearchAct.getIntent());
    	
/*    	WineManager wManager = new WineManager(DisplaySearchActivity.disSearchAct);
        wines= wManager.downloadWineByName(parseText);
*/        
    /*
        for (Wine currWine : wines) {
    		mCardView.addCard(new MyCard(currWine.getName()));
        }
    */      
 /*       for(final Wine currWine:wines) {
    		
    		//DisplaySearchCard currentCard = new DisplaySearchCard(currWine.getName(),String.valueOf(currWine.getSnoothrank()),currWine.getRegion(),currWine.getVintage(), currWine.getType(), currWine.getWinery(),String.valueOf(currWine.getPrice()));
    		
    		//wineName = currWine.getCode();
    		
    		currentCard.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    		        Intent i = new Intent(getApplication().getApplicationContext(), WineInfo.class);
    		        DisplaySearchActivity.disSearchAct.bundle.putString("passedWine", currWine.getCode());
    		        i.putExtras(bundle);
    		        startActivity(i);
    			}
    		});
    		
    		dSearchView.getmCardView().addCard(currentCard);

    	}*/
    	dSManager.putWineInfoOnCards(wines, dSV.getmCardView());
    	this.refreshCard(dSV.getmCardView());

    }
    
    public void setSwipeableCards(CardUI card, boolean nope)
    {
    	card.setSwipeable(nope);
    }

    public void refreshCard(CardUI card)
    {
    	card.refresh();
    }
	//mCardView = (CardUI) findViewById(R.id.cardsview);

    //ListView mListView = (ListView) findViewById(android.R.id.list);

}
