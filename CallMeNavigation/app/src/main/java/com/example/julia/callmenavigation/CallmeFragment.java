package com.example.julia.callmenavigation;


import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallmeFragment extends Fragment {
    private Button callMe;
    private Toolbar bar;
    private View view;
    public CallmeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_callme, container, false);
        callMe = (Button) view.findViewById(R.id.button_call);
        callMe.setOnClickListener(new onCallButtonListener());
        // Inflate the layout for this fragment
        return view;
    }
    private class onCallButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            Log.i("Dial","Dialing");
            intent.setData(Uri.parse("tel:"+"8154098677"));
            startActivity(intent);
        }
    }
}
