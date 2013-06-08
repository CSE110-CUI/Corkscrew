package edu.cui.wineapp;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import edu.cui.wineapp.controllers.WineCellarController;
import edu.cui.wineapp.views.WineCellarView;

public class WineCellarActivity extends Activity {

	Bundle bundle3;
	public static WineCellarActivity wineCellar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_wine_cellar);
		
		wineCellar = this;
		
		final WineCellarView wCView = new WineCellarView(this);
		final WineCellarController wCController = new WineCellarController(this, wCView);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wine_cellar, menu);
		return true;
	}

}
