package edu.cui.wineapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fima.cardsui.views.CardUI;

import edu.cui.wineapp.models.DetailedWine;
import edu.cui.wineapp.models.MyCard;
import edu.cui.wineapp.models.Review;
import edu.cui.wineapp.models.ReviewCard;
import edu.cui.wineapp.models.managers.WineInfoManager;

public class WineReviewFragment extends Fragment {
    public static final String ARG_WINE = "currWine";
	CardUI mCardView;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
    	
    	ArrayList<String> reviewBodies = new ArrayList<String>();
    	
        View rootView = inflater.inflate(R.layout.fragment_wine_info_reviews, container, false);
        DetailedWine dWine = WineInfoManager.getDetailedWine();
        		
        mCardView = (CardUI) rootView.findViewById(R.id.cardsview);
		mCardView.setSwipeable(false);
        
        Log.i("WineInfo.java/WineReviewFragment/onCreateView","Review Array Size: "+dWine.getReviews().size());
        
        ArrayList<Review> arrReview = dWine.getReviews();
        
        for(Review r: arrReview){
        	ReviewCard currentCard = new ReviewCard(r.getName(),String.valueOf(r.getSource()),String.valueOf(r.getRating()),r.getBody());
			mCardView.addCard(currentCard);

        }
        
        if(arrReview.size() == 0){
        	mCardView.addCard(new MyCard("Sorry!","No review found."));
        }
        
		mCardView.refresh();

        return rootView;
    }
}