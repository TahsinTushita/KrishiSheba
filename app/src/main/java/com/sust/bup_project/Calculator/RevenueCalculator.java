package com.sust.bup_project.Calculator;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.sust.bup_project.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class RevenueCalculator extends Fragment implements LocationFetcher.LocationListener {
    private static Fragment fragment;

    public static Fragment getFragment() {
        if (fragment == null) return fragment = new RevenueCalculator();
        return fragment;
    }

    private Context context;
    private String currentLocation = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        LocationFetcher fetcher = new LocationFetcher(getFragment(),context);
        fetcher.run();
    }

    private TextView locationTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revenue_calculator, container, false);
        locationTextView = view.findViewById(R.id.location_text_view);
        return view;
    }



    @Override
    public void onThreadComplete(String currentLocation) {
        locationTextView.setText(currentLocation);
    }
}
