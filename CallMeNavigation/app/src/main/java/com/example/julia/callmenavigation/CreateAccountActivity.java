package com.example.julia.callmenavigation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class CreateAccountActivity extends AppCompatActivity {
    private UserObject user;
    private EditText firstName;
    private EditText lastName;
    private EditText address;
    private EditText city;
    private EditText state;
    private EditText country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstName = (EditText) findViewById(R.id.mFirstName);
        lastName = (EditText) findViewById(R.id.mLastName);
        address = (EditText) findViewById(R.id.mStreetAddress);
        city = (EditText) findViewById(R.id.mCity);
        state = (EditText) findViewById(R.id.mState);
        country = (EditText) findViewById(R.id.mCountry);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
