package backgroundnotification;



import ngo.vnexpress.reader.MainActivity;
import ngo.vnexpress.reader.R;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class NotificationActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    displayNotificationOne();
	    // TODO Auto-generated method stub
	}

	protected void displayNotificationOne() {

	      // Invoking the default notification service
	      NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);	
	 
	      mBuilder.setContentTitle("New Message with explicit intent");
	      mBuilder.setContentText("New message from javacodegeeks received");
	      mBuilder.setTicker("Explicit: New Message Received!");
	      mBuilder.setSmallIcon(R.drawable.ic_launcher);

	      // Increase notification number every time a new notification arrives 
	    
	      
	      // Creates an explicit intent for an Activity in your app 
	      Intent resultIntent = new Intent(this, MainActivity.class);
	      int notificationIdOne = 0;
		resultIntent.putExtra("notificationId", notificationIdOne);

	      //This ensures that navigating backward from the Activity leads out of the app to Home page
	      TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	      // Adds the back stack for the Intent
	      

	      // Adds the Intent that starts the Activity to the top of the stack
	      stackBuilder.addNextIntent(resultIntent);
	      PendingIntent resultPendingIntent =
	         stackBuilder.getPendingIntent(
	            0,
	            PendingIntent.FLAG_ONE_SHOT //can only be used once
	         );
	      // start the activity when the user clicks the notification text
	      mBuilder.setContentIntent(resultPendingIntent);

	      NotificationManager myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

	      // pass the Notification object to the system 
	      myNotificationManager.notify(notificationIdOne, mBuilder.build());
	   }

}
