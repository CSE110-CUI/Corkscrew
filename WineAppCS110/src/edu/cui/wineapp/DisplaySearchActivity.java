
package edu.cui.wineapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;
import edu.cui.wineapp.controllers.DisplaySearchController;
import edu.cui.wineapp.utilities.CheckNetwork;
import edu.cui.wineapp.views.DisplaySearchView;

public class DisplaySearchActivity extends Activity {

	public static DisplaySearchActivity disSearchAct;
	
    Bundle bundle = new Bundle();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);
        disSearchAct = this;
        
        CheckNetwork.isInternetAvailable(getApplicationContext());
        
        final DisplaySearchView dSearchView = new DisplaySearchView(this);
        final DisplaySearchController dSearchController = new DisplaySearchController(this, dSearchView);
        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.search_menu, menu);
	    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
	    return super.onCreateOptionsMenu(menu);
	}

}