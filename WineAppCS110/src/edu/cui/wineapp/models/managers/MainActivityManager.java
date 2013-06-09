package edu.cui.wineapp.models.managers;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.fima.cardsui.views.CardUI;

import edu.cui.wineapp.MainActivity;
import edu.cui.wineapp.WineInfoActivity;
import edu.cui.wineapp.models.CurrentlyDrinkingCard;
import edu.cui.wineapp.models.DisplaySearchCard;
import edu.cui.wineapp.models.MyCard;
import edu.cui.wineapp.models.Wine;



public class MainActivityManager extends Activity{
	
	ArrayList<String>wineNames = new ArrayList<String>();
	ArrayList<Wine>myWineList  = new ArrayList<Wine>();
	WineManager wManager = new WineManager(MainActivity.mainActivity.getApplication().getApplicationContext());
	Bundle bundle2 = new Bundle();


	public boolean fetchLocalWines(String parseText, CardUI mCardView) {


		try {
			myWineList = wManager.getWineByName(parseText);
		} catch (SQLiteException e) {
			return false;
		} finally {
			mCardView.clearCards();
			populateCardStack(mCardView);
			mCardView.refresh();
		}
		return true;
	}


	private void populateCardStack(CardUI mCardView) {
		for(final Wine currWine:myWineList) {
			DisplaySearchCard currentCard = new DisplaySearchCard(currWine);

			currentCard.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(MainActivity.mainActivity.getApplication().getApplicationContext(), WineInfoActivity.class);
					bundle2.putString("passedWine", currWine.getCode());
					i.putExtras(bundle2);
					MainActivity.mainActivity.startActivity(i);
				}
			});

			mCardView.addCard(currentCard);
		}
		
	}


}
