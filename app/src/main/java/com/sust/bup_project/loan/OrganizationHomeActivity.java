package com.sust.bup_project.loan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sust.bup_project.R;
import com.sust.bup_project.disease_detector.MainActivity;

import java.util.ArrayList;

public class OrganizationHomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    private Fragment fragment;
    private DrawerLayout drawer;
    ArrayList<OrganizationOffers> organizationOffersArrayList;
    RecyclerView managePostRecyclerView;
    OrganizationOffersAdapter organizationOffersAdapter;
    DatabaseReference postDatabase;
    FloatingActionButton postbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_home);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        setNavigationMenuListener(navigationView);

        managePostRecyclerView = findViewById(R.id.managePostRecyclerView);
        postbtn=findViewById(R.id.fab);
        organizationOffersArrayList = new ArrayList<>();
        organizationOffersAdapter = new OrganizationOffersAdapter(organizationOffersArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        managePostRecyclerView.setLayoutManager(layoutManager);
        managePostRecyclerView.setAdapter(organizationOffersAdapter);

        postDatabase = FirebaseDatabase.getInstance().getReference("user/organization");
        postDatabase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.getKey().equals("loan")) {
                        OrganizationOffers offer = snapshot.getValue(OrganizationOffers.class);
                        organizationOffersArrayList.add(offer);
                    }
                }

                organizationOffersAdapter.notifyDataSetChanged();
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

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrganizationHomeActivity.this,PostOffers.class);
                startActivity(intent);
            }
        });

    }

    private void setNavigationMenuListener(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawer.closeDrawers();
                switch (menuItem.getItemId()) {

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
        startActivity(new Intent(view.getContext(), MainActivity.class));
    }
}
