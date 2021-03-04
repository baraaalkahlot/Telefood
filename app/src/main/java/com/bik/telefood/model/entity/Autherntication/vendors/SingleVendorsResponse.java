package com.bik.telefood.model.entity.Autherntication.vendors;

import com.bik.telefood.model.entity.MainResponse;
import com.bik.telefood.model.entity.general.services.ServicesListModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleVendorsResponse extends MainResponse {
    @SerializedName("user")
    @Expose
    private VendorsModel user;
    @SerializedName("services")
    @Expose
    private ServicesListModel services;

    public VendorsModel getUser() {
        return user;
    }

    public void setUser(VendorsModel user) {
        this.user = user;
    }

    public ServicesListModel getServices() {
        return services;
    }

    public void setServices(ServicesListModel services) {
        this.services = services;
    }
}
