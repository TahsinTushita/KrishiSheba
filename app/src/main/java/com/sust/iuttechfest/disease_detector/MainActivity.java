package com.sust.iuttechfest.disease_detector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.sust.iuttechfest.Calculator.RevenueCalculator;
import com.sust.iuttechfest.Calculator.SeedCalculatorFragment;
import com.sust.iuttechfest.R;
import com.sust.iuttechfest.community.CommunityFragment;
import com.sust.iuttechfest.map.HeatMapActivity;
import com.sust.iuttechfest.map.MapActivity;

import java.util.List;

import tensorflow.Classifier;

public class MainActivity extends AppCompatActivity implements FragmentDiseaseDetector.DiseaseDetectorListeners, DiseaseDetector.DiseaseDetectorListener {

    private Fragment fragment;
    private DrawerLayout drawer;
    public void requestPermissions() {
        ActivityCompat.requestPermissions(this,permissions,1234);
    }

    private final String[] permissions = {
                 "android.permission.ACCESS_NETWORK_STATE"
                ,"android.permission.CAMERA"
                ,"android.hardware.camera"
                ,"android.hardware.camera.autofocus"
                ,"android.permission.WRITE_EXTERNAL_STORAGE"
                ,"android.permission.READ_EXTERNAL_STORAGE"
                ,"android.permission.ACCESS_COARSE_LOCATION"
                ,"android.permission.ACCESS_FINE_LOCATION"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        requestPermissions();
        FirebaseApp.initializeApp(this);
        fragment = new FragmentDiseaseDetector();
        getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment,fragment).commit();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawer.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.nav_community:
                        if(!(fragment instanceof CommunityFragment)) {
                            fragment = new CommunityFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment, fragment).commit();
                        }
                        break;
                    case R.id.nav_detect:
                        if(!(fragment instanceof FragmentDiseaseDetector)) {
                            fragment = FragmentDiseaseDetector.getFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment, fragment).commit();
                        }
                        break;
                    case R.id.nav_approx_production:
                        if(!(fragment instanceof RevenueCalculator)) {
                            fragment = RevenueCalculator.getFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment, fragment).commit();
                        }
                        break;

                    case R.id.seed_calculator:
                        if(!(fragment instanceof SeedCalculatorFragment)) {
                            fragment = SeedCalculatorFragment.getFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment, fragment).commit();
                        }
                        break;

                    case R.id.nav_map:
                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.heatmap:
                        Intent intent1 = new Intent(MainActivity.this, HeatMapActivity.class);
                        startActivity(intent1);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void detect(final Bitmap bitmap,View view) {
        DiseaseDetector detector = new DiseaseDetector(this,bitmap);
        detector.run();
    }

    private void setStringArgs(Fragment fragment, String diseaseName) {
        Bundle args = new Bundle();
        args.putCharSequence(Conventions.getDiseaseNameKey(),diseaseName);
        fragment.setArguments(args);
    }

    @Override
    public void onThreadComplete(List<Classifier.Recognition> results) {
        for (final Classifier.Recognition result : results) {
//                Toast.makeText(this, result.getTitle(), Toast.LENGTH_LONG).show();
        }
        String diseaseName = results.get(0).getTitle();
//        Snackbar.make(view, diseaseName, Snackbar.LENGTH_LONG).show();
        fragment = new FragmentDiseaseInfo();
        setStringArgs(fragment,diseaseName);
        getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment,fragment).addToBackStack(null).commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
            }
        return super.onOptionsItemSelected(item);
    }

}
