package com.bik.telefood.model.entity.general;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user_section_table")
public class UserModel {
    @PrimaryKey
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("phone")
    private String phone;
    @SerializedName("role")
    private String role;
    @SerializedName("status")
    private String status;
    @SerializedName("phone_verified_at")
    private String phoneVerifiedAt;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("governorate_id")
    private String governorateId;
    @SerializedName("governorate")
    private String governorate;
    @SerializedName("city_id")
    private String cityId;
    @SerializedName("city")
    private String city;
    @SerializedName("choosedPlanName")
    private String choosedPlanName;
    @SerializedName("remainingDaysInPlan")
    private String remainingDaysInPlan;
    @SerializedName("sendNotification")
    private boolean sendNotification;
    @SerializedName("rating")
    private String rating;

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

    public String getGovernorate() {
        return governorate;
    }

    public void setGovernorate(String governorate) {
        this.governorate = governorate;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public boolean getSendNotification() {
        return sendNotification;
    }

    public void setSendNotification(boolean sendNotification) {
        this.sendNotification = sendNotification;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
