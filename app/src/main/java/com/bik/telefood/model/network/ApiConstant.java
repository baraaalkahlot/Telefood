package com.bik.telefood.model.network;

public interface ApiConstant {
    String BASE_URL = "http://waseeem.com/old/backend/TeleFood/public/api/";

    String COUNTRY_CODE = "+972";
    String ACCEPT_LANGUAGE = "Accept-Language";
    String ACCEPT = "Accept";
    String AUTHORIZATION = "Authorization";
    String BEARER = "Bearer ";

    String MULTIPART_FORM_DATA = "multipart/form-data";
    String GOVERNORATE = "governorate";

    String Profile_IMAGE_AVATAR = "avatar";

    //Reset Password
    String PHONE = "phone";
    String PASSWORD = "password";
    String PASSWORD_CONFIRMATION = "password_confirmation";
    String OLD_PASSWORD = "old_password";
    String ROLE = "role";
    String ROLE_USER = "user";
    String ROLE_VENDOR = "vendor";
    String NAME = "name";
    String GOVERNORATE_ID = "governorate_id";
    String CITY_ID = "city_id";

    String IMAGE = "image";
    String PACKAGE = "package";
    String ADD_ADS_IMAGES = "imgs[]";

    String PRODUCT_NAME = "name";
    String PRODUCT_CATEGORY = "category";
    String PRODUCT_PRICE = "price";
    String PRODUCT_DETAILS = "details";
    String PAGE = "page";
    String ID = "id";
    String FILTER_NAME = "name";
    String FILTER_CATEGORY = "category";
    String FILTER_GOVERNORATE = "governorate";
    String FILTER_CITY = "city";
    String FILTER_FROM_PRICE = "price_from";
    String FILTER_TO_PRICE = "price_to";
}
