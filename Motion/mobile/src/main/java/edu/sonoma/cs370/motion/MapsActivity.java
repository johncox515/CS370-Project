package edu.sonoma.cs370.motion;


import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
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
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.util.Log;
import android.view.View;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ImageButton startButton;
    private Button pauseButton;
    private Polyline polyline;
    private PolylineOptions rectOptions = new PolylineOptions();

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

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
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
        //Location myLocation = googleMap.getMyLocation();
        if (mMap != null) {
            Log.d("tag", "map != null");

            final ArrayList<LatLng> totalRoutePoints = new ArrayList<LatLng>();

            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location arg0) {
                    Log.d("tag", "onMyLocationChange");
                    LatLng marker = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(marker).visible(false).title("You are here!"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 16));
                    totalRoutePoints.add(marker); //this adds the marker to totalRoutePoints
                    Polyline route = mMap.addPolyline(new PolylineOptions() //this will add a line to the map
                                    .color(Color.BLUE)
                    );
                    route.setPoints(totalRoutePoints); //this looks at totalRoutePoints and put all points onto the map
                    for (int i = 0 ; i < totalRoutePoints.size() ; i++) {
                        Log.d("value is", totalRoutePoints.get(i).toString());
                        Log.d("list size", String.valueOf(totalRoutePoints.size()));
                    }
                    //polyline = mMap.addPolyline(rectOptions);
                    //updatePolyLine(marker);
                    //LatLng oldMarker = marker;
//                    Polyline polygon = mMap.addPolyline(new PolylineOptions()
//                                    //.add(new LatLng(2.169919, 41.387989), new LatLng(2.169919, 41.39))
//                                    .add(marker, new LatLng(0, 5), new LatLng(3, 5))
//                                    .color(Color.BLUE)
//                    );


                }
            });
        }


    }

    private void updatePolyLine(LatLng latlng) {
        List<LatLng> points = polyline.getPoints();
        points.add(latlng);
        polyline.setPoints(points);
        for (int i = 0 ; i < points.size() ; i++) {
            Log.d("value is", points.get(i).toString());
            Log.d("list size", String.valueOf(points.size()));
        }
    }


}









