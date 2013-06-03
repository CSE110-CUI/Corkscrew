package edu.cui.wineapp;


import java.util.ArrayList;

import com.origamilabs.library.views.StaggeredGridView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class WineCellar extends Activity {

	Bundle bundle3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine_cellar);
		
		StaggeredGridView gridView = (StaggeredGridView) this.findViewById(R.id.staggeredGridView1);
		
		//int margin = getResources().getDimensionPixelSize(R.dimen.margin);
		int margin = 20;
		gridView.setItemMargin(margin); // set the GridView margin
		
		gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well 
		
		WineManager wManager = new WineManager(this);
		final ArrayList<Wine> myWines = wManager.fetchAllWines();
		ArrayList<String> myWineURLs = new ArrayList<String>();
		
		for(Wine w : myWines){
			myWineURLs.add(w.getImage());
		}
		
		
		StaggeredAdapter adapter = new StaggeredAdapter(getApplication(), R.id.imageView1, myWineURLs);
		
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new StaggeredGridView.OnItemClickListener() {

			@Override
			public void onItemClick(StaggeredGridView parent, View view,
					int position, long id) {
				bundle3 = new Bundle();
				Intent i = new Intent(getApplication().getApplicationContext(),WineInfo.class);
				Wine currWine = myWines.get(position);
		        bundle3.putString("passedWine", currWine.getCode());
		        i.putExtras(bundle3);
		        startActivity(i);
				
				
			}
			});
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wine_cellar, menu);
		return true;
	}

	
	
	
	
	/**
	 * Images are taken by Romain Guy ! He's a great photographer as well as a
	 * great programmer. http://www.flickr.com/photos/romainguy
	 */
	
	/**
	 * This will not work so great since the heights of the imageViews 
	 * are calculated on the iamgeLoader callback ruining the offsets. To fix this try to get 
	 * the (intrinsic) image width and height and set the views height manually. I will
	 * look into a fix once I find extra time.
	 */
	
	
	
	
}
