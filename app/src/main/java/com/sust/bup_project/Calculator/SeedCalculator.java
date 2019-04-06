package com.sust.bup_project.Calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sust.bup_project.R;

public class SeedCalculator extends AppCompatActivity {

    Spinner cropsSpinner,unitSpinner;
    EditText unitEdittext;
    TextView resultText;
    Button calcubtn,confirmbtn;
    ArrayAdapter<CharSequence> unitAdapter,cropsAdapter;
    //Double size,result;
    double size,result;
    String s,resultString;
    double d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seed_calculator);

        unitAdapter = ArrayAdapter.createFromResource(this,R.array.unit_list,android.R.layout.simple_spinner_dropdown_item);
        cropsAdapter = ArrayAdapter.createFromResource(this,R.array.crops_list,android.R.layout.simple_spinner_dropdown_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cropsSpinner = findViewById(R.id.cropSpinner);
        unitSpinner = findViewById(R.id.unitSpinner);
        unitEdittext = findViewById(R.id.landSizeText);
        resultText = findViewById(R.id.resultText);
        confirmbtn = findViewById(R.id.confirmbtn);
        calcubtn = findViewById(R.id.calcubtn);
        unitSpinner.setAdapter(unitAdapter);
        cropsSpinner.setAdapter(cropsAdapter);

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getLandSize();
                Toast.makeText(SeedCalculator.this,Double.toString(size),Toast.LENGTH_SHORT).show();

            }
        });

        unitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    size = size;
                    Toast.makeText(SeedCalculator.this,Double.toString(size),Toast.LENGTH_SHORT).show();
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

    }

    public void getLandSize(){
        s = unitEdittext.getText().toString().trim();
        size = Double.parseDouble(s);

    }

    public String getResult(){
        resultString = Double.toString(result);
        return resultString;
    }
}
