package ngo.vnexpress.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.Map;

import ngo.vnexpress.reader.R;
import ngo.vnexpress.reader.Fragments.ListViewNewsLiveFragment;
import ngo.vnexpress.reader.Fragments.NavigationDrawerFragment;
import ngo.vnexpress.reader.Fragments.SocialNetworkFragment;
import ngo.vnexpress.reader.RSS.RSSItem;
import ngo.vnexpress.reader.backgroundnotification.NotificationService;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends FragmentActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	public static boolean FirstOpen;
	public static Constant currentFragment = Constant.List;
	public static Constant curViewGroup = null;
	public static Activity activity = null;
	public static List<RSSItem> rssItems = new ArrayList<RSSItem>();
	public static int LIMITED_NUMBER = 100;
	public static NameCategories nameCategory = null;
	public static int numberNewPost = 0;
	public static HashMap<NameCategories, Integer> newArticlePerCate = new HashMap<NameCategories,Integer>();
	private AdView adView;
	private static final String AD_UNIT_ID = "ca-app-pub-9919394378512649/7852974411";
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	private int mIconId;
	
	/**
	 * Screen's Size
	 */
	private static int screenHeight;
	private static int screenWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Initialize Map
		if (newArticlePerCate.isEmpty()){
			Log.d("DEBUG", "INITIAL MAP");
			initializeMap();
		}
		
		setContentView(R.layout.activity_main);
		/**
		 * get screen's size;
		 */
		//Start background Service
		//Intent i=new Intent(this, NotificationService.class);
		// Create an ad.
	   // adView = (AdView) findViewById(R.id.adView);
	    // Add the AdView to the view hierarchy. The view will have no size
	    // until the ad is loaded.
	    
	    
	 // Create an ad request. Check logcat output for the hashed device ID to
	    // get test ads on a physical device.
	  //  AdRequest adRequest = new AdRequest.Builder().build();

	    // Start loading the ad in the background.
	   // adView.loadAd(adRequest);
	
	  
				
		/**
		 * get screen's size;
		 */
		
		
		//Get the width and length of the screen
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		screenHeight = displayMetrics.heightPixels;
		screenWidth = displayMetrics.widthPixels;
		activity = this;
		/**
		 * Navigation Drawer
		 */
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);

		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		// COPY FILE FROM ASSET FOLDER TO MEMORY
		// copyAssets();
		
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		//Action when one of items in drawer fragment clicked
		FragmentManager fragmentManager = getFragmentManager();
		switch (position) {
		case 0:
			nameCategory = NameCategories.Homepage;
			mTitle = getString(R.string.title_home_page);
			mIconId = R.drawable.home;
			break;
		case 1:
			nameCategory = NameCategories.News;
			mTitle = getString(R.string.title_news);
			mIconId = R.drawable.news;
			break;
		case 2:
			nameCategory = NameCategories.Life;
			mTitle = getString(R.string.title_life);
			mIconId = R.drawable.life;
			break;
		case 3:
			nameCategory = NameCategories.World;
			mTitle = getString(R.string.title_world);
			mIconId = R.drawable.world;
			break;
		case 4:
			nameCategory = NameCategories.Business;
			mTitle = getString(R.string.title_business);
			mIconId = R.drawable.business;
			break;
		case 5:
			nameCategory = NameCategories.Entertainment;
			mTitle = getString(R.string.title_entertainment);
			mIconId = R.drawable.entertainment;
			break;
		case 6:
			nameCategory = NameCategories.Sports;
			mTitle = getString(R.string.title_sports);
			mIconId = R.drawable.sport;
			break;
		case 7:
			nameCategory = NameCategories.Laws;
			mTitle = getString(R.string.title_laws);
			mIconId = R.drawable.law;
			break;
		case 8:
			nameCategory = NameCategories.Travelling;
			mTitle = getString(R.string.title_travelling);
			mIconId = R.drawable.travelling;
			break;
		case 9:
			nameCategory = NameCategories.Science;
			mTitle = getString(R.string.title_science);
			mIconId = R.drawable.science;
			break;
		case 10:
			nameCategory = NameCategories.Digital;
			mTitle = getString(R.string.title_digital);
			mIconId = R.drawable.digital;
			break;
		case 11:
			nameCategory = NameCategories.Car;
			mTitle = getString(R.string.title_cars);
			mIconId = R.drawable.car;
			break;
		case 12:
			nameCategory = NameCategories.Social;
			mTitle = getString(R.string.title_social);
			mIconId = R.drawable.social;
			break;
		case 13:
			nameCategory = NameCategories.Chat;
			mTitle = getString(R.string.title_chat);
			mIconId = R.drawable.chat;
			break;
		case 14:
			nameCategory = NameCategories.Funny;
			mTitle = getString(R.string.title_funny);
			mIconId = R.drawable.funny;
			break;
		case 15:
			nameCategory = NameCategories.About;
			mTitle = getString(R.string.title_about);
			mIconId = R.drawable.about;
			break;
		default:
			nameCategory = NameCategories.Homepage;
			mTitle = getString(R.string.title_home_page);
			mIconId = R.drawable.home;
			break;

		}

		//Go to conresponding fragment
		curViewGroup = Constant.List;
		if (nameCategory != NameCategories.About) {
			fragmentManager.beginTransaction()
					.replace(R.id.container, new ListViewNewsLiveFragment())
					.commit();
		} else {
			fragmentManager.beginTransaction()
					.replace(R.id.container, new SocialNetworkFragment())
					.commit();
		}

	}

	//Set title on ActionBar
	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(mIconId);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		
		actionBar.setTitle(mTitle);
	}

	/**
	 * Create Menu in Action Bar
	 * 1: Switch layout
	 * 2: Share on Facebook
	 */
	
	//Create menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			if (currentFragment == Constant.Grid
					|| currentFragment == Constant.List) {
				getMenuInflater().inflate(R.menu.news, menu);
			}
			if(currentFragment == Constant.Web){
				getMenuInflater().inflate(R.menu.webview, menu);
			}
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		return super.onOptionsItemSelected(item);
	}

	/**
	 * @return the standard size for rendering item
	 */
	public static int getStandardSize() {
		return Math.min(screenWidth, screenHeight);
	}
	

	
	
	public void initializeMap(){
		//Log.d("DEBUG", "MAP INI");
		
		for (NameCategories name : NameCategories.values()){
			if (!newArticlePerCate.containsKey(name)){
				//Log.d("MAIN ACTIVITY", "CATE ");
				newArticlePerCate.put(name, 0);
			}
		}
	}
	  public void onResume() {
		    super.onResume();
		  //  if (adView != null) {
		  //    adView.resume();
		 //   }
		  }

		  @Override
		  public void onPause() {
		 //   if (adView != null) {
		 //     adView.pause();
		 //   }
		    super.onPause();
		  }

		  /** Called before the activity is destroyed. */
		  @Override
		  public void onDestroy() {
		    // Destroy the AdView.
			  Intent i=new Intent(this, NotificationService.class);
				startService(i);
		   // if (adView != null) {
		  //    adView.destroy();
		  //  }
		    super.onDestroy();
		  }
		
	
}
