package com.sust.bup_project.community;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sust.bup_project.R;

public class SignUpFragment extends Fragment {

    private EditText userNameEditText,fullNameEditText,passWordEditText,
    addressEditText,phoneNoEditText,emailEditText;
    private Button signUpBtn;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        findViews(view);
        signUpBtn.setOnClickListener(signUpClickListener);
        return view;
    }

    private void findViews(View view) {
        userNameEditText = view.findViewById(R.id.userName);
        passWordEditText = view.findViewById(R.id.passWordEditText);
        fullNameEditText=view.findViewById(R.id.FullNameEditText);
        phoneNoEditText = view.findViewById(R.id.phoneNoEditText);
        addressEditText = view.findViewById(R.id.addressEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        signUpBtn = view.findViewById(R.id.signUpBtn);
    }

    View.OnClickListener signUpClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String username,password,email,address,phoneNo,fullName;

            username = userNameEditText.getText().toString();
            password = passWordEditText.getText().toString();
            email = emailEditText.getText().toString();
            phoneNo = phoneNoEditText.getText().toString();
            address = addressEditText.getText().toString();
            fullName = fullNameEditText.getText().toString();

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(username+"@sust.com",password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        DatabaseReference dref = FirebaseDatabase.getInstance().getReference("User/"+username);
                        dref.setValue(new UserInfo(username,fullName,address,phoneNo,email));
                        User.setUserName(username);
                        startActivity(new Intent(getContext(),CommunityActivity.class));
                    } else {
                        Snackbar.make(getView(),"Failed",Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }
    };

}
