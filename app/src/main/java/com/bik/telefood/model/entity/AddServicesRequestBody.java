package com.bik.telefood.model.entity;

import java.util.List;

public class AddServicesRequestBody {
    private String id;
    private String name;
    private int category;
    private String price;
    private String details;
    private List<Integer> imgId;


    public AddServicesRequestBody(String id, String name, int category, String price, String details, List<Integer> imgId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.details = details;
        this.imgId = imgId;
    }

    public AddServicesRequestBody(String name, int category, String price, String details, List<Integer> imgId) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.details = details;
        this.imgId = imgId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Integer> getImgId() {
        return imgId;
    }

    public void setImgId(List<Integer> imgId) {
        this.imgId = imgId;
    }
}
