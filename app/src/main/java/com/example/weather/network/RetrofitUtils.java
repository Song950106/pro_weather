package com.example.weather.network;

import android.util.Log;

import com.example.weather.MyApplication;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtils {
    private static Retrofit.Builder retrofit_builder ;

    public static OkHttpClient.Builder getOkHttpClientBuilder() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    Log.e("OKHttp-----", URLDecoder.decode(message, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("OKHttp-----", message);
                }
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheFile = new File(MyApplication.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        return new OkHttpClient.Builder()
//                .readTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
//                .connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                // .sslSocketFactory(SslContextFactory.getSSLSocketFactoryForTwoWay())  // https认证 如果要使用https且为自定义证书 可以去掉这两行注释，并自行配制证书。
                // .hostnameVerifier(new SafeHostnameVerifier())
                .cache(cache);
    }

    public static synchronized Retrofit.Builder getSingleRetrofitBuilder(String baseUrl){
        if(null == retrofit_builder){
            retrofit_builder = new Retrofit.Builder().
                        client(getOkHttpClientBuilder().build()).
                        addConverterFactory(ScalarsConverterFactory.create()).
                        addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                        baseUrl(baseUrl);
        }
        return retrofit_builder.baseUrl(baseUrl);
    }

    public static <T> T getApiService(Class<T> cls,String baseUrl){
        return getSingleRetrofitBuilder(baseUrl).build().create(cls);
    }
}
