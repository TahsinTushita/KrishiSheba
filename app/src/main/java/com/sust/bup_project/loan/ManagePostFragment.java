package com.sust.bup_project.loan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sust.bup_project.R;

import java.util.ArrayList;


public class ManagePostFragment extends Fragment {

    ArrayList<OrganizationOffers> organizationOffersArrayList;
    RecyclerView managePostRecyclerView;
    OrganizationOffersAdapter organizationOffersAdapter;

    public ManagePostFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_post, container, false);

    }


}