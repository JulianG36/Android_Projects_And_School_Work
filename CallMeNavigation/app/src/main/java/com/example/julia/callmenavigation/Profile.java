package com.example.julia.callmenavigation;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {
    private FirebaseAuth firebaseAuth;
    private TextView textViewEmail;
    private Button logoutButton;
    private Button saveButton;
    private Button getObjectsButton;
    private DatabaseReference databaseReference;
    FirebaseUser user;


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        textViewEmail = (TextView) view.findViewById(R.id.mProfileName);
        logoutButton = (Button) view.findViewById(R.id.mLogOut);
        saveButton = (Button) view.findViewById(R.id.mSaveObjects);
        getObjectsButton = (Button) view.findViewById(R.id.mGetObjects);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        if(firebaseAuth.getCurrentUser() == null){
            Intent intent = new Intent(getContext(), LoginActivity.class);
        }
        else{

            user = firebaseAuth.getCurrentUser();
            textViewEmail.setText(user.getEmail());
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveLocationTimeObjects();
            }
        });
        getObjectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //LocationTimeObject.instance = dataSnapshot.getValue(LocationTimeObject.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onStart(){
        super.onStart();

    }
    public void saveLocationTimeObjects(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).child("LocationObject").setValue(LocationTimeObject.Instance());
    }
    public void getRefrence(){
        databaseReference = FirebaseDatabase.getInstance().getReference("LocationObject");
    }
    public void getLocationTimeObjects(DataSnapshot dataSnapshot){
        if (user != null){
            LocationTimeObject.instance = dataSnapshot.getValue(LocationTimeObject.class);
        }
    }

}
