package com.sust.bup_project.loan;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.sust.bup_project.R;

import java.util.ArrayList;

public class OrganizationOffersAdapter extends RecyclerView.Adapter<OrganizationOffersAdapter.OrganizationOffersViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(OrganizationOffers offer);
    }

    ArrayList<OrganizationOffers> organizationOffersArrayList;
    Context context;
    public OnItemClickListener listener;

    public OrganizationOffersAdapter(OnItemClickListener listener,ArrayList<OrganizationOffers> organizationOffersArrayList,Context context){
        this.organizationOffersArrayList = organizationOffersArrayList;
        this.context = context;
        this.listener = listener;
    }
    public OrganizationOffersAdapter(ArrayList<OrganizationOffers> organizationOffersArrayList){
        this.organizationOffersArrayList = organizationOffersArrayList;
    }
    @NonNull
    @Override
    public OrganizationOffersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LinearLayout layout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_post_layout,viewGroup,false);
        return new OrganizationOffersAdapter.OrganizationOffersViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationOffersViewHolder organizationOffersViewHolder, int i) {
        OrganizationOffers organizationOffers = organizationOffersArrayList.get(i);
        organizationOffersViewHolder.setDetails(organizationOffers);
        organizationOffersViewHolder.bind(organizationOffers,listener);
    }

    @Override
    public int getItemCount() {
        return organizationOffersArrayList.size();
    }

    public class OrganizationOffersViewHolder extends RecyclerView.ViewHolder {

        TextView loanName,criteria,maxAmount,duration,installments,intRate,vat,mortgage,tax,others;

        public OrganizationOffersViewHolder(@NonNull View itemView) {
            super(itemView);
            loanName = itemView.findViewById(R.id.loanName);
            criteria = itemView.findViewById(R.id.loanCriteria);
            maxAmount = itemView.findViewById(R.id.maxLoanAmount);
            duration = itemView.findViewById(R.id.loanDuration);
            installments = itemView.findViewById(R.id.loanInstallment);
            intRate = itemView.findViewById(R.id.loanInterestRate);
            vat = itemView.findViewById(R.id.loanVat);
            mortgage = itemView.findViewById(R.id.loanMortgage);
            tax = itemView.findViewById(R.id.loanTax);
            others = itemView.findViewById(R.id.loanOthers);
        }

        public void setDetails(OrganizationOffers organizationOffers){
            loanName.setText(organizationOffers.getLoanname());
            criteria.setText(organizationOffers.getCriteria());
            maxAmount.setText(organizationOffers.getMaxamount()+"");
            duration.setText(organizationOffers.getDuration()+"");
            installments.setText(organizationOffers.getIntallments()+"");
            intRate.setText(organizationOffers.getIntrate());
            vat.setText(organizationOffers.getVat());
            mortgage.setText(organizationOffers.getMortgage());
            tax.setText(organizationOffers.getTax());
            others.setText(organizationOffers.getOthers());
        }

        public void bind(final OrganizationOffers offers,OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ShowPost.class);
                    intent.putExtra("offer",offers);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
