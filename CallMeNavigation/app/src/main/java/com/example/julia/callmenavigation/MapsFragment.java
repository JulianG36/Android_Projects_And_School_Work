package com.example.julia.callmenavigation;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationClickListener,GoogleMap.OnMyLocationButtonClickListener {
GoogleMap map;
private LocationTimeObject locationTimeObject;
private static final String TAG = "MapsFragment";
    public MapsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps,container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.map);
        locationTimeObject = locationTimeObject.Instance();
        mapFragment.getMapAsync(this);
        return view;
    }
    //prepares map to display it and initializes map
@Override
    public void onMapReady(GoogleMap googleMap){
    map = googleMap;
    map.getUiSettings().setZoomControlsEnabled(true);
    //Checks for permissions in order to obtain the current location of the user
    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }
    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
    }
    LatLng pp = new LatLng(20, 030);
    MarkerOptions option = new MarkerOptions();
    //Sets all the markers in the LocationTimeObject and adds a marker
    for(int i = 0; i <= locationTimeObject.Instance().getLength()-1; i++) {
        double lat = ((double) locationTimeObject.Instance().getSingleLatitude(i));
        double lon = ((double) locationTimeObject.Instance().getSingleLongitude(i));
        pp = new LatLng(lat, lon);
        option.position(pp).title("Home");
        map.addMarker(option);
        Log.i(TAG, "Added Marker");
    }
    map.moveCamera(CameraUpdateFactory.newLatLng(pp));
}
//When my location button is clicked the following methods are called to obtain and zoom into a users location
    public void onMyLocationClick(Location location){
    }
    public boolean onMyLocationButtonClick(){
        return false;
    }
}
