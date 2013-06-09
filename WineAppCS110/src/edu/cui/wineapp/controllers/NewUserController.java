package edu.cui.wineapp.controllers;

import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import edu.cui.wineapp.MainActivity;
import edu.cui.wineapp.NewUserActivity;
import edu.cui.wineapp.models.User;
import edu.cui.wineapp.models.managers.UserManager;
import edu.cui.wineapp.utilities.FormValidation;
import edu.cui.wineapp.views.NewUserView;

public class NewUserController {
	private NewUserManager newUManager;
	private NewUserView newUView;
	private User currUser = null;;
	public NewUserController(Activity activity, NewUserView newUserView)
	{
		this.newUView = newUserView;
		this.newUManager = new NewUserManager();

		createUser();
	}

	public void createUser()
	{
		this.newUView.getCbMale().setOnClickListener(new OnClickListener()
		{
			public void onClick(View view2)
			{
				if(newUView.getCbFemale().isChecked())
				{
					newUView.getCbFemale().toggle();
				}
			}
		});

		this.newUView.getCbFemale().setOnClickListener(new OnClickListener()
		{
			public void onClick(View view2)
			{
				if(newUView.getCbMale().isChecked())
				{
					newUView.getCbMale().toggle();
				}
			}
		});
		newUView.getButton().setOnClickListener(new OnClickListener()
		{
			private String email, sex, country, name, pass,id, pass2;
			float weight;
			int age;
			User currUser = null;

			@Override
			public void onClick(View view)
			{
				pass = newUView.getPass().getText().toString();
				pass2 = newUView.getConfirmPass().getText().toString();
				email = newUView.getEmail().getText().toString();
				country = newUView.getCountry().getText().toString();
				name = newUView.getName().getText().toString();
				id = convertEmailtoID(email);
				String weightString = newUView.getWeight().getText().toString();
				//Log.e("WEIGHT", weightString);
				if(pass.length() == 0)
					Log.e("LENGTH == 0", "UEAFHAF");
				else
					Log.e("LENGHT!=0",""+pass.length());

				if( pass.length() == 0 || email.length() == 0 || newUView.getWeight().getText().toString().length() == 0 || country.length() == 0 || name.length() == 0 || newUView.getAge().getText().toString().length() == 0 || pass2.length() == 0)
				{
					Log.e("ERROR","LAHDKLAHSDKLJAHSDLKJHASKLDJHALKSDJHAKLSJDHALKJSHDKLAJSHDLHJ");
					Toast.makeText(NewUserActivity.newUserAct.getApplication().getApplicationContext(), "All fields required!", Toast.LENGTH_LONG).show();
				}
				
				else{
					weight = Float.parseFloat(newUView.getWeight().getText().toString());
					age = Integer.parseInt(newUView.getAge().getText().toString());

					if( !FormValidation.checkNoDigit(name))
					{
						Toast.makeText(NewUserActivity.newUserAct.getApplication().getApplicationContext(), "Invalid Username! Only letters are allowed.", Toast.LENGTH_LONG).show();
					}
					else if( !FormValidation.isValidEmail(email))
					{
						Toast.makeText(NewUserActivity.newUserAct.getApplication().getApplicationContext(), "Invalid Email!", Toast.LENGTH_LONG).show();
					}
					else if( !FormValidation.checkAge(age))
					{
						Toast.makeText(NewUserActivity.newUserAct.getApplication().getApplicationContext(), "Invalid Age!", Toast.LENGTH_LONG).show();
					}
					else if( !FormValidation.checkWeight(weight))
					{
						Toast.makeText(NewUserActivity.newUserAct.getApplication().getApplicationContext(), "Invalid weight!", Toast.LENGTH_LONG).show();
					}
					else if( !FormValidation.confirmPassword(pass, pass2) )
					{
						Toast.makeText(NewUserActivity.newUserAct.getApplication().getApplicationContext(), "Your passwords do not match! Please try again.", Toast.LENGTH_LONG).show();
						newUView.clearPasswords();
					}
					else if( !FormValidation.checkNoDigit(country))
					{
						Toast.makeText(NewUserActivity.newUserAct.getApplication().getApplicationContext(), "Invalid location!", Toast.LENGTH_LONG).show();

					}
					else if( !newUView.getCbMale().isChecked() && !newUView.getCbFemale().isChecked() )
					{
						Toast.makeText(NewUserActivity.newUserAct.getApplication().getApplicationContext(), "Please select your gender!", Toast.LENGTH_LONG).show();
					}
					else
					{
						if( newUView.getCbMale().isChecked())
						{
							sex = "male";
						}
						else if( newUView.getCbFemale().isChecked())
						{
							sex = "female";
						}

						currUser = newUManager.createUser(name, age, weight, email, sex, country, "", id, pass);
						if( currUser == null )
						{
							Log.e("NewUserControl.java/ifCurrUser==Null","CurrUser==Null");
							Toast.makeText(NewUserActivity.newUserAct.getApplication().getApplicationContext(), "ID already exists!!!", Toast.LENGTH_LONG).show();

						}
						else
						{
							Log.e("NewUserControl.java/else","shouldnot b here");

							Toast.makeText(NewUserActivity.newUserAct.getApplication().getApplicationContext(), "Account Created!", Toast.LENGTH_LONG).show();
							try
							{
								currUser = UserManager.getUserManager(NewUserActivity.newUserAct.getApplication().getApplicationContext()).loginToServer(this.id, this.pass);
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
							/*** Sign User in locally ***/
							currUser.getName();
									UserManager.getUserManager(NewUserActivity.newUserAct.getApplicationContext()).setLocalUser(currUser.getName(), currUser.getAge(), currUser.getWeight(), currUser.getEmail(), currUser.getSex(), currUser.getCountry(), currUser.getPhotoUrl(), currUser.getId());

							Intent i = new Intent(NewUserActivity.newUserAct.getApplication().getApplicationContext(), MainActivity.class);
							UserManager.clearLocalWineCellar();
							NewUserActivity.newUserAct.startActivity(i);
							NewUserActivity.newUserAct.finish();
						}
					}
				}
			}

			private String convertEmailtoID(String emailAdd) {
				char[] emailarray = emailAdd.toCharArray();
				StringBuilder s = new StringBuilder(emailarray.length);
				for(int i = 0; i< emailarray.length; i++)
				{
					s.append((int)emailarray[i]);
				}
				String id1 = s.toString();
				Log.e("tag", id1);
				return id1;
			}
		});	
	}

}
