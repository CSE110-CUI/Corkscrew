package edu.cui.wineapp.models.managers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import edu.cui.wineapp.WineInfoActivity;
import edu.cui.wineapp.models.DetailedWine;
import edu.cui.wineapp.models.Wine;

public class WineInfoManager extends Activity {

	static DetailedWine dWine;
	static Wine basicWine;

	public WineInfoManager(){
		initDetailedWine(WineInfoActivity.wineInfoActivity
				.getIntent().getExtras().getString("passedWine"));
		initBasicWine();
	}

	private void initBasicWine() {
		  basicWine = new Wine(
				    dWine.getId(),
				    dWine.getName(),
				    dWine.getCode(),
				    dWine.getRegion(),
				    dWine.getWinery(),
				    dWine.getWinery_id(),
				    dWine.getVarietal(),
				    dWine.getPrice(),
				    dWine.getVintage(),
				    dWine.getType(),
				    dWine.getLink(),
				    dWine.getTags(),
				    dWine.getImage(),
				    dWine.getSnoothrank(),
				    dWine.getAvailability(),
				    dWine.getNum_merchants(),
				    dWine.getNum_reviews()
				   );		
	}

	private void initDetailedWine(String passedWine){
		dWine = WineManager.getWineManager(WineInfoActivity.wineInfoActivity.getBaseContext())
				.downloadDetailedWine(passedWine);

	}

	public String getWineName(){
		return dWine.getName();
	}

	public String getWineVarietal(){
		return dWine.getVarietal();
	}

	public String getWineRegion(){
		return dWine.getRegion();
	}

	public int getWineRating(){
		return (int) dWine.getSnoothrank();
	}
	
	public void setWinePhoto(ImageView wineImage){
		new WineInfoPhotoManager(wineImage).execute(dWine.getImage());	
	}
	
	public static DetailedWine getDetailedWine(){
		return dWine;
	}

	public static Wine getBasicWine() {
		return basicWine;
	}
	
	public String getCurrentWineName(){
		return UserManager.getLocalUser().getCurrentWine().getName();
	}
	
	public String getCurrentBAC(){
		return String.valueOf(UserManager.getLocalUser().getBAC());
	}
}



