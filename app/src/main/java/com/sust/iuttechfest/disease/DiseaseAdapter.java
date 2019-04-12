package com.sust.iuttechfest.disease;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.sust.iuttechfest.R;

import java.util.ArrayList;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder> {
    private Context context;
    private ArrayList<Disease> diseases;

    public DiseaseAdapter(Context context, ArrayList<Disease> diseases) {
        this.context = context;
        this.diseases = diseases;
    }

    @NonNull
    @Override
    public DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.disease_photos,viewGroup,false);
        return new DiseaseViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseViewHolder diseaseViewHolder, int i) {
        Disease disease = diseases.get(i);
        diseaseViewHolder.setDetails(disease);
    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }

    public static class DiseaseViewHolder extends RecyclerView.ViewHolder {

        private ImageView diseaseSnapShot;

        public DiseaseViewHolder(@NonNull View itemView) {
            super(itemView);
            diseaseSnapShot = itemView.findViewById(R.id.diseaseSnapShot_image_view);
        }

        public void setDetails(Disease disease) {
            Picasso.get().load(disease.getUri()).fit().into(diseaseSnapShot);
        }
    }
}
