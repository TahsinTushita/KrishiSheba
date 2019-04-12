package com.sust.iuttechfest.community;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sust.iuttechfest.R;

public class CreatePostActivity extends AppCompatActivity {

    private EditText titleEditText,postEditText;
    private Button createPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        titleEditText = findViewById(R.id.title);
        postEditText = findViewById(R.id.post);
        createPostButton = findViewById(R.id.createPostBtn);
        createPostButton.setOnClickListener(createPostBtnListener);
    }

    View.OnClickListener createPostBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String title = titleEditText.getText().toString();
            String post = postEditText.getText().toString();
            DatabaseReference dref  = FirebaseDatabase.getInstance().getReference("Community/Posts").push();
            dref.setValue(new Post(User.getUserName(),title,post,null)).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    onBackPressed();
                }
            });
        }
    };
}
