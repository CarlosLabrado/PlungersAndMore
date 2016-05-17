package us.petrolog.plungersandmore.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.firebase.ui.auth.core.FirebaseLoginBaseActivity;
import com.firebase.ui.auth.core.FirebaseLoginError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.petrolog.plungersandmore.R;
import us.petrolog.plungersandmore.model.User;
import us.petrolog.plungersandmore.model.Well;
import us.petrolog.plungersandmore.utils.Constants;
import us.petrolog.plungersandmore.utils.LogUtil;

public class MainActivity extends FirebaseLoginBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.nav_view)
    NavigationView mNavigationView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.editTextSearch)
    EditText mEditTextSearch;

    public static final String TAG = MainActivity.class.getSimpleName();

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private LatLng mLatLng;
    private Firebase mFirebaseRef;
    private ValueEventListener mUserRefListener;

    private Firebase mUserRef;

    private User mCurrentUser;

    private RecyclerView mRecyclerViewWell;
    private FirebaseRecyclerAdapter<Well, WellListHolder> mRecycleViewAdapter;

    private ArrayList<Well> mWells;

    private final long SEARCH_TRIGGER_DELAY_IN_MS = 1000;
    private boolean searchIsRunning = false;

    private Handler mHandler;

    private String mUserPID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /** toolBar **/
        setUpToolBar();

        mFirebaseRef = new Firebase(Constants.FIREBASE_URL);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mUserPID = sp.getString(Constants.KEY_USER_PID, null);
        mUserRef = new Firebase(Constants.FIREBASE_URL_USERS).child(mUserPID);

        LogUtil.logD(TAG, mUserPID);

        /**
         * Add ValueEventListeners to Firebase references
         * to control get data and control behavior and visibility of elements
         */
        mUserRefListener = mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mCurrentUser = snapshot.getValue(User.class);

                /**
                 * Set the activity title to current user name if user is not null
                 */
                if (mCurrentUser != null) {
                    /* Assumes that the first word in the user's name is the user's first name. */
                    try {
                        String firstName = mCurrentUser.getName().split("\\s+")[0];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                LogUtil.logE(TAG,
                        getString(R.string.log_error_the_read_failed) +
                                firebaseError.getMessage());
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //generateTestWells();
                showTabs();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        checkForLocationServicesEnabled();
        enableLocationRequests();

        populateRecyclerView();

        setUpMap();
        mHandler = new Handler();


        mEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            search();
                        }
                    }, SEARCH_TRIGGER_DELAY_IN_MS);
                }
            }
        });
    }

    public void search() {
        Firebase wellRef = new Firebase(Constants.FIREBASE_URL_WELLS);
        Query query = wellRef;
        if (!mEditTextSearch.getText().toString().isEmpty()) {
            query = wellRef.orderByChild("name").equalTo(mEditTextSearch.getText().toString());
        }

        mRecyclerViewWell.removeAllViews();
        mRecycleViewAdapter = new FirebaseRecyclerAdapter<Well, WellListHolder>(Well.class, R.layout.item_well, WellListHolder.class, query) {
            @Override
            public void populateViewHolder(WellListHolder listView, Well well, int position) {
                listView.setName(well.getName());
                listView.setState(String.valueOf(well.getCurrentStatus().getCyclesCompleted()));
            }
        };

        mRecyclerViewWell.setAdapter(mRecycleViewAdapter);
    }

    private void showTabs() {
        Intent intent = new Intent(this, TabContainerActivity.class);
        startActivity(intent);
    }

    private void enableLocationRequests() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

    }

    /**
     * checks for the GPS to be enabled
     */
    private void checkForLocationServicesEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(getResources().getString(R.string.gps_network_not_enabled));
            dialog.setMessage(getResources().getString(R.string.gps_network_not_enabled_message));
            dialog.setPositiveButton(getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
        }
    }

    private void setUpMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void populateRecyclerView() {
        mRecyclerViewWell = (RecyclerView) findViewById(R.id.recyclerViewWellList);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(false);

        mRecyclerViewWell.setHasFixedSize(false);
        mRecyclerViewWell.setLayoutManager(manager);

        Firebase wellRef = new Firebase(Constants.FIREBASE_URL_WELLS);
        Query query = wellRef.orderByChild("users").equalTo("z@gmail.com");


//        wellRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot usersSnapshot) {
//                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
//                    Well well = userSnapshot.getValue(Well.class);
//                    boolean carlos = false;
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });

        mRecycleViewAdapter = new FirebaseRecyclerAdapter<Well, WellListHolder>(Well.class, R.layout.item_well, WellListHolder.class, query) {
            @Override
            public void populateViewHolder(WellListHolder listView, Well well, int position) {
                listView.setName(well.getName());
                listView.setState(String.valueOf(well.getCurrentStatus().getCyclesCompleted()));
            }
        };

        mRecyclerViewWell.setAdapter(mRecycleViewAdapter);
    }

