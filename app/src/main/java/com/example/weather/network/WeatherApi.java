package com.example.weather.network;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("v2/city/lookup")
    Observable<String> getCity(@Query("location") String location, @Query("key") String key, @Query("mode") String mode);
    @GET("v7/weather/{dataType}")
    Observable<String> getWeatherData(@Path("dataType")String dataType , @Query("location") String location, @Query("key") String key);

}
