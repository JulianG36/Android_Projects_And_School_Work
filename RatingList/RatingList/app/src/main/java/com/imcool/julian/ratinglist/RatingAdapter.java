package com.imcool.julian.ratinglist;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Julian on 4/4/2018.
 */

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder> {
    private final LayoutInflater inflater;
    private RatingObject ratingObject;
    private final String TAG = "RatingAdapter";
    public RatingAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.ratingObject = ratingObject.Instance();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rating_row,parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.ratingBar.setRating(ratingObject.getRatingObjectRating(position));
        holder.editTextView.setText(ratingObject.getRatingObjectName(position));
        holder.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingObject.Instance().saveRatingObject(position, holder.ratingBar.getRating(),holder.editTextView.getText().toString());
                Log.i(TAG,"Saved");
            }
        });
    }

    @Override
    public int getItemCount() {

        return ratingObject.getLength();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        RatingBar ratingBar;
        EditText editTextView;
        Button saveButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            saveButton = itemView.findViewById(R.id.mSaveButton);
            ratingBar = itemView.findViewById(R.id.mCustomRating);
            editTextView = itemView.findViewById(R.id.mCustomTextView);
        }
    }
}
