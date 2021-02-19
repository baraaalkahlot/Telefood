package com.bik.telefood.model.entity.Autherntication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateProfileModel {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("governorate_id")
    @Expose
    private String governorateId;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("choosedPlanName")
    @Expose
    private String choosedPlanName;
    @SerializedName("remainingAdds")
    @Expose
    private long remainingAdds;
    @SerializedName("remainingDaysInPlan")
    @Expose
    private long remainingDaysInPlan;
    @SerializedName("askForNewPackage")
    @Expose
    private String askForNewPackage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGovernorateId() {
        return governorateId;
    }

    public void setGovernorateId(String governorateId) {
        this.governorateId = governorateId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getChoosedPlanName() {
        return choosedPlanName;
    }

    public void setChoosedPlanName(String choosedPlanName) {
        this.choosedPlanName = choosedPlanName;
    }

    public long getRemainingAdds() {
        return remainingAdds;
    }

    public void setRemainingAdds(long remainingAdds) {
        this.remainingAdds = remainingAdds;
    }

    public long getRemainingDaysInPlan() {
        return remainingDaysInPlan;
    }

    public void setRemainingDaysInPlan(long remainingDaysInPlan) {
        this.remainingDaysInPlan = remainingDaysInPlan;
    }

    public String getAskForNewPackage() {
        return askForNewPackage;
    }

    public void setAskForNewPackage(String askForNewPackage) {
        this.askForNewPackage = askForNewPackage;
    }

}
