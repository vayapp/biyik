package com.vayapp.flashlight;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	private Camera cameraObj;
	Camera.Parameters cameraParams;

	boolean off = true;
	ImageButton imageButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnButton();
	}

	public void addListenerOnButton() {

		imageButton = (ImageButton) findViewById(R.id.imageButtonon);

		imageButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (off) {
					MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.lighton);
					mp.start();
					mp.seekTo(0);
					
					switchOn();
					imageButton.setImageResource(R.drawable.off);
					
				}

				else {
					MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.lightoff);
					mp.start();
					mp.seekTo(0);
					
					switchOff();
					imageButton.setImageResource(R.drawable.on);
				}
			}

		});

	}

	public void switchOn() {
		buildNotification(R.drawable.ic_launcher, "Flashlight", "Switched on!");

		
		off = false;
		cameraObj = Camera.open();
		Camera.Parameters cameraParams = cameraObj.getParameters();
		cameraParams.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
		cameraObj.setParameters(cameraParams);
		cameraObj.startPreview();

	}

	public void switchOff() {
		buildNotification(R.drawable.ic_launcher, "Flashlight", "Switched off!");

		
		off = true;
		cameraObj.startPreview();
		cameraObj.release();
	}

	public void buildNotification(int icon, String contentTitle,
			String contentText) {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(icon).setContentTitle(contentTitle)
				.setContentText(contentText);

		Intent resultIntent = new Intent(this, MainActivity.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);

		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(999, mBuilder.build());
	}
		

}
