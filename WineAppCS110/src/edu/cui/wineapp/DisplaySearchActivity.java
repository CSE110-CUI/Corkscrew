package edu.cui.wineapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DisplaySearchActivity extends ListActivity {


    private ArrayList<Wine> wines;
    private WineManager wineManager;
    private UserManager userManager;
    private String[] prodInfo = new String[25];

    ArrayAdapter<String> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < prodInfo.length; ++i) prodInfo[i] = "Loading...";

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        wineManager = WineManager.getWineManager(this);
        new DownloadWebpageText().execute(message);
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prodInfo);
        setListAdapter(myAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Wine wineToPass = (Wine) wines.get(position);
        Intent i = new Intent(this, WineInfo.class);
        Bundle bundle2 = new Bundle();
        bundle2.putLong("passedWine", wineToPass.getId());
        i.putExtras(bundle2);
        startActivity(i);
    }

    private class DownloadWebpageText extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
            String message = arg0[0];
            wines = wineManager.downloadWineByName(message);

            return null;
        }

        protected void onPostExecute(String result) {
            for (int i = 0; i < 25; ++i) {
                prodInfo[i] = wines.get(i).toString();
            }
            myAdapter.notifyDataSetChanged();
        }
    }
}
