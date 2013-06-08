package edu.cui.wineapp.models.managers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import edu.cui.wineapp.WineInfoActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;


public class WineInfoPhotoManager extends AsyncTask<String, Void, Bitmap> {
	ImageView bmImage;

	public WineInfoPhotoManager(ImageView bmImage) {
		this.bmImage = bmImage;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap bitmapIcon = null;

		try {
			InputStream in = new URL(urldisplay).openStream();
			bitmapIcon = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			try {
				InputStream in = WineInfoActivity.wineInfoActivity
						.getAssets().open("wineDefault.jpg");
				Log.e("TRY", "Trying to put default photo");
				bitmapIcon = BitmapFactory.decodeStream(in);
			} catch (IOException e1) {
				Log.e("CATCH", "Failed to put default photo");
				e1.printStackTrace();
			}
		}

		return bitmapIcon;
	}

	protected void onPostExecute(Bitmap result) {
		bmImage.setImageBitmap(result);
	}
}