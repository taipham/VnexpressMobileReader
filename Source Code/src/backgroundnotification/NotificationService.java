package backgroundnotification;
/**
 * Background Service
 */
import java.security.PublicKey;





import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.jar.Attributes.Name;



















import ngo.vnexpress.reader.MainActivity;
import ngo.vnexpress.reader.NameCategories;
import ngo.vnexpress.reader.R;
import ngo.vnexpress.reader.BasicFunctions.BasicFunctions;
import ngo.vnexpress.reader.Fragments.DisplaySwipeViewNewsFragment;
import ngo.vnexpress.reader.RSS.LoadRSSFeedItems;
import ngo.vnexpress.reader.RSS.LoadRSSFeedItemsService;
import ngo.vnexpress.reader.RSS.RSSDatabaseHandler;
import ngo.vnexpress.reader.RSS.RSSItem;
import ngo.vnexpress.reader.RSS.RSSParser;
import ngo.vnexpress.reader.RSS.WebSite;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

public class NotificationService extends Service {

	
	private static final int TIME_PERIOD_HOUR  = 1;
	private CountDownTimer countDownTimer;
	//private static final String TAG = "MyService";
	private int notificationIdOne;
	private NotificationManager myNotificationManager;
	//private static int numberNewArticles = 0;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {

		
		Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_LONG).show();
		//Log.d(TAG, "onCreate");
			countDownTimer = new CountDownTimer(TIME_PERIOD_HOUR*600*1000,401*1000) {			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				Log.d("DEBUG", "CATE NUMBER NEW POST " + String.valueOf(MainActivity.numberNewPost));
				//Display notification only if new article > 0
				if(MainActivity.numberNewPost > 0){
					displayNotification();
				}
				//Reset the number of new article
				MainActivity.numberNewPost = 0;
				
			}
			
			@Override
			public void onFinish() {
				play();
				startTimer();
				
				// TODO Auto-generated method stub
				
			}
		};
	}

	@Override
	public int onStartCommand(Intent intent,int flags, int startId) {
		//String playlist=intent.getStringExtra(EXTRA_PLAYLIST);
	    //boolean useShuffle=intent.getBooleanExtra(EXTRA_SHUFFLE, false);

		 startTimer();
	         
	    return(START_NOT_STICKY);
	}
	public void startTimer(){
		countDownTimer.start();
	}
	@Override
	public void onDestroy() {
	//	Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
		//Log.d(TAG, "onDestroy");
	}

	
	
	private void play() {
				//MainActivity.numberNewPost = 0;
			  LoadRSSFeedItemsService loadRSS = new LoadRSSFeedItemsService();
			  loadRSS.execute();
		
	}
	
	private String getCurrentTime(){
		String time = "";
		 Calendar c = Calendar.getInstance(); 
	      int seconds = c.get(Calendar.SECOND);
	      int mins = c.get(Calendar.MINUTE);
	      int hours = c.get(Calendar.HOUR_OF_DAY);
	      int days = c.get(Calendar.DAY_OF_MONTH);
	      int months = c.get(Calendar.MONTH);
	      int years = c.get(Calendar.YEAR);
	      time = String.valueOf(hours) + ":"
	    		  + String.valueOf(mins) + ":"
	    		  + String.valueOf(seconds) + " "
	    		  + String.valueOf(days) + " / "
	    		  + String.valueOf(months) + " / "
	    		  + String.valueOf(years);
	      return time;
	}
	
	protected String getRssUrl(){
		String url_name;
		switch(MainActivity.nameCategory){
		
		case Homepage:
			url_name = MainActivity.activity.getString(R.string.rss_home_page);
			break;
		case Business:
			url_name = MainActivity.activity.getString(R.string.rss_business);
			break;
		case Car:
			url_name = MainActivity.activity.getString(R.string.rss_cars);
			break;
		case Chat:
			url_name = MainActivity.activity.getString(R.string.rss_chat);
			break;
		case Digital:
			url_name = MainActivity.activity.getString(R.string.rss_digital);
			break;
		case Entertainment:
			url_name = MainActivity.activity.getString(R.string.rss_entertainment);
			break;
		case Sports:
			url_name = MainActivity.activity.getString(R.string.rss_sports);
			break;
		case Funny:
			url_name = MainActivity.activity.getString(R.string.rss_funny);
			break;
		case Laws:
			url_name = MainActivity.activity.getString(R.string.rss_laws);
			break;
		case Life:
			url_name = MainActivity.activity.getString(R.string.rss_life);
			break;
		case News:
			url_name = MainActivity.activity.getString(R.string.rss_news);
			break;
		case Science:
			url_name = MainActivity.activity.getString(R.string.rss_science);
			break;
		case Social:
			url_name = MainActivity.activity.getString(R.string.rss_social);
			break;
		case Travelling:
			url_name = MainActivity.activity.getString(R.string.rss_travelling);
			break;
		case World:
			url_name = MainActivity.activity.getString(R.string.rss_world);
			break;
		default:
			url_name = MainActivity.activity.getString(R.string.rss_home_page);
			
		}
		return url_name;
	}
	
	void displayNotification(){
		NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);	
		 
	    
	      String time = getCurrentTime();
	     
	    
	      mBuilder.setContentTitle("Vnexpress Reader");
	      mBuilder.setContentText( String.valueOf(MainActivity.numberNewPost) + " "+ getString(R.string.articles) +  " " + time);
	      mBuilder.setTicker(String.valueOf(MainActivity.numberNewPost) + " " +  getString(R.string.articles) +  " " + time);
	      mBuilder.setSmallIcon(R.drawable.ic_launcher);
	      
	      Intent i = new Intent(this,MainActivity.class);
	    

	    //This ensures that navigating backward from the Activity leads out of the app to Home page
	      TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	      // Adds the back stack for the Intent
	     //stackBuilder.addParentStack(D.class);

	      // Adds the Intent that starts the Activity to the top of the stack
	      stackBuilder.addNextIntent(i);
	      PendingIntent resultPendingIntent =
	         stackBuilder.getPendingIntent(
	            0,
	            PendingIntent.FLAG_ONE_SHOT //can only be used once
	         );
	   // start the activity when the user clicks the notification text
	      mBuilder.setContentIntent(resultPendingIntent);
	      myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

	      // pass the Notification object to the system 
	      myNotificationManager.notify(notificationIdOne, mBuilder.build());
	     
	      Notification fakeNotification = new Notification();
	     
		      startForeground(1, fakeNotification);
		      //note.
		      stopForeground(true);
		     // Log.d("DEBUG", "CATE  = " + String.valueOf(MainActivity.numberNewPost));
	     
	}
	
	

}
