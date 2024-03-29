/**
 * Function:
 * 	Add a new row in websites table
	Returns all the rows as Website class objects
	Update existing row
	Returns single row
	Deletes a single row
	Check if a website is already existed
 */
package ngo.vnexpress.reader.RSS;

import java.util.ArrayList;
import java.util.List;

import ngo.vnexpress.reader.MainActivity;
import ngo.vnexpress.reader.NameCategories;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RSSDatabaseHandler extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION= 1;

	
	// Database Name
	private static final String DATABASE_NAME = "rssReader";

	// Contacts table name
	private static String TABLE_RSS = "websites";
	
	private static RSSDatabaseHandler mInstance = null;

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_LINK = "link";
	// private static final String KEY_RSS_LINK = "rss_link";
	private static final String KEY_DESCRIPTION = "description";
	// private static final String KEY_IMG = "img_name";
	private static final String KEY_IMG_LINK = "img_link";
	private static final String KEY_PUBLIC_DATE = "public_date";

	private static final int MAX_SIZE_DATABASE = 100;
	// String of table-name
	private String table_name_array[] = { "home_page", "business", "cars",
			"chat", "digital", "entertainment", "sports", "funny", "laws",
			"life", "news", "science", "social", "travelling", "world" };

	// private long insertedRowIndex;

	public RSSDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
		
		
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		for (String table_name : table_name_array) {
			String CREATE_RSS_TABLE = "CREATE TABLE " + table_name + "("
					+ KEY_ID + " INTEGER PRIMARY KEY, " + KEY_TITLE + " TEXT, "
					+ KEY_LINK + " TEXT, " + KEY_IMG_LINK + " TEXT, "
					+ KEY_DESCRIPTION + " TEXT, " + KEY_PUBLIC_DATE + " TEXT"
					+ ");";
			db.execSQL(CREATE_RSS_TABLE);
		}

	}
	
	

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RSS);

		// Create tables again
		onCreate(db);
	}

	
	  public static RSSDatabaseHandler getInstance(Context ctx) {

		    // Use the application context, which will ensure that you 
		    // don't accidentally leak an Activity's context.
		    // See this article for more information: http://bit.ly/6LRzfx
		    if (mInstance == null) {
		      mInstance = new RSSDatabaseHandler(ctx);
		    }
		    return mInstance;
		  }
	  
	/**
	 * Adding a new website in websites table Function will check if a site
	 * already existed in database. If existed will update the old one else
	 * creates a new row
	 * */
	public void addSite(WebSite site) {
		TABLE_RSS = getTableName();
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, site.getTitle()); // site title
		values.put(KEY_LINK, site.getLink()); // site url
		values.put(KEY_IMG_LINK, site.getImageLink()); // rss img
		values.put(KEY_DESCRIPTION, site.getDescription()); // site description

		values.put(KEY_PUBLIC_DATE, site.getPubDate()); // public date
		// Check if row already existed in database
		if (!isSiteExists(db, site.getLink(), site.getTitle(), site.getImageLink())) {
			// site not existed, create a new row
			db.insert(TABLE_RSS, null, values);
			db.close();
		} else {
			// site already existed update the row
			updateSite(site);
			db.close();
		}
	}

	/**
	 * Reading all rows from database
	 * */
	public List<WebSite> getAllSitesByID() {
		TABLE_RSS = getTableName();
		List<WebSite> siteList = new ArrayList<WebSite>();
		//Log.d("DEBUG", "SQL " + TABLE_RSS);
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_RSS + " ORDER BY "
				+ KEY_ID + " DESC";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				WebSite site = new WebSite();
				site.setId(Integer.parseInt(cursor.getString(0)));
				site.setTitle(cursor.getString(1));
				site.setLink(cursor.getString(2));
				site.setImageLink(cursor.getString(3));
				site.setDescription(cursor.getString(4));
				site.setPubDate(cursor.getString(5));
				// Adding contact to list
				//Log.d("DEBUG", "SQL " + String.valueOf(site.getId()));
				siteList.add(site);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		// return contact list

		return siteList;
	}

	// /**
	// * Reading all rows from database
	// * */
	// public List<WebSite> getAllSitesLimited(int limitedNumber) {
	// int count = 0;
	// List<WebSite> siteList = new ArrayList<WebSite>();
	// // Select All Query
	// String selectQuery = "SELECT * FROM " + TABLE_RSS
	// + " ORDER BY " + KEY_ID + " DESC";
	//
	// SQLiteDatabase db = this.getReadableDatabase();
	// Cursor cursor = db.rawQuery(selectQuery, null);
	//
	// // looping through all rows and adding to list
	// if (cursor.moveToFirst()) {
	// do {
	// count++;
	// WebSite site = new WebSite();
	// site.setId(Integer.parseInt(cursor.getString(0)));
	// site.setTitle(cursor.getString(1));
	// site.setLink(cursor.getString(2));
	// site.setImageLink(cursor.getString(3));
	// site.setDescription(cursor.getString(4));
	// site.setPubDate(cursor.getString(5));
	// // Adding contact to list
	// siteList.add(site);
	// } while (cursor.moveToNext() && count < limitedNumber);
	// }
	// cursor.close();
	// db.close();
	//
	// // return contact list
	//
	// return siteList;
	// }

	/**
	 * Updating a single row row will be identified by rss link
	 * */
	public int updateSite(WebSite site) {
		TABLE_RSS = getTableName();
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, site.getTitle());
		values.put(KEY_LINK, site.getLink());
		// values.put(KEY_RSS_LINK, site.getRSSLink());
		values.put(KEY_DESCRIPTION, site.getDescription());
		values.put(KEY_IMG_LINK, site.getImageLink());
		values.put(KEY_PUBLIC_DATE, site.getPubDate());

		// updating row return
		int update = db.update(TABLE_RSS, values, KEY_IMG_LINK + " = ?",
				new String[] { String.valueOf(site.getImageLink()) });
		db.close();
		return update;

	}

	/**
	 * Reading a row (website) row is identified by row id
	 * */
	public WebSite getSiteById(int id) {
		TABLE_RSS = getTableName();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_RSS, new String[] { KEY_ID, KEY_TITLE,
				KEY_LINK, KEY_IMG_LINK, KEY_DESCRIPTION, KEY_PUBLIC_DATE },
				KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null,
				null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}

		WebSite site = new WebSite(cursor.getString(1), cursor.getString(2),
				cursor.getString(3), cursor.getString(4), cursor.getString(5));

		site.setId(Integer.parseInt(cursor.getString(0)));
		site.setTitle(cursor.getString(1));
		site.setLink(cursor.getString(2));
		site.setImageLink(cursor.getString(3));
		site.setDescription(cursor.getString(4));
		site.setPubDate(cursor.getString(5));
		cursor.close();
		db.close();
		return site;
	}

	/**
	 * Reading a row (website) row is identified by link
	 * */
	public WebSite getSiteByLink(String link) {
		TABLE_RSS = getTableName();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_RSS, new String[] { KEY_ID, KEY_TITLE,
				KEY_LINK, KEY_IMG_LINK, KEY_DESCRIPTION, KEY_PUBLIC_DATE },
				KEY_LINK + "=?", new String[] { link }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}

		WebSite site = new WebSite(cursor.getString(cursor.getColumnIndex(KEY_TITLE)),
									cursor.getString(cursor.getColumnIndex(KEY_LINK)),
									cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)),
									cursor.getString(cursor.getColumnIndex(KEY_PUBLIC_DATE)),
									cursor.getString(cursor.getColumnIndex(KEY_IMG_LINK)));

		site.setId(cursor.getInt((cursor.getColumnIndex(KEY_ID))));

		cursor.close();
		db.close();
		return site;
	}

	/**
	 * Deleting single row
	 * */
	public void deleteSite(WebSite site) {
		TABLE_RSS = getTableName();
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_RSS, KEY_ID + " = ?",
				new String[] { String.valueOf(site.getId()) });
		db.close();
	}
	
	/**
	 * Deleting many rows
	 * */
	public void cleanDatabase() {
		TABLE_RSS = getTableName();
		SQLiteDatabase db = this.getWritableDatabase();
		int numberOfDelete = getDatabaseSize() - MAX_SIZE_DATABASE;
		Log.d("DEBUG" , "COUNT " + String.valueOf(numberOfDelete));
		int count = 0;
		
		if (numberOfDelete <= 0) return;
		else{
			String selectQuery = "SELECT * FROM " + TABLE_RSS + " ORDER BY "
					+ KEY_ID + " ASC";
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					db.delete(TABLE_RSS, KEY_ID + " = ?",
							new String[] { cursor.getString(0)});
					count++;
					if (count > numberOfDelete) break;
					Log.d("DEBUG" , "COUNT " + String.valueOf(count));
					//WebSite site = new WebSite();
					//site.setId(Integer.parseInt(cursor.getString(0)));
//					site.setTitle(cursor.getString(1));
//					site.setLink(cursor.getString(2));
//					site.setImageLink(cursor.getString(3));
//					site.setDescription(cursor.getString(4));
//					site.setPubDate(cursor.getString(5));
					// Adding contact to list
					//Log.d("DEBUG", "SQL " + String.valueOf(site.getId()));
					//siteList.add(site);
				} while (cursor.moveToNext());
			}
		
		}
		db.close();
		
		//SELECT TOP 2 * FROM Customers;
		
		
	}

	/**
	 * 
	 */
	public int getLatestId(){
		TABLE_RSS = getTableName();
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_RSS + " ORDER BY "
				+ KEY_ID + " DESC";
		Cursor cursor = db.rawQuery(selectQuery, null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}
	/**
	 * Checking whether a site is already existed check is done by matching rss
	 * link
	 * */
	public boolean isSiteExists(SQLiteDatabase db, String link, String title, String img_link) {
		TABLE_RSS = getTableName();
		boolean exists = false;
		Cursor cursor;
		//has the same link
		cursor = db.rawQuery("SELECT * FROM " + TABLE_RSS
				+ " WHERE link = '" + link + "'", new String[] {});
		exists = (cursor.getCount() > 0);
		if (exists){
			return true;
		}
		
//		//has the same title
//		cursor = db.rawQuery("SELECT * FROM " + TABLE_RSS
//				+ " WHERE title = '" + title + "'", new String[] {});
//		exists = (cursor.getCount() > 0);
//		if (exists){
//			return true;
//		}
		
		//has the same image
		cursor = db.rawQuery("SELECT * FROM " + TABLE_RSS
				+ " WHERE img_link = '" + img_link + "'", new String[] {});
		exists = (cursor.getCount() > 0);
		if (exists){
			return true;
		}		
		return false;
	}

	/**
	 * Get the size of the database
	 */
	public int getDatabaseSize() {
		TABLE_RSS = getTableName();
		String countQuery = "SELECT  * FROM " + TABLE_RSS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int cnt = cursor.getCount();
		cursor.close();
		return cnt;
	}

	
	/**
	 * Set table name
	 */
	private String getTableName() {
		
		String table_name;
		//Log.d("DEBUG", "SQL CATE " + MainActivity.nameCategory);
		switch (MainActivity.nameCategory) {

		case Homepage:
			table_name = table_name_array[0];
			break;
		case Business:
			table_name = table_name_array[1];
			break;
		case Car:
			table_name = table_name_array[2];
			break;
		case Chat:
			table_name = table_name_array[3];
			break;
		case Digital:
			table_name = table_name_array[4];
			break;
		case Entertainment:
			table_name = table_name_array[5];
			break;
		case Sports:
			table_name = table_name_array[6];
			break;
		case Funny:
			table_name = table_name_array[7];
			break;
		case Laws:
			table_name = table_name_array[8];
			break;
		case Life:
			table_name = table_name_array[9];
			break;
		case News:
			table_name = table_name_array[10];
			break;
		case Science:
			table_name = table_name_array[11];
			break;
		case Social:
			table_name = table_name_array[12];
			break;
		case Travelling:
			table_name = table_name_array[13];
			break;
		case World:
			table_name = table_name_array[14];
			break;
		default:
			table_name = table_name_array[0];

		}
		return table_name;
	}

}
