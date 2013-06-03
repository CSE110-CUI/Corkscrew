package edu.cui.wineapp;

import java.util.HashMap;
import java.util.Map.Entry;

import android.app.Application;
import android.util.Log;

class GlobalData extends Application{
	public HashMap<String,String> preTagDB = new HashMap<String,String>();
	public void add(String winecode, String description){
		preTagDB.put(winecode, description);
		for(Entry<String, String> entry: preTagDB.entrySet()){
			Log.e(entry.getKey(),entry.getValue());
		}
	}
}
