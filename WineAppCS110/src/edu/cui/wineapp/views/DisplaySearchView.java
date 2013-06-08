package edu.cui.wineapp.views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import com.fima.cardsui.views.CardUI;

import edu.cui.wineapp.DisplaySearchActivity;
import edu.cui.wineapp.R;

public class DisplaySearchView extends View{
	private CardUI mCardView;
	private ListView mListView;
	private SearchView searchView;
	
	public DisplaySearchView (Activity activity)
	{
		super(activity, null);
		View view = DisplaySearchActivity.disSearchAct.getWindow().getDecorView().getRootView();

		mCardView = (CardUI) view.findViewById(R.id.cardsview);
		mListView = (ListView) view.findViewById(android.R.id.list);
	}


	public CardUI getmCardView() {
		return mCardView;
	}

	public void setmCardView(CardUI mCardView) {
		this.mCardView = mCardView;
	}

	public ListView getmListView() {
		return mListView;
	}

	public void setmListView(ListView mListView) {
		this.mListView = mListView;
	}

	public SearchView getSearchView() {
		return searchView;
	}

	public void setSearchView(SearchView searchView) {
		this.searchView = searchView;
	}
}
