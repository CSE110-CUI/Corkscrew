package edu.cui.wineapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class WineReviewFragment extends ListFragment {
    public static final String ARG_VIEW_INDEX = "view_indx";
    public static final String ARG_WINE = "currWine";
    public static final String ARG_CONTEXT = "currWine";

   ArrayAdapter<String> myAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    	
    	ArrayList<String> reviewBodies = new ArrayList<String>();
        View rootView = inflater.inflate(R.layout.fragment_wine_info_reviews, container, false);
        Bundle currWine = getArguments();
        DetailedWine dWine = (DetailedWine)currWine.getSerializable(ARG_WINE);
        
        Log.i("WineInfo.java/WineReviewFragment/onCreateView","Review Array Size: "+dWine.getReviews().size());
        
        for(int i = 0; i < dWine.getReviews().size(); ++i){
        	reviewBodies.add(dWine.getReviews().get(i).getBody());
        }
        
        Log.i("WineInfo.java/WineReviewFragment/onCreateView","ReviewBody Array Size: "+reviewBodies.size());

        
        //Log.e("WineInfo.java/WineViewFragment/onCreateView","reviewBodies.size() "+Integer.toString((dWine.getReviews().size())));
        //Log.e("WineInfo.java/WineViewFragment/onCreateView","reviewBodies.size() "+Integer.toString((reviewBodies.size())));
        
        myAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item_black, reviewBodies);
        setListAdapter(myAdapter);


        //((TextView) rootView.findViewById(R.id.textView)).setText(Integer.toString(args.getInt(ARG_VIEW_INDEX)));
               			
				
    
        
        
        return rootView;
    }
}