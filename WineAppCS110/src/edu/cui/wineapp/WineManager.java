package edu.cui.wineapp;

import java.util.ArrayList;

public class WineManager {
		private static DAO dao = DAO.getInstance();
		private static WineManager ourInstance = new WineManager();
		private WineManager(){}
		
		public static WineManager getWineManager(){
			return ourInstance;
		}
		
		public static ArrayList<Wine> getWineByName(String name){
			return dao.getWineByName(name);
		}
		
		public static Wine getWineById(int wineId){
			return dao.getWineById(wineId);
		}
		
}
