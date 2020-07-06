package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterView;

public class MainActivity extends FlutterActivity {

    private final String TAG = "MainActivity";
    private final String CHANNEL = "com.example.weather.weatherData";

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
        MethodChannel methodChannel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),CHANNEL);
        methodChannel.setMethodCallHandler(
                (call, result) -> {
                    if ("getWeatherData".equals(call.method)) {
                        //TODO
                        result.success("=======  test channel ========");
                    } else {
                        result.notImplemented();
                    }
                }
        );
    }
}
