package edu.cui.wineapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import edu.cui.wineapp.controllers.WineCellarController;
import edu.cui.wineapp.utilities.CheckNetwork;
import edu.cui.wineapp.views.WineCellarView;

public class WineCellarActivity extends Activity {

	Bundle bundle3;
	public static WineCellarActivity wineCellar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_wine_cellar);
		
		wineCellar = this;
		
        CheckNetwork.isInternetAvailable(getApplicationContext());
		
		final WineCellarView wCView = new WineCellarView(this);
		final WineCellarController wCController = new WineCellarController(this, wCView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wine_cellar, menu);
		return true;
	}
	
	 
	 @Override
	   public boolean onOptionsItemSelected(MenuItem item) {
	  Intent i = new Intent(this, SettingsActivity.class);
	  startActivity(i);
	  return true;
	  
	 }

}
