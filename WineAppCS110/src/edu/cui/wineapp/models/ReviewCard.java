package edu.cui.wineapp.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

import edu.cui.wineapp.R;
import edu.cui.wineapp.R.id;
import edu.cui.wineapp.R.layout;

public class ReviewCard extends Card {
	String source;
	String name;
	String rating;
	String body;

	public ReviewCard(String name,String source, String rating, 
			String body){
		//super(name);
		this.source = source;
		this.rating = rating;
		this.body = body;
		this.name = name;

		//Name
		//SnoothRank
		//Region
		//Vintage
		//Color (orType?)
		//Winery
	}

	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.review, null);

		//((TextView) view.findViewById(R.id.source)).setText(source);
		((TextView) view.findViewById(R.id.rating)).setText(rating);

		if(rating.contains("0.0")){
			((TextView) view.findViewById(R.id.rating)).setText("NA");

		}
		((TextView) view.findViewById(R.id.body)).setText(body);
		((TextView) view.findViewById(R.id.name)).setText(name);
//		((TextView) view.findViewById(R.id.winery)).setText(winery);
	//	((TextView) view.findViewById(R.id.rank)).setText(rank);
		

		
		return view;
	}

	
	
	
}
