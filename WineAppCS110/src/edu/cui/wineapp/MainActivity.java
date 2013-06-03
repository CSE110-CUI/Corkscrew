package edu.cui.wineapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.fima.cardsui.objects.CardStack;
import com.fima.cardsui.views.CardUI;


public class MainActivity extends Activity implements SearchView.OnQueryTextListener,
SearchView.OnCloseListener {

	public final static String EXTRA_MESSAGE = "com.cui.wineapp.MESSAGE";
	ArrayList<Wine> myWineList;
	ArrayList<String> wineNames = new ArrayList<String>();
	private ListView mListView;
	private ArrayAdapter<String> myAdapter;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private CardUI mCardView;
    private ArrayList<String> sideMenu = new ArrayList<String>();
	String wineName; 
	TextView name;

	Bundle bundle2 = new Bundle();

	/*
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Wine wineToPass = myWineList.get(position);
        Intent i = new Intent(this, WineCellar.class);
        bundle2.putString("passedWine", wineToPass.getName());
        i.putExtras(bundle2);
        startActivity(i);
    }
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Typeface robottoBold = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-BoldCondensed.ttf");

		TextView name = (TextView) findViewById(R.id.textview_name);
		name.setTypeface(robottoBold);
		name.setTextSize(60);
		name.setGravity(Gravity.CENTER_HORIZONTAL);

		if(UserManager.getUserManager(getApplicationContext()).getLocalUser() == null){
			name.setText("Welcome! To Get Started, Search a Wine Below!");
			Toast.makeText(getApplicationContext(), "INSIDE+NULL", Toast.LENGTH_SHORT).show();
		}
		else{
			name.setText(UserManager.getUserManager(getApplicationContext()).getLocalUser().getCurrentWine());
		}


		mTitle = mDrawerTitle = getTitle();
        sideMenu.add("Choose-a-Wine");
        sideMenu.add("Build-a-Wine");
        sideMenu.add("Pair-a-Wine");
        sideMenu.add("Wine History");

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		// set a custom shadow that overlays the main content when the drawer opens
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new CustomAdapter(this, sideMenu));


		SearchView searchView = (SearchView) findViewById(R.id.searchView1);
		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);
		//mListView = (ListView) findViewById(android.R.id.list);
		//myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wineNames);
		//mListView.setAdapter(myAdapter);
		mCardView = (CardUI) findViewById(R.id.cardsview);
		mCardView.setSwipeable(false);
		mCardView.refresh();

		/*
		 * TextView wineNameText = (TextView) findViewById(R.id.wineName);

		if(UserManager.getLocalUser() == null){
			wineNameText.setText("Welcome! To Get Started, Search a Wine Below!");
			wineNameText.setTypeface(robottoBold);
			wineNameText.setTextSize(60);
		}
		 */





		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				mDrawerLayout,         /* DrawerLayout object */
				R.drawable.ic_launcher,  /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description for accessibility */
				R.string.drawer_close  /* "close drawer" description for accessibility */
				) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


		if (savedInstanceState == null) {
			//selectItem(0);
		}

		Log.i("Inside_OnCreate", "Basic Info");


		//	setListAdapter(myAdapter);
	}
	/*
    @Override
    protected void onResume(){
        super.onResume();
    	if(UserManager.getUserManager(getApplicationContext()).getLocalUser() == null){
    		name.setText("Welcome! To Get Started, Search a Wine Below!");
    		Toast.makeText(getApplicationContext(), "INSIDE+NULL", Toast.LENGTH_SHORT).show();
    	}
    	else{
    		name.setText(UserManager.getUserManager(getApplicationContext()).getLocalUser().getCurrentWine());
    	}
    }
    */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onClose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextChange(String parseText) throws SQLiteException {
		if (parseText.length() >= 3) {

			wineNames = new ArrayList<String>();
			WineManager wManager = new WineManager(this);

			try {
				myWineList = wManager.getWineByName(parseText);
			} catch (SQLiteException e) {
				myWineList = wManager.downloadWineByName(parseText);
			} finally {
				//for (Wine currWine : myWineList) wineNames.add(currWine.getName());
				//myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wineNames);
				//mListView.setAdapter(myAdapter);
				//myAdapter.notifyDataSetChanged();
				for(final Wine currWine:myWineList) {

					DetailedWine dWine = WineManager.getWineManager(getApplicationContext()).downloadDetailedWine(currWine.getCode());
					MyCard currentCard = new MyCard(currWine.getName(),dWine.getWm_notes());

					//wineName = currWine.getCode();

					currentCard.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent i = new Intent(getApplication().getApplicationContext(), WineInfo.class);
							bundle2.putString("passedWine", currWine.getCode());
							i.putExtras(bundle2);
							startActivity(i);
						}
					});

					mCardView.addCard(currentCard);

				}
				mCardView.refresh();
			}
			return true;
		}

		if (parseText.length() == 0) {

			mCardView.clearCards();
			mCardView.refresh();
		}


		return false;
	}



	@Override
	public boolean onQueryTextSubmit(String parseText) {

		Intent i = new Intent(this, DisplaySearchActivity.class);
		bundle2.putString("passedSearchTerm", parseText);
		i.putExtras(bundle2);
		startActivity(i);
		//finish();

		/*
    	wineNames = new ArrayList<String>();
        myWineList = new ArrayList<Wine>();


        Log.i("Inside_QTChange", "Text>=3");

        SearchView searchView = (SearchView) findViewById(R.id.searchView1);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        mListView = (ListView) findViewById(android.R.id.list);

        WineManager wManager = new WineManager(this);
        myWineList = wManager.downloadWineByName(parseText);

        Log.e("WINES SIZE: ",""+myWineList.size());


        for (Wine currWine : myWineList) {
        	if(currWine != null)
        		wineNames.add(currWine.getName());
        }

        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wineNames);
        mListView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
		 */
		/*
        HashMap<String,String> dWineNotes = new HashMap<String,String>();
        int j = 0;
        for(Wine w : WineManager.getWineManager(this).fetchAllWines()){
        	DetailedWine dWine = WineManager.getWineManager(this).downloadDetailedWine(w);
    		dWineNotes.put(dWine.getCode(), dWine.getWm_notes());
        	Log.e("STILL PARSING HASHMAP","WINES PARSED: "+ (j++));
        	//if(j==220)break;
        }

        Log.e("FINISHED PARSING WINES","TOTAL WINES PARSED: "+j);

        String descriptions = new String();

        for(Entry<String, String> entry: dWineNotes.entrySet()){
        	descriptions += entry.getValue() + " ";
        }

        Log.e("FINISHED PARSING VALUES","TOTAL VALUES PARSED: "+j);

        String filename = "tagsDB.txt";
        //String string = "Hello world!";
        FileOutputStream outputStream;

        try {
          outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
          outputStream.write(descriptions.getBytes());
          outputStream.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
		 */





		/*   
        String allWords ="wine 336 2.5% 1 fruit 140 1% 2 red 95 0.7% 3 flavors 90 0.7% 3 finish 88 0.7% 3 aromas 84 0.6% 4 palate 83 0.6% 4 notes 76 0.6% 4 fresh 74 0.6% 4 wines 59 0.4% 5 sweet 58 0.4% 5 pinot 56 0.4% 5 rich 56 0.4% 5 well 54 0.4% 5 white 52 0.4% 5 nose 52 0.4% 5 long 50 0.4% 5 our 49 0.4% 5 grapes 49 0.4% 5 very 49 0.4% 5 vintage 47 0.3% 6 oak 46 0.3% 6 chardonnay 45 0.3% 6 blend 45 0.3% 6 acidity 45 0.3% 6 noir 44 0.3% 6 color 44 0.3% 6 great 43 0.3% 6 full 43 0.3% 6 tannins 43 0.3% 6 years 41 0.3% 6 ripe 39 0.3% 6 vineyard 37 0.3% 6 moscato 37 0.3% 6 bouquet 37 0.3% 6 yet 35 0.3% 6 sauvignon 35 0.3% 6 soft 34 0.3% 6 balanced 34 0.3% 6 und 34 0.3% 6 bodied 34 0.3% 6 fermentation 33 0.2% 7 perfect 32 0.2% 7 complex 32 0.2% 7 light 32 0.2% 7 citrus 32 0.2% 7 made 32 0.2% 7 black 31 0.2% 7 cherry 31 0.2% 7 valley 30 0.2% 7 bright 30 0.2% 7 mouth 30 0.2% 7 cabernet 30 0.2% 7 complexity 30 0.2% 7 spicy 30 0.2% 7 crisp 29 0.2% 7 spice 29 0.2% 7 peach 29 0.2% 7 rose 28 0.2% 7 good 28 0.2% 7 you 28 0.2% 7 blanc 27 0.2% 7 elegant 27 0.2% 7 like 27 0.2% 7 dry 27 0.2% 7 floral 27 0.2% 7 balance 26 0.2% 7 fruity 26 0.2% 7 now 26 0.2% 7 green 26 0.2% 7 tasting 25 0.2% 7 best 25 0.2% 7 dark 25 0.2% 7 vineyards 24 0.2% 7 texture 24 0.2% 7 months 24 0.2% 7 fruits 23 0.2% 7 yellow 23 0.2% 7 fine 23 0.2% 7 dishes 22 0.2% 7 delicate 22 0.2% 7 alcohol 22 0.2% 7 aromatic 22 0.2% 7 hint 22 0.2% 7 medium 22 0.2% 7 food 22 0.2% 7 apple 21 0.2% 7 taste 21 0.2% 7 fermented 21 0.2% 7 character 21 0.2% 7 clean 21 0.2% 7 style 21 0.2% 7 grape 21 0.2% 7 pear 20 0.1% 8 winery 20 0.1% 8 refreshing 20 0.1% 8 points 20 0.1% 8 through 20 0.1% 8 year 20 0.1% 8 hints 19 0.1% 8 fish 19 0.1% 8 drink 19 0.1% 8 colour 19 0.1% 8 vanilla 19 0.1% 8 mineral 19 0.1% 8 classic 19 0.1% 8 structure 19 0.1% 8 vibrant 19 0.1% 8 cool 19 0.1% 8 smooth 19 0.1% 8 aging 18 0.1% 8 then 18 0.1% 8 pink 18 0.1% 8 notes 18 0.1% 8 merlot 18 0.1% 8 aperitif 18 0.1% 8 intense 18 0.1% 8 mit 18 0.1% 8 lemon 18 0.1% 8 bottle 18 0.1% 8 pale 18 0.1% 8 melon 17 0.1% 8 strawberry 17 0.1% 8 new 17 0.1% 8 produced 17 0.1% 8 lively 17 0.1% 8 excellent 16 0.1% 8 sparkling 16 0.1% 8 subtle 16 0.1% 8 barrels 16 0.1% 8 river 16 0.1% 8 warm 16 0.1% 8 make 16 0.1% 8 zinfandel 16 0.1% 8 grown 16 0.1% 8 gold 15 0.1% 8 honey 15 0.1% 8 summer 15 0.1% 8 deep 15 0.1% 8 ideal 15 0.1% 8 estate 15 0.1% 8 powerful 15 0.1% 8 vines 15 0.1% 8 followed 15 0.1% 8 barrel 15 0.1% 8 hand 15 0.1% 8 orange 15 0.1% 8 grilled 15 0.1% 8 meat 15 0.1% 8 berries 15 0.1% 8 what 15 0.1% 8 creamy 14 0.1% 8 supple 14 0.1% 8 lovely 14 0.1% 8 these 14 0.1% 8 tropical 14 0.1% 8 cherries 14 0.1% 8 cheeses 14 0.1% 8 berry 14 0.1% 8 growing 14 0.1% 8 rosé 14 0.1% 8 napa 14 0.1% 8 dried 14 0.1% 8 temperature 14 0.1% 8 sonoma 14 0.1% 8 russian 14 0.1% 8 two 14 0.1% 8 aged 14 0.1% 8 time 14 0.1% 8 first 13 0.1% 8 franc 13 0.1% 8 concentrated 13 0.1% 8 glass 13 0.1% 8 quality 13 0.1% 8 still 13 0.1% 8 feel 13 0.1% 8 age 13 0.1% 8 flowers 13 0.1% 8 brut 13 0.1% 8 cassis 13 0.1% 8 cold 13 0.1% 8 small 13 0.1% 8 aroma 13 0.1% 8 french 13 0.1% 8 harvest 13 0.1% 8 where 13 0.1% 8 flavor 13 0.1% 8 shows 13 0.1% 8 production 12 0.1% 8 freshness 12 0.1% 8 gives 12 0.1% 8 delicious 12 0.1% 8 days 12 0.1% 8 body 12 0.1% 8 touch 12 0.1% 8 raspberry 12 0.1% 8 plum 12 0.1% 8 straw 12 0.1% 8 makes 12 0.1% 8 stainless 12 0.1% 8 flavours 12 0.1% 8 blossom 12 0.1% 8 steel 12 0.1% 8 round 12 0.1% 8 blackberry 12 0.1% 8 purple 12 0.1% 8 top 12 0.1% 8 old 11 0.1% 8 dessert 11 0.1% 8 beautiful 11 0.1% 8 low 11 0.1% 8 serve 11 0.1% 8 der 11 0.1% 8 high 11 0.1% 8 beautifully 11 0.1% 8 while 11 0.1% 8 bottled 11 0.1% 8 region 11 0.1% 8 spices 11 0.1% 8 early 11 0.1% 8 layers 11 0.1% 8 ruby 11 0.1% 8 sugar 11 0.1% 8 before 11 0.1% 8 exotic 11 0.1% 8 bottling 11 0.1% 8 herbs 11 0.1% 8 few 11 0.1% 8 release 11 0.1% 8 enjoy 11 0.1% 8 chilled 11 0.1% 8 including 10 0.1% 8 offers 10 0.1% 8 pineapple 10 0.1% 8 three 10 0.1% 8 appellation 10 0.1% 8 pressed 10 0.1% 8 following 10 0.1% 8 brilliant 10 0.1% 8 von 10 0.1% 8 chocolate 10 0.1% 8 enjoyed 10 0.1% 8 roasted 10 0.1% 8 peaches 10 0.1% 8 natural 10 0.1% 8 syrah 10 0.1% 8 both 10 0.1% 8 soil 10 0.1% 8 persistent 10 0.1% 8 blueberry 10 0.1% 8 note 10 0.1% 8 foods 10 0.1% 8 cellar 10 0.1% 8 variety 10 0.1% 8 lots 10 0.1% 8 asti 10 0.1% 8 juice 10 0.1% 8 finest 10 0.1% 8 yeast 10 0.1% 8 lingering 10 0.1% 8 showing 10 0.1% 8 firm 9 0.1% 8 tobacco 9 0.1% 8 planted 9 0.1% 8 champagne 9 0.1% 8 meats 9 0.1% 8 traditional 9 0.1% 8 international 9 0.1% 8 must 9 0.1% 8 richness 9 0.1% 8 perfectly 9 0.1% 8 tank 9 0.1% 8 slightly 9 0.1% 8 raspberries 9 0.1% 8 find 9 0.1% 8 velvety 9 0.1% 8 gris 9 0.1% 8 bubbles 9 0.1% 8 lime 9 0.1% 8 pepper 9 0.1% 8 san 9 0.1% 8 serving 9 0.1% 8 apricot 9 0.1% 8 aromen 9 0.1% 8 pressing 9 0.1% 8 gaumen 9 0.1% 8 harvested 9 0.1% 8 unique 9 0.1% 8 length 9 0.1% 8 winemaking 9 0.1% 8 wonderful 9 0.1% 8 cases 9 0.1% 8 licorice 9 0.1% 8 focused 9 0.1% 8 smoke 9 0.1% 8 violet 9 0.1% 8 expression 9 0.1% 8 bordeaux 9 0.1% 8 wild 9 0.1% 8 may 9 0.1% 8 honeysuckle 8 0.1% 8 blue 8 0.1% 8 abgang 8 0.1% 8 family 8 0.1% 8 minerality 8 0.1% 8 percentage 8 0.1% 8 winemaker 8 0.1% 8 qualities 8 0.1% 8 smoky 8 0.1% 8 even 8 0.1% 8 carneros 8 0.1% 8 france 8 0.1% 8 whole 8 0.1% 8 characters 8 0.1% 8 california 8 0.1% 8 just 8 0.1% 8 selected 8 0.1% 8 muscat 8 0.1% 8 county 8 0.1% 8 toasty 8 0.1% 8 quite 8 0.1% 8 cru 8 0.1% 8 nice 8 0.1% 8 drinking 8 0.1% 8 adds 8 0.1% 8 each 8 0.1% 8 special 8 0.1% 8 down 8 0.1% 8 your 8 0.1% 8 climate 8 0.1% 8 between 8 0.1% 8 once 8 0.1% 8 depth 8 0.1% 8 world 8 0.1% 8 grapefruit 8 0.1% 8 sweetness 8 0.1% 8 rather 8 0.1% 8 spring 8 0.1% 8 blancs 8 0.1% 8 easy 8 0.1% 8 place 8 0.1% 8 strong 8 0.1% 8 show 8 0.1% 8 mid 7 0.1% 8 provide 7 0.1% 8 order 7 0.1% 8 die 7 0.1% 8 asian 7 0.1% 8 stone 7 0.1% 8 another 7 0.1% 8 pure 7 0.1% 8 times 7 0.1% 8 accompaniment 7 0.1% 8 grenache 7 0.1% 8 addition 7 0.1% 8 wood 7 0.1% 8 horse 7 0.1% 8 day 7 0.1% 8 weather 7 0.1% 8 air 7 0.1% 8 pretty 7 0.1% 8 give 7 0.1% 8 malolactic 7 0.1% 8 selection 7 0.1% 8 delicately 7 0.1% 8 characteristics 7 0.1% 8 italy 7 0.1% 8 clear 7 0.1% 8 nuances 7 0.1% 8 beaune 7 0.1% 8 back 7 0.1% 8 blended 7 0.1% 8 strawberries 7 0.1% 8 picked 7 0.1% 8 american 7 0.1% 8 mocha 7 0.1% 8 level 7 0.1% 8 soils 7 0.1% 8 intensity 7 0.1% 8 recommended 7 0.1% 8 ginger 7 0.1% 8 rated 7 0.1% 8 hot 7 0.1% 8 lamb 7 0.1% 8 apricots 7 0.1% 8 produce 7 0.1% 8 highly 7 0.1% 8 refined 7 0.1% 8 here 7 0.1% 8 during 7 0.1% 8 per 7 0.1% 8 result 7 0.1% 8 many 7 0.1% 8 typical 7 0.1% 8 sun 7 0.1% 8 advocate 7 0.1% 8 forward 7 0.1% 8 fermenters 7 0.1% 8 class 7 0.1% 8 reveals 7 0.1% 8 seafood 7 0.1% 8 finishes 7 0.1% 8 call 7 0.1% 8 pair 7 0.1% 8 cinnamon 7 0.1% 8 competition 7 0.1% 8 potential 7 0.1% 8 producing 7 0.1% 8 acid 7 0.1% 8 should 7 0.1% 8 late 7 0.1% 8 offering 7 0.1% 8 served 6 0% 9 varieties 6 0% 9 grand 6 0% 9 august 6 0% 9 under 6 0% 9 young 6 0% 9 chicken 6 0% 9 salad 6 0% 9 resulting 6 0% 9 five 6 0% 9 lush 6 0% 9 savory 6 0% 9 mango 6 0% 9 pleasant 6 0% 9 santa 6 0% 9 harmonious 6 0% 9 parker 6 0% 9 especially 6 0% 9 caramel 6 0% 9 based 6 0% 9 prior 6 0% 9 integrated 6 0% 9 lightly 6 0% 9 rare 6 0% 9 sauce 6 0% 9 although 6 0% 9 superb 6 0% 9 structured 6 0% 9 further 6 0% 9 being 6 0% 9 earthy 6 0% 9 exceptional 6 0% 9 right 6 0% 9 tannin 6 0% 9 next 6 0% 9 varietal 6 0% 9 match 6 0% 9 displays 6 0% 9 semi 6 0% 9 found 6 0% 9 range 6 0% 9 cream 6 0% 9 exhibits 6 0% 9 nach 6 0% 9 semillon 6 0% 9 noten 6 0% 9 shellfish 6 0% 9 add 6 0% 9 clusters 6 0% 9 pairings 6 0% 9 highlights 6 0% 9 way 6 0% 9 itself 6 0% 9 sunshine 6 0% 9 petals 6 0% 9 established 6 0% 9 weeks 6 0% 9 almost 6 0% 9 business 6 0% 9 scents 6 0% 9 comes 6 0% 9 components 6 0% 9 currant 6 0% 9 use 6 0% 9 open 6 0% 9 known 6 0% 9 rex 6 0% 9 terroir 6 0% 9 soave 6 0% 9 racked 6 0% 9 controlled 6 0% 9 game 6 0% 9 com 6 0% 9 own 6 0% 9 process 6 0% 9 wein 6 0% 9 free 6 0% 9 certainly 5 0% 9 grass 5 0% 9 nase 5 0% 9 tea 5 0% 9 coast 5 0% 9 slight 5 0% 9 les 5 0% 9 frucht 5 0% 9 meyer 5 0% 9 allowed 5 0% 9 evident 5 0% 9 mellow 5 0% 9 blending 5 0% 9 super 5 0% 9 represents 5 0% 9 eye 5 0% 9 become 5 0% 9 component 5 0% 9 entry 5 0% 9 season 5 0% 9 art 5 0% 9 item 5 0% 9 spectator 5 0% 9 chablis 5 0% 9 total 5 0% 9 four 5 0% 9 silky 5 0% 9 steak 5 0% 9 six 5 0% 9 press 5 0% 9 direct 5 0% 9 burghound 5 0% 9 beach 5 0% 9 aftertaste 5 0% 9 elements 5 0% 9 matched 5 0% 9 elegance 5 0% 9 method 5 0% 9 perfumed 5 0% 9 fragrant 5 0% 9 extract 5 0% 9 limestone 5 0% 9 organic 5 0% 9 apples 5 0% 9 used 5 0% 9 historic 5 0% 9 different 5 0% 9 remarkable 5 0% 9 area 5 0% 9 interesting 5 0% 9 adding 5 0% 9 period 5 0% 9 spent 5 0% 9 sich 5 0% 9 persistence 5 0% 9 finishing 5 0% 9 gooseberry 5 0% 9 lasting 5 0% 9 those 5 0% 9 gently 5 0% 9 earth 5 0% 9 candied 5 0% 9 destemmed 5 0% 9 voluptuous 5 0% 9 leather 5 0% 9 desserts 5 0% 9 carefully 5 0% 9 golden 5 0% 9 vats 5 0% 9 sur 5 0% 9 making 5 0% 9 contact 5 0% 9 owned 5 0% 9 extraordinary 5 0% 9 rock 5 0% 9 precision 5 0% 9 north 5 0% 9 september 5 0% 9 aromatics 5 0% 9 mature 5 0% 9 several 5 0% 9 along 5 0% 9 date 5 0% 9 enough 5 0% 9 mediterranean 5 0% 9 everyday 5 0% 9 large 5 0% 9 create 5 0% 9 combine 5 0% 9 coffee 5 0% 9 fleshy 5 0% 9 maceration 5 0% 9 type 5 0% 9 tempranillo 5 0% 9 gentle 5 0% 9 already 5 0% 9 heart 5 0% 9 finesse 5 0% 9 cuisine 5 0% 9 again 5 0% 9 always 5 0% 9 pairs 5 0% 9 though 5 0% 9 tiers 5 0% 9 label 5 0% 9 hills 5 0% 9 robert 5 0% 9 dense 5 0% 9 seductive 5 0% 9 crushed 5 0% 9 crus 5 0% 9 built 5 0% 9 vine 5 0% 9 layered 5 0% 9 lingers 4 0% 9 aspects 4 0% 9 liqueur 4 0% 9 acres 4 0% 9 deliciously 4 0% 9 riesling 4 0% 9 toasted 4 0% 9 mushroom 4 0% 9 rioja 4 0% 9 friendly 4 0% 9 colored 4 0% 9 jam 4 0% 9 off 4 0% 9 pairing 4 0% 9 bottles 4 0% 9 soak 4 0% 9 reminiscent 4 0% 9 enjoyment 4 0% 9 flavour 4 0% 9 youth 4 0% 9 sea 4 0% 9 des 4 0% 9 tastes 4 0% 9 allen 4 0% 9 meadows 4 0% 9 winemakers 4 0% 9 since 4 0% 9 cava 4 0% 9 regions 4 0% 9 enticing 4 0% 9 put 4 0% 9 mostly 4 0% 9 allow 4 0% 9 arrival 4 0% 9 cab 4 0% 9 minerals 4 0% 9 bitter 4 0% 9 underscored 4 0% 9 perdrix 4 0% 9 power 4 0% 9 complemented 4 0% 9 issue 4 0% 9 hectares 4 0% 9 senses 4 0% 9 silver 4 0% 9 skins 4 0% 9 broad 4 0% 9 salmon 4 0% 9 morning 4 0% 9 using 4 0% 9 poultry 4 0% 9 roses 4 0% 9 jun 4 0% 9 less 4 0% 9 value 4 0% 9 southern 4 0% 9 amazing 4 0% 9 italian 4 0% 9 does 4 0% 9 mild 4 0% 9 frame 4 0% 9 beef 4 0% 9 minimal 4 0% 9 baked 4 0% 9 pts 4 0% 9 primary 4 0% 9 premier 4 0% 9 sensual 4 0% 9 enjoyable 4 0% 9 product 4 0% 9 peel 4 0% 9 above 4 0% 9 average 4 0% 9 famous 4 0% 9 ages 4 0% 9 ample 4 0% 9 combines 4 0% 9 vosne 4 0% 9 trousseau 4 0% 9 frizzante 4 0% 9 heavy 4 0% 9 ridge 4 0% 9 blood 4 0% 9 keep 4 0% 9 climates 4 0% 9 information 4 0% 9 distinctive 4 0% 9 skin 4 0% 9 fragrance 4 0% 9 real 4 0% 9 louis 4 0% 9 run 4 0% 9 certification 4 0% 9 block 4 0% 9 almonds 4 0% 9 wonderfully 4 0% 9 sleek 4 0% 9 rounded 4 0% 9 beaujolais 4 0% 9 concentrate 4 0% 9 hue 4 0% 9 garnet 4 0% 9 bit 4 0% 9 native 4 0% 9 mint 4 0% 9 until 4 0% 9 amount 4 0% 9 temperatures 4 0% 9 it’s 4 0% 9 domaine 4 0% 9 opens 4 0% 9 true 4 0% 9 ist 4 0% 9 heat 4 0% 9 passionfruit 4 0% 9 complement 4 0% 9 contributed 4 0% 9 results 4 0% 9 added 4 0% 9 bursting 4 0% 9 hill 4 0% 9 example 4 0% 9 aber 4 0% 9 blossoms 4 0% 9 opened 4 0% 9 often 4 0% 9 honeyed 4 0% 9 wide 4 0% 9 reviewed 4 0% 9 lees 4 0% 9 lobster 4 0% 9 outstanding 4 0% 9 wine’s 4 0% 9 vinification 4 0% 9 mouthfeel 4 0% 9 anselmi 4 0% 9 ein 4 0% 9 cedar 4 0% 9 secondary 4 0% 9 toast 4 0% 9 anderson 4 0% 9 better 4 0% 9 usually 4 0% 9 buttery 4 0% 9 month 4 0% 9 filtered 4 0% 9 dosage 4 0% 9 tangerine 4 0% 9 cuvee 4 0% 9 came 4 0% 9 maturity 4 0% 9 einem 4 0% 9 among 4 0% 9 nutmeg 4 0% 9 producers 4 0% 9 duarte 4 0% 9 technical 4 0% 9 cork 4 0% 9 iron 4 0% 9 zest 4 0% 9 smoked 4 0% 9 grow 4 0% 9 juicy 4 0% 9 particularly 4 0% 9 plenty 4 0% 9 impression 4 0% 9 burgundy 4 0% 9 combination 4 0% 9 reifen 4 0% 9 cut 4 0% 9 experience 4 0% 9 home 4 0% 9 south 4 0% 9 box 3 0% 9 called 3 0% 9 den 3 0% 9 reds 3 0% 9 canelli 3 0% 9 vegetables 3 0% 9 harvesting 3 0% 9 pedigree 3 0% 9 champs 3 0% 9 blackberries 3 0% 9 extraordinarily 3 0% 9 graphite 3 0% 9 körper 3 0% 9 ripening 3 0% 9 help 3 0% 9 within 3 0% 9 tanks 3 0% 9 villages 3 0% 9 eine 3 0% 9 roundness 3 0% 9 meant 3 0% 9 das 3 0% 9 anise 3 0% 9 filled 3 0% 9 chalky 3 0% 9 meditazione 3 0% 9 savoury 3 0% 9 throughout 3 0% 9 northern 3 0% 9 today 3 0% 9 flavorful 3 0% 9 regular 3 0% 9 really 3 0% 9 scent 3 0% 9 produces 3 0% 9 daniel 3 0% 9 appearance 3 0% 9 immediate 3 0% 9 ever 3 0% 9 “the 3 0% 9 residual 3 0% 9 solera 3 0% 9 system 3 0% 9 meal 3 0% 9 mandrarossa 3 0% 9 revealing 3 0% 9 truly 3 0% 9 features 3 0% 9 regularly 3 0% 9 stock 3 0% 9 across 3 0% 9 relatively 3 0% 9 price 3 0% 9 spain 3 0% 9 facing 3 0% 9 rains 3 0% 9 cuvée 3 0% 9 difference 3 0% 9 www 3 0% 9 towards 3 0% 9 petit 3 0% 9 amber 3 0% 9 weight 3 0% 9 inside 3 0% 9 textured 3 0% 9 canopy 3 0% 9 caramelized 3 0% 9 mouthwatering 3 0% 9 patolitas 3 0% 9 las 3 0% 9 settle 3 0% 9 overnight 3 0% 9 change 3 0% 9 stopped 3 0% 9 linger 3 0% 9 amongst 3 0% 9 effort 3 0% 9 fairly 3 0% 9 accolades 3 0% 9 became 3 0% 9 retain 3 0% 9 chateau 3 0% 9 cinsault 3 0% 9 incense 3 0% 9 half 3 0% 9 too 3 0% 9 malbec 3 0% 9 rate 3 0% 9 district 3 0% 9 enhance 3 0% 9 developing 3 0% 9 attractive 3 0% 9 iwc 3 0% 9 lends 3 0% 9 crystal 3 0% 9 lavender 3 0% 9 seem 3 0% 9 saw 3 0% 9 expressive 3 0% 9 crush 3 0% 9 clone 3 0% 9 plush 3 0% 9 tiny 3 0% 9 riper 3 0% 9 focus 3 0% 9 packed 3 0% 9 shy 3 0% 9 offer 3 0% 9 completely 3 0% 9 gras 3 0% 9 foie 3 0% 9 profile 3 0% 9 cellaring 3 0% 9 favorite 3 0% 9 exclusively 3 0% 9 hearty 3 0% 9 understated 3 0% 9 considering 3 0% 9 vibrancy 3 0% 9 pastry 3 0% 9 almond 3 0% 9 hallmark 3 0% 9 crop 3 0% 9 tapanappa 3 0% 9 management 3 0% 9 fog 3 0% 9 part 3 0% 9 ripeness 3 0% 9 nuanced 3 0% 9 defined 3 0% 9 barbara 3 0% 9 remo 3 0% 9 acacia 3 0% 9 try 3 0% 9 twice 3 0% 9 sappy 3 0% 9 autumn 3 0% 9 evolve 3 0% 9 sip 3 0% 9 expansive 3 0% 9 strength 3 0% 9 nuance 3 0% 9 volume 3 0% 9 possess 3 0% 9 sparklings 3 0% 9 infused 3 0% 9 coat 3 0% 9 lie 3 0% 9 see 3 0% 9 provides 3 0% 9 floor 3 0% 9 partners 3 0% 9 gracefully 3 0% 9 remains 3 0% 9 ml 3 0% 9 bought 3 0% 9 conditions 3 0% 9 article 3 0% 9 name 3 0% 9 contra 3 0% 9 separately 3 0% 9 josh 3 0% 9 cooking 3 0% 9 room 3 0% 9 ready 3 0% 9 hold 3 0% 9 extremely 3 0% 9 costa 3 0% 9 kiwi 3 0% 9 second 3 0% 9 disgorging 3 0% 9 develop 3 0% 9 oaky 3 0% 9 mendocino 3 0% 9 central 3 0% 9 heady 3 0% 9 underbrush 3 0% 9 local 3 0% 9 thanks 3 0% 9 rhone 3 0% 9 pie 3 0% 9 viticultural 3 0% 9 background 3 0% 9 sterling 3 0% 9 leading 3 0% 9 fanucchi 3 0% 9 terrific 3 0% 9 leaves 3 0% 9 parcels 3 0% 9 turley 3 0% 9 table 3 0% 9 sehr 3 0% 9 framed 3 0% 9 having 3 0% 9 lighter 3 0% 9 champenoise 3 0% 9 softness 3 0% 9 slow 3 0% 9 exactly 3 0% 9 fig 3 0% 9 impressively 3 0% 9 forest 3 0% 9 cheese 3 0% 9 approximately 3 0% 9 looking 3 0% 9 risotto 3 0% 9 impressive 3 0% 9 cooled 3 0% 9 delightful 3 0% 9 creates 3 0% 9 dundee 3 0% 9 oregon 3 0% 9 allegro 3 0% 9 weich 3 0% 9 blosser 3 0% 9 sokol 3 0% 9 multi 3 0% 9 popular 3 0% 9 exceptionally 3 0% 9 spirits 3 0% 9 point 3 0% 9 accents 3 0% 9 non 3 0% 9 degrees 3 0% 9 ";
        for(int i = 0; i < 10;  i++){
        	char[] myCharArray = Integer.toString(i).toCharArray();
        	allWords = allWords.replace(myCharArray[0], ' ');
        }
        allWords = allWords.replace('.', ' ');
        allWords = allWords.replace('%', ' ');
        String filename = "sanitized.txt";
        //String string = "Hello world!";
        FileOutputStream outputStream;

        try {
          outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
          outputStream.write(allWords.getBytes());
          outputStream.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
		 */

		return true;


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	private int getSearchViewX() {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		return size.x;
	}

	private int getSearchViewY() {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);

		return size.y;
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id) {
			switch(position){
			case 0:
				Intent i = new Intent(MainActivity.this,WineCellar.class);
				startActivity(i);
				break;

			}
		}
	}


}
