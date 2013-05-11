package edu.cui.wineapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class WineInfo extends Activity {
	//Merge

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine_info);
		
		Intent i = getIntent();
		Wine currentWine = (Wine)WineManager.getWineManager(this).getWineById(i.getExtras().getLong(("passedWine")));
		
		//currentWine.stripJSON();
		
		TextView name = (TextView) findViewById(R.id.textView1);
		name.setText(currentWine.getName());	

		
		TextView varietal = (TextView) findViewById(R.id.TextView03);
		varietal.setText(currentWine.getVarietal());		
		
		TextView region = (TextView) findViewById(R.id.TextView05);
		region.setText(currentWine.getRegion());		

		
		new DownloadImageTask((ImageView) findViewById(R.id.image))
        .execute(currentWine.getImage_URL());
		
		RatingBar wineRating = (RatingBar) findViewById(R.id.ratingBar1);
		String rank = currentWine.getRank();
		if (rank=="n/a"){
			rank="0";
		}
		wineRating.setRating(Float.parseFloat(rank));
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.wine_info, menu);
		
		return true;
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		  ImageView bmImage;

		  public DownloadImageTask(ImageView bmImage) {this.bmImage = bmImage;}

		  protected Bitmap doInBackground(String... urls) {
		      String urldisplay = urls[0];
		      Bitmap mIcon11 = null;
		      
		      try {
		    	  InputStream in = new URL(urldisplay).openStream();
			      mIcon11 = BitmapFactory.decodeStream(in);} catch (Exception e) {
			    	  try {
			    		   	InputStream in = getAssets().open("wineDefault.jpg");
			    		   	Log.e("TRY","Trying to put default photo");
			    		   	mIcon11 = BitmapFactory.decodeStream(in);} catch (IOException e1){
				    		   	Log.e("CATCH","Failed to put default photo");
			    	  		   	e1.printStackTrace();
		    	  		   	}
		      }
		      
		      return mIcon11;
		  }

		  protected void onPostExecute(Bitmap result) {
		      bmImage.setImageBitmap(result);
		  }
		}

}
