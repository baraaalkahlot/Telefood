package com.bik.telefood.model.entity.general;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankInfoResponse extends MainResponse {
    @SerializedName("information")
    @Expose
    private BankInformationModel information;

    public BankInformationModel getInformation() {
        return information;
    }

    public void setInformation(BankInformationModel information) {
        this.information = information;
    }
}
