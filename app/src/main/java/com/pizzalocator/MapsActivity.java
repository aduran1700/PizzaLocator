package com.pizzalocator;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pizzalocator.base.BaseEventReportingActivity;
import com.pizzalocator.events.GetPizzaVenues;
import com.pizzalocator.events.PizzaVenuesResult;
import com.pizzalocator.models.Venue;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends BaseEventReportingActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    private GoogleApiClient mGoogleApiClient;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private HashMap<Marker, Venue> venueHashMap;
    private SupportMapFragment supportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        PLEventBusRegistry.getInstance().registerSubscriber(this);
        venueHashMap = new HashMap<>();

        if (checkPlayServices()) {
            buildGoogleApiClient();
        }
    }

    @Override
    protected void onDestroy() {
        PLEventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    /**
     * Sets up SupportMapFragment if not null
     */
    private void setUpMapIfNeeded() {
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            supportMapFragment.getMapAsync(this);
        }
    }


    /**
     * Adds venue markers to the map
     * @param event A PizzaVenueSResult event that
     *              holds a list of venues
     */
    public void onEvent(PizzaVenuesResult event) {
        ArrayList<Venue> venueArrayList = (ArrayList<Venue>)event.getVenueList();

        for(int i = 0; i < venueArrayList.size(); i++) {
            Venue venue = venueArrayList.get(i);
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(venue.getLatitude(), venue.getLongitude())).title(venue.getName())
                    .snippet((venue.getFormattedPhone().equals("") ? "" : (venue.getFormattedPhone() + "\n")) + venue.getAddress() + "\n" + venue.getCity() + ", " + venue.getState()));

            venueHashMap.put(marker, venue);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            mMap = googleMap;
            mMap.setOnInfoWindowClickListener(this);
            mMap.setInfoWindowAdapter(new InfoAdapter(getLayoutInflater()));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14));
            PLEventBus.postEvent(new GetPizzaVenues(latitude, longitude));


        } else {
            Toast.makeText(getApplicationContext(), "(Couldn't get the location. Make sure location is enabled on the device)", Toast.LENGTH_LONG).show();
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    @Override
    public void onConnected(Bundle arg0) {
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);
        setUpMapIfNeeded();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this,"Connection Failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Venue venue = venueHashMap.get(marker);

        Intent intent = new Intent(this, VenueDetailActivity.class);
        intent.putExtra("Venue", venue);
        startActivity(intent);
    }
}
