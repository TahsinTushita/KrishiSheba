package com.sust.bup_project.loan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sust.bup_project.R;

import java.util.ArrayList;


public class ManagePostFragment extends Fragment {

    RecyclerView managePostRecyclerView;
    OrganizationOffersAdapter organizationOffersAdapter;
    DatabaseReference postDatabase;
    ArrayList<OrganizationOffers> organizationOffersArrayList;

    public ManagePostFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_post, container, false);

        managePostRecyclerView = view.findViewById(R.id.managePostRecyclerView);
        organizationOffersArrayList = new ArrayList<>();
        organizationOffersAdapter = new OrganizationOffersAdapter(organizationOffersArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        managePostRecyclerView.setLayoutManager(layoutManager);
        managePostRecyclerView.setAdapter(organizationOffersAdapter);

        postDatabase = FirebaseDatabase.getInstance().getReference("user/organization");
        postDatabase.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                organizationOffersArrayList.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    OrganizationOffers organizationOffers = snapshot.getValue(OrganizationOffers.class);
                    organizationOffersArrayList.add(organizationOffers);
                    Toast.makeText(getContext(),snapshot.toString(),Toast.LENGTH_SHORT).show();
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

        return view;
    }


}
