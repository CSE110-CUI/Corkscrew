package edu.cui.wineapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class WineInfo extends FragmentActivity {

    ArrayList<String> foodNames;

    ViewPager mPager;
    pgrAdapter mAdapter;
    static ArrayList<String> Cheeses;
    static int NUM_ITEMS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Cheeses = new ArrayList<String>();

        Cheeses.add("One");
        Cheeses.add("Two");
        Cheeses.add("Three");

        setContentView(R.layout.activity_wine_info);


        mAdapter = new pgrAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);


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

    public static class pgrAdapter extends FragmentPagerAdapter {
        public pgrAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            return ArrayListFragment.newInstance(position);
        }
    }

    public static class ArrayListFragment extends ListFragment {
        int mNum;

        /**
         * Create a new instance of CountingFragment, providing "num"
         * as an argument.
         */
        static ArrayListFragment newInstance(int num) {
            ArrayListFragment f = new ArrayListFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        /**
         * When creating, retrieve this instance's number from its arguments.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        /**
         * The Fragment's UI is just a simple text view showing its
         * instance number.
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
            View tv = v.findViewById(R.id.text);
            ((TextView) tv).setText("Fragment #" + mNum);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, Cheeses));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("FragmentList", "Item clicked: " + id);
        }
    }

}



