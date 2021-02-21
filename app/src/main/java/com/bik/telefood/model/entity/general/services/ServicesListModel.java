package com.bik.telefood.model.entity.general.services;

import com.bik.telefood.model.entity.PaginationModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServicesListModel extends PaginationModel {
    @SerializedName("data")
    @Expose
    private List<ServicesItemModel> data = null;

    public List<ServicesItemModel> getData() {
        return data;
    }

    public void setData(List<ServicesItemModel> data) {
        this.data = data;
    }
}
