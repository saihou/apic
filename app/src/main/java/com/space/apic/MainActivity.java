package com.space.apic;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        HomeFragment.OnFragmentInteractionListener,
        ChallengeFragment.OnFragmentInteractionListener,
        ChallengeFragmentBase.OnFragmentInteractionListener,
        NearbyChallengeFragment.OnFragmentInteractionListener,
        MakeNewPostFragment.OnFragmentInteractionListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    Fragment activeFragment;
    String TAG = "MainActivity";
    GoogleApiClient mGoogleApiClient;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.space.apic.R.layout.activity_main);

        Utils.init();

        Toolbar toolbar = (Toolbar) findViewById(com.space.apic.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(com.space.apic.R.string.app_name);

        DrawerLayout drawer = (DrawerLayout) findViewById(com.space.apic.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.space.apic.R.string.navigation_drawer_open, com.space.apic.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(com.space.apic.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        //set default to home
        navigationView.setCheckedItem(com.space.apic.R.id.nav_home);
        HomeFragment fragment = new HomeFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(com.space.apic.R.id.container, fragment);
        fragmentTransaction.commit();
        activeFragment = fragment;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(com.space.apic.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.space.apic.R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.space.apic.R.id.action_settings) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickIntent, com.space.apic.Constants.SELECT_PIC_REQUEST_CODE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        //Checking if the item is in checked state or not, if not make it in checked state
        if(menuItem.isChecked()) menuItem.setChecked(false);
        else menuItem.setChecked(true);

        if (id == com.space.apic.R.id.nav_home) {
            getSupportActionBar().setTitle(com.space.apic.R.string.app_name);
            HomeFragment fragment = new HomeFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(com.space.apic.R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            activeFragment = fragment;
        } else if (id == com.space.apic.R.id.nav_challenge) {
            getSupportActionBar().setTitle(com.space.apic.R.string.challenge);
            ChallengeFragment fragment = new ChallengeFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(com.space.apic.R.id.container,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            activeFragment = fragment;
        } else if (id == com.space.apic.R.id.nav_following) {
            getSupportActionBar().setTitle(R.string.following);
//            SettingsFragment fragment = new SettingsFragment();
//            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.container,fragment);
//            fragmentTransaction.commit();
        } else if (id == com.space.apic.R.id.nav_store) {
            getSupportActionBar().setTitle(R.string.store);
            StoreFragment fragment = new StoreFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            activeFragment = fragment;
        } else if (id == R.id.nav_trip_experiences) {
            getSupportActionBar().setTitle(R.string.trip_experiences);
            TripExperiencesFragment fragment = new TripExperiencesFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            activeFragment = fragment;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(com.space.apic.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(int fragmentId) {
        System.out.println(fragmentId);
        activeFragment = getSupportFragmentManager().findFragmentById(fragmentId);
    }

    @Override
    public void onFragmentInteraction(HomeCardData cardData) {
        HomeFragment fragment = new HomeFragment();
        getSupportActionBar().setTitle(getString(R.string.home));
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
        activeFragment = fragment;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        System.out.println(uri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.TAKE_PIC_REQUEST_CODE ||
                requestCode == Constants.SELECT_PIC_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                Uri imageUri;
                if (requestCode == Constants.SELECT_PIC_REQUEST_CODE) {
                    imageUri = data.getData();
                    Utils.mostRecentPhoto = imageUri;
                } else {
                    imageUri = Utils.mostRecentPhoto;
                }

                Log.d("Image Location", imageUri.toString());

                MakeNewPostFragment fragment = MakeNewPostFragment.newInstance(Utils.mostRecentMerchantName,
                                                        Utils.mostRecentMerchantDistance);
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commitAllowingStateLoss();

                getSupportActionBar().setTitle(getString(R.string.make_new_post));
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image selection
            } else {
                // Image selection failed, advise user
            }
        } else if (resultCode == Constants.LAUNCH_UBER_REQUEST_CODE) {
            System.out.println("CAME BACK FROM UBER");
        }

        activeFragment.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        Log.v(TAG, "onActivityResult");
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        activeFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.v(TAG, "onRequestPermissionsResult");
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "Google Play Services connected!");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            Utils.setLastKnownLocation(mLastLocation);
            Log.d(TAG, "Latitude: " + Utils.getLastKnownLatitude());
            Log.d(TAG, "Longitude: " + Utils.getLastKnownLongitude());
        } else {
            Log.d(TAG, "Location is null");
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Google Play Services suspended!");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Failed to connect: Google Play Services!");
    }
}
