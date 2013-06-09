package edu.cui.wineapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import edu.cui.wineapp.R;
import edu.cui.wineapp.R.drawable;
import edu.cui.wineapp.R.id;
import edu.cui.wineapp.R.layout;

public class CustomAdapter extends ArrayAdapter<String>
{
	private final Context context;
	private final ArrayList<String> values;
	
	public CustomAdapter(Context context, ArrayList<String> values)
	{
		super(context, R.layout.rowlayout, values);
		this.context = context;
		this.values = values;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		textView.setText(values.get(position));
		
		// change the icon
		String s = values.get(position);
		if(s.startsWith("Get Random Wine"))
		{
			imageView.setImageResource(R.drawable.rand_icon);
		}
		else if(s.startsWith("Top Wines"))
		{
			imageView.setImageResource(R.drawable.top_icon);
		}
		else if(s.startsWith("Current BAC"))
		{
			imageView.setImageResource(R.drawable.curr_bax_x);
		}		
		else if(s.startsWith("Clear BAC"))
		{
			imageView.setImageResource(R.drawable.clr_bar);
		}	
		else if(s.startsWith("User Profile"))
		{
			imageView.setImageResource(R.drawable.user_prof);
		}
		else if(s.startsWith("User Profile"))
		{
			imageView.setImageResource(R.drawable.user_prof);
		}
		else if(s.startsWith("DO NOT TOUCH"))
		{
			imageView.setImageResource(R.drawable.gillsp);
		}
		else if(s.startsWith("Clear Wine"))
		{
			imageView.setImageResource(R.drawable.clear_icon);
		}	
		else if(s.startsWith("Logout"))
		{
			imageView.setImageResource(R.drawable.logout_icon);
		}	
		else
		{
			imageView.setImageResource(R.drawable.wh_icon);
		}
		return rowView;
	}
}
