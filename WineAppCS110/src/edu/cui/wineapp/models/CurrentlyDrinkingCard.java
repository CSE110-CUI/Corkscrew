package edu.cui.wineapp.models;

import edu.cui.wineapp.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class CurrentlyDrinkingCard extends MyCard {
	
	String rank;
	String region;
	String vintage;
	String color;
	String winery;
	String price;
	
	public CurrentlyDrinkingCard(Wine curWine) {
		super(curWine.getName());
		this.rank = String.valueOf(curWine.getSnoothrank());
		this.region = curWine.getRegion();
		this.color = curWine.getType();
		this.vintage = curWine.getVintage();
		this.winery = curWine.getWinery();
		this.price = String.valueOf(curWine.getPrice());
	}

	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_currentlydrinking, null);

		((TextView) view.findViewById(R.id.title)).setText(title);
		((TextView) view.findViewById(R.id.region)).setText(region);
		((TextView) view.findViewById(R.id.vintage)).setText(vintage);
		((TextView) view.findViewById(R.id.type)).setText(color);
		((TextView) view.findViewById(R.id.winery)).setText(winery);
		((TextView) view.findViewById(R.id.rank)).setText(rank);
		((TextView) view.findViewById(R.id.price)).setText(price);
		

		
		return view;
	}


}
