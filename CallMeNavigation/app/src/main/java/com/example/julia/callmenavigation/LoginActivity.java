package com.example.julia.callmenavigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView email;
    private TextView password;
    private String fireEmail = " ";
    private String firePassword = " ";
    private TextView createAccount;
    private Button loginButton;
    private final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        email = (TextView) findViewById(R.id.mEmail);
        password = (TextView) findViewById(R.id.mPassword);
        loginButton = (Button) findViewById(R.id.mLoginButton);
        createAccount = (TextView) findViewById(R.id.mCreateAccount);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getBaseContext(),"Enter an email",Toast.LENGTH_SHORT);
                    toast.show();
                }else if(password.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getBaseContext(),"Enter a password",Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    fireEmail = email.getText().toString();
                    firePassword = password.getText().toString();
                    mAuth.signInWithEmailAndPassword(fireEmail, firePassword).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                Log.i(TAG, "Sign in Succesful");
                                finish();
                            }else {
                                Log.i(TAG, "Sign in Failed");
                                updateUI(null);
                                finish();
                            }

                        }
                    });
                }
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getBaseContext(), CreateAccountActivity.class);
                getBaseContext().startActivity(intent);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        /**
        mAuth.createUserWithEmailAndPassword(fireEmail, firePassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "User Created");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }else {
                            Log.i(TAG, "User Creation Failed");
                            updateUI(null);
                        }
                    }
                });
**/
    }
    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    public void updateUI(FirebaseUser firebaseUser){
        //FireBase user = FirebaseAuth.getInstance().getCurrentUser(); Used to get user data
        if (firebaseUser != null){
            String name = firebaseUser.getDisplayName();
            String email = firebaseUser.getEmail();
            Uri photoUrl = firebaseUser.getPhotoUrl();
            String Uid = firebaseUser.getUid();
            boolean emailVerified = firebaseUser.isEmailVerified();


        }

    }

}