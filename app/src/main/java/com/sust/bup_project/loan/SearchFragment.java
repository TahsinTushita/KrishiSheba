package com.sust.bup_project.loan;


import android.app.DownloadManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.sust.bup_project.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

    private EditText maxAmountEditText,maxDurationEditText;
    private Button searchBtn;

    private static Fragment fragment;

    public static Fragment getFragment() {
        if(fragment == null) fragment = new SearchFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        maxAmountEditText = view.findViewById(R.id.loanAmount);
        maxDurationEditText = view.findViewById(R.id.loanDuration);
        searchBtn = view.findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maxAmount = maxAmountEditText.getText().toString();
                String maxDuration = maxDurationEditText.getText().toString();
                searchLoans(maxAmount,maxDuration);
            }
        });
        return view;
    }

    private void searchLoans(String maxAmount, String maxDuration) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        fragment.setItems(maxAmount,maxDuration);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainFragment,fragment).commit();
    }

}
