package edu.cui.wineapp.fragments;

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
import edu.cui.wineapp.R;
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


		contextForDialog = getActivity().getParent();
		
		dWine = WineInfoManager.getDetailedWine();
		//dWine.getName();

		updateComments();

		commentBox.setOnEditorActionListener(new OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView v, int actionId,	KeyEvent event){
				commentLength = commentBox.getText().toString().length();
				boolean handled = false;

				if (actionId == EditorInfo.IME_ACTION_SEND){

					UserManager.getUserManager(getActivity()).setComment(
							dWine.getCode(), String.valueOf(UserManager.getLocalUser().getId()), commentBox.getText().toString());
					commentBox.setText("");
					
					handled = true;
				}

				prevCommentCount = currentCommentCount;
				if(commentLength > 0){
					do updateComments(); while(prevCommentCount == currentCommentCount);
				}

				return handled;
			}});

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
					    	commentLength=userInput.getText().toString().length();
							UserManager.getUserManager(getActivity()).setComment(
									dWine.getCode(), String.valueOf(UserManager.getLocalUser().getId()), userInput.getText().toString());
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
		return rootView;
	}

	protected void updateComments(){
				
		ArrayList<Comment>commentList = (UserManager.getUserManager(getActivity()).getComment(
				"WHERE winecode = '"+dWine.getCode()+"' ORDER BY date DESC"));
		
		currentCommentCount = commentList.size();
		
		ArrayList<String> commentStrings = new ArrayList<String>();

		for(Comment c:commentList)
			commentStrings.add(c.getComment());

		ArrayAdapter<String> t =new ArrayAdapter<String>(
				getActivity().getApplicationContext(), R.layout.list_item_black, commentStrings);
		setListAdapter(t);
		t.notifyDataSetChanged();
	}
}