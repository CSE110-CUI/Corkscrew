package edu.cui.wineapp;

import edu.cui.wineapp.controllers.MainActivityController;
import edu.cui.wineapp.models.managers.MainActivityManager;
import edu.cui.wineapp.views.MainActivityView;
import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;

public class MainActivity extends Activity {

	public static MainActivity mainActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mainActivity = this;
		
		final MainActivityView mainView = new MainActivityView(this);
		final MainActivityManager mainManager = new MainActivityManager();
		final MainActivityController mainController = new MainActivityController(this, mainView,mainManager);

		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		final MainActivityView mainView = new MainActivityView(this);
		final MainActivityManager mainManager = new MainActivityManager();
		final MainActivityController mainController = new MainActivityController(this, mainView,mainManager);

		mainController.initTitleView(mainView.getmCardName());
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
