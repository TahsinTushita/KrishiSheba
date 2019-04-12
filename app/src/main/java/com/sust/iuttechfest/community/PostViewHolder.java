package com.sust.iuttechfest.community;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sust.iuttechfest.R;

public class PostViewHolder extends RecyclerView.ViewHolder {

    private TextView usernameTextView,titleTextView,descriptionTextView;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        usernameTextView = itemView.findViewById(R.id.userName);
        titleTextView = itemView.findViewById(R.id.postTitle);
        descriptionTextView = itemView.findViewById(R.id.post);
    }

    public void setDetails(Post post) {
        usernameTextView.setText(post.getUsername());
        titleTextView.setText(post.getTitle());
        descriptionTextView.setText(post.getDescription());
    }

    public void bind(final PostItemListener postItemListener, final Post post) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postItemListener.OnPostClick(post);
            }
        });
    }
}
