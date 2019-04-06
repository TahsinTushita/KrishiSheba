package com.sust.bup_project.loan;

public class OrganizationOffers {
    String loanname;
    String criteria;
    String maxamount;
    String duration;
    String installments;
    String intrate;
    String vat;
    String mortgage;
    String tax;
    String others;
    String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLoanName() {
        return loanname;
    }

    public void setLoanName(String loanname) {
        this.loanname = loanname;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getMaxAmount() {
        return maxamount;
    }

    public void setMaxAmount(String maxamount) {
        this.maxamount = maxamount;
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
        return intrate;
    }

    public void setIntRate(String intrate) {
        this.intrate = intrate;
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

    public OrganizationOffers(String loanname, String criteria, String maxamount,
                              String duration, String installments, String intrate, String vat,
                              String mortgage, String tax, String others) {
        this.loanname = loanname;
        this.criteria = criteria;
        this.maxamount = maxamount;
        this.duration = duration;
        this.installments = installments;
        this.intrate = intrate;
        this.vat = vat;
        this.mortgage = mortgage;
        this.tax = tax;
        this.others = others;
    }

    public OrganizationOffers(String logo,String loanname,String duration,String maxamount){
        this.logo = logo;
        this.loanname = loanname;
        this.duration = duration;
        this.maxamount = maxamount;
    }

    public OrganizationOffers() {
    }
}
