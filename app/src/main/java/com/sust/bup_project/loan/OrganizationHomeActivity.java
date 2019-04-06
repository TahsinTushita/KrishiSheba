package com.sust.bup_project.loan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sust.bup_project.R;

public class OrganizationHomeActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
