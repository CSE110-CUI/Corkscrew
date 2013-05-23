package edu.cui.wineapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
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
    //UserManager myUM = null;
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
        
    	//myUM = UserManager.getUserManager(this);

        //myUM.testpost("1", "1", "1");
        
        
        mAdapter = new CollectionAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAdapter);
        
        TabPageIndicator titleIndicator = (TabPageIndicator)findViewById(R.id.indicator);
        titleIndicator.setViewPager(mViewPager);

        foodNames = new ArrayList<String>();

        Intent i = getIntent();
        Wine currentWineBASIC = (Wine) WineManager.getWineManager(this).getWineById(i.getExtras().getLong(("passedWine")));
        currentWine = WineManager.getWineManager(this).downloadDetailedWine(currentWineBASIC);

        TextView name = (TextView) findViewById(R.id.textView1);
        name.setText(currentWine.getName());
        name.setTypeface(robottoBold);



        

        TextView varietal = (TextView) findViewById(R.id.TextView03);
        varietal.setText(currentWine.getVarietal());

        TextView region = (TextView) findViewById(R.id.TextView05);
        region.setText(currentWine.getRegion());
        
        TextView wineRating = (TextView) findViewById(R.id.TextView01);
        wineRating.setText((int)currentWine.getSnoothrank() * 20 > 0 ? Integer.toString((int)currentWine.getSnoothrank() * 20) : "NA");
        wineRating.setTextColor(Color.parseColor("#A60000"));



        
        new DownloadImageTask((ImageView) findViewById(R.id.image))
                .execute(currentWine.getImage());



        ArrayList<Food> myFoods = currentWine.getFoodPairings(this);
        Log.i("WineInfo.java/onCreate", "Local myFoods.size() = " + Integer.toString(myFoods.size()));

        for (int count = 0; count < myFoods.size(); count++) {
            foodNames.add(myFoods.get(count).getName());
        }

/*
        ListView foodList = (ListView) findViewById(R.id.listView);
        foodList.setAdapter(new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, foodNames));
*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
            Bitmap mIcon11 = null;

            try {
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                try {
                    InputStream in = getAssets().open("wineDefault.jpg");
                    Log.e("TRY", "Trying to put default photo");
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (IOException e1) {
                    Log.e("CATCH", "Failed to put default photo");
                    e1.printStackTrace();
                }
            }

            return mIcon11;
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
            Fragment fragment;
            
            fragment = null;
        	
            //Overview
        	if(i == 0){
        		fragment = new WineOverviewFragment();
                currWine.putSerializable(WineOverviewFragment.ARG_WINE, currentWine);
                fragment.setArguments(currWine);

        	}
        	
        	//Notes
        	else if(i == 1){
        		fragment = new WineNotesFragment();
                currWine.putSerializable(WineOverviewFragment.ARG_WINE, currentWine);
                fragment.setArguments(currWine);
        	}
        	
        	//Reviews
        	else if(i == 2){
        		fragment = new WineReviewFragment();
                currWine.putSerializable(WineReviewFragment.ARG_WINE, currentWine);
                fragment.setArguments(currWine);
        	}
        	
        	//Food Pairings
        	else if(i == 3){
        		fragment = new WineOverviewFragment();
                currWine.putSerializable(WineOverviewFragment.ARG_WINE, currentWine);
                fragment.setArguments(currWine);
        	}

            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0) return "Overview";
            else if(position == 1) return "Notes";
            else if(position == 2) return "Reviews";
            else if(position == 3) return "More Info";
        	return "OBJECT " + (position + 1);
        }

    }

}
    
    
    
    



    
    




