package com.bik.telefood.model.network;

public interface ApiConstant {
    String BASE_URL = "https://telefoodsa.com/backend/api/";

    String COUNTRY_CODE = "+966";
    String ACCEPT_LANGUAGE = "Accept-Language";
    String ACCEPT = "Accept";
    String AUTHORIZATION = "Authorization";
    String PLATFORM = "platform";
    String VERSION = "version";
    String FCM_TOKEN = "fcmToken";
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
    String UPLOAD_ADS_IMAGE = "img";

    String PRODUCT_ID = "id";
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
    String OPEN = "open";
    String CLOSED = "closed";
    String ATTACHMENT = "attachments[]";
    String TICKET_SUBJECT = "subject";
    String TICKET_DETAILS = "details";
    String FAVORITE_TYPE = "type";
    String FAVORITE_TYPE_VENDOR = "vendor";
    String FAVORITE_TYPE_SERVICE = "service";

    String CHAT_TYPE_CURRENT_USER = "currentUser";
    String USER_ID = "user_id";
    String CHAT = "CHAT";
    String MESSAGES = "Messages";
    String MESSAGE_TYPE_TEXT = "text";
    String MESSAGE = "msg";
    String MESSAGE_TYPE_LOCATION = "location";
}
