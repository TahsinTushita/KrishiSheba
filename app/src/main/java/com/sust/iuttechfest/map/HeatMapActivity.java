package com.sust.iuttechfest.map;

import android.app.Activity;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
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
    private String cropsSearchString;
    private DatabaseReference cropsDatabase;
    private Query cropsDatabaseQuery;
    private Geocoder geocoder;
    private ArrayList<Crops> cropsArrayList;
    private ArrayList<Address> addresses;
    private ArrayList<LatLng> cropsLatlngList;

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

//25 143
        cropsSearchText = findViewById(R.id.cropsHeatmapSearchtext);
        cropsSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {

                    searchPlaces();
                    hideSoftKeyboard(HeatMapActivity.this);
                }
                return false;
            }
        });
    }

    public void searchPlaces(){
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

}
