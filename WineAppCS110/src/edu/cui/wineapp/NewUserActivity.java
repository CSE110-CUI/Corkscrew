package edu.cui.wineapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import edu.cui.wineapp.controllers.NewUserController;
import edu.cui.wineapp.views.NewUserView;

public class NewUserActivity extends Activity {

	public static NewUserActivity newUserAct;
	Button butt;
	
	public void onCreate(Bundle savedInstanceState) {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = 
			        new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		newUserAct = this;
		
		final NewUserView newUserView = new NewUserView(this);
		NewUserController newUserController = new NewUserController(this, newUserView);
				
	}
}
