package com.vayapp.flashlight;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Camera cameraObj;
	boolean off = true;
	Button switchButton, twinkleButton;
	Context ctx;
	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		switchButton = (Button) findViewById(R.id.switchButton);
		
		switchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (off) switchOn(); else switchOff();
			}
		});
		

	}

	public void switchOn() {
		buildNotification(R.drawable.ic_launcher,"Flashlight","Switched on!");	
		
		off = false;
		switchButton.setText(R.string.off);
		cameraObj = Camera.open();
		Camera.Parameters cameraParams = cameraObj.getParameters();
		cameraParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
		cameraObj.setParameters(cameraParams);
		cameraObj.startPreview();
	}

	public void switchOff() {
		buildNotification(R.drawable.ic_launcher,"Flashlight","Switched off!");	
		
		off = true;
		switchButton.setText(R.string.on);	
		cameraObj.startPreview();
		cameraObj.release();
	}
	

	public void buildNotification(int icon, String contentTitle, String contentText){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(icon)
		        .setContentTitle(contentTitle)
		        .setContentText(contentText);
		
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainActivity.class);
		
		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(999, mBuilder.build());
	}

}
