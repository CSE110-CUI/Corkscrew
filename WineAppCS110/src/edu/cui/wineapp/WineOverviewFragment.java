package edu.cui.wineapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WineOverviewFragment extends Fragment {
    public static final String ARG_VIEW_INDEX = "view_indx";
    public static final String ARG_WINE = "currWine";
    public static final String ARG_CONTEXT = "currWine_context";
    
    String comment = "commentInsideClass";
 
    ArrayAdapter<String> myAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    	
    	//UserManager.getUserManager(getActivity()).testpost("1", "1", comment);
    	
    	
        View rootView = inflater.inflate(R.layout.fragment_wine_view, container, false);
        Bundle currWine = getArguments();
        DetailedWine dWine = (DetailedWine)currWine.getSerializable(ARG_WINE);
        
		((TextView) rootView.findViewById(R.id.textView)).setText(dWine.getWm_notes());
		
        return rootView;
    }
}