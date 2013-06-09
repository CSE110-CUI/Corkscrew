package edu.cui.wineapp;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.ListFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import edu.cui.wineapp.models.Comment;
import edu.cui.wineapp.models.managers.UserManager;
import edu.cui.wineapp.models.managers.WineInfoManager;

public class WineNotesFragmentManager extends ListFragment{

	private int currentCommentCount;
	private int prevCommentCount;
	private Activity activity;
/*	
	public void updateComments(){
		UserManager.getUserManager(WineNotesFragment.contextForUM);
		ArrayList<Comment>commentList = (UserManager.getUserManager(WineNotesFragment.contextForUM).getComment(
				"WHERE winecode = '"+WineInfoManager.getDetailedWine().getCode()+"' ORDER BY date DESC"));

		setCurrentCommentCount(commentList.size());

		ArrayList<String> commentStrings = new ArrayList<String>();

		for(Comment c:commentList)
			commentStrings.add(c.getComment());

		//WineNotesFragment.wineNotesFragment.updateInFragment(this);
		//WineNotesFragment wineFrag = new WineNotesFragment();
		//WineNotesFragment.wineNotesFragment.updateInFragment(this);
		//WineNotesFragment.wineNotesFragment.updateInFragment();
		
		ArrayAdapter<String> comments =new ArrayAdapter<String>(
				WineNotesFragment.contextForUM, R.layout.list_item_black, commentStrings);
		setListAdapter(comments);
		comments.notifyDataSetChanged();
		
		//return commentStrings;
		
	}

	public int getCurrentCommentCount() {
		return currentCommentCount;
	}

	public void setCurrentCommentCount(int currentCommentCount) {
		this.currentCommentCount = currentCommentCount;
	}

	protected static void postComment(TextView v) {
		UserManager.getUserManager(WineNotesFragment.contextForUM).setComment(
				WineInfoManager.getDetailedWine().getCode(), String.valueOf(UserManager.getLocalUser().getId()), v.getText().toString());
		v.setText("");
	}


	public OnEditorActionListener getEAL(final EditText commentBox){
		return new OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView v, int actionId,	KeyEvent event){
				boolean handled = false;

				if (actionId == EditorInfo.IME_ACTION_SEND){
					postComment(commentBox);
					handled = true;
				}

				refreshComments(commentBox);
				return handled;
			}
		};

	}

	public OnClickListener getOCL(final EditText commentBox){
		return new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// get prompts.xml view
				LayoutInflater li = (LayoutInflater)WineNotesFragment.wineNotesFragment.getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View promptsView = li.inflate(R.layout.popup_prompt, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(WineNotesFragment.wineNotesFragment.getActivity());

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);

				// set dialog message
				initADB(userInput, alertDialogBuilder);

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}
		};		
	}

	private void refreshComments(TextView v) {
		prevCommentCount = currentCommentCount;
		if(v.getText().length() > 0){
			do updateComments(); while(prevCommentCount == currentCommentCount);
		}
	}

	private void initADB(final EditText commentBox,
			AlertDialog.Builder alertDialogBuilder) {
		alertDialogBuilder
		.setCancelable(false)
		.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				postComment(commentBox);
				refreshComments(commentBox);
			}
		})
		.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});
	}*/
}

/*
	public void updateComments(){
		UserManager.getUserManager(WineNotesFragment.contextForUM);
		ArrayList<Comment>commentList = (UserManager.getUserManager(WineNotesFragment.contextForUM).getComment(
				"WHERE winecode = '"+WineInfoManager.getDetailedWine().getCode()+"' ORDER BY date DESC"));

		setCurrentCommentCount(commentList.size());

		ArrayList<String> commentStrings = new ArrayList<String>();

		for(Comment c:commentList)
			commentStrings.add(c.getComment());

		
		//WineNotesFragment.wineNotesFragment.getActivity().getApplicationContext();
		
		
		  ArrayAdapter<String> t =new ArrayAdapter<String>(
				    WineNotesFragment.contextForUM, R.layout.list_item_black, commentStrings);
				  setListAdapter(t);
				  t.notifyDataSetChanged();
	}
 */
//WineNotesFragment.wineNotesFragment.getActivity().getApplicationContext();

/*
		  ArrayAdapter<String> t =new ArrayAdapter<String>(
				    WineNotesFragment.contextForUM, R.layout.list_item_black, commentStrings);
				  setListAdapter(t);
				  t.notifyDataSetChanged();
 */

//return null;