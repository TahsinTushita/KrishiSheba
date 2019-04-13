package com.sust.iuttechfest.map;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.HeterogeneousExpandableList;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.sust.iuttechfest.R;

import java.io.IOException;
import java.util.ArrayList;

public class HeatMapActivity extends BaseDemoActivity {

    /**
     * Alternative radius for convolution
     */
    private static final int ALT_HEATMAP_RADIUS = 10;

    /**
     * Alternative opacity of heatmap overlay
     */
    private static final double ALT_HEATMAP_OPACITY = 0.4;

    /**
     * Alternative heatmap gradient (blue -> red)
     * Copied from Javascript version
     */
    private static final int[] ALT_HEATMAP_GRADIENT_COLORS = {
            Color.argb(0, 0, 255, 255),// transparent
            Color.argb(255 / 3 * 2, 0, 255, 255),
            Color.rgb(0, 191, 255),
            Color.rgb(0, 0, 127),
            Color.rgb(255, 0, 0)
    };

    public static final float[] ALT_HEATMAP_GRADIENT_START_POINTS = {
            0.0f, 0.10f, 0.20f, 0.60f, 1.0f
    };

    public static final Gradient ALT_HEATMAP_GRADIENT = new Gradient(ALT_HEATMAP_GRADIENT_COLORS,
            ALT_HEATMAP_GRADIENT_START_POINTS);

    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;
    private EditText cropsSearchText;
    private EditText placesSearchText;
    private String placesSearchString;
    private String cropsSearchString;
    private DatabaseReference placesDatabase;
    private Query placesDatabaseQuery;
    private ArrayList<Places> placesArrayList;
    private DatabaseReference cropsDatabase;
    private Query cropsDatabaseQuery;
    private Geocoder geocoder;
    private ArrayList<Crops> cropsArrayList;
    private ArrayList<Address> addresses;
    private Marker marker;
    private ArrayList<LatLng> cropsLatlngList;
    private Address address;
    private LocationManager locationManager;
    private LocationListener locationListener;
    RecyclerView recyclerView;
    PlacesAdapter adapter;
    private ImageView mGps;

    private boolean mDefaultGradient = true;
    private boolean mDefaultRadius = true;
    private boolean mDefaultOpacity = true;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_heat_map;
    }

    @Override
    protected void startDemo() {
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(20.8103,90.4125), 6));
        placesArrayList = new ArrayList<>();
