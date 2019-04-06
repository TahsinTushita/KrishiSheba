package com.sust.bup_project.loan;

public class OrganizationOffers {
    String loanName;
    String criteria;
    String maxAmount;
    String duration;
    String installments;
    String intRate;
    String vat;
    String mortgage;
    String tax;
    String others;

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getInstallments() {
        return installments;
    }

    public void setInstallments(String installments) {
        this.installments = installments;
    }

    public String getIntRate() {
        return intRate;
    }

    public void setIntRate(String intRate) {
        this.intRate = intRate;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getMortgage() {
        return mortgage;
    }

    public void setMortgage(String mortgage) {
        this.mortgage = mortgage;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public OrganizationOffers(String loanName, String criteria, String maxAmount,
                              String duration, String installments, String intRate, String vat,
                              String mortgage, String tax, String others) {
        this.loanName = loanName;
        this.criteria = criteria;
        this.maxAmount = maxAmount;
        this.duration = duration;
        this.installments = installments;
        this.intRate = intRate;
        this.vat = vat;
        this.mortgage = mortgage;
        this.tax = tax;
        this.others = others;
    }

    public OrganizationOffers() {
    }
}
