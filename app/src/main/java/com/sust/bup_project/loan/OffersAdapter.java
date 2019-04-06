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

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {

    ArrayList<OrganizationOffers> arrayList;

    public OffersAdapter(ArrayList<OrganizationOffers> arrayList){
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout layout= (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_post_layout,viewGroup,false);
        return new OffersAdapter.OffersViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder offersViewHolder, int i) {
        OrganizationOffers offers = arrayList.get(i);
        offersViewHolder.setDetails(offers);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class OffersViewHolder extends RecyclerView.ViewHolder {
        TextView loanname,criteria,maxamount,duration,installment,intrate,vat,mortgage,tax,others;
        public OffersViewHolder(@NonNull View itemView) {
            super(itemView);
            loanname = itemView.findViewById(R.id.loanName);
            criteria = itemView.findViewById(R.id.loanCriteria);
            maxamount = itemView.findViewById(R.id.maxLoanAmount);
            duration = itemView.findViewById(R.id.loanDuration);
            installment = itemView.findViewById(R.id.loanInstallment);
            intrate =itemView.findViewById(R.id.loanInterestRate);
            vat=itemView.findViewById(R.id.loanVat);
            mortgage=itemView.findViewById(R.id.loanMortgage);
            others=itemView.findViewById(R.id.loanOthers);
            tax=itemView.findViewById(R.id.loanTax);

        }

        public void setDetails(OrganizationOffers organizationOffers){
            loanname.setText(organizationOffers.getLoanname());
            criteria.setText(organizationOffers.getCriteria());
            maxamount.setText(organizationOffers.getMaxamount()+"");
            duration.setText(organizationOffers.getDuration()+"");
            installment.setText(organizationOffers.getIntallments()+"");
            intrate.setText(organizationOffers.getIntrate());
            vat.setText(organizationOffers.getVat());
            mortgage.setText(organizationOffers.getMortgage());
            others.setText(organizationOffers.getOthers());
            tax.setText(organizationOffers.getTax());
        }
    }
}
