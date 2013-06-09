package edu.cui.wineapp.views;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import edu.cui.wineapp.NewUserActivity;
import edu.cui.wineapp.R;

public class NewUserView {
	private Button button;
	private View view;
	private CheckBox cbMale, cbFemale;
	private EditText name, weight, pass, email, country, sex,age, pass2;
	public NewUserView(Activity activity)
	{
		
		view = NewUserActivity.newUserAct.getWindow().getDecorView().getRootView();
		Typeface robottoBlack = Typeface.createFromAsset(NewUserActivity.newUserAct.getAssets(),
				"fonts/Roboto-Black.ttf");        
		Typeface robottoBold = Typeface.createFromAsset(NewUserActivity.newUserAct.getAssets(),
				"fonts/Roboto-BoldCondensed.ttf");
		Typeface robottoThin = Typeface.createFromAsset(NewUserActivity.newUserAct.getAssets(),
				"fonts/Roboto-Thin.ttf");
		Drawable bg = NewUserActivity.newUserAct.getResources().getDrawable(R.drawable.greenbg);
		bg.setAlpha(160);
		
		TextView greeting = (TextView) view.findViewById(R.id.greeting);
		greeting.setTypeface(robottoBold);
		TextView greeting2 = (TextView) view.findViewById(R.id.greeting2);
		greeting2.setTypeface(robottoThin);
		
		button = (Button) view.findViewById(R.id.btnRegister);
		cbMale = (CheckBox) view.findViewById(R.id.male);
		cbFemale = (CheckBox) view.findViewById(R.id.female);
		
		name = (EditText) view.findViewById(R.id.reg_name);
		weight = (EditText) view.findViewById(R.id.reg_weight);
		country = (EditText) view.findViewById(R.id.reg_country);
		age = (EditText) view.findViewById(R.id.reg_age);
		email = (EditText) view.findViewById(R.id.reg_email);
		pass = (EditText) view.findViewById(R.id.reg_password);
		pass2 = (EditText) view.findViewById(R.id.reg_password1);
	}
	
	public void clearPasswords()
	{
		((EditText) view.findViewById(R.id.reg_password)).setText("");
		((EditText) view.findViewById(R.id.reg_password1)).setText("");

	}
	
	public Button getButton() {
		return button;
	}
	public void setButton(Button button) {
		this.button = button;
	}
	public EditText getName() {
		return name;
	}
	public void setName(EditText name) {
		this.name = name;
	}
	public EditText getWeight() {
		return weight;
	}
	public void setWeight(EditText weight) {
		this.weight = weight;
	}
	public EditText getPass() {
		return pass;
	}
	public void setPass(EditText pass) {
		this.pass = pass;
	}
	public EditText getConfirmPass() {
		return pass2;
	}
	public void setConfirmPass(EditText pass2) {
		this.pass2 = pass2;
	}
	public EditText getEmail() {
		return email;
	}
	public void setEmail(EditText email) {
		this.email = email;
	}
	public EditText getCountry() {
		return country;
	}
	public void setCountry(EditText country) {
		this.country = country;
	}
	public EditText getSex() {
		return sex;
	}
	public void setSex(EditText sex) {
		this.sex = sex;
	}
	public EditText getAge() {
		return age;
	}
	public void setAge(EditText age) {
		this.age = age;
	}
	public CheckBox getCbMale() {
		return cbMale;
	}
	public void setCbMale(CheckBox cbMale) {
		this.cbMale = cbMale;
	}
	public CheckBox getCbFemale() {
		return cbFemale;
	}
	public void setCbFemale(CheckBox cbFemale) {
		this.cbFemale = cbFemale;
	}
}