//    private void generateTestWells() {
//        //String userEmail = (String) mFirebaseRef.getAuth().getProviderData().get("email");
//
//        Calendar c = Calendar.getInstance();
//        c.getTimeInMillis();
//        Cycle cycle = new Cycle(new Open(1521, 1000, 100, c.getTimeInMillis()), new ShutIn(1521, 1000, 100, c.getTimeInMillis()), c.getTimeInMillis());
//
//        HashMap<String, Cycle> cycles = new HashMap<>();
//        cycles.put("pop", cycle);
//
//        Well well = new Well("uno",
//                new CurrentStatus(1234, 1000, 20, 2152, 100),
//                cycles,
//                new us.petrolog.plungersandmore.model.Location(28.6752758, -106.1404511),
//                null);
//        Well well2 = new Well("dos",
//                new CurrentStatus(1234, 1000, 20, 2152, 100),
//                cycles,
//                new us.petrolog.plungersandmore.model.Location(28.6802217, -106.1182286),
//                null);
//        Well well3 = new Well("tres",
//                new CurrentStatus(1234, 1000, 20, 2152, 100),
//                cycles,
//                new us.petrolog.plungersandmore.model.Location(28.6756834, -106.1366093),
//                null);
//        Well well4 = new Well("cuatro",
//                new CurrentStatus(1234, 1000, 20, 2152, 100),
//                cycles,
//                new us.petrolog.plungersandmore.model.Location(28.6802217, -106.1182286),
//                null);
//        Well well5 = new Well("cinco",
//                new CurrentStatus(1234, 1000, 20, 2152, 100),
//                cycles,
//                new us.petrolog.plungersandmore.model.Location(28.6735469, -106.1384236),
//                null);
//        Well well6 = new Well("seis",
//                new CurrentStatus(1234, 1000, 20, 2152, 100),
//                cycles,
//                new us.petrolog.plungersandmore.model.Location(28.6738751, -106.1303234),
//                null);
//        mFirebaseRef.child("wellsTest").push().setValue(well);
//        mFirebaseRef.child("wellsTest").push().setValue(well2);
//        mFirebaseRef.child("wellsTest").push().setValue(well3);
//        mFirebaseRef.child("wellsTest").push().setValue(well4);
//        mFirebaseRef.child("wellsTest").push().setValue(well5);
//        mFirebaseRef.child("wellsTest").push().setValue(well6);
//    }

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected Firebase getFirebaseRef() {
        return mFirebaseRef;
    }

    @Override
    protected void onFirebaseLoginProviderError(FirebaseLoginError firebaseLoginError) {
        dismissFirebaseLoginPrompt();
        Toast.makeText(this, "There is a connection error, please try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onFirebaseLoginUserError(FirebaseLoginError firebaseLoginError) {
        dismissFirebaseLoginPrompt();
        Toast.makeText(this, "Non valid credentials, please try again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFirebaseLoggedIn(AuthData authData) {
        Toast.makeText(this, "Hi ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFirebaseLoggedOut() {
        finish();
        // TODO: Handle logout
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        populateMapArray();

        mMap.setMyLocationEnabled(true);

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void populateMapArray() {
        Firebase wellRef = new Firebase(Constants.FIREBASE_URL_WELLS);

        mWells = new ArrayList<>();

        wellRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Well well = postSnapshot.getValue(Well.class);
                    mWells.add(well);
                    drawMarker(well);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private void drawMarker(Well well) {
        LatLng latLng = new LatLng(well.getLocation().getLat(), well.getLocation().getLon());
        mMap.addMarker(new MarkerOptions().position(latLng).title(well.getName()));
    }

    /**
     * sets up the top bar
     */
    public void setUpToolBar() {
        setSupportActionBar(toolbar);
        setActionBarTitle(getResources().getString(R.string.app_name), null, false);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            // enabling action bar app icon and behaving it as toggle button
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    /**
     * Gets called from the fragments onResume and its because only the first doesn't have the up
     * button on the actionBar
     *
     * @param title          The title to show on the ActionBar
     * @param subtitle       The subtitle to show on the ActionBar
     * @param showNavigateUp if true, shows the up button
     */
    public void setActionBarTitle(String title, String subtitle, boolean showNavigateUp) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            if (subtitle != null) {
                getSupportActionBar().setSubtitle(subtitle);
            } else {
                getSupportActionBar().setSubtitle(null);
            }
            if (showNavigateUp) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        LogUtil.logD(TAG, "Location services connected.");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LogUtil.logD(TAG, "onConnected requesting Loc");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            LogUtil.logD(TAG, "onConnected has last location");
            handleNewLocation(location);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        LogUtil.logD(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onLocationChanged(Location location) {
        LogUtil.logD(TAG, "onLocationChanged location changed");
        handleNewLocation(location);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, Constants.CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            LogUtil.logD(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * gets the location and then asks Map fragment to update it
     *
     * @param location current loc
     */
    private void handleNewLocation(Location location) {
        LogUtil.logD(TAG, location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        mLatLng = new LatLng(currentLatitude, currentLongitude);
        CameraUpdate cameraUpdateFactory = CameraUpdateFactory.newLatLngZoom(mLatLng, 17);
        mMap.animateCamera(cameraUpdateFactory);
        LogUtil.logE(TAG, "ACCURACY " + String.valueOf(location.getAccuracy()));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUserRef.removeEventListener(mUserRefListener);
    }


    /**
     * For the recycler view
     */
    public static class WellListHolder extends RecyclerView.ViewHolder {
        View mView;

        public WellListHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mView.getContext(), "message", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setName(String name) {
            TextView field = (TextView) mView.findViewById(R.id.textViewItemName);
            field.setText(name);
        }

        public void setState(String text) {
            TextView field = (TextView) mView.findViewById(R.id.textViewitemState);
            field.setText(text);
        }
    }
}
