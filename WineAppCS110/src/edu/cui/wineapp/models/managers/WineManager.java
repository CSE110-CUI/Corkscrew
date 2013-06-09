package edu.cui.wineapp.models.managers;

import java.util.ArrayList;

import android.content.Context;
import edu.cui.wineapp.models.DetailedWine;
import edu.cui.wineapp.models.Wine;
import edu.cui.wineapp.models.data.WineDAO;

public class WineManager {
		private static Context context = null;
		private static WineDAO dao =null;
		//private static WineManager ourInstance = new WineManager();
		
		public WineManager(Context context){
			this.context = context;
			this.dao = dao.getWineDAO(context);
		}
		
		public static WineManager getWineManager(Context context){
			return new WineManager(context);
		}
		
		public ArrayList<Wine> downloadWineByName(String name){
			return dao.downloadWineByName(name);
		}
		
		public DetailedWine downloadDetailedWine(String passedWine){

			return dao.downloadDetailedWine(passedWine);

		}
		
		public ArrayList<Wine> fetchAllWines(){
			return dao.getAllWinesInwineDataBase();
		}
		
		public Wine getWineById(long wineId){
			return dao.getWineById(wineId);
		}
		
		public ArrayList<Wine> getWineByName(String name){
			return dao.getWineByName(name);
		}
		
		public void populateDB(Wine w){
			dao.createWine(w);
		}
		
}
