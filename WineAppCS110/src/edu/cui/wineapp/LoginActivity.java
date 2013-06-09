package edu.cui.wineapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import edu.cui.wineapp.controllers.LoginController;
import edu.cui.wineapp.views.LoginView;

public class LoginActivity extends Activity {
	public static LoginActivity loginAct;
	public LoginController lgController;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		loginAct = this;
		final LoginView lgView = new LoginView(this);
		lgController = new LoginController(this, lgView);
	}
}
