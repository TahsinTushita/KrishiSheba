package com.sust.bup_project.loan;

public class OrganizationOffers {
    String criteria,
    intrate,
    loanname,
    logo,
    mortgage,
    others,
    tax,
    vat,
    personal;

    int duration,
    maxamount,intallments;

    public OrganizationOffers() {
    }

    public OrganizationOffers(String criteria, String intrate, String loanname, String logo, String mortgage, String others, String tax, String vat, String personal, int duration, int maxamount, int intallments) {
        this.criteria = criteria;
        this.intrate = intrate;
        this.loanname = loanname;
        this.logo = logo;
        this.mortgage = mortgage;
        this.others = others;
        this.tax = tax;
        this.vat = vat;
        this.personal = personal;
        this.duration = duration;
        this.maxamount = maxamount;
        this.intallments = intallments;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getIntrate() {
        return intrate;
    }

    public void setIntrate(String intrate) {
        this.intrate = intrate;
    }

    public String getLoanname() {
        return loanname;
    }

    public void setLoanname(String loanname) {
        this.loanname = loanname;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMortgage() {
        return mortgage;
    }

    public void setMortgage(String mortgage) {
        this.mortgage = mortgage;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getMaxamount() {
        return maxamount;
    }

    public void setMaxamount(int maxamount) {
        this.maxamount = maxamount;
    }

    public int getIntallments() {
        return intallments;
    }

    public void setIntallments(int intallments) {
        this.intallments = intallments;
    }
}
