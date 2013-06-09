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
  
    public DisplaySearchController(Activity activity, DisplaySearchView dSV)
    {
    	dSManager = new DisplaySearchManager();
    	this.disSearchView = dSV;
    	
    	wineNames = new ArrayList<String>();
    	wines = new ArrayList<Wine>();
    	
    	setSwipeableCards(dSV.getmCardView(), false);

    	this.wines = dSManager.textParser(DisplaySearchActivity.disSearchAct.getIntent());

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

}
