package edu.cui.wineapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class WineInfo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine_info);
		
		Intent i = getIntent();
		Wine currentWine = (Wine)i.getSerializableExtra("passedWine");
		
		currentWine.stripJSON();
		
		TextView name = (TextView) findViewById(R.id.textView1);
		name.setText(currentWine.getName());	
		
		TextView varietal = (TextView) findViewById(R.id.TextView03);
		varietal.setText(currentWine.getVarietal());		
		
		TextView label_url = (TextView) findViewById(R.id.TextView02);
		label_url.setText(currentWine.getLabel_URL());	
		
		TextView avin = (TextView) findViewById(R.id.TextView05);
		avin.setText(currentWine.getAvin());	
		
		TextView rating = (TextView) findViewById(R.id.TextView04);
		rating.setText(currentWine.getRating());	
		
		TextView country = (TextView) findViewById(R.id.TextView01);
		country.setText(currentWine.getCountry());
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wine_info, menu);
		return true;
	}
	

}
