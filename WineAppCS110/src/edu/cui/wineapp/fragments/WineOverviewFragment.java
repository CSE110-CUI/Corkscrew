package edu.cui.wineapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fima.cardsui.views.CardUI;

import edu.cui.wineapp.R;
import edu.cui.wineapp.models.DetailedWine;
import edu.cui.wineapp.models.MyCard;
import edu.cui.wineapp.models.managers.WineInfoManager;

public class WineOverviewFragment extends Fragment {
	public static final String ARG_WINE = "currWine";
	CardUI mCardView;


	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_wine_view, container, false);
		DetailedWine dWine = WineInfoManager.getDetailedWine();

		mCardView = (CardUI) rootView.findViewById(R.id.cardsview);
		mCardView.setSwipeable(false);


		if(dWine.getWm_notes().length() == 0){
			mCardView.addCard(new MyCard("Sorry!","No wine overview found."));
		}else{
			mCardView.addCard(new MyCard(dWine.getName(),dWine.getWm_notes()));
		}
		
		mCardView.refresh();
		
		return rootView;
	}
}