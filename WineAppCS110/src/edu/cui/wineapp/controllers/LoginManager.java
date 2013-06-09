package edu.cui.wineapp.controllers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.widget.Toast;

import edu.cui.wineapp.LoginActivity;
import edu.cui.wineapp.models.User;
import edu.cui.wineapp.models.Wine;
import edu.cui.wineapp.models.data.UserDAO;
import edu.cui.wineapp.models.managers.UserManager;
import edu.cui.wineapp.models.managers.WineManager;

public class LoginManager {

	public boolean login(String id, String pass)
	{
		if(UserDAO.getUserDAO(LoginActivity.loginAct.getApplicationContext()).loginServer(id, pass).size() == 0){
			return false;
			
		}else{
			
/**** Added by Nay (to be tested) ****/			
			User curr = null;
			try {
				curr = UserManager.getUserManager(LoginActivity.loginAct.getApplicationContext()).loginToServer(id, pass);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			UserManager.getUserManager(LoginActivity.loginAct.getApplicationContext()).setLocalUser(curr.getName(), curr.getAge(), curr.getWeight(), curr.getEmail(), curr.getSex(), curr.getCountry(), curr.getPhotoUrl(), curr.getId());
			Toast.makeText(LoginActivity.loginAct.getApplicationContext(), "Downloading Wines...", Toast.LENGTH_LONG).show();
			ArrayList<Wine> drankWines = UserManager.fetchOnlineWines();
			for(Wine w: drankWines){
				//WineManager.getWineManager(LoginActivity.loginAct.getApplicationContext()).
				WineManager.getWineManager(LoginActivity.loginAct.getApplicationContext()).populateDB(w);
			}

		}
		return true;
	}
	
}
