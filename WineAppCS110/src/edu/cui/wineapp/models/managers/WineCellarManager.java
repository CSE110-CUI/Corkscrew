package edu.cui.wineapp.models.managers;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.origamilabs.library.views.StaggeredGridView;

import edu.cui.wineapp.R;
import edu.cui.wineapp.WineCellarActivity;
import edu.cui.wineapp.WineInfoActivity;
import edu.cui.wineapp.adapters.StaggeredAdapter;
import edu.cui.wineapp.models.Wine;

public class WineCellarManager {
	StaggeredGridView sGView;
	Bundle bundle;
	ArrayList<Wine> wineList = new ArrayList<Wine>();
	ArrayList<String> wineURLs = new ArrayList<String>();
	private WineManager wManager;
	
	public WineCellarManager(StaggeredGridView gView)
	{
		this.sGView = gView;
	}
	
	public ArrayList<Wine> fetchWines()
	{
		wManager = new WineManager(WineCellarActivity.wineCellar.getApplicationContext());
		wineList = wManager.fetchAllWines();
		return wineList;
	}
	
	public ArrayList<String> getWineImages(ArrayList<Wine> wines)
	{
		for(Wine w : wines)
		{
			wineURLs.add(w.getImage());
		}
		return wineURLs;
	}
	
	public void setUpandStartAct(ArrayList<String> wineImages)
	{
		StaggeredAdapter adapter = new StaggeredAdapter(WineCellarActivity.wineCellar.getApplication(), R.id.imageView1, wineImages);
		this.sGView.setAdapter(adapter);
		this.sGView.setOnItemClickListener(new StaggeredGridView.OnItemClickListener() {
			
			@Override
			public void onItemClick(StaggeredGridView parent, View view, int position,
					long id) {

				bundle = new Bundle();
				Intent i = new Intent(WineCellarActivity.wineCellar.getApplication().getApplicationContext(),WineInfoActivity.class);
				Wine currWine = fetchWines().get(position);
				bundle.putString("passedWine", currWine.getCode());
				i.putExtras(bundle);
			    WineCellarActivity.wineCellar.startActivity(i);
				
			}
	        });
		adapter.notifyDataSetChanged();
	}

}
