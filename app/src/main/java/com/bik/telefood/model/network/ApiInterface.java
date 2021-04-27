package com.bik.telefood.model.network;

import com.bik.telefood.model.entity.AddServicesRequestBody;
import com.bik.telefood.model.entity.Autherntication.CategoryResponse;
import com.bik.telefood.model.entity.Autherntication.MyServiceResponse;
import com.bik.telefood.model.entity.Autherntication.NotificationResponse;
import com.bik.telefood.model.entity.Autherntication.UpdateProfileResponse;
import com.bik.telefood.model.entity.Autherntication.UploadImagesResponse;
import com.bik.telefood.model.entity.Autherntication.vendors.FeaturedVendorsResponse;
import com.bik.telefood.model.entity.Autherntication.vendors.SingleVendorsResponse;
import com.bik.telefood.model.entity.Autherntication.vendors.VendorsResponse;
import com.bik.telefood.model.entity.MainResponse;
import com.bik.telefood.model.entity.auth.LoginResponse;
import com.bik.telefood.model.entity.chat.CreateRoomResponse;
import com.bik.telefood.model.entity.chat.MyRoomModel;
import com.bik.telefood.model.entity.general.AboutAppResponse;
import com.bik.telefood.model.entity.general.BankInfoResponse;
import com.bik.telefood.model.entity.general.CitiesResponse;
import com.bik.telefood.model.entity.general.GovernoratesResponse;
import com.bik.telefood.model.entity.general.PackageResponse;
import com.bik.telefood.model.entity.general.PrivacyPolicyResponse;
import com.bik.telefood.model.entity.general.services.ServicesResponse;
import com.bik.telefood.model.entity.general.singleservices.SingleServiceResponse;
import com.bik.telefood.model.entity.support.MyTicketResponse;
import com.bik.telefood.model.entity.support.ShowDetailsResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
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

    /*-----------{General}-------------*/
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

    //Vendors
    @FormUrlEncoded
    @POST("vendors")
    Call<VendorsResponse> getVendors(@Query(ApiConstant.PAGE) Integer page, @FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("singleVendor")
    Call<SingleVendorsResponse> getSingleVendors(@Query(ApiConstant.PAGE) Integer page, @Field(ApiConstant.ID) int id);

    @POST("featuredVendors")
    Call<FeaturedVendorsResponse> getFeaturedVendors();

    //Services
    @FormUrlEncoded
    @POST("services")
    Call<ServicesResponse> getServicesList(@Query(ApiConstant.PAGE) Integer page, @FieldMap HashMap<String, String> params);

    @FormUrlEncoded
    @POST("singleService")
    Call<SingleServiceResponse> getSingleServicesList(@Field(ApiConstant.ID) int id);

    @FormUrlEncoded
    @POST("featuredServices")
    Call<ServicesResponse> getFeaturedServices(@Query(ApiConstant.PAGE) Integer page, @FieldMap HashMap<String, String> params);


    /*-----------{Auth}-------------*/
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
    @POST("addNewService")
    Call<MainResponse> addService(@Body AddServicesRequestBody body);

    @POST("updateNewService")
    Call<MainResponse> updateService(@Body AddServicesRequestBody body);

    @FormUrlEncoded
    @POST("deleteService")
    Call<MainResponse> deleteService(@Field(ApiConstant.ID) int id);

    @FormUrlEncoded
    @POST("myService")
    Call<MyServiceResponse> myService(@FieldMap HashMap<String, String> params);

    @Multipart
    @POST("uploadServiceImage")
    Call<UploadImagesResponse> uploadImage(@Part MultipartBody.Part image);

    //Favorite
    @FormUrlEncoded
    @POST("myFavorites")
    Call<VendorsResponse> myFavoritesVendors(@Field(ApiConstant.FAVORITE_TYPE) String type);

    @FormUrlEncoded
    @POST("myFavorites")
    Call<ServicesResponse> myFavoritesProduct(@Field(ApiConstant.FAVORITE_TYPE) String type);

    @FormUrlEncoded
    @POST("favoriteToggle")
    Call<MainResponse> favoriteToggle(@Field(ApiConstant.FAVORITE_TYPE) String type, @Field(ApiConstant.ID) int id);

    //Support Center
    @GET("tickets/all")
    Call<MyTicketResponse> getMyTicket();

    @Multipart
    @POST("tickets/add")
    Call<MainResponse> addTicket(@PartMap HashMap<String, RequestBody> params, @Part MultipartBody.Part[] attachment);

    @FormUrlEncoded
    @POST("tickets/show")
    Call<ShowDetailsResponse> showTicket(@Field(ApiConstant.ID) String id);

    @Multipart
    @POST("tickets/sendMessage")
    Call<MainResponse> sendMessage(@PartMap HashMap<String, RequestBody> params, @Part MultipartBody.Part[] attachment);

    //Notification
    @GET("myNotifications")
    Call<NotificationResponse> getNotification();

    @POST("myNotifications/toggle")
    Call<MainResponse> toggleNotification();

    //Chat
    @GET("chat/getRooms")
    Call<MyRoomModel> getMyRooms();

    @FormUrlEncoded
    @POST("chat/creatRoom")
    Call<CreateRoomResponse> createOrFindRoom(@Field(ApiConstant.USER_ID) int userId);
}
