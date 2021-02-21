package com.bik.telefood.model.entity.general.singleservices;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleServiceResponse extends MainResponse {
    @SerializedName("service")
    @Expose
    private SingleServiceModel service;

    public SingleServiceModel getService() {
        return service;
    }

    public void setService(SingleServiceModel service) {
        this.service = service;
    }
}
