package com.example.julia.callmenavigation;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by julia on 5/17/2018.
 */

public class MapViewHolder extends RecyclerView.ViewHolder {
    TextView longitudeTextView;
    TextView latitudeTextView;
    TextView timeTextView;
    TextView dateTextView;
    TextView approximateLocation;
    CheckBox saveCheckBox;
    LinearLayout linearLayout;
    //private MapViewList mMapViewListItemView;

    public MapViewHolder(View itemView, MapViewList mapViewList) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.mLayout);
        longitudeTextView = itemView.findViewById(R.id.mLongitude);
        latitudeTextView = itemView.findViewById(R.id.mLatitude);
        timeTextView = itemView.findViewById(R.id.mTime);
        dateTextView = itemView.findViewById(R.id.mDate);
        saveCheckBox = itemView.findViewById(R.id.mSave);
        approximateLocation = itemView.findViewById(R.id.mApproximate);

    }
    /*
    public void setMapViewList(MapViewList mapViewList){
        mMapViewListItemView = mapViewList;
        mMapViewListItemView.mapViewOnCreate(null);
    }

    public void mapViewListItemViewOnCreate(Bundle savedInstanceState) {
        if (mMapViewListItemView != null) {
            mMapViewListItemView.mapViewOnCreate(savedInstanceState);
        }
    }

    public void mapViewListItemViewOnResume() {
        if (mMapViewListItemView != null) {
            mMapViewListItemView.onResume();
        }
    }

    public void mapViewListItemViewOnPause() {
        if (mMapViewListItemView != null) {
            mMapViewListItemView.mapOnPause();
        }
    }

    public void mapViewListItemViewOnDestroy() {
        if (mMapViewListItemView != null) {
            mMapViewListItemView.mapOnDestroy();
        }
    }

    public void mapViewListItemViewOnLowMemory() {
        if (mMapViewListItemView != null) {
            mMapViewListItemView.mapViewOnLowMemory();
        }
    }

    public void mapViewListItemViewOnSaveInstanceState(Bundle outState) {
        if (mMapViewListItemView != null) {
            mMapViewListItemView.mapViewOnSaveInstanceState(outState);
        }
    }
*/

}