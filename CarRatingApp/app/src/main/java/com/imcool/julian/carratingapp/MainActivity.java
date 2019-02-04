package com.imcool.julian.carratingapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.content.Context;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.content.res.Resources.Theme;

import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable {
    private static ArrayList<Cars> carsArray = new ArrayList();
    private static EditText comments;
    private static int section;
    private static RadioButton rb1;
    private static RadioButton rb2;
    private static RadioButton rb3;
    private static RatingBar rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Log.i("MainActivity","Receiving Data");

        if(carsArray.size()==0) {
            carsArray.add(new Cars("Dodge Hellcat", 0, "", 0));
            carsArray.add(new Cars("Mercedes AMG GT", 0, "", 0));
            carsArray.add(new Cars("BMW M2", 0, "", 0));
            Log.i("MainActivity", "Array created");
        }


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // Setup spinner
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(
                toolbar.getContext(),
                new String[]{
                        carsArray.get(0).getNames(),
                        carsArray.get(1).getNames(),
                        carsArray.get(2).getNames(),
                }));
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int spinPosition = spinner.getSelectedItemPosition();
                if (spinPosition == 0) {
                    section = 0;
                }
                if (spinPosition == 1) {
                    section = 1;
                }
                if (spinPosition == 2) {
                    section = 2;
                }
                receiveData();
                // When the given dropdown item is selected, show its contents in the
                // container view.
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ride = "";
                if (carsArray.get(section).getSelection()==1) {
                    ride = "Yes";
                }
                if (carsArray.get(section).getSelection()==2) {
                    ride = "No";
                }
                if (carsArray.get(section).getSelection()==3) {
                    ride = "Not Sure";
                }
                Snackbar.make(view, "Car: " + carsArray.get(section).getNames() + "      Comments: " + carsArray.get(section).getComments() + "\nWould Ride: " + ride + "      Rating: " + carsArray.get(section).getRating(), Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MyAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            int sectionNum = getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            Bundle bundle = new Bundle();
            bundle.putInt("sectionNum",section);
            bundle.putParcelableArrayList("CarsArray",(ArrayList) carsArray);
            FragmentManager manager = getFragmentManager();
            CarsFragment carsFragment = new CarsFragment();
            carsFragment.setArguments(bundle);


            if (sectionNum == 1) {
                textView.setText("Dodge Hellcat");

            }
            if (sectionNum == 2) {
                textView.setText("Mercedes AMG GT");
            }
            if (sectionNum == 3) {
                textView.setText("BMW M2");
            }
            manager.beginTransaction().replace(R.id.mainLayout, carsFragment).commit();
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }


    }
    private void receiveData(){
        ArrayList<Cars> carsArray2 = new ArrayList();
        Intent intent = getIntent();

        carsArray2 = (ArrayList) intent.getSerializableExtra("CarsArray");
        if (carsArray2 != null){
            carsArray = carsArray2;
            Log.i("MainActivity", "Got Intent");
        }else{
            Log.i("MainActivity", "Didnt get intent");
        }

    }
}
