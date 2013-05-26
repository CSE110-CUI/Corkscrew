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
    public static final String ARG_WINE = "currWine";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    	
    	ArrayList<String> reviewBodies = new ArrayList<String>();
    	
        View rootView = inflater.inflate(R.layout.fragment_wine_info_reviews, container, false);
        DetailedWine dWine = (DetailedWine)getArguments().getSerializable(ARG_WINE);
        
        Log.i("WineInfo.java/WineReviewFragment/onCreateView","Review Array Size: "+dWine.getReviews().size());
        
        for(Review r: dWine.getReviews())
        	reviewBodies.add(r.getBody());
        
        setListAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.list_item_black, reviewBodies));        
        return rootView;
    }
}