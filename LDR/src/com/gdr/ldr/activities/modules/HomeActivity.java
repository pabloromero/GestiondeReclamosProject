package com.gdr.ldr.activities.modules;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gdr.ldr.R;
import com.gdr.ldr.activities.modules.Claim.ListClaimActivity;
import com.gdr.ldr.activities.modules.webview.WebViewActivity;
import com.gdr.ldr.service.GPSTracker;

public class HomeActivity extends Activity {

	private Button btnShowLocation;
	private Button btnCancelLocation;
	private Button btnShowListClaim;
	private Button btnShowOpenBrowser;
	private PendingIntent pendingIntent;
	private Timer mTimer = null;

	// private Intent serviceIntent;
	// boolean mBounded;
	// GPSTracker mServer;
	private GPSTracker gps;

	public static final long NOTIFY_INTERVAL = 10 * 2000; // 20 seconds

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		btnShowListClaim = (Button) findViewById(R.id.btnShowListClaim);
		btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
		btnCancelLocation = (Button) findViewById(R.id.btnCancelLocation);
		btnShowOpenBrowser = (Button) findViewById(R.id.btnShowOpenBrowser);

		btnShowOpenBrowser.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				openBrowser();
			}
		});

		btnShowListClaim.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				changeActivityList();
			}
		});

		btnShowLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// startService(new
				// Intent(HomeActivity.this,PositionService.class));
				if (mTimer == null) {
					mTimer = new Timer();
				}

				Log.i("LDR -> INFO", "Información LAT/LONG INCICIANDO");
				gps = new GPSTracker(HomeActivity.this);
				mTimer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						try {
							Log.i("LDR -> INFO",
									"Información LAT/LONG INFORMANDO");

							runOnUiThread(new Runnable() {
								@Override
								public void run() {

									EditText editTextLat = (EditText) findViewById(R.id.text_lat);
									editTextLat.setText(new String(String
											.valueOf(gps.getLatitude())));

									EditText editTextLong = (EditText) findViewById(R.id.text_long);
									editTextLong.setText(new String(String
											.valueOf(gps.getLongitude())));

									// Toast.makeText(getApplicationContext(),
									// "Your Location is - \nLat: " + latitude +
									// "\nLong: " + longitude,
									// Toast.LENGTH_LONG).show();
								}
							});

							// HttpHandler handler = new HttpHandler();
							// String txt =
							// handler.post("http://192.168.150.119:8080/GDR-API_Restful/adr/service/promero/"+gps.getLatitude()+"/"+gps.getLongitude());

							Log.i("gps.getLatitude(): ",
									new String(
											String.valueOf(gps.getLatitude())));
							Log.i("gps.getLongitude():",
									new String(String.valueOf(gps
											.getLongitude())));

						} catch (Exception e) {
							// e.printStackTrace();
							Log.e("ERRORRRRRR", e.getMessage());
						}
					}
				},
				// Set how long before to start calling the TimerTask (in
				// milliseconds)
						0,
						// Set the amount of time between each execution (in
						// milliseconds)
						10000);

			}

			/*
			 * @Override public void onClick(View arg0) { gps = new
			 * GPSTracker(HomeActivity.this);
			 * 
			 * // check if GPS enabled if(gps.canGetLocation()){
			 * 
			 * double latitude = gps.getLatitude(); double longitude =
			 * gps.getLongitude();
			 * 
			 * // \n is for new line Toast.makeText(getApplicationContext(),
			 * "Your Location is - \nLat: " + latitude + "\nLong: " + longitude,
			 * Toast.LENGTH_LONG).show(); }else{ // can't get location // GPS or
			 * Network is not enabled // Ask user to enable GPS/network in
			 * settings gps.showSettingsAlert(); }
			 * 
			 * }
			 */
		});
		// geo fix 2.2 1.1

		btnCancelLocation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mTimer != null) {
					mTimer.cancel();

					EditText editTextLat = (EditText) findViewById(R.id.text_lat);
					editTextLat.setText("", TextView.BufferType.EDITABLE);

					EditText editTextLong = (EditText) findViewById(R.id.text_long);
					editTextLong.setText("", TextView.BufferType.EDITABLE);

					mTimer = null;

					Log.i("LDR -> INFO", "Información LAT/LONG CANCELADO");

				}

			}
		});

		// show location button click event
		/*
		 * btnShowLocation.setOnClickListener(new View.OnClickListener() {
		 * 
		 * 
		 * 
		 * });
		 */

	}

	/*
	 * @Override protected void onStart() { super.onStart(); Intent mIntent =
	 * new Intent(this, GPSTracker.class); bindService(mIntent, mConnection,
	 * BIND_AUTO_CREATE); };
	 * 
	 * ServiceConnection mConnection = new ServiceConnection() {
	 * 
	 * public void onServiceDisconnected(ComponentName name) {
	 * Toast.makeText(HomeActivity.this, "Service is disconnected",
	 * 1000).show(); mBounded = false; mServer = null; }
	 * 
	 * public void onServiceConnected(ComponentName name, IBinder service) {
	 * Toast.makeText(HomeActivity.this, "Service is connected", 1000).show();
	 * mBounded = true; GPSTracker.LocalBinder mLocalBinder =
	 * (GPSTracker.LocalBinder)service; mServer =
	 * mLocalBinder.getServerInstance(); } };
	 * 
	 * @Override protected void onStop() { super.onStop(); if(mBounded) {
	 * unbindService(mConnection); mBounded = false; } };
	 */

	private void openBrowser() {
		Intent i = new Intent(this, WebViewActivity.class);
		startActivity(i);
	}

	private void changeActivityList() {
		Intent i = new Intent(this, ListClaimActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_exit_app_home:
			System.exit(0);
			finish();
			return true;
		default:
			return false;
		}

	}

}
