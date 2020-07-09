package com.example.weather;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.util.Log;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainActivity extends FlutterActivity {

    private final String TAG = "MainActivity";
    public final String METHOD_CHANNEL = "com.example.weather.methodChannel";
    public final String EVENT_CHANNEL = "com.example.weather.eventChannel";
    public final String QUERY_TYPE_WEEKLY = "7d";
    public final String QUERY_TYPE_DAILY = "24h";
    public final String KEY = "2869245148564fd0a43251b16a168cbd";
    public String jsonData = null;
    public MethodChannel methodChannel;
    public EventChannel eventChannel;
    public EventChannel.EventSink eventSink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ===================");
//        startActivity(
//                FlutterActivity
//                        .withNewEngine()
//                        .initialRoute("/")
//                        .build(MainActivity.this)
//        );
        GeneratedPluginRegistrant.registerWith(MyApplication.getFlutterEngine());
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        methodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),METHOD_CHANNEL);
        methodChannel.setMethodCallHandler(
                (call, result) -> {
                    if ("getWeatherData".equals(call.method)) {
                        testNetwork();
                    } else {
                        result.notImplemented();
                    }
                }
        );

        eventChannel = new EventChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),EVENT_CHANNEL);
        eventChannel.setStreamHandler(
                new EventChannel.StreamHandler() {
                    @Override
                    public void onListen(Object arguments, EventChannel.EventSink events) {
                        Log.d(TAG, "onListen: arguments is " + arguments);
                        eventSink = events;
                    }
                    @Override
                    public void onCancel(Object arguments) {
                        eventSink = null;
                    }
                }
        );
    }

    public void  testNetwork(){
        Log.d(TAG, "testNetwork: ==============");
//        https://devapi.heweather.net/v7/weather/7d
        Retrofit city_retrofit = new Retrofit.Builder().
                addConverterFactory(ScalarsConverterFactory.create()).
                baseUrl("https://geoapi.heweather.net/").
                build();
        Retrofit weather_retrofit = new Retrofit.Builder().
                addConverterFactory(ScalarsConverterFactory.create()).
                baseUrl("https://devapi.heweather.net/").
                build();
//        Call<String> call = retrofit.create(WeatherCall.class).getData("110101","138cf8679b0eb0790a904a6a9996e1c0","all");
        Call<String> city_call = city_retrofit.create(WeatherCall.class).getCity("beijing",KEY,"fuzzy");
        Call<String> weekly_weather_call = weather_retrofit.create(WeatherCall.class).getWeatherData(QUERY_TYPE_WEEKLY,"101010100",KEY);
        Call<String> daily_weather_call = weather_retrofit.create(WeatherCall.class).getWeatherData(QUERY_TYPE_DAILY,"101010100",KEY);
        weekly_weather_call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call call, Response response) {
                assert response.body() != null;
                jsonData = response.body().toString();
                if(null != eventSink)
                    eventSink.success(jsonData);
                else
                    Log.d(TAG, "onResponse: eventSink is null !!!!!!!!!");
                Log.d(TAG, "onResponse: response body is " + response.body());
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public interface WeatherCall {
        @GET("v2/city/lookup")
        Call<String> getCity(@Query("location") String location,@Query("key") String key,@Query("mode") String mode);
        @GET("v7/weather/{dataType}")
        Call<String> getWeatherData(@Path ("dataType")String dataType ,@Query("location") String location, @Query("key") String key);
    }
}
