package com.bik.telefood.model.entity.Autherntication.vendors;

import com.bik.telefood.model.entity.PaginationModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VendorsListModel extends PaginationModel {
    @SerializedName("data")
    @Expose
    private List<VendorsModel> data = null;

    public List<VendorsModel> getData() {
        return data;
    }

    public void setData(List<VendorsModel> data) {
        this.data = data;
    }
}
