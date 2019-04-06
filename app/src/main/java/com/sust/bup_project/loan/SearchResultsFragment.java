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

public class SearchResultsFragment extends Fragment {

    private String maxDuration,maxAmount;
    OrganizationOffersAdapter.OnItemClickListener listener;


    public void setItems(String maxAmount,String maxDuration) {
        this.maxAmount = maxAmount;
        this.maxDuration = maxDuration;
    }

    public SearchResultsFragment() {

    }

    private RecyclerView recyclerView;
    private OrganizationOffersAdapter adapter;
    private ArrayList<OrganizationOffers> offers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);
        recyclerView = view.findViewById(R.id.showLoanAds);
        offers = new ArrayList<>();

        adapter = new OrganizationOffersAdapter(offers);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user/organization");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.getKey().equals("loan")) {
                        for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                                OrganizationOffers offer = childSnapshot.getValue(OrganizationOffers.class);
                                Toast.makeText(getContext(), offer.getLoanname() + "" + maxAmount, Toast.LENGTH_LONG).show();
                                if (offer.getMaxamount() >= Integer.valueOf(maxAmount)) {
                                    offers.add(offer);
                                } else if (offer.getDuration() >= Integer.valueOf(maxDuration)) {
                                    offers.add(offer);
                                }
                            }
                    }
                }
                adapter.notifyDataSetChanged();
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
        return  view;
    }

}
