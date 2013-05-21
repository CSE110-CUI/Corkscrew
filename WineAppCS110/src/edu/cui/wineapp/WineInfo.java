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
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class WineInfo extends FragmentActivity {

    ArrayList<String> foodNames;

    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;

    static ArrayList<String> Cheeses;
    static int NUM_ITEMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cheeses = new ArrayList<String>();

        Cheeses.add("One");
        Cheeses.add("Two");
        Cheeses.add("Three");

        setContentView(R.layout.activity_wine_info);


        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);


        foodNames = new ArrayList<String>();

        Intent i = getIntent();
        Wine currentWine = (Wine) WineManager.getWineManager(this).getWineById(i.getExtras().getLong(("passedWine")));

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


        //FoodManager fManager = new FoodManager(this);
        ArrayList<Food> myFoods =
                //fManager.downloadFoodPairings(currentWine.getCode());
                currentWine.getFoodPairings(this);
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

    public class DemoCollectionPagerAdapter extends FragmentPagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 100;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }

    }

    // Instances of this class are fragments representing a single
// object in our collection.
    public static class DemoObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.fragment_pager_list, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(R.id.textView)).setText(
                    Integer.toString(args.getInt(ARG_OBJECT)));
            return rootView;
        }
    }

}



