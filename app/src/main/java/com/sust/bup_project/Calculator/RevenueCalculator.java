package com.sust.bup_project.Calculator;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sust.bup_project.R;
import com.sust.bup_project.disease_detector.FragmentDiseaseDetector;

public class RevenueCalculator extends Fragment {


    private static Fragment fragment;

    public static Fragment getFragment() {
        if(fragment == null) return fragment = new RevenueCalculator();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revenue_calculator, container, false);
        return view;
    }

}
