package com.bik.telefood.model.entity.general;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GovernoratesResponse extends MainResponse {
    @SerializedName("governorates")
    @Expose
    private List<Governorate> governorates = null;

    public List<Governorate> getGovernorates() {
        return governorates;
    }

    public void setGovernorates(List<Governorate> governorates) {
        this.governorates = governorates;
    }
}
