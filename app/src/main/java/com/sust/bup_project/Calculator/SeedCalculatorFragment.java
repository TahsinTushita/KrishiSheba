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
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;


public class SeedCalculatorFragment extends Fragment {

    private SearchableSpinner unitSpinner;
    private SearchableSpinner cropSpinner;
    private Button calculateBtn;
    private EditText landSizeText;
    private TextView resultTextView;

    private static Fragment fragment;

    public static Fragment getFragment() {
        if (fragment == null) return fragment = new SeedCalculatorFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seed_calculator, container, false);
        unitSpinner = view.findViewById(R.id.unitSpinner);
        cropSpinner = view.findViewById(R.id.cropSpinner);
        calculateBtn = view.findViewById(R.id.calcubtn);
        landSizeText = view.findViewById(R.id.landSizeText);
        resultTextView = view.findViewById(R.id.resultText);

        calculateBtn.setOnClickListener(calculateClickListener);
        return view;
    }

    View.OnClickListener calculateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            double result = Double.valueOf(landSizeText.getText().toString());
            String unit = unitSpinner.getSelectedItem().toString();
            String crop = cropSpinner.getSelectedItem().toString();
            Converter converter = new Converter(result,unit);

            Double tempResult = 0.0;
            switch (crop) {
                case "potato" :
                    tempResult = converter.getConversion() * SeedPerArea.POTATO.getSeedsInGrams();
                    break;
                case "tomato" :
                    tempResult = converter.getConversion() * SeedPerArea.TOMATO.getSeedsInGrams();
                    break;
                case "rice" :
                    tempResult = converter.getConversion() * SeedPerArea.RICE.getSeedsInGrams();
                    break;
                case "corn" :
                    tempResult = converter.getConversion() * SeedPerArea.CORN.getSeedsInGrams();
                    break;

            }
            resultTextView.setText("You need " + min(tempResult.shortValue(),1) + " grams of " + crop + " seeds");
        }
    };

    private String min(short shortValue, int i) {
        if (shortValue < i) {
            return String.valueOf(i);
        }
        return String.valueOf(shortValue);
    }

}