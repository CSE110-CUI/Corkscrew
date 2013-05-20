package edu.cui.wineapp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.Context;

public class WineManager {
		private static Context context = null;
		private static DAO dao =null;
		//private static WineManager ourInstance = new WineManager();
		private WineManager(Context context){this.context = context;
		this.dao = DAO.getDAO(context);
		}
		
		public static WineManager getWineManager(Context context){
			return new WineManager(context);
		}
		/**
		public ArrayList<Wine> downloadWineByName(String name){
			return dao.getWineByName(name);
		}**/
		
		//public Wine getWineByCertainName(String name){
			//return dao.getWineByCertainName(name);
		//}
		public Wine getSnoothWineDetailByCode(String code){
			return dao.getSnoothWineDetailByCode(code);
		}
		
		public ArrayList<Wine> getWineByName(String name){
			return dao.getWineByName(name);
		}
		public ArrayList<Wine> getWineByColor(String color){
			return dao.getWineByColor(color);
		}
		//pass in query in snooth website: plz ref api.snooth.com. dont need to enter key. just enter the part after key:
		//for example: string"q=red&n=25" can be the query.
		public ArrayList<Wine>getWineByQuery(String query){
			return dao.getWineByQuery(query);
		}
		
}
