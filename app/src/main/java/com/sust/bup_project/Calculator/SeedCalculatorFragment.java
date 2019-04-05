package com.sust.bup_project.Calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sust.bup_project.R;


public class SeedCalculatorFragment extends Fragment {

    Spinner cropsSpinner,unitSpinner;
    EditText unitEdittext;
    TextView resultText;
    Button calcubtn,confirmbtn;
    ArrayAdapter<CharSequence> unitAdapter,cropsAdapter;
    Double size,result;
    String s,resultString;


    private static Fragment fragment;

    public static Fragment getFragment() {
        if (fragment == null) return fragment = new SeedCalculatorFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        unitAdapter = ArrayAdapter.createFromResource(context,R.array.unit_list,android.R.layout.simple_spinner_dropdown_item);
        cropsAdapter = ArrayAdapter.createFromResource(context,R.array.crops_list,android.R.layout.simple_spinner_dropdown_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        confirmbtn = view.findViewById(R.id.confirmbtn);
        calcubtn = view.findViewById(R.id.calcubtn);
        unitSpinner.setAdapter(unitAdapter);
        cropsSpinner.setAdapter(cropsAdapter);

        size = new Double(0);
        result = new Double(0);

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLandSize();
            }
        });

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    size = size;
                    Toast.makeText(getContext(),size.toString(),Toast.LENGTH_SHORT).show();
                }
                else if(i==1){
                    size = size*(0.01652853);
                }
                else if(i==2){
                    size = size*(.33333333);
                }
                else if(i==3){
                    size = size*(0.01);
                }
                else if(i==4){
                    size = size*(2.47);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cropsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    result = size*728744.939271;
                }
                else if(i==1){
                    result = size*28.3495;
                }
                else if(i==2){
                    result = size*68038.9;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        calcubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultText.setText("The approximate amount of seed needed for production is "+resultString+" gm");
            }
        });




        return view;
    }

    public void getLandSize(){
        s = unitEdittext.getText().toString().trim();
        size = Double.parseDouble(s);
    }

    public String getResult(){
        resultString = result.toString();
        return resultString;
    }



}