//25 143
        cropsSearchText = findViewById(R.id.cropsHeatmapSearchtext);
        placesSearchText = findViewById(R.id.placesHeatmapSearchtext);
        recyclerView = findViewById(R.id.recyclerviewID);

        mGps = (ImageView) findViewById(R.id.gpsid);
        cropsSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {

                    recyclerView.setVisibility(View.GONE);
                    searchCrops();
                    hideSoftKeyboard(HeatMapActivity.this);
                }
                return false;
            }
        });

        placesSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {

                    geolocate();
                    searchPlaces();
                    hideSoftKeyboard(HeatMapActivity.this);
                }

                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getDeviceLocation();
                getCurrentLocation();
                placesArrayList.clear();
                recyclerView.setVisibility(View.GONE);
            }
        });

        getMap().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            Drawable drawable;
            String title;
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.showInfoWindow();

                if(placesArrayList.size()>0)
                    recyclerView.setVisibility(View.VISIBLE);
                else
                    recyclerView.setVisibility(View.GONE);

                return false;
            }
        });

    }


    public void searchCrops(){
        recyclerView.setVisibility(View.GONE);
        cropsSearchString = cropsSearchText.getText().toString().toLowerCase();
        cropsArrayList = new ArrayList<>();
        addresses = new ArrayList<>();
        cropsLatlngList = new ArrayList<>();
        getMap().clear();

        cropsDatabase = FirebaseDatabase.getInstance().getReference("Crops");
        cropsDatabaseQuery = cropsDatabase.orderByKey().equalTo(cropsSearchString);
        cropsDatabaseQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                cropsArrayList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Crops crops = snapshot.getValue(Crops.class);
                    cropsArrayList.add(crops);
                }

                try {
                    geocoder = new Geocoder(HeatMapActivity.this);
                    addresses.clear();
                    cropsLatlngList.clear();
                    String address;
                    for (int i = 0; i < cropsArrayList.size(); i++) {
                        address = cropsArrayList.get(i).getPlace();
                        addresses = (ArrayList<Address>) geocoder.getFromLocationName(address, 1);
                        for(Address add:addresses){
                            double longitude = add.getLongitude();
                            double latitude = add.getLatitude();
                            cropsLatlngList.add(new LatLng(latitude,longitude));
                        }
                    }

                    mProvider = new HeatmapTileProvider.Builder().data(cropsLatlngList).build();
                    mProvider.setGradient(ALT_HEATMAP_GRADIENT);
                    mOverlay = getMap().addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        moveCam(new LatLng(20.8103,90.4125),5f);
    }

    public void searchPlaces(){
        placesSearchString = placesSearchText.getText().toString().toLowerCase();
        placesArrayList = new ArrayList<>();
        addresses = new ArrayList<>();
        adapter = new PlacesAdapter(HeatMapActivity.this,placesArrayList);

        placesDatabase = FirebaseDatabase.getInstance().getReference("Places");
        placesDatabaseQuery = placesDatabase.orderByKey().equalTo(placesSearchString);
        placesDatabaseQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                placesArrayList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Places places = snapshot.getValue(Places.class);
                    placesArrayList.add(places);
                }
                if(placesArrayList.size()>0){
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HeatMapActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    //recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void geolocate(){
        getMap().clear();
        placesSearchString = placesSearchText.getText().toString().toLowerCase();
        geocoder = new Geocoder(HeatMapActivity.this);
        addresses = new ArrayList<>();
        try {
            getMap().clear();
            addresses = (ArrayList<Address>) geocoder.getFromLocationName(placesSearchString, 1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size() > 0) {
            address = addresses.get(0);
            //Toast.makeText(MapActivity.this, address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), 15f ,address.getAddressLine(0));

        }
    }

    public void moveCamera(LatLng latLng,float zoom,String title){
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //mMap.setMyLocationEnabled(true);
        //mMap.getUiSettings().setMyLocationButtonEnabled(false);

        if (!title.equals("my location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            marker = getMap().addMarker(options);
        }
        hideSoftKeyboard(HeatMapActivity.this);
    }

    public void moveCam(LatLng latLng,float zoom){
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        hideSoftKeyboard(HeatMapActivity.this);
    }

    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    double latitude;
    double longitude;

    private MarkerOptions userMarker;
    private Marker myMarker;

    private void drawNearby() {

        ArrayList<Address> addresses = new ArrayList<Address>();
        String address = null;


    }

    private void getCurrentLocation(){

        if (ActivityCompat.checkSelfPermission
                (this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission
                        (this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {


        }
        else {

            // enable location buttons

            // fetch last location if any from provider - GPS.
            final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            final Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //if last known location is not available
            if (loc == null) {

                final LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(final Location location) {

                        // getting location of user
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        //do something with Lat and Lng
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        //when user enables the GPS setting, this method is triggered.
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                        //when no provider is available in this case GPS provider, trigger your gpsDialog here.
                    }
                };

                //update location every 10sec in 500m radius with both provider GPS and Network.

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10*1000, 500, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 500, locationListener);
            }
            else {
                //do something with last known location.
                // getting location of user
                latitude = loc.getLatitude();
                longitude = loc.getLongitude();
            }
        }

        if(userMarker == null) {
            //mMap.addCircle(new CircleOptions().center(new LatLng(latitude,longitude)).radius(10000).strokeWidth(0f).fillColor(0xE6FFBEBE));

            userMarker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Current Location");
            myMarker = getMap().addMarker(userMarker);
            myMarker.showInfoWindow();
            //hs.put(myMarker,LoginActivity.user);
        }
        else {
            myMarker.remove();

            userMarker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Current Location");
            getMap().clear();
            //mMap.addCircle(new CircleOptions().center(new LatLng(latitude,longitude)).radius(10000).strokeWidth(0f).fillColor(0xE6FFBEBE));

            myMarker = getMap().addMarker(userMarker);
            myMarker.showInfoWindow();

            //hs.put(myMarker,LoginActivity.user);
        }
        //Toast.makeText(this,"lat:"+latitude+" long:"+longitude,Toast.LENGTH_SHORT).show();
        moveCamera(new LatLng(latitude, longitude),15f
                , "my location");

        drawNearby();
    }

}
