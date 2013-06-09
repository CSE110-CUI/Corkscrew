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

public class CommentAdapter extends ArrayAdapter<String>
{
	private final Context context;
	private final ArrayList<String> values;
	
	public CommentAdapter(Context context, ArrayList<String> values)
	{
		super(context, R.layout.comment_layout, values);
		this.context = context;
		this.values = values;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.comment_layout, parent, false);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		TextView nameView = (TextView) rowView.findViewById(R.id.name);
		TextView timeView = (TextView) rowView.findViewById(R.id.time);
		TextView commentView = (TextView) rowView.findViewById(R.id.comment);
		
		nameView.setText(values.get(position));
		
		String s = values.get(position);
		if(s.startsWith("Viewtiful Joe"))
		{
			imageView.setImageResource(R.drawable.viewtiful_joe);
		}
		else if(s.startsWith("Puppy"))
		{
			imageView.setImageResource(R.drawable.puppy);
		}
		return rowView;
	}
}
