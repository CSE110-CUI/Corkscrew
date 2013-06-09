package edu.cui.wineapp.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import edu.cui.wineapp.LoginActivity;
import edu.cui.wineapp.MainActivity;
import edu.cui.wineapp.NewUserActivity;
import edu.cui.wineapp.models.managers.LoginManager;
import edu.cui.wineapp.utilities.FormValidation;
import edu.cui.wineapp.views.LoginView;

public class LoginController {
	protected static final Context Context = null;
	private LoginManager lgManager;
	private LoginView lgView;
	public LoginController(Activity activity, LoginView loginView)
	{
		this.lgManager = new LoginManager();
		this.lgView = loginView;
		
		login();
		signup();
	}
	
	public void login()
	{
		lgView.getLoginButton().setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean success;
				String emailAdd = lgView.getId().getText().toString();
				if(!FormValidation.isValidEmail(emailAdd))
				{
					Toast.makeText(LoginActivity.loginAct.getApplication().getApplicationContext(), "Invalid Email. Try Again!", Toast.LENGTH_LONG).show();
					lgView.clearText();
				}
				else
				{
					char[] emailarray = emailAdd.toCharArray();
					StringBuilder s = new StringBuilder(emailarray.length);
					for(int i = 0; i< emailarray.length; i++)
					{
						s.append((int)emailarray[i]);
					}
					String id = s.toString();
					Log.e("LOGIN", id);
					Log.e("PASSWORD",lgView.getPassword().getText().toString());
					success = lgManager.login(id, lgView.getPassword().getText().toString());
					if(!success)
					{
						Toast.makeText(LoginActivity.loginAct.getApplication().getApplicationContext(), "Invalid Username or Password. Try Again!", Toast.LENGTH_LONG).show();
						lgView.clearText();

					}
					else
					{
						Intent i = new Intent(LoginActivity.loginAct.getApplication().getApplicationContext(), MainActivity.class);
						Bundle bundle = new Bundle();

				    	LoginActivity.loginAct.startActivity(i);
				    	LoginActivity.loginAct.finish();
					}
				}
			}
			
			
		});
	}
	
	public void signup()
	{
		lgView.getSignUpButton().setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LoginActivity.loginAct.getApplication().getApplicationContext(), NewUserActivity.class);
				//Log.e("SUCESSFUL", "YUP");
				LoginActivity.loginAct.startActivity(i);
			}
			
		});
	}

}
