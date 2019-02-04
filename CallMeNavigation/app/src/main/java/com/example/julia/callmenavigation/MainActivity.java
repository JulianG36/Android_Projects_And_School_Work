package com.example.julia.callmenavigation;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.nfc.Tag;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Database myDb;
    private LocationTimeObject mLocationTimeObject;
    private FusedLocationProviderClient mFusedLocation;
    private static final String TAG = "MyActivity";
    protected LocationManager mLocationManager;
    private Switch mSwitch;
    private Button markerButton;
    private Context context;
    private DatabaseReference mDatabaseRefrence;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;



    protected  LocationListener mLocationListener = new LocationListener() {
    @Override
    public void onLocationChanged(Location location) {
        mLocationTimeObject = LocationTimeObject.Instance();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        DateFormat tf = new SimpleDateFormat("HH:mm a");
        Date today = Calendar.getInstance().getTime();
        mLocationTimeObject.Instance().addSingleObject(location.getLatitude(), location.getLongitude(),tf.format(today),df.format(today),false,-1);
        location.getAccuracy();
        double Lat = location.getLatitude();
        double Lon = location.getLongitude();
        Log.e(TAG,"Location: " + Lat + ", " + Lon);
        Log.i(TAG, "Getting Location..");
        saveData();

    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationTimeObject.Instance();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        myDb = new Database(this);
        mSwitch = (Switch) findViewById(R.id.mSwitch);
        mSwitch.setChecked(true);
        markerButton = (Button) findViewById(R.id.mMarkerButton);
        context = getBaseContext();
        firebaseAuth = FirebaseAuth.getInstance();
        checkLoginState();
        //Loads data through a Json
        loadData();
        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);





        //Asking Permission for use of location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        //If the permissoion is granted then the Location listener will begin requesting locations
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (mSwitch.isChecked() == true){
                mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, (float) 2, mLocationListener);
            }

        }
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mSwitch.isChecked()==true){
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, (float) 2, mLocationListener);
                    }
                }
                else if (mSwitch.isChecked()==false){
                    mLocationManager.removeUpdates(mLocationListener);
                    Toast toast = Toast.makeText(context, "Stopped Tracking", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        markerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mLocationListener, null);
                    Toast toast = Toast.makeText(context, "Obtaining Location...", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        //floating action button to refresh the recycler fragment
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportActionBar().setTitle("Home");
                StartHomeRecycler();
            }
        });
        //Sets the navigation drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //handles selections in the navigation drawer
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager manager = getSupportFragmentManager();
        int id = item.getItemId();
        if (id == R.id.nav_home){
            getSupportActionBar().setTitle("Home");
            StartHomeRecycler();

        }else if (id == R.id.nav_call) {
            getSupportActionBar().setTitle("Contact Us");
            CallmeFragment callFragment = new CallmeFragment();
            manager.beginTransaction().replace(R.id.mainLayout, callFragment ).commit();

        } else if (id == R.id.nav_maps) {
            getSupportActionBar().setTitle("Been Here");
            MapsFragment mapsFragment = new MapsFragment();
            manager.beginTransaction().replace(R.id.mainLayout, mapsFragment).commit();

        } else if (id == R.id.nav_database) {
            getSupportActionBar().setTitle("Saved Locations");
            SavedLocationsRecycler savedLocationsRecycler = new SavedLocationsRecycler();
            manager.beginTransaction().replace(R.id.mainLayout, savedLocationsRecycler).commit();

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(context, SettingsActivity.class);
            context.startActivity(intent);

        } else if (id == R.id.nav_reset) {
            //resets the location objects
            this.mLocationTimeObject = new LocationTimeObject();
            mLocationTimeObject.resetInstance();
            saveData();
            StartHomeRecycler();
        } else if (id == R.id.nav_login) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        } else if (id == R.id.nav_profile){
            Profile profile = new Profile();
            manager.beginTransaction().replace(R.id.mainLayout, profile).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //used to check whether or not the database added the query
    public void dbChecker(boolean check){
        if (check == false){
            Log.i(TAG,"Did not add");
        }
        else{
            Log.i(TAG, "Added");
        }
    }
    //saves data to a json for future use when the app is reopened
    private void saveData() {
        LocationTimeObject locationTimeObject = mLocationTimeObject.Instance();
        SharedPreferences sharedPreferences = getSharedPreferences("shared prefrences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(locationTimeObject);
        editor.putString("LocationTimeObjects", json);
        editor.apply();
    }
    //loads data from a json
    private void loadData(){
        mLocationTimeObject.Instance();
        SharedPreferences sharedPreferences = getSharedPreferences("shared prefrences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("LocationTimeObjects", null);
        Type type = new TypeToken<LocationTimeObject>(){}.getType();
        mLocationTimeObject.instance = gson.fromJson(json,type);
        if (mLocationTimeObject == null){
            mLocationTimeObject.Instance();
        }

    }
    //Begins homeRecycler
    private void StartHomeRecycler(){
        FragmentManager manager = getSupportFragmentManager();
        HomePageRecycler homePageRecycler = new HomePageRecycler();
        manager.beginTransaction().replace(R.id.mainLayout, homePageRecycler). commit();
    }
    public void checkLoginState(){
        if(firebaseAuth.getCurrentUser() != null){
            //profile
            FragmentManager manager = getSupportFragmentManager();
            Profile profile = new Profile();
            manager.beginTransaction().replace(R.id.mainLayout, profile ).commit();

        }else{
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        }
    }
}
