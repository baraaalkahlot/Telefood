package com.bik.telefood.model.entity.general.services;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesResponse extends MainResponse {
    @SerializedName("services")
    @Expose
    private ServicesListModel services;

    public ServicesListModel getServices() {
        return services;
    }

    public void setServices(ServicesListModel services) {
        this.services = services;
    }
}
