package com.sust.bup_project.Calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.sust.bup_project.R;


public class SeedCalculatorFragment extends Fragment {

    Spinner cropsSpinner,unitSpinner;
    EditText unitEdittext;
    TextView resultText;
    Button calcubtn;
    ArrayAdapter<CharSequence> unitAdapter,cropsAdapter;

    private static Fragment fragment;

    public static Fragment getFragment() {
        if (fragment == null) return fragment = new SeedCalculatorFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.crops_list,android.R.layout.simple_spinner_dropdown_item);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_seed_calculator, container, false);

        cropsSpinner = view.findViewById(R.id.cropSpinner);
        unitSpinner = view.findViewById(R.id.unitSpinner);
        unitEdittext = view.findViewById(R.id.landSizeText);
        resultText = view.findViewById(R.id.resultText);
        calcubtn = view.findViewById(R.id.calcubtn);
        return view;
    }

}