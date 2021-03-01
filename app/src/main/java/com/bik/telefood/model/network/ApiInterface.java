package com.bik.telefood.model.network;

import com.bik.telefood.model.entity.Autherntication.CategoryResponse;
import com.bik.telefood.model.entity.Autherntication.MyServiceResponse;
import com.bik.telefood.model.entity.Autherntication.NotificationResponse;
import com.bik.telefood.model.entity.Autherntication.UpdateProfileResponse;
import com.bik.telefood.model.entity.MainResponse;
import com.bik.telefood.model.entity.auth.LoginResponse;
import com.bik.telefood.model.entity.general.AboutAppResponse;
import com.bik.telefood.model.entity.general.BankInfoResponse;
import com.bik.telefood.model.entity.general.CitiesResponse;
import com.bik.telefood.model.entity.general.GovernoratesResponse;
import com.bik.telefood.model.entity.general.PackageResponse;
import com.bik.telefood.model.entity.general.PrivacyPolicyResponse;
import com.bik.telefood.model.entity.general.services.ServicesResponse;
import com.bik.telefood.model.entity.general.singleservices.SingleServiceResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiInterface {

    //General
    @GET("governorates")
    Call<GovernoratesResponse> getGovernoratesList();

    @FormUrlEncoded
    @POST("cities")
    Call<CitiesResponse> getCitiesResponseList(@Field(ApiConstant.GOVERNORATE) int governorate);

    @GET("bankAccount")
    Call<BankInfoResponse> getBankInfo();

    @GET("aboutApp")
    Call<AboutAppResponse> aboutApp();

    @GET("privacyPolicy")
    Call<PrivacyPolicyResponse> privacyPolicy();

    @GET("packages")
    Call<PackageResponse> getSubscriptionPackages();

    @GET("categories")
    Call<CategoryResponse> getCategoriesList();

    @FormUrlEncoded
    @POST("services")
    Call<ServicesResponse> getServicesList(@Query(ApiConstant.PAGE) Integer page, @FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("singleService")
    Call<SingleServiceResponse> getSingleServicesList(@Field(ApiConstant.ID) int id);

    //Auth APIs
    @Multipart
    @POST("auth/register")
    Call<MainResponse> register(@PartMap HashMap<String, RequestBody> params, @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginResponse> login(@Field(ApiConstant.PHONE) int Phone, @Field(ApiConstant.PASSWORD) String Password);

    @FormUrlEncoded
    @POST("auth/resetPassword")
    Call<MainResponse> resetPassword(@FieldMap HashMap<String, String> params);

    @POST("auth/logout")
    Call<MainResponse> logout();


    /*-----------{Authenticated Api}-------------*/

    //User Information
    @Multipart
    @POST("user/update")
    Call<UpdateProfileResponse> updateProfile(@PartMap HashMap<String, RequestBody> params, @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @POST("user/changPassword")
    Call<MainResponse> changePassword(@FieldMap HashMap<String, String> params);

    @POST("user/profile")
    Call<UpdateProfileResponse> getProfile();

    @Multipart
    @POST("user/newSubscription")
    Call<MainResponse> newSubscription(@Part MultipartBody.Part image, @Part(ApiConstant.PACKAGE) int packageId);

    //Services
    @Multipart
    @POST("addService")
    Call<MainResponse> addService(@PartMap HashMap<String, RequestBody> params, @Part MultipartBody.Part[] images);

    @Multipart
    @POST("updateService")
    Call<MainResponse> updateService(@PartMap HashMap<String, RequestBody> params, @Part MultipartBody.Part[] images);

    @FormUrlEncoded
    @POST("deleteService")
    Call<MainResponse> deleteService(@Field(ApiConstant.ID) int id);

    @GET("myService")
    Call<MyServiceResponse> myService();


    //Notification
    @GET("myNotifications")
    Call<NotificationResponse> getNotification();
}
