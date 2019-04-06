package com.sust.bup_project.loan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.sust.bup_project.R;

public class ShowPost extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        webView = findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/primebank" + ".html");
    }
}
