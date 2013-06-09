package edu.cui.wineapp.views;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.cui.wineapp.LoginActivity;
import edu.cui.wineapp.NewUserActivity;
import edu.cui.wineapp.R;

public class LoginView {
	private Button loginButton, signupButton;
	private EditText id;
	private EditText password;
	private View view;
	public LoginView(Activity activity)
	{
		view = LoginActivity.loginAct.getWindow().getDecorView().getRootView();
		Typeface robottoBold = Typeface.createFromAsset(LoginActivity.loginAct.getAssets(),
				"fonts/Roboto-BoldCondensed.ttf");
		Typeface robottoThin = Typeface.createFromAsset(LoginActivity.loginAct.getAssets(),
				"fonts/Roboto-Thin.ttf");
		this.id = (EditText)view.findViewById(R.id.login_email);
		this.password = (EditText)view.findViewById(R.id.login_password);
		loginButton = (Button) view.findViewById(R.id.login_button);
		signupButton = (Button) view.findViewById(R.id.signup_button);
		TextView header = (TextView) view.findViewById(R.id.appName);
		header.setTypeface(robottoBold);
		TextView disclaimer = (TextView) view.findViewById(R.id.disclaimer);
		header.setTypeface(robottoThin);
	}
	
	public void clearText(){
		
		((EditText)view.findViewById(R.id.login_email)).setText("");
		((EditText)view.findViewById(R.id.login_password)).setText("");
		
	}
	public Button getLoginButton() {
		return loginButton;
	}
	public void setLoginButton(Button loginButton) {
		this.loginButton = loginButton;
	}

	public Button getSignUpButton() {
		return signupButton;
	}
	public void setSignUpButton(Button signupButton) {
		this.signupButton = signupButton;
	}

	public EditText getId() {
		return id;
	}

	public void setId(EditText id) {
		this.id = id;
	}

	public EditText getPassword() {
		return password;
	}

	public void setPassword(EditText password) {
		this.password = password;
	}

}
