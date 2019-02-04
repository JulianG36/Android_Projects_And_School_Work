package com.imcool.julian.carratingapp;

import android.app.Activity;
import android.content.res.Resources;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    final static Cars[] carsArray = new Cars[3];
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
        final ImageView imgView = (ImageView) findViewById(R.id.imgView);
        comments = (EditText) findViewById(R.id.edtText);
        rb1 = (RadioButton) findViewById(R.id.yes);
        rb2 = (RadioButton) findViewById(R.id.No);
        rb3 = (RadioButton) findViewById(R.id.maybe);
        rating = (RatingBar) findViewById(R.id.bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Initializes Cars in carsArray
        carsArray[0] = new Cars("Dodge Hellcat", 0, "",0);
        carsArray[1] = new Cars("Mercedes AMG GT", 0, "",0);
        carsArray[2] = new Cars("BMW M2", 0, "",0);
        // Sets Listeners
        comments.setOnKeyListener(new OnEnterListener());
        rb1.setOnClickListener(new radioButtonListener());
        rb2.setOnClickListener(new radioButtonListener());
        rb3.setOnClickListener(new radioButtonListener());
        rating.setOnRatingBarChangeListener(new ratingChangeListener());
        // Setup spinner
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(
                toolbar.getContext(),
                new String[]{
                        carsArray[0].getNames(),
                        carsArray[1].getNames(),
                        carsArray[2].getNames(),
                }));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int spinPosition = spinner.getSelectedItemPosition();
                if (spinPosition == 0) {
                    imgView.setImageResource(R.drawable.hellcat1);
                    section = 0;
                }
                if (spinPosition == 1) {
                    imgView.setImageResource(R.drawable.mercedes);
                    section = 1;
                }
                if (spinPosition == 2) {
                    imgView.setImageResource(R.drawable.bmwm2);
                    section = 2;
                }
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
                if (carsArray[section].getSelection()==1) {
                    ride = "Yes";
                }
                if (carsArray[section].getSelection()==2) {
                    ride = "No";
                }
                if (carsArray[section].getSelection()==3) {
                    ride = "Not Sure";
                }
                Snackbar.make(view, "Car: " + carsArray[section].getNames() + "      Comments: " + carsArray[section].getComments() + "\nWould Ride: " + ride + "      Rating: " + carsArray[section].getRating(), Snackbar.LENGTH_LONG).setAction("Action", null).show();

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

            if (sectionNum == 1) {
                textView.setText("Dodge Hellcat");
                comments.setText(carsArray[section].getComments());
                rating.setRating(carsArray[section].getRating());

            }
            if (sectionNum == 2) {
                textView.setText("Mercedes AMG GT");
                comments.setText(carsArray[section].getComments());
                rating.setRating(carsArray[section].getRating());
            }
            if (sectionNum == 3) {
                textView.setText("BMW M2");
                comments.setText(carsArray[section].getComments());
                rating.setRating(carsArray[section].getRating());
            }
            //Sets the buttons back to the cars checked state;
            if (carsArray[section].getSelection() == 1) {
                rb1.setChecked(true);
            }
            if (carsArray[section].getSelection() == 2) {
                rb2.setChecked(true);
            }
            if (carsArray[section].getSelection() == 3) {
                rb3.setChecked(true);
            }
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

    }

    public class OnEnterListener implements View.OnKeyListener {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent event) {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String text = comments.getText().toString();
                carsArray[section].setComments(text);
                return true;
            }
            return false;

        }
    }
    public class radioButtonListener implements  View.OnClickListener{
        @Override
        public void onClick(View view){
            if (rb1.isChecked()){
                carsArray[section].setSelection(1);
            }
            if (rb2.isChecked()){
                carsArray[section].setSelection(2);
            }
            if (rb3.isChecked()){
                carsArray[section].setSelection(3);
            }

        }
    }
    public class ratingChangeListener implements RatingBar.OnRatingBarChangeListener{
        @Override
        public void onRatingChanged(RatingBar bar, float rating, boolean fromUser){
            carsArray[section].setRating(rating);
        }
    }
}
