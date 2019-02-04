package com.example.julia.callmenavigation;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.AttributedCharacterIterator;

/**
 * Created by julia on 5/17/2018.
 */

public class MapViewList  extends AppCompatActivity implements OnMapReadyCallback{
    protected MapView mapView;
    private Context context;
    private LayoutInflater inflater;
    private  final String TAG = "MapViewList";
    private LocationTimeObject locationTimeObject;

    public MapViewList(Context context){
        this(context, null);
    }
    public MapViewList(Context context, AttributeSet attrib){
        this.context = context;
        inflater = LayoutInflater.from(context);
        //setupView();
    }
    private void setupView(){
        View view = inflater.inflate(R.layout.recycler_row_layout,null);
        //mapView = view.findViewById(R.id.mRecyclerMap);
    }
    public  void mapViewOnCreate(Bundle savedInstanceState){
        if (mapView != null){
            mapView.onCreate(savedInstanceState);
            locationTimeObject =  new LocationTimeObject();
            if (locationTimeObject != null){
                mapView.getMapAsync(this);
                mapView.onCreate(null);
            }
        }
    }
    @Override
    public void onResume(){
        if (mapView != null){
            super.onResume();
            mapView.onResume();
        }
    }
    public void mapOnPause(){
        if (mapView != null){
            mapView.onPause();
        }
    }
    public void mapOnDestroy(){
        if (mapView != null){
            mapView.onDestroy();
        }
    }
    public void mapViewOnLowMemory(){
        if (mapView != null){
            mapView.onLowMemory();
        }
    }
    public  void mapViewOnSaveInstanceState(Bundle outState){
        if (mapView != null){
            mapView.onSaveInstanceState(outState);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap map = googleMap;
        LatLng pp = new LatLng(20, 030);
        MarkerOptions option = new MarkerOptions();
        double lat = ((double) locationTimeObject.Instance().getSingleLatitude(1));
        double lon = ((double) locationTimeObject.Instance().getSingleLongitude(1));
        pp = new LatLng(lat, lon);
        option.position(pp).title("Home");
        map.addMarker(option);
        Log.i(TAG, "Added Marker");
        map.moveCamera(CameraUpdateFactory.newLatLng(pp));
    }
}
