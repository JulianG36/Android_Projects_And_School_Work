package com.example.julia.viewtester2;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RatingBar stars;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stars = (RatingBar) findViewById(R.id.ratingBar);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this,
                R.array.colors, android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(staticAdapter);
        spinner.setOnItemSelectedListener(new SpinnerActivity());
        stars .setOnRatingBarChangeListener(new RatingListener());
        /*
        // Sets the listener directly better to use a class
        stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                float rating = ratingBar.getRating();
                String text = "";
                if (rating == 5.0){
                    text = "5 Stars";
                }
                if (rating == 4.0){
                    text = "4 Stars";
                }
                if (rating == 3.0){
                    text = "3 Stars";
                }
                if (rating == 2.0){
                    text = "2 Stars";
                }
                if (rating == 1.0){
                    text = "1 Star";
                }

                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        */
    }
    public class SpinnerActivity implements AdapterView.OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            int position = spinner.getSelectedItemPosition();
            String selectedVal = getResources().getStringArray(R.array.colors)[position];
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, selectedVal, Toast.LENGTH_SHORT);
            toast.show();
        }
        public void onNothingSelected(AdapterView<?> parent){

        }
    }

    public class RatingListener implements RatingBar.OnRatingBarChangeListener{
        @Override
        public void onRatingChanged(RatingBar ratingBar,float v, boolean b){
            float rating = ratingBar.getRating();
            String text = "";
            if (rating == 5.0){
                text = "5 Stars";
            }
            if (rating == 4.0){
                text = "4 Stars";
            }
            if (rating == 3.0){
                text = "3 Stars";
            }
            if (rating == 2.0){
                text = "2 Stars";
            }
            if (rating == 1.0){
                text = "1 Star";
            }
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context,text,Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
