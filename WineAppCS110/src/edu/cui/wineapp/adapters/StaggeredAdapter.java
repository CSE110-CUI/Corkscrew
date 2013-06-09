package edu.cui.wineapp.adapters;

import java.util.ArrayList;

import com.fima.cardsui.views.CardUI;

import edu.cui.wineapp.R;
import edu.cui.wineapp.R.id;
import edu.cui.wineapp.R.layout;
import edu.cui.wineapp.utilities.ImageLoader;
import edu.cui.wineapp.views.ScaleImageView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


public class StaggeredAdapter extends ArrayAdapter<String> {

	private ImageLoader mLoader;

	public StaggeredAdapter(Context context, int textViewResourceId,
			ArrayList<String> myWineURLs) {
		super(context, textViewResourceId, myWineURLs);
		mLoader = new ImageLoader(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;



		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			convertView = layoutInflator.inflate(R.layout.row_staggered_demo,null);
			holder = new ViewHolder();
			holder.imageView = (ScaleImageView) convertView .findViewById(R.id.imageView1);
			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();

		mLoader.DisplayImage(getItem(position), holder.imageView);

		return convertView;
	}

	static class ViewHolder {
		ScaleImageView imageView;
	}
}
