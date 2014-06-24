package ngo.vnexpress.reader;

import java.util.ArrayList;
import java.util.List;

import ngo.vnexpress.reader.R;
import ngo.vnexpress.reader.Fragments.ListViewNewsLiveFragment;
import ngo.vnexpress.reader.Fragments.NavigationDrawerFragment;
import ngo.vnexpress.reader.Fragments.SocialNetworkFragment;
import ngo.vnexpress.reader.RSS.RSSItem;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	public static boolean FirstOpen;
	public static Constant currentFragment = Constant.List;
	public static Constant curViewGroup;
	public static Activity activity;
	public static List<RSSItem> rssItems = new ArrayList<RSSItem>();
	public static int LIMITED_NUMBER = 100;
	public static NameCategories nameCategory;
	
	
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
		setContentView(R.layout.activity_main);
		/**
		 * get screen's size;
		 */
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
		
		//COPY FILE FROM ASSET FOLDER TO MEMORY
		//copyAssets();
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
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
			mIconId = R.drawable.global;
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
		
		curViewGroup = Constant.List;
		if(nameCategory!=NameCategories.About){
			fragmentManager.beginTransaction().replace(R.id.container, new ListViewNewsLiveFragment()).commit();
		} else{
			fragmentManager.beginTransaction().replace(R.id.container, new SocialNetworkFragment()).commit();
		}

	}

	

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(mIconId);
		 actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		 actionBar.setDisplayShowTitleEnabled(true);
		 actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
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
	

}