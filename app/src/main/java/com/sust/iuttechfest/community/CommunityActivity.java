package com.sust.iuttechfest.community;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sust.iuttechfest.R;

import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity {

    private RecyclerView postRecyclerView;
    private ArrayList<Post> postArrayList;
    private PostAdapter postAdapter;

    PostItemListener postListener = new PostItemListener() {
        @Override
        public void OnPostClick(Post post) {
            Intent intent = new Intent(CommunityActivity.this,InsidePostActivity.class);
            intent.putExtra("post",post);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        postRecyclerView = findViewById(R.id.postRecyclerView);
        postArrayList = new ArrayList<>();
        postAdapter = new PostAdapter(postListener,this,postArrayList);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postRecyclerView.setAdapter(postAdapter);
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Community/Posts");
        database.addChildEventListener(postChildEventListener);

    }

    ChildEventListener postChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Post post = dataSnapshot.getValue(Post.class);
                    post.setKey(dataSnapshot.getKey());
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

    public void onLogOutClick(View view) {
        FirebaseAuth.getInstance().signOut();
        onBackPressed();
    }

    public void onCreatePostClick(View view) {
        startActivity(new Intent(CommunityActivity.this,CreatePostActivity.class));
    }
}
