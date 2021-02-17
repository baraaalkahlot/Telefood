package com.bik.telefood.model.entity.general;

import com.bik.telefood.model.entity.MainResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AboutAppResponse extends MainResponse {

    @SerializedName("aboutApp")
    @Expose
    private String aboutApp;

    public String getAboutApp() {
        return aboutApp;
    }

    public void setAboutApp(String aboutApp) {
        this.aboutApp = aboutApp;
    }
}
