package com.bik.telefood.model.entity.general;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user_section_table")
public class UserModel {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("phone_verified_at")
    @Expose
    private String phoneVerifiedAt;
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
    @SerializedName("remainingDaysInPlan")
    @Expose
    private String remainingDaysInPlan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhoneVerifiedAt() {
        return phoneVerifiedAt;
    }

    public void setPhoneVerifiedAt(String phoneVerifiedAt) {
        this.phoneVerifiedAt = phoneVerifiedAt;
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

    public String getRemainingDaysInPlan() {
        return remainingDaysInPlan;
    }

    public void setRemainingDaysInPlan(String remainingDaysInPlan) {
        this.remainingDaysInPlan = remainingDaysInPlan;
    }
}
