package com.bik.telefood.model.entity.general;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageResponse extends MainResponse {
    @SerializedName("packages")
    @Expose
    private List<PackageModel> packages = null;

    public List<PackageModel> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageModel> packages) {
        this.packages = packages;
    }
}
