package com.example.julia.callmenavigation;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePage extends Fragment {
    private LocationTimeObject locationTimeObject;
    public ArrayList longitude = new ArrayList();
    public ArrayList latitude = new ArrayList();
    public ArrayList<LocationTimeObject> LocationObjects = new ArrayList();
    private final String TAG = "HomePage";



    public HomePage() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page,container,false);
        LocationObjects = (ArrayList) getArguments().getParcelableArrayList("LocationObject");
        int size = LocationObjects.size();

        for (int i = 0; i < size && i < 10; i++) {
            //Loops through and sets each textView as the time longitude and latitude from the object
            //Array Position used to display position from most recent to first location
            int ArrayPosition = (size-1) - i;
            String df = locationTimeObject.getSingleTime(ArrayPosition);
            //Log Files to check if Location and time are correct
            Log.i(TAG,"Time: "+  df);
            Log.i(TAG, "LocationObject Latitude: "+ locationTimeObject.Instance().getSingleLatitude(ArrayPosition));
            Log.i(TAG, "LocationObject Longitude: "+ locationTimeObject.Instance().getSingleLongitude(ArrayPosition));

            //Gets the identifier based on where the loop is
            int mId = HomePage.this.getResources().getIdentifier("latitude"+(i+1),"id",getContext().getPackageName());
            int mId2 = HomePage.this.getResources().getIdentifier("longitude"+(i+1),"id",getContext().getPackageName());
            TextView mTextView = (TextView) view.findViewById(mId);
            TextView mTimeTextView = (TextView) view. findViewById(mId2);

            //Logs
            Log.i(TAG,"String ID: location"+(i+1));
            Log.i(TAG,"ID Location: " + mId);
            Log.i(TAG, "ID Time: " + mId2);

            //Formats double to make it shorter and manageable
            DecimalFormat mDecimalFormat = new DecimalFormat("#.##");
            String latText = mDecimalFormat.format(locationTimeObject.Instance().getSingleLatitude(ArrayPosition));
            String lonText = mDecimalFormat.format(locationTimeObject.Instance().getSingleLongitude(ArrayPosition));

            //sets TextViews text
            mTextView.setText(latText);
            mTimeTextView.setText(lonText);

            // Inflate the layout for this fragment
        }
        return view;
    }

}
