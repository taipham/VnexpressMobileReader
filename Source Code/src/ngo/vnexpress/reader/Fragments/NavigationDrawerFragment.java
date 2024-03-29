package ngo.vnexpress.reader.Fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ngo.vnexpress.reader.Constant;
import ngo.vnexpress.reader.MainActivity;
import ngo.vnexpress.reader.NameCategories;
import ngo.vnexpress.reader.R;
import ngo.vnexpress.reader.Adapters.ListDrawerItemAdapter;
import ngo.vnexpress.reader.Items.DrawerItem;
import ngo.vnexpress.reader.backgroundnotification.NotificationService;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

/**
 * Fragment used for managing interactions for and presentation of a navigation
 * drawer. See the <a href=
 * "https://developer.android.com/design/patterns/navigation-drawer.html#Interaction"
 * > design guidelines</a> for a complete explanation of the behaviors
 * implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

	/**
	 * Remember the position of the selected item.
	 */
	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

	/**
	 * Per the design guidelines, you should show the drawer on launch until the
	 * user manually expands it. This shared preference tracks this.
	 */
	private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

	/**
	 * A pointer to the current callbacks instance (the Activity).
	 */
	private NavigationDrawerCallbacks mCallbacks;

	/**
	 * Helper component that ties the action bar to the navigation drawer.
	 */
	private ActionBarDrawerToggle mDrawerToggle;

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerListView;
	private View mFragmentContainerView;

	private int mCurrentSelectedPosition = 0;
	private boolean mFromSavedInstanceState;
	private boolean mUserLearnedDrawer;

	public NavigationDrawerFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * Auto generated code
		 */
		// Read in the flag indicating whether or not the user has demonstrated
		// awareness of the
		// drawer. See PREF_USER_LEARNED_DRAWER for details.
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

		if (savedInstanceState != null) {
			mCurrentSelectedPosition = savedInstanceState
					.getInt(STATE_SELECTED_POSITION);
			mFromSavedInstanceState = true;
		}

		// Select either the default item (0) or the last selected item.
		selectItem(mCurrentSelectedPosition);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Indicate that this fragment would like to influence the set of
		// actions in the action bar.
		setHasOptionsMenu(true);
		selectItem(0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mDrawerListView = (ListView) inflater.inflate(
				R.layout.fragment_navigation_drawer, container, false);
		mDrawerListView
		.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectItem(position);
				
			}
		});

		// Create ArrayList for Item in menu
