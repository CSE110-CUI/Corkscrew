package edu.cui.wineapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    //Bundle currWine = new Bundle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_info);
        Bundle currWine = new Bundle();
        
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


        TextView varietal = (TextView) findViewById(R.id.TextView03);
        varietal.setText(currentWine.getVarietal());

        TextView region = (TextView) findViewById(R.id.TextView05);
        region.setText(currentWine.getRegion());



        
        new DownloadImageTask((ImageView) findViewById(R.id.image))
                .execute(currentWine.getImage());

        RatingBar wineRating = (RatingBar) findViewById(R.id.ratingBar1);
        wineRating.setRating(currentWine.getSnoothrank());


        ArrayList<Food> myFoods = currentWine.getFoodPairings(this);
        Log.e("WineInfo.java/onCreate", "Local myFoods.size() = " + Integer.toString(myFoods.size()));

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
            Fragment fragment = new WineViewFragment();
            Bundle currWine = new Bundle();
            // Our object is just an integer :-P
            currWine.putInt(WineViewFragment.ARG_VIEW_INDEX, i);
            currWine.putSerializable(WineViewFragment.ARG_WINE, currentWine);
            //currWine.putSerializable(WineViewFragment.ARG_CONTEXT, getApplicationContext());

            fragment.setArguments(currWine);
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

    // Instances of this class are fragments representing a single
// object in our collection.
    public static class WineViewFragment extends Fragment {
        public static final String ARG_VIEW_INDEX = "view_indx";
        public static final String ARG_WINE = "currWine";
        public static final String ARG_CONTEXT = "currWine";

       
        //private ArrayList<String> reviewBodies;
        
        ArrayAdapter<String> myAdapter;


        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
        	
        	
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(R.layout.fragment_wine_view, container, false);
            Bundle args = getArguments();
            Bundle currWine = getArguments();
            DetailedWine dWine = (DetailedWine)currWine.getSerializable(ARG_WINE);
            
            /*
            for(Review r : dWine.getReviews()){
            	reviewBodies.add(r.getBody());
            }
            Log.e("WineInfo.java/WineViewFragment/onCreateView","reviewBodies.size() "+Integer.toString((dWine.getReviews().size())));
            Log.e("WineInfo.java/WineViewFragment/onCreateView","reviewBodies.size() "+Integer.toString((reviewBodies.size())));
            
           myAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, reviewBodies);
            setListAdapter(myAdapter);

            */
            ((TextView) rootView.findViewById(R.id.textView)).setText(Integer.toString(args.getInt(ARG_VIEW_INDEX)));
            
            switch(args.getInt(ARG_VIEW_INDEX)){
        		case 0:
        			rootView = inflater.inflate(R.layout.fragment_wine_view, container, false);
        			((TextView) rootView.findViewById(R.id.textView)).setText(dWine.getWm_notes());
        			//rootView = inflater.inflate(R.layout.fragment_wine_info_reviews, container, false);

        			break;
        		case 2:
        			//rootView = inflater.inflate(R.layout.fragment_wine_info_reviews, container, false);
        			
    				
        			
        }
        
            
            
            return rootView;
        }
    }
}
    
    
    
    



    
    




