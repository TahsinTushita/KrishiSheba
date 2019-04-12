package com.sust.iuttechfest.community;

public class UserInfo {
    private String username,fullname,address,phoneNo,email;

    public UserInfo(String username, String fullname, String address, String phoneNo, String email) {
        this.username = username;
        this.fullname = fullname;
        this.address = address;
        this.phoneNo = phoneNo;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
