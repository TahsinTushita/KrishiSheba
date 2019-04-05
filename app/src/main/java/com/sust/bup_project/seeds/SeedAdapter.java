package com.sust.bup_project.seeds;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sust.bup_project.R;

import java.util.List;

public class SeedAdapter extends RecyclerView.Adapter<SeedAdapter.SeedViewHolder> {

    private Context context;
    private List<Seed> seedList;

    public SeedAdapter(Context context, List<Seed> seedList) {
        this.context = context;
        this.seedList = seedList;
    }

    @NonNull
    @Override
    public SeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.seed_recyclerview,viewGroup);
        return new SeedViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SeedViewHolder seedViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class SeedViewHolder extends RecyclerView.ViewHolder{

        public SeedViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
