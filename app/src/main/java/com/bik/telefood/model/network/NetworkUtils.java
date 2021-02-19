package com.bik.telefood.model.network;

import android.app.Application;
import android.os.Build;
import android.os.LocaleList;

import com.bik.telefood.CommonUtils.SharedPreferencesHelper;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    private static NetworkUtils sInstance;
    private ApiInterface apiInterface;
    private Application application;

    private NetworkUtils(Application application) {
        this.application = application;

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(networkingParent())
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static NetworkUtils getInstance(Application application) {
        if (sInstance == null) {
            sInstance = new NetworkUtils(application);
        }
        return sInstance;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    private Interceptor networkingParent() {
        Interceptor interceptor;
        interceptor = chain -> {
            Request.Builder builder = chain.request().newBuilder()
                    .addHeader(ApiConstant.ACCEPT_LANGUAGE, getLanguage())
                    .addHeader(ApiConstant.ACCEPT, "application/json")
                    .addHeader(ApiConstant.AUTHORIZATION, ApiConstant.BEARER + SharedPreferencesHelper.getToken(application));
            return chain.proceed(builder.build());
        };
        return interceptor;
    }

    private String getLanguage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return LocaleList.getDefault().toLanguageTags();
        } else {
            return Locale.getDefault().getLanguage();
        }
    }

}
