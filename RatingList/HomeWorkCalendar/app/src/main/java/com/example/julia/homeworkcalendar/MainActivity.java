package com.example.julia.homeworkcalendar;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Database db;
    private Button viewButton;
    private Button SubmissionsButton;
    private Button deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new Database(this);
        deleteButton = (Button) findViewById(R.id.mDeleteAll);
        viewButton = (Button) findViewById(R.id.mViewHomeWork);
        SubmissionsButton = (Button) findViewById(R.id.mSubmissions);
        beginSubmissionsFrag();

        //Opens Submissions Page
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.getAllData().getCount()==0){
                    Toast toast = Toast.makeText(getBaseContext(), "No Data in DataBase", Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    beginViewHomeworkFrag();
            }
        }});
        //Opens Submit Page
        SubmissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginSubmissionsFrag();
            }
        });
        //deletes everything in table and then calls fragment again to reset
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAll();
                Toast toast = Toast.makeText(getBaseContext(), "Deleted all entries", Toast.LENGTH_LONG);
                toast.show();
                beginViewHomeworkFrag();
            }
        });

    }
    //Starts Fragments
    public void beginViewHomeworkFrag(){
        FragmentManager manager = getSupportFragmentManager();
        HomeWorkRecycler homeWorkRecycler = new HomeWorkRecycler();
        manager.beginTransaction().replace(R.id.mainLayout, homeWorkRecycler).commit();
    }
    public void beginSubmissionsFrag(){
        FragmentManager manager = getSupportFragmentManager();
        HomeworkFragment homeworkFragment = new HomeworkFragment();
        manager.beginTransaction().replace(R.id.mainLayout,homeworkFragment).commit();
    }
}
