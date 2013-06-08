package edu.cui.wineapp;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import edu.cui.wineapp.models.Comment;
import edu.cui.wineapp.models.DetailedWine;
import edu.cui.wineapp.models.managers.UserManager;
import edu.cui.wineapp.models.managers.WineInfoManager;

public class WineNotesFragment extends ListFragment {
	public static final String ARG_WINE = "currWine";
	public static DetailedWine dWine;
	public int currentCommentCount = 0;
	public int prevCommentCount = -1;
	public int commentLength = 0;
	Context contextForDialog;
	
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState){

		View rootView = inflater.inflate(R.layout.fragment_wine_notes, container, false);
		final EditText commentBox = (EditText) rootView.findViewById(R.id.comment);
		final int origWidth = commentBox.getWidth();
		final int origHeight = commentBox.getHeight();

		contextForDialog = getActivity().getParent();
		
		//dWine = (DetailedWine) getArguments().getSerializable(ARG_WINE);
		dWine = WineInfoManager.getDetailedWine();

		updateComments();

		commentBox.setOnEditorActionListener(new OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView v, int actionId,	KeyEvent event){
				commentLength = commentBox.getText().toString().length();
				if(commentLength > 3)Toast.makeText(getActivity(), "TEXT LONGER THAN 3", Toast.LENGTH_LONG).show();
				boolean handled = false;

				if (actionId == EditorInfo.IME_ACTION_SEND){
/*
					UserManager.getUserManager(getActivity()).setComment(
							dWine.getCode(), String.valueOf(UserManager.getLocalUser().getId()), commentBox.getText().toString());
					commentBox.setText("");
					*/
					handled = true;
				}

				prevCommentCount = currentCommentCount;
				if(commentLength > 0){
					do updateComments(); while(prevCommentCount == currentCommentCount);
				}
				
				commentBox.setWidth(origWidth);
				commentBox.setHeight(origHeight);
				
				return handled;
			}});

		
		final int newWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		final int newHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();


		commentBox.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
				// get prompts.xml view
				LayoutInflater li = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View promptsView = li.inflate(R.layout.popup_prompt, null);
 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
 
				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);
 
				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);
 
				// set dialog message
				alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						// get user input and set it to result
						// edit text
							UserManager.getUserManager(getActivity()).setComment(
									dWine.getCode(), String.valueOf(UserManager.getLocalUser().getId()), commentBox.getText().toString());
							commentBox.setText("");
							prevCommentCount = currentCommentCount;
							if(commentLength > 0){
								do updateComments(); while(prevCommentCount == currentCommentCount);
							}
						
						
						
						
						
					    }
					  })
					.setNegativeButton("Cancel",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
 
				// show it
				alertDialog.show();
 
			}
		});
		/*	
			@Override
			public void onClick(View arg0) {
				//Animation anim=AnimationUtils.loadAnimation( getActivity().getApplicationContext(), R.anim.animation_sample);

				
					commentBox.setWidth(newWidth);
					commentBox.setHeight(newHeight);

				//commentBox.startAnimation(anim);
			}
			

		});
		*/
			

		
		
		return rootView;
	}

	protected void updateComments(){
				
		ArrayList<Comment>commentList = (UserManager.getUserManager(getActivity()).getComment(
				"WHERE winecode = '"+dWine.getCode()+"' ORDER BY date DESC LIMIT 10"));
		
		currentCommentCount = commentList.size();
		
		ArrayList<String> commentStrings = new ArrayList<String>();

		for(Comment c:commentList)
			commentStrings.add(c.getComment());

		setListAdapter(new ArrayAdapter<String>(
				getActivity().getApplicationContext(), R.layout.list_item_black, commentStrings));
	}
}