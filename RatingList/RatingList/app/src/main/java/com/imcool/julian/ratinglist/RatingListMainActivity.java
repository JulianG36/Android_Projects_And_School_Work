package com.imcool.julian.ratinglist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RatingListMainActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private EditText editText;
    private Button SubmitButton;
    private Button ListButton;
    private Button ResetButton;
    private RatingObject ratingObject;
    private final String TAG = "RatingListMainActivity";
    int resetCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final RatingObject ratingObject = new RatingObject();
        final Context context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_list_main);
        ratingBar = findViewById(R.id.mRatingBar);
        editText = findViewById(R.id.mEditText);
        SubmitButton = findViewById(R.id.mSubmitButton);
        ListButton = findViewById(R.id.mListButton);
        ResetButton = findViewById(R.id.mResetButton);

        //Submits and checks to make sure if there is a name.
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().equals("")){
                    Toast toast = Toast.makeText(context,"Enter a name!", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    ratingObject.Instance().addRatingObject(ratingBar.getRating(),editText.getText().toString());
                    Log.i(TAG, "added rating object");
                    Toast toast = Toast.makeText(context, "Submitted", Toast.LENGTH_LONG);
                    toast.show();
                    resetRating();
                }

            }
        });
        //Starts Recycle Activity and makes sure there is a rating before starting
        ListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ratingObject.getLength()!=0){
                    Intent recycleViewActivity = new Intent(RatingListMainActivity.this, RecycleViewActivity.class);
                    Log.i(TAG, "RecycleViewActivityStarting");
                    startActivity(recycleViewActivity);
                }
                else{
                    Toast toast = Toast.makeText(context,"Submit Rating to View", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        //Makes sure user wants to reset and then calls reset method
        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resetCount == 0){
                    Toast toast = Toast.makeText(context, "Are You Sure, Click Again to Reset", Toast.LENGTH_LONG);
                    toast.show();
                    resetCount++;
                }
                else{
                    ratingObject.Instance().resetObjects();
                    resetCount = 0;
                    Toast toast = Toast.makeText(context,"Reset", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

    }
    //Called to make editText and Rating to default
    public void resetRating(){
        ratingBar.setRating(0);
        editText.setText("");
    }
}
