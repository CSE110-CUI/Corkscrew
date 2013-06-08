package edu.cui.wineapp.controllers;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.TabPageIndicator;

import edu.cui.wineapp.WineInfoActivity;
import edu.cui.wineapp.models.managers.FragmentCollectionManager;
import edu.cui.wineapp.models.managers.UserManager;
import edu.cui.wineapp.models.managers.WineInfoManager;
import edu.cui.wineapp.views.WineInfoView;

public class WineInfoController extends FragmentActivity{
	WineInfoView currView;
	WineInfoManager currManager;

	public WineInfoController(Activity activity, WineInfoView currView, 
			WineInfoManager currManager){

		this.currView = currView;
		this.currManager = currManager;

		initmViewPager(currView.getmViewPager());
		initmTitlePageIndicator(currView.getTitleIndicator());
		initWineName(currView.getName());
		initWineVarietal(currView.getVarietal());
		initWineRegion(currView.getRegion());
		initWineRating(currView.getWineRating());
		initWinePhoto(currView.getWineImage());

		Log.e("4","4");
	}

	private void initWinePhoto(ImageView wineImage) {
		currManager.setWinePhoto(wineImage);
	}

	private void initWineRating(TextView wineRating) {
		wineRating.setText(currManager.getWineRating() * 20 > 0 ? 
				Integer.toString(currManager.getWineRating() * 20) : "NA");
		wineRating.setTextColor(Color.parseColor("#A60000"));	
	}

	private void initWineRegion(TextView region) {
		region.setText(currManager.getWineRegion());
	}

	private void initWineVarietal(TextView varietal) {
		varietal.setText(currManager.getWineVarietal());
	}

	private void initWineName(TextView name) {
		name.setText(currManager.getWineName());
		//name.setTypeface(Typeface.createFromAsset(getAssets(),
		//	"fonts/Roboto-BoldCondensed.ttf"));
	}

	private void initmTitlePageIndicator(TabPageIndicator titleIndicator) {
		titleIndicator.setViewPager(currView.getmViewPager());
	}

	private void initmViewPager(ViewPager mViewPager) {
		FragmentCollectionManager mAdapter = new FragmentCollectionManager(WineInfoActivity.wineInfoActivity.getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);

	}

	public void addDrink() {
		//STOPGAP
		UserManager.setLocalUser("2", 2, 2, "2", "male", "c", "c", 1);
		UserManager.addDrink(WineInfoManager.getBasicWine());
		Toast.makeText(WineInfoActivity.wineInfoActivity, 
				"Current Wine: " + currManager.getCurrentWineName()+"\n" +
				"Current BAC: "+currManager.getCurrentBAC(), Toast.LENGTH_SHORT)
				.show();	
	}

}
