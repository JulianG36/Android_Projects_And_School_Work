package com.imcool.julian.carratingapp;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;

import java.io.Serializable;
import java.util.ArrayList;


public class CarsFragment extends Fragment implements Serializable {
    private static EditText comments;
    private static int section;
    private static RadioButton rb1;
    private static RadioButton rb2;
    private static RadioButton rb3;
    private static RatingBar rating;
    private static ArrayList<Cars> carsArray = new ArrayList<>();
    Bundle bundle = new Bundle();


    public CarsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        carsArray = (ArrayList) getArguments().getParcelableArrayList("CarsArray");
        section = getArguments().getInt("sectionNum");
        View view = inflater.inflate(R.layout.fragment_cars, container, false);
        comments = view.findViewById(R.id.edtText);
        rb1 = view.findViewById(R.id.yes);
        rb2 = view.findViewById(R.id.No);
        rb3 = view.findViewById(R.id.maybe);
        rating = view.findViewById(R.id.bar);
        final ImageView imgView = (ImageView) view.findViewById(R.id.imgView);
        comments.setOnKeyListener(new OnEnterListener());
        rb1.setOnClickListener(new radioButtonListener());
        rb2.setOnClickListener(new radioButtonListener());
        rb3.setOnClickListener(new radioButtonListener());
        rating.setOnRatingBarChangeListener(new ratingChangeListener());
        comments.setText(carsArray.get(section).getComments());
        rating.setRating(carsArray.get(section).getRating());
        bundle.putSerializable("carsArray", carsArray);
        

        //Sets the buttons back to the cars checked state;
        if (carsArray.get(section).getSelection() == 1) {
            rb1.setChecked(true);
        }
        if (carsArray.get(section).getSelection() == 2) {
            rb2.setChecked(true);
        }
        if (carsArray.get(section).getSelection() == 3) {
            rb3.setChecked(true);
        }
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


        return inflater.inflate(R.layout.fragment_cars, container, false);
    }
    public class OnEnterListener implements View.OnKeyListener {
        @Override
        public boolean onKey(View view, int keyCode, KeyEvent event) {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                Intent intent = new Intent(getActivity().getBaseContext(),MainActivity.class);
                String text = comments.getText().toString();
                carsArray.get(section).setComments(text);
                intent.putParcelableArrayListExtra("CarsArray",(ArrayList) carsArray);
                getActivity().startActivity(intent);
                return true;
            }
            return false;

        }
    }
    public class radioButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Intent intent = new Intent(getActivity().getBaseContext(),MainActivity.class);

            if (rb1.isChecked()){
                Log.i("CarsFragment", "Rb1 checked");
                carsArray.get(section).setSelection(1);
                intent.putParcelableArrayListExtra("CarsArray",(ArrayList) carsArray);
            }
            if (rb2.isChecked()){
                carsArray.get(section).setSelection(2);
                intent.putParcelableArrayListExtra("CarsArray",(ArrayList) carsArray);
            }
            if (rb3.isChecked()){
                carsArray.get(section).setSelection(3);
                intent.putParcelableArrayListExtra("CarsArray",(ArrayList) carsArray);
            }
            getActivity().startActivity(intent);
        }
    }
    public class ratingChangeListener implements RatingBar.OnRatingBarChangeListener {
        @Override
        public void onRatingChanged(RatingBar bar, float rating, boolean fromUser){
            Intent intent = new Intent(getActivity().getBaseContext(),MainActivity.class);
            carsArray.get(section).setRating(rating);
            intent.putParcelableArrayListExtra("CarsArray",(ArrayList) carsArray);
            getActivity().startActivity(intent);
        }
    }

}
