package com.sust.bup_project.loan;


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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sust.bup_project.R;
import com.sust.bup_project.disease_detector.DiseaseDetector;
import com.sust.bup_project.disease_detector.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoanFragment extends Fragment{


    public LoanFragment() {

    }

    private Context context;

    private static Fragment fragment;

    public static Fragment getFragment() {
        if(fragment == null) return fragment = new LoanFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private EditText userNameEditText,passwordEditText;
    private Button loginBtn,signUpBtn;
    private RadioGroup selectModeRadioGroup;


    private static String[] SELECT_BUTTON_TEXT = {"@user.com","@organization.com"};

    private int radioButtonState = 0;
    public int getRadioButtonState() {
        return radioButtonState;
    }

    public void setRadioButtonState(int radioButtonState) {
        this.radioButtonState = radioButtonState;
    }

    private void setUpRadioGroup() {
        selectModeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.personal) {
                    setRadioButtonState(0);
                }
                else if (checkedId == R.id.organization) {
                    setRadioButtonState(1);
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_loan, container, false);
        userNameEditText = v.findViewById(R.id.username_text);
        passwordEditText = v.findViewById(R.id.password_text);
        loginBtn = v.findViewById(R.id.login_btn_id);
        signUpBtn = v.findViewById(R.id.signup_btn);
        selectModeRadioGroup = v.findViewById(R.id.radioGroupUserType);

        addListenerToLoginOrSignUp();
        setUpRadioGroup();

        return v;
    }

    void addListenerToLoginOrSignUp() {
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null) {
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = userNameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();

                    View nview = getActivity().getCurrentFocus();
                    if (nview != null) {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(nview.getWindowToken(), 0);
                    }
                    //01 part ends here

                    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                        Snackbar.make(getView(), R.string.invalid_login_msg, Snackbar.LENGTH_SHORT)
                                .show();
                    } else {
                        firebaseAuth.signInWithEmailAndPassword(username + SELECT_BUTTON_TEXT[getRadioButtonState()], password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String user = firebaseAuth.getCurrentUser().getEmail();
                                    user = user.substring(0,user.lastIndexOf('@')).trim();
                                    Snackbar.make(getView(), R.string.successful_login, Snackbar.LENGTH_SHORT).show();
                                    if(getRadioButtonState() == 0)
                                    startActivity(new Intent(context, PersonalAccountActivity.class));
                                    else
                                    startActivity(new Intent(context,OrganizationHomeActivity.class));
                                } else {
                                    Snackbar.make(getView(), R.string.unsuccessful_login, Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }
            });
        }else {

            startActivity(new Intent(context, PersonalAccountActivity.class));
            getActivity().finish();
        }

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,SignUpActivity.class));
            }
        });
    }
}
