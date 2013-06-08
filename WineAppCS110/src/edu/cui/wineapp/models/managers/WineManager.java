package edu.cui.wineapp.models.managers;

import java.util.ArrayList;

import android.content.Context;
import edu.cui.wineapp.DAO;
import edu.cui.wineapp.models.DetailedWine;
import edu.cui.wineapp.models.Wine;

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
		
		public DetailedWine downloadDetailedWine(String passedWine){
			//Log.e("WineManager.java/downloadDetailedWine","passedWine: "+passedWine.getName());
			//GETS FROM LOCAL DATABASE ONLY -- IF YOU HAVE A PROBLEM, REMMEBER THAT
			return dao.downloadDetailedWine(passedWine);
			//DetailedWine dWine = dao.downloadDetailedWine(passedWine.getCode());
			//Log.e("WineManager.java/downloadDetailedWine","Reviews Array size = "+dWine.getReviews().size());
			//return dWine;
		}
		
		public ArrayList<Wine> fetchAllWines(){
			return dao.getAllWinesInwineDataBase();
		}
		
		public Wine getWineById(long wineId){
			return dao.getWineById(wineId);
		}
		
		public ArrayList<Wine> getWineByName(String name){
			//DAO dao = new
			return dao.getWineByName(name);
		}
		
}
