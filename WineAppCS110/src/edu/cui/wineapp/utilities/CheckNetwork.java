package edu.cui.wineapp.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import edu.cui.wineapp.LoginActivity;
import edu.cui.wineapp.MainActivity;

public class CheckNetwork {


	public static boolean isInternetAvailable(Context context)
	{
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
				context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

		if (info == null)
		{
			Intent i = new Intent(context,LoginActivity.class);
			MainActivity.mainActivity.startActivity(i);    
			Toast.makeText(context, "Please check your internet connection!", Toast.LENGTH_LONG).show();
			return false;
		}
		else
		{
			return true;
		}
	}
}