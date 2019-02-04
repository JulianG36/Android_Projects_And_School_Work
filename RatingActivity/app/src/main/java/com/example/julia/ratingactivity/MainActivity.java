package com.example.julia.ratingactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
private TextView nameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent received = getIntent();
        String name = received.getStringExtra("names");
        nameView = (TextView) findViewById(R.id.firstText);
        nameView.setText(name);
    }
    public void buttonClick(View v){
        Intent launchRating = new Intent(this, LaunchActivity.class);
        String name = nameView.getText().toString();
        launchRating.putExtra("name", name);
        startActivity(launchRating);
    }
}
