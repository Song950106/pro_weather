package com.example.weather.network_util;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtils {
    private static Retrofit retrofit ;

    public static synchronized Retrofit getSingleRetrofit(String baseUrl){
        if(null == retrofit){
            retrofit = new Retrofit.Builder().
                        addConverterFactory(ScalarsConverterFactory.create()).
                        addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                        baseUrl(baseUrl).
                        build();
        }
        return retrofit;
    }


}
