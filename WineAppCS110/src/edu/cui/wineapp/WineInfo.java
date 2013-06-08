package edu.cui.wineapp;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.TabPageIndicator;

import edu.cui.wineapp.Controllers.UserManager;
import edu.cui.wineapp.Models.Comment;
import edu.cui.wineapp.Models.DetailedWine;
import edu.cui.wineapp.Models.Food;
import edu.cui.wineapp.Models.Wine;

public class WineInfo extends FragmentActivity {

	ArrayList<String> foodNames;

	CollectionAdapter mAdapter;
	ViewPager mViewPager;
	Wine currentWineBASIC;
	DetailedWine currentWine;
	Context context = null;
	ArrayList<Comment> commentArrayList;
	ArrayList<String>  commentBodyArrayList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {


		Typeface robottoBlack = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Black.ttf");        
		Typeface robottoBold = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-BoldCondensed.ttf");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wine_info);


		mAdapter = new CollectionAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAdapter);


		TabPageIndicator titleIndicator = (TabPageIndicator)findViewById(R.id.indicator);
		titleIndicator.setViewPager(mViewPager);

		Log.e("WineInfo.java/onCreate","Wine from Bundle: "+getIntent().getExtras().getString("passedWine"));
		
		//currentWineBASIC = (Wine) WineManager.getWineManager(this).getWineByName(getIntent().getExtras().getString(("passedWine"))).get(0);
		//currentWineBASIC = (Wine) WineManager.getWineManager(this).downloadWineByName(getIntent().getExtras().getString("passedWine")).get(0);
		//currentWineBASIC = WineManager.getWineManager(this).downloadWineByName(getIntent().getExtras().getString("passedWine")).get(0);
		currentWine = WineManager.getWineManager(this).downloadDetailedWine(getIntent().getExtras().getString("passedWine"));
		currentWineBASIC = new Wine(
				currentWine.getId(),
				currentWine.getName(),
				currentWine.getCode(),
				currentWine.getRegion(),
				currentWine.getWinery(),
				currentWine.getWinery_id(),
				currentWine.getVarietal(),
				currentWine.getPrice(),
				currentWine.getVintage(),
				currentWine.getType(),
				currentWine.getLink(),
				currentWine.getTags(),
				currentWine.getImage(),
				currentWine.getSnoothrank(),
				currentWine.getAvailability(),
				currentWine.getNum_merchants(),
				currentWine.getNum_reviews()
			);
		
		TextView name = (TextView) findViewById(R.id.textview_name);
		name.setText(currentWine.getName());
		name.setTypeface(robottoBold);

		TextView varietal = (TextView) findViewById(R.id.textview_varietal);
		varietal.setText(currentWine.getVarietal());

		TextView region = (TextView) findViewById(R.id.textview_region);
		region.setText(currentWine.getRegion());

		TextView wineRating = (TextView) findViewById(R.id.textview_rating);
		wineRating.setText((int)currentWine.getSnoothrank() * 20 > 0 ? Integer.toString((int)currentWine.getSnoothrank() * 20) : "NA");
		wineRating.setTextColor(Color.parseColor("#A60000"));


