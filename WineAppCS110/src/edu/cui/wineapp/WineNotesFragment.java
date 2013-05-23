package edu.cui.wineapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class WineNotesFragment extends Fragment {
	public static final String ARG_VIEW_INDEX = "view_indx";
	public static final String ARG_WINE = "currWine";
	public static final String ARG_CONTEXT = "currWine";

	// private UserManager userManager;
	//private static DAO dao = null;


	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.fragment_wine_notes, container, false);
		final EditText comment = (EditText) rootView.findViewById(R.id.comment);
		//UserManager.getUserManager(getActivity()).testpost("w", "u", comment.getText().toString());



		comment.setOnEditorActionListener(new OnEditorActionListener()
		{

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event)
			{
				// TODO Auto-generated method stub
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEND)
				{
					UserManager.getUserManager(getActivity()).testpost("w", "u", comment.getText().toString());

					Log.e("bitch nigga","button clicked");
					handled = true;
				}
				return handled;
			}
		});

		return rootView;

	}
}