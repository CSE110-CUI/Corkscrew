package edu.cui.wineapp.controllers;

import java.util.ArrayList;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.cui.wineapp.DisplaySearchActivity;
import edu.cui.wineapp.LoginActivity;
import edu.cui.wineapp.MainActivity;
import edu.cui.wineapp.FizzGameActivity;
import edu.cui.wineapp.R;
import edu.cui.wineapp.WineCellarActivity;
import edu.cui.wineapp.WineInfoActivity;
import edu.cui.wineapp.models.BAC;
import edu.cui.wineapp.models.Wine;
import edu.cui.wineapp.models.managers.MainActivityManager;
import edu.cui.wineapp.models.managers.UserManager;
import edu.cui.wineapp.models.managers.WineManager;
import edu.cui.wineapp.utilities.CheckNetwork;
import edu.cui.wineapp.views.MainActivityView;

public class DrawerItemController implements ListView.OnItemClickListener {
	@Override
	public void onItemClick(AdapterView parent, View view, int position, long id) {
		switch(position){
		//User Profile
		case 0:
			// get prompts.xml view
			LayoutInflater li = (LayoutInflater)MainActivity.mainActivity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View promptsView = li.inflate(R.layout.popup_user_profile, null);
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.mainActivity);
			
			// set prompts.xml to alertdialog builder
			alertDialogBuilder.setView(promptsView);
			
			initUserProf(promptsView);
			
			// set dialog message
			alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
				});
			
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			
			// show it
			alertDialog.show();
			break;
			
		case 1:
	        CheckNetwork.isInternetAvailable(MainActivity.mainActivity.getApplicationContext());

			Intent i = new Intent(MainActivity.mainActivity.getApplicationContext(),WineCellarActivity.class);
			MainActivity.mainActivity.startActivity(i);
			break;
			
		case 2:
	        CheckNetwork.isInternetAvailable(MainActivity.mainActivity.getApplicationContext());

			Bundle bundle2 = new Bundle();
			Intent j = new Intent(MainActivity.mainActivity.getBaseContext(), DisplaySearchActivity.class);
			bundle2.putString("passedSearchTerm","wines");
			j.putExtras(bundle2);
			MainActivity.mainActivity.startActivity(j);		
			break;
			
		case 3:
	        CheckNetwork.isInternetAvailable(MainActivity.mainActivity.getApplicationContext());

			Bundle bundle = new Bundle();
			ArrayList<Wine> myWines = WineManager.getWineManager(MainActivity.mainActivity).downloadWineByName("wines");
			Wine currWine = myWines.get(new Random().nextInt(98));
			Intent l = new Intent(MainActivity.mainActivity.getApplicationContext(),WineInfoActivity.class);
			bundle.putString("passedWine", currWine.getCode());
			l.putExtras(bundle);
			MainActivity.mainActivity.startActivity(l);
			
			
		case 4:
	        CheckNetwork.isInternetAvailable(MainActivity.mainActivity.getApplicationContext());

			//User currUser = UserManager.getUserManager(MainActivity.mainActivity.getApplicationContext()).getLocalUser();
			//Toast.makeText(MainActivity.mainActivity.getApplicationContext(), String.valueOf(currUser.getBAC()), Toast.LENGTH_LONG).show();
			Double currBAC = UserManager.getLocalUser().getBAC();
			if(currBAC > 0) Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "CurrentBAC: "+currBAC, Toast.LENGTH_LONG).show();
			else Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "Current BAC: 0", Toast.LENGTH_LONG).show();
			break;
			
		case 5:
	        CheckNetwork.isInternetAvailable(MainActivity.mainActivity.getApplicationContext());

			UserManager.getUserManager(MainActivity.mainActivity.getApplicationContext()).clearBAC();
			//BAC.dVar = 0;
			BAC.drink_count = 0;
			if(UserManager.getLocalUser().getBAC() == 0)
				Toast.makeText(MainActivity.mainActivity.getApplicationContext(), "BAC Cleared!", Toast.LENGTH_LONG).show();
			break;
			
		case 6:
	        CheckNetwork.isInternetAvailable(MainActivity.mainActivity.getApplicationContext());

			if(UserManager.getLocalUser() != null)
					UserManager.getLocalUser().setCurrentWine(null);
			new MainActivityController(MainActivity.mainActivity, new MainActivityView(MainActivity.mainActivity), new MainActivityManager()).initTitleView(new MainActivityView(MainActivity.mainActivity).getmCardName());
			//name.clearCards();
			Toast.makeText(MainActivity.mainActivity, "Wine Cleared!", Toast.LENGTH_LONG).show();
			break;
			
		case 7:
			
			Intent m = new Intent(MainActivity.mainActivity.getApplicationContext(),FizzGameActivity.class);
			MainActivity.mainActivity.startActivity(m);
			break;
		//Logout
		case 8:
	        CheckNetwork.isInternetAvailable(MainActivity.mainActivity.getApplicationContext());

			Intent k = new Intent(MainActivity.mainActivity.getApplicationContext(),LoginActivity.class);
			MainActivity.mainActivity.startActivity(k); 
			MainActivity.mainActivity.finish();
			BAC.drink_count=0;
			UserManager.clearLocalWineCellar();
			break;

		}
	}

	private void initUserProf(View promptsView) {
		final TextView name = (TextView) promptsView
				.findViewById(R.id.name);
			name.setText("Name: " + UserManager.getLocalUser().getName());
		
		final TextView age = (TextView) promptsView
				.findViewById(R.id.age);
			age.setText("Age: "+String.valueOf(UserManager.getLocalUser().getAge()));
		
		final TextView sex = (TextView) promptsView
				.findViewById(R.id.sex);
			sex.setText("Gender: "+UserManager.getLocalUser().getSex());
			
		final TextView country = (TextView) promptsView
				.findViewById(R.id.country);
			country.setText("Country: "+UserManager.getLocalUser().getCountry());
			
		final TextView weight = (TextView) promptsView
				.findViewById(R.id.weight);
			weight.setText(String.valueOf("Weight: "+UserManager.getLocalUser().getWeight()));
	}
}