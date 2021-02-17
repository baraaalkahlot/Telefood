package com.bik.telefood.model.entity.general;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Features extends MainResponse {
    @SerializedName("features")
    @Expose
    private List<FeatureModel> features = null;

    public List<FeatureModel> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureModel> features) {
        this.features = features;
    }
}
