package com.sust.bup_project.community;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

        }
    };

}
