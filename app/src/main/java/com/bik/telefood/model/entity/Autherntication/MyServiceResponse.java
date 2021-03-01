package com.bik.telefood.model.entity.Autherntication;

import com.bik.telefood.model.entity.MainResponse;
import com.bik.telefood.model.entity.general.services.ServicesItemModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyServiceResponse extends MainResponse {
    @SerializedName("services")
    @Expose
    private List<ServicesItemModel> services = null;

    public List<ServicesItemModel> getServices() {
        return services;
    }

    public void setServices(List<ServicesItemModel> services) {
        this.services = services;
    }
}
