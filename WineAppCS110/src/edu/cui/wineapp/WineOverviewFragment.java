package edu.cui.wineapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import edu.cui.wineapp.models.DetailedWine;
import edu.cui.wineapp.models.managers.WineInfoManager;

public class WineOverviewFragment extends Fragment {
    public static final String ARG_WINE = "currWine";
     
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_wine_view, container, false);
        DetailedWine dWine = WineInfoManager.getDetailedWine();
		((TextView) rootView.findViewById(R.id.textView)).setText(dWine.getWm_notes());
		
        return rootView;
    }
}