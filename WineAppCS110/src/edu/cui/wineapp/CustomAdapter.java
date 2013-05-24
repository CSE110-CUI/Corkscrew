package edu.cui.wineapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

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
		if(s.startsWith("Choose-a-Wine"))
		{
			imageView.setImageResource(R.drawable.caw_icon);
		}
		else if(s.startsWith("Build-a-Wine"))
		{
			imageView.setImageResource(R.drawable.baw_icon);
		}
		else if(s.startsWith("Pair-a-Wine"))
		{
			imageView.setImageResource(R.drawable.paw_icon);
		}
		else if(s.startsWith("Wine History"))
		{
			imageView.setImageResource(R.drawable.wh_icon);
		}
		return rowView;
	}
}
