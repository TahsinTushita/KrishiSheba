package com.sust.bup_project.disease_detector;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sust.bup_project.R;

import java.util.ArrayList;

import com.sust.bup_project.disease.Disease;
import com.sust.bup_project.disease.DiseaseAdapter;

public class  FragmentDiseaseInfo extends Fragment {

    private String diseaseName;
    private TextView diseaseNameTextView;
    private WebView descriptionWebView;
    private RecyclerView photoDisplayRecyclerView;
    private DatabaseReference databaseReference;

    private ArrayList<Disease> diseases;

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        diseaseName = (String) args.getCharSequence(Conventions.getDiseaseNameKey());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.disease_info_fragment,container,false);
        diseaseNameTextView = view.findViewById(R.id.disease_name_text_view);
        descriptionWebView = view.findViewById(R.id.description_web_view);
        photoDisplayRecyclerView = view.findViewById(R.id.photo_display_recycler_view);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        photoDisplayRecyclerView.setLayoutManager(layoutManager);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        setDetails(view);

        databaseReference.child("disease/" +diseaseName)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            Disease disease = dataSnapshot.getValue(Disease.class);
                            diseases.add(disease);
                            photoDisplayRecyclerView.setAdapter(adapter);
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
        return view;
    }
    private DiseaseAdapter adapter;
    private void setDetails(View view) {
        diseaseNameTextView.setText(diseaseName);
        try {
            descriptionWebView.loadUrl("file:///android_asset/" + diseaseName + ".html");
        } catch (Exception e) {
            Snackbar.make(view,"URL can not be loaded",Snackbar.LENGTH_LONG).show();
        }
        diseases = new ArrayList<>();
        adapter = new DiseaseAdapter(getContext(),diseases);
    }
}
