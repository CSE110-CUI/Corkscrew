package edu.cui.wineapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

public class DisplaySearchCard extends Card {
	String rank;
	String region;
	String vintage;
	String color;
	String winery;

	public DisplaySearchCard(String title,String rank, String region, 
			String vintage, String color, String winery){
		super(title);
		this.rank = rank;
		this.region = region;
		this.color = color;
		this.vintage = vintage;
		this.winery = winery;
		//Name
		//SnoothRank
		//Region
		//Vintage
		//Color (orType?)
		//Winery
	}

	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.display_wine_card_basic, null);

		((TextView) view.findViewById(R.id.title)).setText(title);
		((TextView) view.findViewById(R.id.region)).setText(region);
		((TextView) view.findViewById(R.id.vintage)).setText(vintage);
		((TextView) view.findViewById(R.id.type)).setText(color);
		((TextView) view.findViewById(R.id.winery)).setText(winery);
		((TextView) view.findViewById(R.id.rank)).setText(rank);
		

		
		return view;
	}

	
	
	
}
