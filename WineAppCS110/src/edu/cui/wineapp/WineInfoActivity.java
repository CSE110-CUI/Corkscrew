package edu.cui.wineapp;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import edu.cui.wineapp.controllers.WineInfoController;
import edu.cui.wineapp.models.BAC;
import edu.cui.wineapp.models.managers.WineInfoManager;
import edu.cui.wineapp.utilities.CheckNetwork;
import edu.cui.wineapp.views.WineInfoView;

public class WineInfoActivity extends FragmentActivity {

	public static WineInfoActivity wineInfoActivity;

	ArrayList<String> foodNames;
	String Mode;

	WineInfoController wineController;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine_info);
		wineInfoActivity = this;

		CheckNetwork.isInternetAvailable(getApplicationContext());

		final WineInfoView wineInfoView = new WineInfoView(this);
		final WineInfoManager wineInfoManager = new WineInfoManager();
		wineController = new WineInfoController(this, wineInfoView, wineInfoManager);


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			//TODO add these Code
			SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
			int glasssize;
			String activeMode;
			if (Mode == null) Mode = sharedPrefs.getString("BAC_Mode", "Wildmark");
			glasssize = Integer.valueOf(sharedPrefs.getString("Glass_Size", "1"));
			BAC.setGlassSize(glasssize);
			activeMode = sharedPrefs.getString("BAC_Mode", "Wildmark");
			if (!Mode.equals(activeMode)) {
				/* TODO add clear BAC */
				BAC.drink_count = 0;
				Mode = activeMode;
				if(Mode.equals("Wildmark")) BAC.unsetConservative_rate();
				else if(Mode.equals("DMV")) BAC.setConservative_rate();
			}
			wineController.addDrink();
			break;
		case R.id.action_settings:
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			return true;
		default:
			break;
		}
		return true;
	}

}













