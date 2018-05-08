package com.sunyard.sundemo.model.http;


import com.blankj.utilcode.util.AppUtils;
import com.sunyard.sundemo.model.SunProtocol;
import com.sunyard.sundemo.model.http.service.OrderService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MengJie on 2017/6/18.
 */

public class ApiClient {

    /**
     * Service
     */
    public static OrderService orderService;


    public static void init() {
        orderService = initService(OrderService.class, SunProtocol.host);
    }

    private static <T> T initService(Class<T> clazz, String host) {

        //log interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS);

        if (AppUtils.isAppDebug()) {
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

}
