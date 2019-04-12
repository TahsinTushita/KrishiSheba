package com.sust.iuttechfest.Calculator;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationFetcher implements Runnable {
    interface LocationListener {
        void onThreadComplete(String currentLocation);
    }

    private LocationListener fragment;
    private Context context;

    public LocationFetcher(Fragment fragment,Context context) {
        this.fragment = (LocationListener) fragment;
        this.context = context;
    }

    private FusedLocationProviderClient fusedLocationProviderClient;

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getLocality()).append("\n");
                result.append(address.getCountryName());
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        return result.toString();
    }

    private String currentLocation;

    private void getLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {

        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>()

        {
            @Override
            public void onSuccess (Location location){
                if (location != null) {
                    currentLocation = getAddress(location.getLatitude(), location.getLongitude());
                    fragment.onThreadComplete(currentLocation);
                }
            }
        });
    }

    @Override
    public void run() {
            getLocation();
        }
}
