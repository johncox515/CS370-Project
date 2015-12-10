package edu.sonoma.cs370.motion;


import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageButton startButton;
    private ImageButton finishButton;

    boolean isPressed = true;

    private TextView timerValue;
    private TextView milesValue;

    private long startTime = 0L;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int secs = 0;
    int mins = 0;
    int milliseconds = 0;

    float totalMiles;

    //MotionDbHelper mydb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        timerValue = (TextView) findViewById(R.id.timerValue);

        startButton = (ImageButton) findViewById(R.id.startButton);

        finishButton = (ImageButton) findViewById(R.id.finishButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isPressed) {
                    //Log.v("BV within true", Boolean.toString(isPressed));
                    startButton.setImageResource(R.drawable.stopbutton);
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                    //Log.d("tag","Start");
                } else {
                    //Log.v("BV within false", Boolean.toString(isPressed));
                    startButton.setImageResource(R.drawable.startbutton);
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    //Log.d("tag", "Stop");

                }
                isPressed = !isPressed;
                //Log.v("Boolean Value", Boolean.toString(isPressed));
                //Log.d("tag","Switch");

            }

        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                builder.setTitle(R.string.app_name);
                builder.setMessage("Finish run?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String FinalMins = String.format("%02d", mins);
                        //Log.d("Final Minutes: ", FinalMins);

                        String FinalSecs = String.format("%02d", secs);
                        //Log.d("Final Seconds: ", FinalSecs);

                        String FinalMilliseconds = String.format("%03d", milliseconds);
                        //Log.d("Final Milliseconds: ", FinalMilliseconds);

                        String FinalTime = String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds);

                        String FinalMiles = String.format("%.2f", totalMiles) + " Miles";
                        //Log.d("Final Miles: ", FinalMiles);

                        String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
                        Log.d("Date: ", date);


                        //CALL TO DATABASE HERE
                        MotionDbHelper mydb = new MotionDbHelper(getBaseContext());
                        mydb.createAddEntry(totalMiles,FinalTime, date);
                        //Log.d("Database Output: ", String.valueOf(mydb.viewEntries()));
                        Log.d("Calories Output: ", String.valueOf(mydb.getCalories()));
                        mydb.getTotalStats();


                        startTime = 0L;
                        timeInMilliseconds = 0L;
                        timeSwapBuff = 0L;
                        updatedTime = 0L;
                        secs = 0;
                        mins = 0;
                        milliseconds = 0;
                        startButton.setImageResource(R.drawable.startbutton);
                        customHandler.removeCallbacks(updateTimerThread);
                        timerValue.setText("00:00:000");
                        totalMiles = 0;
                        milesValue = (TextView) findViewById(R.id.milesValue);
                        milesValue.setText(String.format("%.2f", totalMiles) + " Miles");
                        //RETURN TO HOME SCREEN HERE

                        startActivity(new Intent(MapsActivity.this, MainActivity.class));
                        //isPressed = false;
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alert = builder.show();
            }
        });

    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;

            secs = (int) (updatedTime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedTime % 1000);
            if (mins < 10) {
                timerValue.setText("0" + mins + ":" + String.format("%02d", secs) + ":"
                        + String.format("%03d", milliseconds));

            } else {
                timerValue.setText("" + mins + ":" + String.format("%02d", secs) + ":"
                        + String.format("%03d", milliseconds));
            }
            customHandler.postDelayed(this, 0);
        }
    };

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("tag", "onMapReady");
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setPadding(0, 0, 0, 200);

        if (mMap == null) {
            Log.d("tag", "map == null");
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        }
        if (mMap != null) {
            Log.d("tag", "map != null");

            final ArrayList<LatLng> totalRoutePoints = new ArrayList<LatLng>();
            final float[] results = new float[1];
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location arg0) {
                    Log.d("tag", "onMyLocationChange");
                    LatLng marker = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(marker).visible(false).title("You are here!"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 16));
                    totalRoutePoints.add(marker); //this adds the marker to totalRoutePoints

                    if(totalRoutePoints.size() >= 2) {
                        mMap.addPolyline(new PolylineOptions() //this will add a line to the map
                                        .add(totalRoutePoints.get(totalRoutePoints.size() - 1),
                                                totalRoutePoints.get(totalRoutePoints.size() - 2))
                                        .color(Color.BLUE)
                        );
                        Location.distanceBetween(totalRoutePoints.get(totalRoutePoints.size() - 1).latitude,
                                totalRoutePoints.get(totalRoutePoints.size() - 1).longitude,
                                totalRoutePoints.get(totalRoutePoints.size() - 2).latitude,
                                totalRoutePoints.get(totalRoutePoints.size() - 2).longitude, results);
                        for (int i = 0; i < results.length; i++) {
                            totalMiles(results[i]);
                            Log.d("results[i]:", String.valueOf(results[i]));
                            System.out.println("After return to loop: " + totalMiles);
                        }
                    }
                    //route.setPoints(totalRoutePoints); //this looks at totalRoutePoints and put all points onto the map
                    //debug to print out points in the list
                    for (int i = 0 ; i < totalRoutePoints.size() ; i++) {
                        Log.d("value is", totalRoutePoints.get(i).toString());
                        Log.d("list size", String.valueOf(totalRoutePoints.size()));
                    }


                }
            });
        }


    }

    private double totalMiles(double distance){
        Log.d("tag", "inside totalMiles");
        System.out.println("Before Conversion: " + distance);
        distance *= 0.000621371; //convert to miles
        distance = Math.round(distance*100.0)/100.0;
        System.out.println("After Conversion: " + distance);
        System.out.println("Before Add: " + totalMiles);
        totalMiles += distance;
        totalMiles = (float) (Math.round(totalMiles*100.0)/100.0);
        System.out.println("After Add: " + totalMiles);
        milesValue = (TextView) findViewById(R.id.milesValue);
        milesValue.setText(String.format("%.2f", totalMiles) + " Miles");
        return totalMiles;
    };

}









