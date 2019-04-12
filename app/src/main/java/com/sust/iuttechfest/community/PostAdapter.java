package com.sust.iuttechfest.community;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.sust.iuttechfest.R;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {
    private PostItemListener postItemListener;
    private Context context;
    private ArrayList<Post> postArrayList;

    public PostAdapter(PostItemListener postItemListener, Context context, ArrayList<Post> postArrayList) {
        this.postItemListener = postItemListener;
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item_row,viewGroup,false);
        return new PostViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        Post post = postArrayList.get(i);
        postViewHolder.setDetails(post);
        postViewHolder.bind(postItemListener,post);
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }
}
