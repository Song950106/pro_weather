package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.view.FlutterView;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ===================");
        startActivity(
                FlutterActivity
                        .withNewEngine()
                        .initialRoute("/")
                        .build(MainActivity.this)
        );
    }
}
