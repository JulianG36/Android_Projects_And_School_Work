package com.imcool.julian.ratinglist;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class RecycleViewActivity extends AppCompatActivity {
    private final String TAG = "RecycleViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        //Used Intent to retrieve data but singleton much better
        //RatingObject ratingObject = (RatingObject) getIntent().getSerializableExtra("RatingObjects");
        //Log.i(TAG,ratingObject.getRatingObjectName(0));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Used bundles before figuring out singletons
        //Kept for future reference
        //Bundle bundle = new Bundle();
        //bundle.putSerializable("RatingObject", ratingObject);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RatingListFragment ratingListFragment= new RatingListFragment();
        //ratingListFragment.setArguments(bundle);
        transaction.replace(R.id.main_layout, ratingListFragment);
        transaction.commit();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
