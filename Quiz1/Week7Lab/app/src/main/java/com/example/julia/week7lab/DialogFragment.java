package com.example.julia.week7lab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class DialogFragment extends Fragment {
private Spinner mSpinner;
private EditText mEditText;
private CheckBox mCheckBox;
private boolean checked = false;
private final String TAG = "DialogFragment";

    public DialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog,container, false);
        mSpinner = (Spinner) view.findViewById(R.id.mSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.spinner_items, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mEditText = (EditText) view.findViewById(R.id.mEditText);
        mCheckBox = (CheckBox) view.findViewById(R.id.mCheckbox);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "Beggining Change");

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d(TAG, "Text Changed");

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "Text Done Changing");

            }
        });
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCheckBox.isChecked()==true){
                    checked=true;
                    Log.d(TAG, "Set True");
                }
                if (mCheckBox.isChecked()==false){
                    checked=false;
                    Log.d(TAG, "Set False");
                }


            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
