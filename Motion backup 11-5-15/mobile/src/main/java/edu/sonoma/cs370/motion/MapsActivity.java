package edu.sonoma.cs370.motion;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.app.Activity;
import android.os.Bundle;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.util.Log;
import android.view.View;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageButton startButton;
    private Button pauseButton;

    boolean isPressed = true;

    private TextView timerValue;

    private long startTime = 0L;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        if (mMap != null){
//            mMap.setMyLocationEnabled(true);
//        }

        timerValue = (TextView) findViewById(R.id.timerValue);

        startButton = (ImageButton) findViewById(R.id.startButton);

        startButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(isPressed) {
                    //Log.v("BV within true", Boolean.toString(isPressed));
                    startButton.setImageResource(R.drawable.stopbutton);
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
                    //Log.d("tag","Start");
                }
                else{
                    //Log.v("BV within false", Boolean.toString(isPressed));
                    startButton.setImageResource(R.drawable.startbutton);
                    timeSwapBuff += timeInMilliseconds;
                    customHandler.removeCallbacks(updateTimerThread);
                    //Log.d("tag", "Stop");

                }
                isPressed  = !isPressed;
                //Log.v("Boolean Value", Boolean.toString(isPressed));
                //Log.d("tag","Switch");

            }

        });

//        pauseButton = (Button) findViewById(R.id.pauseButton);
//
//        pauseButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View view) {
//                timeSwapBuff += timeInMilliseconds;
//                customHandler.removeCallbacks(updateTimerThread);
//            }
//        });

    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime/1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            if(mins < 10) {
                timerValue.setText("0" + mins + ":" + String.format("%02d", secs) + ":"
                        + String.format("%03d", milliseconds));
            }
            else{
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

        mMap = googleMap;

        // Add a marker and move the camera
        LatLng marker = new LatLng(38.348175, -122.710721);
        mMap.addMarker(new MarkerOptions()
                .position(marker)
                .title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 14));

    }

}
