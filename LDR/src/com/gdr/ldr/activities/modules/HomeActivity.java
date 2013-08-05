package com.gdr.ldr.activities.modules;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gdr.ldr.R;
import com.gdr.ldr.activities.modules.Claim.ListClaimActivity;
import com.gdr.ldr.activities.modules.webview.WebViewActivity;
import com.gdr.ldr.service.GPSTracker;
import com.gdr.ldr.service.PositionService;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.String.*;

public class HomeActivity extends Activity {

    private Button btnShowLocation;
    private Button btnCancelLocation;
    private Button btnShowListClaim;
    private Button btnShowOpenBrowser;
    private PendingIntent pendingIntent;
    private Timer mTimer = null;

    // GPSTracker class
    GPSTracker gps = new GPSTracker(HomeActivity.this);


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
                //startService(new Intent(HomeActivity.this,PositionService.class));
                Timer t = new Timer();
                //Set the schedule function and rate
                t.scheduleAtFixedRate(new TimerTask() {

                    @Override
                    public void run() {
                        Log.i("gps.getLatitude(): ", new String(String.valueOf(gps.getLatitude())));
                        Log.i("gps.getLongitude():",  new String(String.valueOf(gps.getLongitude())));
                    }

                },
                //Set how long before to start calling the TimerTask (in milliseconds)
                        0,
                    //Set the amount of time between each execution (in milliseconds)
                        10000);


            }

            /*@Override
            public void onClick(View arg0) {
                // create class object
                gps = new GPSTracker(HomeActivity.this);
                // check if GPS enabled
                //if(gps.canGetLocation()){

                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                    //Construimos el objeto cliente en formato JSON
                    try {
                        HttpClient httpClient = new DefaultHttpClient();

                        HttpPost post = new
                                HttpPost("http://10.0.2.2:2731/Api/Clientes/Cliente");

                        post.setHeader("content-type", "application/json");


                        JSONObject dato = new JSONObject();

                        dato.put("latitude", latitude);
                        dato.put("longitude", longitude);

                    } catch(Exception ex)
                    {
                        Log.e("ServicioRest","Error!", ex);
                    }

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }

            }*/

        });


        btnCancelLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //stopService(new Intent(HomeActivity.this,PositionService.class));
            }
        });





        // show location button click event
       /*
       btnShowLocation.setOnClickListener(new View.OnClickListener() {



        });
*/


    }

    private void openBrowser(){
        Intent i =  new Intent(this, WebViewActivity.class);
        startActivity(i);
    }

    private void changeActivityList(){
        Intent i =  new Intent(this, ListClaimActivity.class);
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
