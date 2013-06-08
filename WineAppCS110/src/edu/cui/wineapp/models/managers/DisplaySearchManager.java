package edu.cui.wineapp.models.managers;

import java.util.ArrayList;

import com.fima.cardsui.views.CardUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import edu.cui.wineapp.DisplaySearchActivity;
import edu.cui.wineapp.WineInfoActivity;
import edu.cui.wineapp.models.DisplaySearchCard;
import edu.cui.wineapp.models.Wine;
import edu.cui.wineapp.views.DisplaySearchView;

public class DisplaySearchManager {
	String text;
	ArrayList<Wine> wines;
	Bundle bundle = new Bundle();

	public DisplaySearchManager()
	{
	}
	
	public ArrayList<Wine> textParser(Intent passedIntent)
	{
		this.text = passedIntent.getExtras().getString("passedSearchTerm");
		return getWines(text);
	}
	
	private ArrayList<Wine> getWines(String parsedText)
	{
		wines = new ArrayList<Wine>();
		WineManager wManager = new WineManager(DisplaySearchActivity.disSearchAct.getApplicationContext());
		return wManager.downloadWineByName(parsedText);
	}
	
	public void putWineInfoOnCards(ArrayList<Wine> wineList, CardUI currCard)
	{
		for(final Wine currWine:wineList)
		{
    		DisplaySearchCard currentCard = new DisplaySearchCard(currWine.getName(),String.valueOf(currWine.getSnoothrank()),currWine.getRegion(),currWine.getVintage(), currWine.getType(), currWine.getWinery(),String.valueOf(currWine.getPrice()));
    		currentCard.setOnClickListener(new OnClickListener() {
    			@Override
    			public void onClick(View v) {
    		        Intent i = new Intent(DisplaySearchActivity.disSearchAct.getApplication().getApplicationContext(), WineInfoActivity.class);
    		        bundle.putString("passedWine", currWine.getCode());
    		        i.putExtras(bundle);
    		        DisplaySearchActivity.disSearchAct.startActivity(i);
    			}
    		});
    		currCard.addCard(currentCard);
		}
	}
}
