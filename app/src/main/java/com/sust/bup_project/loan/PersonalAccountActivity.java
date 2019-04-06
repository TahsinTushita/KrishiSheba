package com.sust.bup_project.loan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.sust.bup_project.Calculator.RevenueCalculator;
import com.sust.bup_project.Calculator.SeedCalculatorFragment;
import com.sust.bup_project.R;
import com.sust.bup_project.disease_detector.FragmentDiseaseDetector;
import com.sust.bup_project.disease_detector.MainActivity;
import com.sust.bup_project.map.MapActivity;

public class PersonalAccountActivity extends AppCompatActivity {

    private Fragment fragment;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_account);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        setNavigationMenuListener(navigationView);

        fragment = new ShowLoanAdsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment,fragment).commit();

    }

    private void setNavigationMenuListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawer.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.nav_search:
                        if(!(fragment instanceof SearchFragment)) {
                            fragment = new SearchFragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment, fragment).commit();
                        }
                        break;
                }
                return true;
            }
        });
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

    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this,MainActivity.class));
    }
}
