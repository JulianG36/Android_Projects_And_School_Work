package com.example.julia.callmenavigation;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by julia on 4/5/2018.
 */

public class HomePageAdapter extends RecyclerView.Adapter<MapViewHolder> implements OnMapReadyCallback {
    Database db;
    private MapView mapView;
    private LocationTimeObject locationTimeObject;
    private final LayoutInflater inflater;
    private Context context;
    private final String TAG = "HomePageAdapter";
    private List<Address> addresses;
    public HomePageAdapter(Context context, ArrayList<LocationTimeObject> locationTimeObjects, Activity activity){
        inflater = LayoutInflater.from(context);
        db = new Database(context);
        this.context = context;
        this.locationTimeObject = locationTimeObject.Instance();
    }


    @Override
    public MapViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_row_layout, parent, false);
        MapViewList mapViewList = new MapViewList(context);
            mapViewList.mapViewOnCreate(null);
        MapViewHolder holder = new MapViewHolder(view, mapViewList);
        //holder.mapViewListItemViewOnResume();
        return holder;
    }


    @Override
    public void onBindViewHolder(final MapViewHolder holder, final int position) {
        MapViewList mapViewList = new MapViewList(context);
        mapViewList.mapViewOnCreate(null);
        //holder.setMapViewList(mapViewList);
        holder.saveCheckBox.setOnCheckedChangeListener(null);
        holder.saveCheckBox.setChecked(locationTimeObject.Instance().getSingleChecked(position));
        this.locationTimeObject = locationTimeObject.Instance();
        DecimalFormat mDecimalFormat = new DecimalFormat("#.########");
        String latText = mDecimalFormat.format(locationTimeObject.getSingleLatitude(position));
        String lonText = mDecimalFormat.format(locationTimeObject.getSingleLongitude(position));
        holder.timeTextView.setText(locationTimeObject.getSingleTime(position));
        holder.latitudeTextView.setText(latText);
        holder.longitudeTextView.setText(lonText);
        holder.dateTextView.setText(locationTimeObject.getSingleDate(position));
        holder.approximateLocation.setText(getGeoLocation(locationTimeObject.getSingleLongitude(position),locationTimeObject.getSingleLatitude(position)));
        if (locationTimeObject.getSingleChecked(position)){
            holder.saveCheckBox.setClickable(false);
            holder.saveCheckBox.setChecked(true);
        }
        holder.saveCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.saveCheckBox.isChecked()==true){
                    locationTimeObject.Instance().setSingleChecked(position,true);
                    double tempLong = locationTimeObject.getSingleLongitude(position);
                    double tempLat = locationTimeObject.getSingleLatitude(position);
                    String tempTime = locationTimeObject.getSingleTime(position);
                    String tempDate = locationTimeObject.getSingleDate(position);
                    Integer IDChecked = db.insertData(tempLat,tempLong,tempTime,tempDate);
                    locationTimeObject.Instance().setSingleID(position, IDChecked);
                    holder.saveCheckBox.setClickable(false);
                }
                //Prevents user from clicking the Save Location checkBox more than once
                else{
                    holder.saveCheckBox.setClickable(true);

                }
            }
        });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;
                intent = new Intent(context, RowRecyclerMapActivity.class);
                Bundle index = new Bundle();
                index.putInt("position", position);
                index.putInt("recyclerVersion", 1);
                intent.putExtras(index);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationTimeObject.getLength();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView longitudeTextView;
        TextView latitudeTextView;
        TextView timeTextView;
        TextView dateTextView;
        CheckBox saveCheckBox;
        LinearLayout linearLayout;


        public MyViewHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.mLayout);
            longitudeTextView = itemView.findViewById(R.id.mLongitude);
            latitudeTextView = itemView.findViewById(R.id.mLatitude);
            timeTextView = itemView.findViewById(R.id.mTime);
            dateTextView = itemView.findViewById(R.id.mDate);
            saveCheckBox = itemView.findViewById(R.id.mSave);
            //mapView = itemView.findViewById(R.id.mRecyclerMap);
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
    private String getGeoLocation(double lon, double lat){
        Geocoder geocoder = new Geocoder(context);
        Address address;
        String addressText = "No Address";
        try {
            addresses = geocoder.getFromLocation(lat, lon,1);
        }catch (Exception e){
         e.printStackTrace();
        }
        if (addresses.get(0) != null && addresses.size() > 0){
            address = addresses.get(0);
            addressText = String.format("%s", address.getAddressLine(0));
        }
        return addressText;
    }


    //private void changeLocation(int index) {
    //    MarkerOptions options = new MarkerOptions();
    //    double lat = ((double) locationTimeObject.Instance().getSingleLatitude(index));
    //    double lon = ((double) locationTimeObject.Instance().getSingleLongitude(index));
    //    LatLng pp = new LatLng(lat, lon);
    //    options.position(pp).title("Location");
    //    HomePageRecycler.map.addMarker(options);
    //    HomePageRecycler.map.moveCamera(CameraUpdateFactory.newLatLng(pp));
    //}
}
