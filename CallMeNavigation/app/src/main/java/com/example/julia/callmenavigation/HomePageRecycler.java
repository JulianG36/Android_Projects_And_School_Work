package com.example.julia.callmenavigation;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageRecycler extends Fragment{
    public static GoogleMap map;
    private final String TAG = "HomePageRecycler";
    private MapView mapView;
    private Database db;
    private HomePageAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<LocationTimeObject> locationTimeObjects;

    public HomePageRecycler() {
        // Required empty public constructor
    }
    public void onViewCreated(View view, Bundle savedInstance){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = getContext();
        db = new Database(context);
        View view = inflater.inflate(R.layout.fragment_home_page_recycler, container,false);

        //Implement way to click on each row and open a map
        //this.mapView = (MapView) view.findViewById(R.id.tempMap);
        //mapView.getMapAsync(this);
        //mapView.onCreate(null);
        adapter = new HomePageAdapter(context,locationTimeObjects, getActivity());
        //googleServicesChecker();
        recyclerView = (RecyclerView) view.findViewById(R.id.mRecycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        // Inflate the layout for this fragment
        return view;
    }
    private void googleServicesChecker(){
        try{
            MapsInitializer.initialize(getActivity());
        }catch (Exception e){
            //HomePageAdapter.mapsSupported = false;
        }
    }


}