		String tags = "wine fruit red flavors finish aromas palate notes fresh wines sweet pinot rich well white nose long our grapes very vintage oak chardonnay blend acidity noir color great full tannins years ripe vineyard moscato bouquet yet sauvignon soft balanced und bodied fermentation perfect complex light citrus made black cherry valley bright mouth cabernet complexity spicy crisp spice peach rose good you blanc elegant like dry floral balance fruity now green tasting best dark vineyards texture months fruits yellow fine dishes delicate alcohol aromatic hint medium food apple taste fermented character clean style grape pear winery refreshing points through year hints fish drink colour vanilla mineral classic structure vibrant cool smooth aging then pink notes merlot aperitif intense mit lemon bottle pale melon strawberry new produced lively excellent sparkling subtle barrels river warm make zinfandel grown gold honey summer deep ideal estate powerful vines followed barrel hand orange grilled meat berries what creamy supple lovely these tropical cherries cheeses berry growing rosé napa dried temperature sonoma russian two aged time first franc concentrated glass quality still feel age flowers brut cassis cold small aroma french harvest where flavor shows production freshness gives delicious days body touch raspberry plum straw makes stainless flavours blossom steel round blackberry purple top old dessert beautiful low serve der high beautifully while bottled region spices early layers ruby sugar before exotic bottling herbs few release enjoy chilled including offers pineapple three appellation pressed following brilliant von chocolate enjoyed roasted peaches natural syrah both soil persistent blueberry note foods cellar variety lots asti juice finest yeast lingering showing firm tobacco planted champagne meats traditional international must richness perfectly tank slightly raspberries find velvety gris bubbles lime pepper san serving apricot aromen pressing gaumen harvested unique length winemaking wonderful cases licorice focused smoke violet expression bordeaux wild may honeysuckle blue abgang family minerality percentage winemaker qualities smoky even carneros france whole characters california just selected muscat county toasty quite cru nice drinking adds each special down your climate between once depth world grapefruit sweetness rather spring blancs easy place strong show mid provide order die asian stone another pure times accompaniment grenache addition wood horse day weather air pretty give malolactic selection delicately characteristics italy clear nuances beaune back blended strawberries picked american mocha level soils intensity recommended ginger rated hot lamb apricots produce highly refined here during per result many typical sun advocate forward fermenters class reveals seafood finishes call pair cinnamon competition potential producing acid should late offering served varieties grand august under young chicken salad resulting five lush savory mango pleasant santa harmonious parker especially caramel based prior integrated lightly rare sauce although superb structured further being earthy exceptional right tannin next varietal match displays semi found range cream exhibits nach semillon noten shellfish add clusters pairings highlights way itself sunshine petals established weeks almost business scents comes components currant use open known rex terroir soave racked controlled game com own process wein free certainly grass nase tea coast slight les frucht meyer allowed evident mellow blending super represents eye become component entry season art item spectator chablis total four silky steak six press direct burghound beach aftertaste elements matched elegance method perfumed fragrant extract limestone organic apples used historic different remarkable area interesting adding period spent sich persistence finishing gooseberry lasting those gently earth candied destemmed voluptuous leather desserts carefully golden vats sur making contact owned extraordinary rock precision north september aromatics mature several along date enough mediterranean everyday large create combine coffee fleshy maceration type tempranillo gentle already heart finesse cuisine again always pairs though tiers label hills robert dense seductive crushed crus built vine layered lingers aspects liqueur acres deliciously riesling toasted mushroom rioja friendly colored jam off pairing bottles soak reminiscent enjoyment flavour youth sea des tastes allen meadows winemakers since cava regions enticing put mostly allow arrival cab minerals bitter underscored perdrix power complemented issue hectares senses silver skins broad salmon morning using poultry roses jun less value southern amazing italian does mild frame beef minimal baked pts primary premier sensual enjoyable product peel above average famous ages ample combines vosne trousseau frizzante heavy ridge blood keep climates information distinctive skin fragrance real louis run certification block almonds wonderfully sleek rounded beaujolais concentrate hue garnet bit native mint until amount temperatures it’s domaine opens true ist heat passionfruit complement contributed results added bursting hill example aber blossoms opened often honeyed wide reviewed lees lobster outstanding wine’s vinification mouthfeel anselmi ein cedar secondary toast anderson better usually buttery month filtered dosage tangerine cuvee came maturity einem among nutmeg producers duarte technical cork iron zest smoked grow juicy particularly plenty impression burgundy combination reifen cut experience home south box called den reds canelli vegetables harvesting pedigree champs blackberries extraordinarily graphite körper ripening help within tanks villages eine roundness meant das anise filled chalky meditazione savoury throughout northern today flavorful regular really scent produces daniel appearance immediate ever “the residual solera system meal mandrarossa revealing truly features regularly stock across relatively price spain facing rains cuvée difference www towards petit amber weight inside textured canopy caramelized mouthwatering patolitas las settle overnight change stopped linger amongst effort fairly accolades became retain chateau cinsault incense half too malbec rate district enhance developing attractive iwc lends crystal lavender seem saw expressive crush clone plush tiny riper focus packed shy offer completely gras foie profile cellaring favorite exclusively hearty understated considering vibrancy pastry almond hallmark crop tapanappa management fog part ripeness nuanced defined barbara remo acacia try twice sappy autumn evolve sip expansive strength nuance volume possess sparklings infused coat lie see provides floor partners gracefully remains ml bought conditions article name contra separately josh cooking room ready hold extremely costa kiwi second disgorging develop oaky mendocino central heady underbrush local thanks rhone pie viticultural background sterling leading fanucchi terrific leaves parcels turley table sehr framed having lighter champenoise softness slow exactly fig impressively forest cheese approximately looking risotto impressive cooled delightful creates dundee oregon allegro weich blosser sokol multi popular exceptionally spirits point accents non degrees";
		String[] tagArr = tags.split(" ");
		Log.e("TAG ARRAY SIZE",""+tagArr.length);
		for(String s : tagArr){
			if(currentWine.getWm_notes().contains(s))
				currentWine.setTags(currentWine.getTags()+s+" ");
		}
		
