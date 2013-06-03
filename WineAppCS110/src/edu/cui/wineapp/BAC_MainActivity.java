package edu.cui.wineapp;

import java.sql.Date;

import android.os.Bundle;
import android.app.Activity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BAC_MainActivity extends Activity {
	double BACresult=0;
	double PreBAC=0;
	Time lastDrink=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bac_activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainmenu, menu);
		
		return true;
	}
	
	public void BACDrink(View v)
	{
		//BAC.incrDrink();
		//BACresult = BAC.calculateBAC(150, "male");
		
		Log.e("1","~");
		Time currentTime = new Time();
		Log.e("1","~");
		currentTime.setToNow();
		if(lastDrink==null){
			lastDrink=new Time();
			lastDrink.setToNow();
		}
		int hr = calculateHour(lastDrink,currentTime);
		//lastDrinkTime.setToNow();
		
		Log.e("debug",currentTime.format3339(false));
		lastDrink = currentTime;
		
		BAC.incrDrink();
		BAC.setHour(hr);
		PreBAC=BACresult;
		BACresult = BAC.calculateBAC(150, "male");
		Log.d("BAC is ", String.valueOf(BACresult));
		TextView BAC = (TextView)findViewById(R.id.BACResult);
		BAC.setText(String.valueOf(BACresult));
	}
	private int calculateHour(Time lastDrink, Time currentTime) {
		// TODO Auto-generated method stub
		return 24*(currentTime.yearDay-lastDrink.yearDay)+currentTime.hour-lastDrink.hour;
	}

	public void BACCancel(View v)
	{
		Time currentTime = new Time();
		currentTime.setToNow();
		int diff = calculateSecond(currentTime,lastDrink);
		Log.e("time",String.valueOf(diff));
		if (diff>5){
			Toast.makeText(getApplicationContext(), "time out", Toast.LENGTH_SHORT).show();		
		}else{
			Toast.makeText(getApplicationContext(), "Drink cancled!", Toast.LENGTH_SHORT).show();
			BAC.decDrink();
			BACresult=PreBAC;
		}
		TextView BAC = (TextView)findViewById(R.id.BACResult);
		BAC.setText(String.valueOf(BACresult));
	}

	private int calculateSecond(Time currentTime, Time lastDrink2) {
		// TODO Auto-generated method stub
		return 60*(currentTime.minute-lastDrink2.minute)+currentTime.second-lastDrink2.second;
	}
}
