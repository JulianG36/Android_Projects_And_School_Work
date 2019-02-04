package com.example.julia.week7lab;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button Logs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logs = (Button) findViewById(R.id.mLogs_button);
        Logs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                DialogFragment dialogFragment = new DialogFragment();
                manager.beginTransaction().replace(R.id.mainLayout, dialogFragment).commit();
            }
        });


    }
}
