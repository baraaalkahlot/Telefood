package com.bik.telefood.model.entity.Autherntication;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadImagesResponse extends MainResponse {
    @SerializedName("imageId")
    @Expose
    private int imageId;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
