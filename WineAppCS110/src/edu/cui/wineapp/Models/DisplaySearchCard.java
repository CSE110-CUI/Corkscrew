package edu.cui.wineapp.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

import edu.cui.wineapp.R;

public class DisplaySearchCard extends Card {
	String rank;
	String region;
	String vintage;
	String color;
	String winery;
	String price;

	public DisplaySearchCard(String title,String rank, String region, 
			String vintage, String color, String winery,String price){
		super(title);
		this.rank = rank;
		this.region = region;
		this.color = color;
		this.vintage = vintage;
		this.winery = winery;
		this.price = price;
		//Name
		//SnoothRank
		//Region
		//Vintage
		//Color (orType?)
		//Winery
	}

	public DisplaySearchCard(String name, float rank, String region,
			String vintage, String color, String winery, float price) {
		super(name);
		this.rank = String.valueOf(rank);
		this.region = region;
		this.color = color;
		this.vintage = vintage;
		this.winery = winery;
		this.price = String.valueOf(price);	
	}

	public DisplaySearchCard(Wine curWine) {
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
		View view = LayoutInflater.from(context).inflate(R.layout.display_wine_card_basic, null);

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
