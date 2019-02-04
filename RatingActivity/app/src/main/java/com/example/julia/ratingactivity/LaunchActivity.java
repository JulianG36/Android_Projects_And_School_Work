package com.example.julia.ratingactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LaunchActivity extends AppCompatActivity {
private EditText textBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        textBox = (EditText) findViewById(R.id.textBox);
    }
    public void submit(View v){
        finish();
    }

}
