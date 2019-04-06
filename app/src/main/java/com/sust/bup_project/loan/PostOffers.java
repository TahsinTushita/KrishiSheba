package com.sust.bup_project.loan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sust.bup_project.R;

public class PostOffers extends AppCompatActivity {

    Button postbtn;

    EditText name,criteria,amount,duration,installments,intrate,vat,mortgage,others,tax;
    String names,criterias,amounts,durations,installmentss,intrates,vats,mortgages,otherss,taxs;
    int installmentsi,durationsi,amountsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_offers);
        postbtn=findViewById(R.id.post);

        name=findViewById(R.id.nameid);
        criteria=findViewById(R.id.criteriaid);
        amount=findViewById(R.id.amountid);
        duration=findViewById(R.id.durationid);
        installments=findViewById(R.id.installmentid);
        intrate=findViewById(R.id.intrateid);
        vat=findViewById(R.id.vatid);
        mortgage=findViewById(R.id.mortgageid);
        others=findViewById(R.id.othersid);
        tax=findViewById(R.id.taxid);

        postbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                names=name.getText().toString();
                criterias=criteria.getText().toString();
                amounts=amount.getText().toString();
                amountsi=Integer.valueOf(amounts);
                durations=duration.getText().toString();
                durationsi=Integer.valueOf(durations);
                installmentss=installments.getText().toString();
                installmentsi=Integer.valueOf(installmentss);
                intrates=intrate.getText().toString();
                vats=vat.getText().toString();
                mortgages=mortgage.getText().toString();
                otherss=others.getText().toString();
                taxs=tax.getText().toString();
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("user/organization/"+ FirebaseAuth.getInstance().getUid()).child("loan").push();
                reference.child("loanname").setValue(names);
                reference.child("criteria").setValue(criterias);
                reference.child("maxamount").setValue(amountsi);
                reference.child("duration").setValue(durationsi);
                reference.child("intallments").setValue(installmentsi);
                reference.child("intrate").setValue(intrates);
                reference.child("vat").setValue(vats);
                reference.child("mortgage").setValue(mortgages);
                reference.child("others").setValue(otherss);
                reference.child("tax").setValue(taxs);
            }
        });
    }
}
