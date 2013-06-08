package edu.cui.wineapp.controllers;

import java.util.ArrayList;

import android.app.Activity;

import com.origamilabs.library.views.StaggeredGridView;

import edu.cui.wineapp.models.Wine;
import edu.cui.wineapp.models.managers.WineCellarManager;
import edu.cui.wineapp.views.WineCellarView;

public class WineCellarController
{
	private WineCellarView wCView;
	private WineCellarManager wCManager;
	private ArrayList<Wine> wineList;
	private ArrayList<String> wineImages;

	public WineCellarController(Activity activity, WineCellarView wCView)
	{
		wineList = new ArrayList<Wine>();
		wineImages = new ArrayList<String>();
		this.wCView = wCView;
		wCManager = new WineCellarManager(this.wCView.getGridView());
		
		this.setUpImagesandActivity(this.getWinesImages(this.getWines()));
		
		
	}
	public ArrayList<Wine> getWines()
	{
		return wCManager.fetchWines();
	}
	
	public ArrayList<String> getWinesImages(ArrayList<Wine> wList)
	{
		return wCManager.getWineImages(wList);
	}
	
	public void setUpImagesandActivity(ArrayList<String> wineURLs)
	{
		wCManager.setUpandStartAct(wineURLs);
	}
}
