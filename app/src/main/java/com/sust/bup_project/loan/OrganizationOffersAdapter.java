package com.sust.bup_project.loan;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sust.bup_project.R;

import java.util.ArrayList;

public class OrganizationOffersAdapter extends RecyclerView.Adapter<OrganizationOffersAdapter.OrganizationOffersViewHolder> {

    ArrayList<OrganizationOffers> organizationOffersArrayList;

    public OrganizationOffersAdapter(ArrayList<OrganizationOffers> organizationOffersArrayList){
        this.organizationOffersArrayList = organizationOffersArrayList;
    }
    @NonNull
    @Override
    public OrganizationOffersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LinearLayout layout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_post_layout,viewGroup,false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationOffersViewHolder organizationOffersViewHolder, int i) {

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
            loanName.setText(organizationOffers.getLoanName());
            criteria.setText(organizationOffers.getCriteria());
            maxAmount.setText(organizationOffers.getMaxAmount());
            duration.setText(organizationOffers.getDuration());
            installments.setText(organizationOffers.getInstallments());
            intRate.setText(organizationOffers.getIntRate());
            vat.setText(organizationOffers.getVat());
            mortgage.setText(organizationOffers.getMortgage());
            tax.setText(organizationOffers.getTax());
            others.setText(organizationOffers.getOthers());
        }
    }
}
