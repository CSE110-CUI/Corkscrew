package edu.cui.wineapp.controllers;

import edu.cui.wineapp.MainActivity;
import edu.cui.wineapp.WineCellarActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DrawerItemController implements ListView.OnItemClickListener {
	@Override
	public void onItemClick(AdapterView parent, View view, int position, long id) {
		switch(position){
		case 0:
			Intent i = new Intent(MainActivity.mainActivity.getApplicationContext(),WineCellarActivity.class);
			MainActivity.mainActivity.startActivity(i);
			break;

		}
	}
}