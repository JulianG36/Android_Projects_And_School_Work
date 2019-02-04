package com.example.julia.week7lab;


import android.app.*;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.*;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class PickerFragment extends DialogFragment {


    public PickerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picker, container, false);
    }
    public static class DatePickerFragment extends DialogFragment{
        public Dialog onCreateDialog(LayoutInflater inflater, Bundle savedInstanceState, ViewGroup container) {
            View view = inflater.inflate(R.layout.dialog_date, container, false);
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Alert");
            return;
        }
    }

}
