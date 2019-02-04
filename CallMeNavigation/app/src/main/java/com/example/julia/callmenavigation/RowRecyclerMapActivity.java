package com.example.julia.callmenavigation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RowRecyclerMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener{
private MapView mapView;
private GoogleMap map;
private int position;
private int recyclerVersion;
private LocationTimeObject locationTimeObject;
private final String TAG = "RowRecyclerMapActivity";
private double latitude;
private double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_recycler_map);
        //Obtains position from bundle when the intent is made
        position = getIntent().getExtras().getInt("position");
        recyclerVersion = getIntent().getExtras().getInt("recyclerVersion");
        mapView = (MapView) findViewById(R.id.tempMap);
        mapView.getMapAsync(this);
        mapView.onCreate(null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Location");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        //Asks for permission for the my location button
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RowRecyclerMapActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            map.setOnMyLocationButtonClickListener(this);
            map.setOnMyLocationClickListener(this);
        }

        map.getUiSettings().setMyLocationButtonEnabled(true);
        LatLng pp = new LatLng(30,40);
        MarkerOptions option = new MarkerOptions();
        if (recyclerVersion == 1) {
            double lat = ((double) locationTimeObject.Instance().getSingleLatitude(position));
            double lon = ((double) locationTimeObject.Instance().getSingleLongitude(position));
            pp = new LatLng(lat, lon);
        }
        if (recyclerVersion == 2){
            latitude = getIntent().getExtras().getDouble("latitude");
            longitude = getIntent().getExtras().getDouble("longitude");
            pp = new LatLng(latitude, longitude);
        }

        option.position(pp).title("Home");
        map.addMarker(option);
        Log.i(TAG, "Added Marker");
        map.moveCamera(CameraUpdateFactory.newLatLng(pp));

    }
    @Override
    public void onResume(){
        mapView.onResume();
        super.onResume();
    }
    @Override
    public void onMyLocationClick(Location location){

    }
    @Override
    public boolean onMyLocationButtonClick(){

        return false;
    }
}
