package com.sust.iuttechfest.seeds;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sust.iuttechfest.R;

import java.util.List;

public class SeedAdapter extends RecyclerView.Adapter<SeedAdapter.SeedViewHolder> {

    private Context context;
    private List<Seed> seedList;
    public int selectedItem = -1;

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

    class SeedViewHolder extends RecyclerView.ViewHolder{

        private RadioButton selectBtn;
        private TextView seedNameTextView;
        private ImageView imageView;

        public SeedViewHolder(@NonNull View itemView) {
            super(itemView);
            seedNameTextView = itemView.findViewById(R.id.seed_name_text);
            selectBtn = itemView.findViewById(R.id.select_seed_radiobtn);
            imageView = itemView.findViewById(R.id.seed_image_view);

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = getAdapterPosition();
                }
            };

            itemView.setOnClickListener(clickListener);
            selectBtn.setOnClickListener(clickListener);
        }
    }
}
