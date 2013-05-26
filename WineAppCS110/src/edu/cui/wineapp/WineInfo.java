package edu.cui.wineapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import com.viewpagerindicator.TabPageIndicator;

public class WineInfo extends FragmentActivity {

	ArrayList<String> foodNames;

	CollectionAdapter mAdapter;
	ViewPager mViewPager;
	DetailedWine currentWine;
	Context context = null;
	ArrayList<Comment> commentArrayList;
	ArrayList<String>  commentBodyArrayList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {


		Typeface robottoBlack = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Black.ttf");        
		Typeface robottoBold = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-BoldCondensed.ttf");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine_info);


		mAdapter = new CollectionAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAdapter);


		TabPageIndicator titleIndicator = (TabPageIndicator)findViewById(R.id.indicator);
		titleIndicator.setViewPager(mViewPager);


		Wine currentWineBASIC = (Wine) WineManager.getWineManager(this).getWineById(getIntent().getExtras().getLong(("passedWine")));
		currentWine = WineManager.getWineManager(this).downloadDetailedWine(currentWineBASIC);

		TextView name = (TextView) findViewById(R.id.textview_name);
		name.setText(currentWine.getName());
		name.setTypeface(robottoBold);

		TextView varietal = (TextView) findViewById(R.id.textview_varietal);
		varietal.setText(currentWine.getVarietal());

		TextView region = (TextView) findViewById(R.id.textview_region);
		region.setText(currentWine.getRegion());

		TextView wineRating = (TextView) findViewById(R.id.textview_rating);
		wineRating.setText((int)currentWine.getSnoothrank() * 20 > 0 ? Integer.toString((int)currentWine.getSnoothrank() * 20) : "NA");
		wineRating.setTextColor(Color.parseColor("#A60000"));


		new DownloadImageTask((ImageView) findViewById(R.id.image))
		.execute(currentWine.getImage());


		ArrayList<Food> myFoods = currentWine.getFoodPairings(this);
		Log.i("WineInfo.java/onCreate", "Local myFoods.size() = " + Integer.toString(myFoods.size()));

		foodNames = new ArrayList<String>();

		for (Food f:myFoods) 
			foodNames.add(f.getName());


		/*
        ListView foodList = (ListView) findViewById(R.id.listView);
        foodList.setAdapter(new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, foodNames));
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.wine_info, menu);
		return true;
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap bitmapIcon = null;

			try {
				InputStream in = new URL(urldisplay).openStream();
				bitmapIcon = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				try {
					InputStream in = getAssets().open("wineDefault.jpg");
					Log.e("TRY", "Trying to put default photo");
					bitmapIcon = BitmapFactory.decodeStream(in);
				} catch (IOException e1) {
					Log.e("CATCH", "Failed to put default photo");
					e1.printStackTrace();
				}
			}

			return bitmapIcon;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}

	public class CollectionAdapter extends FragmentPagerAdapter {
		public CollectionAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Bundle currWine = new Bundle();
			Fragment fragment = null;

			switch(i){

			//Overview
			case 0:
				fragment = new WineOverviewFragment();
				currWine.putSerializable(WineOverviewFragment.ARG_WINE, currentWine);
				fragment.setArguments(currWine);
				break;

			//Notes
			case 1: 
				fragment = new WineNotesFragment();
				currWine.putSerializable(WineOverviewFragment.ARG_WINE, currentWine);
				fragment.setArguments(currWine);
				break;

			//Reviews
			case 2:
				fragment = new WineReviewFragment();
				currWine.putSerializable(WineReviewFragment.ARG_WINE, currentWine);
				fragment.setArguments(currWine);
				break;

			//More Info
			case 3:
				fragment = new WineOverviewFragment();
				currWine.putSerializable(WineOverviewFragment.ARG_WINE, currentWine);
				fragment.setArguments(currWine);
				break;	
			}

			return fragment;
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String tabTitle = null;

			switch(position){
			case 0:
				tabTitle = "Overview";
				break;
			case 1:
				tabTitle = "Notes";
				break;
			case 2:
				tabTitle = "Reviews";
				break;
			case 3:
				tabTitle = "More Info";
				break;
			}

			return tabTitle;
		}

	}

}













