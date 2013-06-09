package edu.cui.wineapp;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.origamilabs.library.views.StaggeredGridView;

import edu.cui.wineapp.adapters.StaggeredAdapter;
import edu.cui.wineapp.models.DetailedWine;
import edu.cui.wineapp.models.Food;
import edu.cui.wineapp.models.managers.WineInfoManager;

public class WineFoodFragment extends Fragment {
    public static final String ARG_WINE = "currWine";
     
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_wine_food, container, false);
        DetailedWine dWine = WineInfoManager.getDetailedWine();
        final ArrayList<Food> foodArr = dWine.getFoodPairings(WineInfoActivity.wineInfoActivity);
        ArrayList<String> foodUrl = new ArrayList<String>();
        
        for(Food f: foodArr){
        	foodUrl.add(f.getImage());
        }
        
        StaggeredGridView myStagView = (StaggeredGridView) rootView.findViewById(R.id.staggeredGridView1);
        myStagView.setItemMargin(20);
        myStagView.setPadding(20, 0, 20, 0);
        
        StaggeredAdapter adapter = new StaggeredAdapter(WineInfoActivity.wineInfoActivity.getApplication(), R.id.imageView1, foodUrl);
		myStagView.setAdapter(adapter);
		myStagView.setOnItemClickListener(new StaggeredGridView.OnItemClickListener() {
			
			@Override
			public void onItemClick(StaggeredGridView parent, View view, int position,
					long id) {


				Uri uri = Uri.parse(foodArr.get(position).getSource_link());
				 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				 startActivity(intent);

				
			}
	        });
		adapter.notifyDataSetChanged();
        
        
        return rootView;
    }
}