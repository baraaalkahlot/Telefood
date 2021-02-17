package com.bik.telefood.model.entity.general;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankInformationModel {
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("IBAN")
    @Expose
    private String iBAN;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIBAN() {
        return iBAN;
    }

    public void setIBAN(String iBAN) {
        this.iBAN = iBAN;
    }
}
