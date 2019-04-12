package com.sust.bup_project.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sust.bup_project.R;

public class CommunityFragment extends Fragment {

    private EditText userNameEditText, passWordEditText;
    private Button loginBtn,signUpBtn;
    private FirebaseAuth firebaseAuth;

    public CommunityFragment() {
    }

    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        findViews(view);
        if(firebaseAuth.getCurrentUser() == null) {
            loginBtn.setOnClickListener(loginBtnListener);
            signUpBtn.setOnClickListener(signUpListener);
        } else  {
            String user = firebaseAuth.getCurrentUser().getEmail();
            user = user.substring(0, user.lastIndexOf('@')).trim();
            User.setUserName(user);
            startActivity(new Intent(getContext(),CommunityActivity.class));
        }
            return view;
    }

    private void findViews(View view) {
        loginBtn = view.findViewById(R.id.loginBtn);
        userNameEditText = view.findViewById(R.id.userNameEditText);
        passWordEditText = view.findViewById(R.id.passWordEditText);
        signUpBtn = view.findViewById(R.id.RegBtn);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Fragment fragment = new SignUpFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment,fragment).commit();
        }
    };

    View.OnClickListener loginBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            final String username = userNameEditText.getText().toString().trim();
            String pwd = passWordEditText.getText().toString();

            //01 This part is to hide softkeyboard
            View nview = getActivity().getCurrentFocus();
            if (nview != null) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(nview.getWindowToken(), 0);
            }
            //01 part ends here

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
                Snackbar.make(view, R.string.invalid_login_msg, Snackbar.LENGTH_SHORT)
                        .show();
            } else {
                firebaseAuth.signInWithEmailAndPassword(username + "@sust.com", pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String user = firebaseAuth.getCurrentUser().getEmail();
                            user = user.substring(0, user.lastIndexOf('@')).trim();
                            Snackbar.make(getView(), R.string.successful_login, Snackbar.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), CommunityActivity.class));
                        } else {
                            Snackbar.make(getView(), R.string.unsuccessful_login, Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    };
}
