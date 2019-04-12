package com.sust.bup_project.community;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.sust.bup_project.R;

import java.util.ArrayList;

public class InsidePostActivity extends AppCompatActivity {
    private RecyclerView postRecyclerView;
    private ArrayList<Post> postArrayList;
    private PostAdapter postAdapter;
    private Post post;
    private TextView titleTextView,userNameTextView,descriptionTextView;
    private EditText discussionPostEditText;

    PostItemListener postListener = new PostItemListener() {
        @Override
        public void OnPostClick(Post post) {
            Toast.makeText(InsidePostActivity.this,"Clicked",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_post);
        post = (Post) getIntent().getSerializableExtra("post");
        postRecyclerView = findViewById(R.id.postRecyclerView);
        postArrayList = new ArrayList<>();
        postAdapter = new PostAdapter(postListener,this,postArrayList);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postRecyclerView.setAdapter(postAdapter);
        setDetails();
        Query query = FirebaseDatabase.getInstance().getReference("Community/Discussion").orderByChild("key").equalTo(post.getKey());
        query.addChildEventListener(postChildEventListener);

    }

    private void setDetails() {
        discussionPostEditText = findViewById(R.id.discussionPostEditText);
        userNameTextView = findViewById(R.id.usernameTextView);
        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        userNameTextView.setText(post.getUsername());
        titleTextView .setText(post.getTitle());
        descriptionTextView .setText(post.getDescription());
    }

    ChildEventListener postChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Post post = dataSnapshot.getValue(Post.class);
            postArrayList.add(post);
            postAdapter.notifyDataSetChanged();
            postRecyclerView.setAdapter(postAdapter);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void onCreatePostClick(View view) {
        Post newPost = new Post();
        newPost.setTitle(post.getTitle());
        newPost.setKey(post.getKey());
        newPost.setDescription(discussionPostEditText.getText().toString());
        newPost.setUsername(User.getUserName());
        Task<Void> database = FirebaseDatabase.getInstance().getReference("Community/Discussion").push().setValue(newPost);
        if (database.isSuccessful()) {
            Toast.makeText(InsidePostActivity.this,"Post Successful",Toast.LENGTH_LONG).show();
        }
    }
}
