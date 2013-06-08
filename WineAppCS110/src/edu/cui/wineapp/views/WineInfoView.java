package edu.cui.wineapp.views;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.viewpagerindicator.TabPageIndicator;

import edu.cui.wineapp.R;
import edu.cui.wineapp.WineInfoActivity;
import edu.cui.wineapp.R.id;

public class WineInfoView extends View{
	
	private ViewPager mViewPager;
	private TabPageIndicator mTitleIndicator;
	private TextView name;
	private TextView varietal;
	private TextView region;
	private TextView wineRating;
	private ImageView wineImage;

	
	public WineInfoView(Activity activity) {
		super(activity,null);
		
		View view = WineInfoActivity.wineInfoActivity.getWindow().getDecorView().getRootView();
		
		mViewPager = (ViewPager) view.findViewById(R.id.pager);
		mTitleIndicator = (TabPageIndicator)view.findViewById(R.id.indicator);
		
		name 		= (TextView) view.findViewById(R.id.textview_name);
		varietal	= (TextView) view.findViewById(R.id.textview_varietal);
		region 		= (TextView) view.findViewById(R.id.textview_region);
		wineRating 	= (TextView) view.findViewById(R.id.textview_rating);
		wineImage	= (ImageView)view.findViewById(R.id.image);
		
	}

	public ImageView getWineImage() {
		return wineImage;
	}



	public void setWineImage(ImageView wineImage) {
		this.wineImage = wineImage;
	}

	
	
	public ViewPager getmViewPager() {
		return mViewPager;
	}



	public TabPageIndicator getTitleIndicator() {
		return mTitleIndicator;
	}



	public TextView getName() {
		return name;
	}



	public TextView getVarietal() {
		return varietal;
	}



	public TextView getRegion() {
		return region;
	}



	public TextView getWineRating() {
		return wineRating;
	}



	public void setmViewPager(ViewPager mViewPager) {
		this.mViewPager = mViewPager;
	}



	public void setTitleIndicator(TabPageIndicator titleIndicator) {
		this.mTitleIndicator = titleIndicator;
	}



	public void setName(TextView name) {
		this.name = name;
	}



	public void setVarietal(TextView varietal) {
		this.varietal = varietal;
	}



	public void setRegion(TextView region) {
		this.region = region;
	}



	public void setWineRating(TextView wineRating) {
		this.wineRating = wineRating;
	}
	

}