//		 for (NameCategories name : NameCategories.values()){
//			 Log.d(name.toString(), "CATE MAP " + String.valueOf(NotificationService.newArticlePerCate
//						.get(name)));
////		// MainActivity.newArticlePerCate.get(name));
//		 }
		 ArrayList<DrawerItem> listItem = new ArrayList<DrawerItem>();
		//try{
			
			listItem.add(new DrawerItem(getString(R.string.title_home_page),
					R.drawable.home));
			listItem.add(new DrawerItem(getString(R.string.title_news),
					R.drawable.news));
			listItem.add(new DrawerItem(getString(R.string.title_life),
					R.drawable.life));
			listItem.add(new DrawerItem(getString(R.string.title_world),
					R.drawable.world));
			listItem.add(new DrawerItem(getString(R.string.title_business),
					R.drawable.business));
			listItem.add(new DrawerItem(getString(R.string.title_entertainment),
					R.drawable.entertainment));
			listItem.add(new DrawerItem(getString(R.string.title_sports),
					R.drawable.sport));
			listItem.add(new DrawerItem(getString(R.string.title_laws),
					R.drawable.law));
			listItem.add(new DrawerItem(getString(R.string.title_travelling),
					R.drawable.travelling));
			listItem.add(new DrawerItem(getString(R.string.title_science),
					R.drawable.science));
			listItem.add(new DrawerItem(getString(R.string.title_digital),
					R.drawable.digital));
			listItem.add(new DrawerItem(getString(R.string.title_cars),
					R.drawable.car));
			listItem.add(new DrawerItem(getString(R.string.title_social),
					R.drawable.social));
			listItem.add(new DrawerItem(getString(R.string.title_chat),
					R.drawable.chat));
			listItem.add(new DrawerItem(getString(R.string.title_funny),
					R.drawable.funny));
			listItem.add(new DrawerItem(getString(R.string.title_about),
					R.drawable.about));
		//}catch (Exception e){
			
		//}
		// SetAdapter for menu
		mDrawerListView.setAdapter(new ListDrawerItemAdapter(getActionBar()
				.getThemedContext(), R.layout.drawer_list_item, listItem));
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
		NotificationService.numberNewPost = 0;
		
		return mDrawerListView;
	}

	public boolean isDrawerOpen() {
		return mDrawerLayout != null
				&& mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}

	/**
	 * Users of this fragment must call this method to set up the navigation
	 * drawer interactions.
	 *
	 * @param fragmentId
	 *            The android:id of this fragment in its activity's layout.
	 * @param drawerLayout
	 *            The DrawerLayout containing this fragment's UI.
	 */
	public void setUp(int fragmentId, DrawerLayout drawerLayout) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the navigation drawer and the action bar app icon.
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), /* host Activity */
				mDrawerLayout, /* DrawerLayout object */
				R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
				R.string.navigation_drawer_open, /*
				 * "open drawer" description for
				 * accessibility
				 */
				R.string.navigation_drawer_close /*
				 * "close drawer" description for
				 * accessibility
				 */
				) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (!isAdded()) {
					return;
				}

				getActivity().invalidateOptionsMenu(); // calls
				// onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (!isAdded()) {
					return;
				}

				if (!mUserLearnedDrawer) {
					// The user manually opened the drawer; store this flag to
					// prevent auto-showing
					// the navigation drawer automatically in the future.
					mUserLearnedDrawer = true;
					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(getActivity());
					sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true)
					.apply();
				}

				getActivity().invalidateOptionsMenu(); // calls
				// onPrepareOptionsMenu()
			}
			
		};

		// If the user hasn't 'learned' about the drawer, open it to introduce
		// them to the drawer,
		// per the navigation drawer design guidelines.
		if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}

		// Defer code dependent on restoration of previous instance state.
		mDrawerLayout.post(new Runnable() {
			@Override
			public void run() {
				mDrawerToggle.syncState();
			}
		});

		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	private void selectItem(int position) {
		mCurrentSelectedPosition = position;
		
		//Reset the number of new post when the category is clicked
		Set<NameCategories> keySet = NotificationService.newArticlePerCate.keySet();
		if (keySet != null){
			ArrayList<NameCategories> list = new ArrayList<NameCategories>(keySet);
			NameCategories name = list.get(position);
			MainActivity.nameCategory = name;
			NotificationService.newArticlePerCate.put(name, 0);
		}
		
		//Log.d("DEBUG", "DEBUG " + name.toString());
		if (mDrawerListView != null) {
			mDrawerListView.setItemChecked(position, true);
		}
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null) {
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// If the drawer is open, show the global app actions in the action bar.
		// See also
		// showGlobalContextActionBar, which controls the top-left area of the
		// action bar.
		if (mDrawerLayout != null && isDrawerOpen()) {
			inflater.inflate(R.menu.global, menu);
			showGlobalContextActionBar();
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	/**
	 * Config button on Menu Option bar
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		// Button Switch layout
		if (item.getItemId() == R.id.switch_layout) {

			android.app.FragmentManager fragmentManager = getActivity()
					.getFragmentManager();
			switch (MainActivity.currentFragment) {
			// Change from Grid to List
			case Grid:
				ListViewNewsLiveFragment fl = new ListViewNewsLiveFragment();
				fl.setHasOptionsMenu(true);
				fragmentManager.beginTransaction().replace(R.id.container, fl)
				.commit();
				MainActivity.currentFragment = Constant.List;
				MainActivity.curViewGroup = Constant.List;
				break;
				// Change from List to Grid
			case List:
				GridViewNewsLiveFragment fg = new GridViewNewsLiveFragment();
				fg.setHasOptionsMenu(true);
				fragmentManager.beginTransaction().replace(R.id.container, fg)
				.commit();
				MainActivity.currentFragment = Constant.Grid;
				MainActivity.curViewGroup = Constant.Grid;
				break;

			default:
				break;
			}
			return true;
		}
		
		//Share button
		if (item.getItemId() == R.id.share) {

			if (FacebookDialog.canPresentShareDialog(MainActivity.activity.getApplicationContext(), 
                    FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
				FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
						getActivity()).setLink(MainActivity.currentWeb.getLink())
						.setCaption("Find us on Google Play")
						.setName(MainActivity.currentWeb.getTitle())
						.setDescription(MainActivity.currentWeb.getDescription())
						
						.build();
				MainActivity.uiHelper.trackPendingDialogCall(shareDialog.present());
				
			} else {
				  // Fallback. For example, publish the post using the Feed Dialog
				Toast.makeText(getActivity(), "Bạn cần cài ứng dụng facebook", Toast.LENGTH_SHORT).show();
				
			}

		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Per the navigation drawer design guidelines, updates the action bar to
	 * show the global app 'context', rather than just what's in the current
	 * screen.
	 */
	private void showGlobalContextActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setTitle(R.string.app_name);
		actionBar.setIcon(R.drawable.vnexpress);
	}

	private ActionBar getActionBar() {
		return getActivity().getActionBar();
	}

	/**
	 * Callbacks interface that all activities using this fragment must
	 * implement.
	 */
	public static interface NavigationDrawerCallbacks {
		/**
		 * Called when an item in the navigation drawer is selected.
		 */
		void onNavigationDrawerItemSelected(int position);
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

}