		Log.e("Current Wine Tags",currentWine.getTags());
		
		new DownloadImageTask((ImageView) findViewById(R.id.image))
		.execute(currentWine.getImage());


		ArrayList<Food> myFoods = currentWine.getFoodPairings(this);
		//Log.i("WineInfo.java/onCreate", "Local myFoods.size() = " + Integer.toString(myFoods.size()));

		foodNames = new ArrayList<String>();
		if(myFoods != null)
			for (Food f:myFoods) 
				foodNames.add(f.getName());


		/*
        ListView foodList = (ListView) findViewById(R.id.listView);
        foodList.setAdapter(new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, foodNames));
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.mainmenu, menu);
	    return true;
	}
	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_refresh:
	    	UserManager.setLocalUser("2", 2, 2, "2", "male", "c", "c", 1);
	    	UserManager.addDrink(this.currentWineBASIC);
	      Toast.makeText(this, "Current Wine: " + UserManager.getLocalUser().getCurrentWine() 
	    		  +"\nCurrent BAC: "+String.valueOf(UserManager.getLocalUser().getBAC()), Toast.LENGTH_SHORT)
	          .show();
	      break;
	    case R.id.action_settings:
	      Toast.makeText(this, "Action Settings selected", Toast.LENGTH_SHORT)
	          .show();
	      break;

	    default:
	      break;
	    }

	    return true;
	  }

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap bitmapIcon = null;

			try {
				InputStream in = new URL(urldisplay).openStream();
				bitmapIcon = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				try {
					InputStream in = getAssets().open("wineDefault.jpg");
					Log.e("TRY", "Trying to put default photo");
					bitmapIcon = BitmapFactory.decodeStream(in);
				} catch (IOException e1) {
					Log.e("CATCH", "Failed to put default photo");
					e1.printStackTrace();
				}
			}

			return bitmapIcon;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}
	
	

	public class CollectionAdapter extends FragmentPagerAdapter {
		public CollectionAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Bundle currWine = new Bundle();
			Fragment fragment = null;

			switch(i){

			//Overview
			case 0:
				fragment = new WineOverviewFragment();
				currWine.putSerializable(WineOverviewFragment.ARG_WINE, currentWine);
				fragment.setArguments(currWine);
				break;

			//Notes
			case 1: 
				fragment = new WineNotesFragment();
				currWine.putSerializable(WineOverviewFragment.ARG_WINE, currentWine);
				fragment.setArguments(currWine);
				break;

			//Reviews
			case 2:
				fragment = new WineReviewFragment();
				currWine.putSerializable(WineReviewFragment.ARG_WINE, currentWine);
				fragment.setArguments(currWine);
				break;

			//More Info
			case 3:
				fragment = new WineOverviewFragment();
				currWine.putSerializable(WineOverviewFragment.ARG_WINE, currentWine);
				fragment.setArguments(currWine);
				break;	
			}

			return fragment;
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String tabTitle = null;

			switch(position){
			case 0:
				tabTitle = "Overview";
				break;
			case 1:
				tabTitle = "Notes";
				break;
			case 2:
				tabTitle = "Reviews";
				break;
			case 3:
				tabTitle = "More Info";
				break;
			}

			return tabTitle;
		}

	}

}













