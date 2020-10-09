package com.example.weather;
import androidx.annotation.NonNull;

import android.os.Bundle;
import android.util.Log;

import com.example.weather.network.RetrofitUtils;
import com.example.weather.network.WeatherApi;
import com.idlefish.flutterboost.containers.BoostFlutterActivity;

import io.flutter.embedding.engine.FlutterEngine;

import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends BoostFlutterActivity {

    private final String TAG = "MainActivity";
    public final String METHOD_CHANNEL = "com.example.weather.methodChannel";
    public final String EVENT_CHANNEL = "com.example.weather.eventChannel";
    public final String QUERY_TYPE_WEEKLY = "7d";
    public final String QUERY_TYPE_DAILY = "24h";
    public final String QUERY_TYPE_NOW = "now";
    public final String KEY = "2869245148564fd0a43251b16a168cbd";
    public String responseBody ;
    public MethodChannel methodChannel;
    public EventChannel eventChannel;
    public EventChannel.EventSink eventSink;


    final String GET_WEATHER_DAILY = "getWeatherDaily";
    final String GET_WEATHER_WEEKLY = "getWeatherWeekly";
    final String GET_WEATHER_NOW = "getWeatherNow";


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
//        GeneratedPluginRegistrant.registerWith(MyApplication.getFlutterEngine());
        PageRouter.openPageByUrl(this,"mainPage",null,0);
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        methodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),METHOD_CHANNEL);
        methodChannel.setMethodCallHandler(
                (call, result) -> {
                    switch (call.method){
                        case GET_WEATHER_DAILY:
                            getWeatherData(result,1);
                            break;
                        case GET_WEATHER_NOW:
                            getWeatherData(result,0);
                            break;
                        case GET_WEATHER_WEEKLY:
                            getWeatherData(result,2);
                            break;
                        default:
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

    private void getWeatherData(MethodChannel.Result result,int flag ){
        String dataType = "";
        switch (flag){
            case 0:
                dataType = QUERY_TYPE_NOW;
                break;
            case 1:
                dataType = QUERY_TYPE_DAILY;
                break;
            case 2:
                dataType = QUERY_TYPE_WEEKLY;
                break;
        }
        Log.d(TAG, "getWeatherDaily: dataType " + dataType);
        RetrofitUtils.getApiService(WeatherApi.class,"https://devapi.heweather.net/").
                getWeatherData(dataType,"101010100",KEY).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        Log.d(TAG, "onNext: " + value);
                        // no error
                        result.success(0);
                        eventSink.success( flag + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.success(-1);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void  testNetwork(MethodChannel.Result result){


//        Observable<String> city_call = RetrofitUtils.getApiService(WeatherApi.class,"https://geoapi.heweather.net/").
//                getCity("beijing",KEY,"fuzzy");

        // network is unavailable
//        if(!MyApplication.isOnline()){
//            Log.d(TAG, "testNetwork: status ");
//            result.success(-1);
//            return;
//        }

        // 24h weather data
        RetrofitUtils.getApiService(WeatherApi.class,"https://devapi.heweather.net/").
                getWeatherData(QUERY_TYPE_DAILY,"101010100",KEY).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        Log.d(TAG, "onNext: " + value);
                        // no error
                        result.success(0);
                        eventSink.success(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        result.success(-1);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
//        Observable<String> daily_weather_call = RetrofitUtils.getApiService(WeatherApi.class,"https://devapi.heweather.net/").
//                getWeatherData(QUERY_TYPE_DAILY,"101010100",KEY);
    }
}
