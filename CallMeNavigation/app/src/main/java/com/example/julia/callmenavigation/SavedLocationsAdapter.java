package com.example.julia.callmenavigation;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
/**
 * Created by julia on 4/19/2018.
 */
public class SavedLocationsAdapter extends RecyclerView.Adapter<SavedLocationsAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Cursor res;
    private final String TAG = "SavedLocationsAdapter";
    Database db;
    private LocationTimeObject locationTimeObject;
    private Context context;
    public SavedLocationsAdapter(Context context){
        inflater = LayoutInflater.from(context);
        db = new Database(context);
        res = db.getAllData();
        this.context = context;
        Log.i(TAG, "Adapter Initiated, Database Count: "+ res.getCount());
    }

    @Override
    public SavedLocationsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.saved_recycler_row_layout, parent, false);
        SavedLocationsAdapter.MyViewHolder holder = new SavedLocationsAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SavedLocationsAdapter.MyViewHolder holder, final int position) {
        res.moveToPosition(position);
        holder.longitudeTextView.setText(res.getString(1));
        holder.latitudeTextView.setText(res.getString(2));
        holder.timeTextView.setText(res.getString(3));
        holder.dateTextView.setText(res.getString(4));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Moves to first to reset where the cursor is pointing
                res.moveToFirst();
                int ID = res.getInt(0);
                Log.i(TAG, "ID: "+ ID);
                db.deleteData(ID);
                res = db.getAllData();
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,getItemCount());
            }
        });
        Log.i(TAG, "Displayed Recycler: " + position);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RowRecyclerMapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("longitude", res.getInt(1));
                bundle.putDouble("latitude", res.getInt(2));
                bundle.putInt("recyclerVersion", 2);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return res.getCount();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView longitudeTextView;
        TextView latitudeTextView;
        TextView timeTextView;
        TextView dateTextView;
        Button deleteButton;
        LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            longitudeTextView = itemView.findViewById(R.id.mLongitude);
            latitudeTextView = itemView.findViewById(R.id.mLatitude);
            timeTextView = itemView.findViewById(R.id.mTime);
            dateTextView = itemView.findViewById(R.id.mDate);
            deleteButton = itemView.findViewById(R.id.mDelete);
            linearLayout = itemView.findViewById(R.id.mSavedLayout);

        }
    }
}
