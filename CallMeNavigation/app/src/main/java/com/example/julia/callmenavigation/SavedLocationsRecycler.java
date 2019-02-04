package com.example.julia.callmenavigation;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class SavedLocationsRecycler extends Fragment {
SavedLocationsAdapter adapter;
RecyclerView recyclerView;
private final String TAG = "SavedLocationsRecycler";

    public SavedLocationsRecycler() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Context context = getContext();
        View view = inflater.inflate(R.layout.fragment_saved_locations_recycler, container,false);
        adapter = new SavedLocationsAdapter(context);
        recyclerView = (RecyclerView) view.findViewById(R.id.mSavedRecycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        Log.i(TAG, "Database Recycler Initiated");
        // Inflate the layout for this fragment
        return view;
    }

}
