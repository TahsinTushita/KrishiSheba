package com.sust.bup_project.loan;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sust.bup_project.R;

public class SignUpActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    private EditText userName,passWord,address,TIN;
    private Button signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();

        userName = findViewById(R.id.user_name);
        passWord = findViewById(R.id.password_text);
        address = findViewById(R.id.address);
        TIN = findViewById(R.id.tin_text);
        signUpBtn = findViewById(R.id.signup_btn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = userName.getText().toString(),
                        password = passWord.getText().toString(),
                        addressSt = address.getText().toString(),
                        tin = TIN.getText().toString();
                        firebaseAuth.createUserWithEmailAndPassword(username, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                                } else {


                                    DatabaseReference profileDatabase;
                                    profileDatabase = FirebaseDatabase.getInstance().getReference("user/personal/"+FirebaseAuth.getInstance().getUid()+"/info");
                                    profileDatabase.child("username").setValue(username);
                                    profileDatabase.child("address").setValue(addressSt);
                                    profileDatabase.child("tin").setValue(tin);

                                    startActivity(new Intent(SignUpActivity.this, PersonalAccountActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

}
