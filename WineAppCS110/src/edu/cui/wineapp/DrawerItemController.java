package edu.cui.wineapp;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DrawerItemController implements ListView.OnItemClickListener {
	@Override
	public void onItemClick(AdapterView parent, View view, int position, long id) {
		switch(position){
		case 0:
			Intent i = new Intent(MainActivity.mainActivity.getApplicationContext(),WineCellar.class);
			MainActivity.mainActivity.startActivity(i);
			break;

		}
	}
}