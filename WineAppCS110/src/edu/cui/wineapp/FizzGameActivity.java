package edu.cui.wineapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.Toast;
import edu.cui.wineapp.views.AndroObjectView;

public class FizzGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main2);
	    
		//Create the render-able part
		AndroObjectView graphics = new AndroObjectView(this);
		
		//Set the render-able surface to be the top of the layout
		LinearLayout surf = (LinearLayout) findViewById(R.id.surface);
		surf.addView(graphics);
		Toast.makeText(getApplicationContext(), "Touch!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
