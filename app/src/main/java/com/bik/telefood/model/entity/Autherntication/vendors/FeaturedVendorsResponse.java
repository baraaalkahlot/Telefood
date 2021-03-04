package com.bik.telefood.model.entity.Autherntication.vendors;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeaturedVendorsResponse extends MainResponse {
    @SerializedName("vendors")
    @Expose
    private List<FeaturedVendorsModel> vendors = null;

    public List<FeaturedVendorsModel> getVendors() {
        return vendors;
    }

    public void setVendors(List<FeaturedVendorsModel> vendors) {
        this.vendors = vendors;
    }
}
