package edu.cui.wineapp;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class WineManager {
		private static Context context = null;
		private static DAO dao =null;
		//private static WineManager ourInstance = new WineManager();
		
		public WineManager(Context context){
			this.context = context;
			this.dao = DAO.getDAO(context);
		}
		
		public static WineManager getWineManager(Context context){
			return new WineManager(context);
		}
		
		public ArrayList<Wine> downloadWineByName(String name){
			return dao.downloadWineByName(name);
		}
		
		public DetailedWine downloadDetailedWine(Wine passedWine){
			return dao.downloadDetailedWine(passedWine.getCode());
			//DetailedWine dWine = dao.downloadDetailedWine(passedWine.getCode());
			//Log.e("WineManager.java/downloadDetailedWine","Reviews Array size = "+dWine.getReviews().size());
			//return dWine;
		}
		
		public Wine getWineById(long wineId){
			return dao.getWineById(wineId);
		}
		
		public ArrayList<Wine> getWineByName(String name){
			return dao.getWineByName(name);
		}
		
}
