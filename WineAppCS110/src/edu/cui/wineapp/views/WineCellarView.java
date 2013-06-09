package edu.cui.wineapp.views;

import android.app.Activity;
import android.view.View;

import com.origamilabs.library.views.StaggeredGridView;

import edu.cui.wineapp.R;
import edu.cui.wineapp.WineCellarActivity;

public class WineCellarView
{
	private StaggeredGridView gridView;
	private int margin;
	
	public WineCellarView(Activity activity)
	{
		View view = WineCellarActivity.wineCellar.getWindow().getDecorView().getRootView();
		setGridView((StaggeredGridView) view.findViewById(R.id.staggeredGridView1));
		setMargin(20);
		setUpGridImage(getGridView());
	}
	
	public void setUpGridImage(StaggeredGridView gridView)
	{
		gridView.setItemMargin(getMargin());
		gridView.setPadding(getMargin(), 0, getMargin(), 0);
	}
	
	public StaggeredGridView getGridView() {
		return gridView;
	}

	public void setGridView(StaggeredGridView gridView) {
		this.gridView = gridView;
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}
}
