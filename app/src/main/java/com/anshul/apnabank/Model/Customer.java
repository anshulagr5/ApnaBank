package com.anshul.apnabank.Model;

import java.io.Serializable;

public class Customer implements Serializable {
    private int id;
    private String AccountId;
    private String name;
    private String mobileNo;
    private String email;
    private String address;
    private String balance;

    public Customer() {
        // Default constructor
    }

    public Customer(String AccountId, String name, String mobileNo, String email, String address, String balance) {
        this.AccountId=AccountId;
        this.name = name;
        this.mobileNo = mobileNo;
        this.email = email;
        this.address = address;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountId() {
        return AccountId;
    }
    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
