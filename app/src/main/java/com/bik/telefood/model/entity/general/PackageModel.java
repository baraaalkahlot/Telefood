package com.bik.telefood.model.entity.general;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageModel {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("services_count")
    @Expose
    private String servicesCount;
    @SerializedName("day_count")
    @Expose
    private String dayCount;
    @SerializedName("description")
    @Expose
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getServicesCount() {
        return servicesCount;
    }

    public void setServicesCount(String servicesCount) {
        this.servicesCount = servicesCount;
    }

    public String getDayCount() {
        return dayCount;
    }

    public void setDayCount(String dayCount) {
        this.dayCount = dayCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

