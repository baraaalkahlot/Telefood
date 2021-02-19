package com.bik.telefood.model.entity.Autherntication;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse extends MainResponse {
    @SerializedName("categories")
    @Expose
    private List<CategoryModel> categories = null;

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }
}
