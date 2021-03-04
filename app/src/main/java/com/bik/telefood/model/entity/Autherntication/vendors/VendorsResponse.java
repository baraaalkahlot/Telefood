package com.bik.telefood.model.entity.Autherntication.vendors;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorsResponse extends MainResponse {
    @SerializedName("vendors")
    @Expose
    private VendorsListModel vendors;

    public VendorsListModel getVendors() {
        return vendors;
    }

    public void setVendors(VendorsListModel vendors) {
        this.vendors = vendors;
    }
}
