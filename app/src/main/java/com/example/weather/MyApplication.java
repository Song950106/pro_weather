package com.example.weather;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

import com.idlefish.flutterboost.FlutterBoost;
import com.idlefish.flutterboost.FlutterBoostPlugin;
import com.idlefish.flutterboost.Platform;
import com.idlefish.flutterboost.Utils;
import com.idlefish.flutterboost.interfaces.INativeRouter;

import java.util.Map;

import io.flutter.embedding.android.FlutterView;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

public class MyApplication extends Application {
    private static FlutterEngine flutterEngine;
    public static FlutterEngine getFlutterEngine(){
        return flutterEngine;
    }
    private final String TAG = "MyApplication";
    private static Context mContext;

    public static Context getAppContext(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
//        // Instantiate a FlutterEngine.
//        flutterEngine = new FlutterEngine(this);
//        // Configure an initial route.
////        flutterEngine.getNavigationChannel().setInitialRoute("/");
//        // Start executing Dart code to pre-warm the FlutterEngine.
//        flutterEngine.getDartExecutor().executeDartEntrypoint(
//                DartExecutor.DartEntrypoint.createDefault()
//        );
//        // Cache the FlutterEngine to be used by FlutterActivity or FlutterFragment.
//        FlutterEngineCache
//                .getInstance()
//                .put("weather_flutter", flutterEngine);

        // init flutterBoost
        {
            INativeRouter router = (context, url, urlParams, requestCode, exts) -> {
                 String assembleUrl = Utils.assembleUrl(url, urlParams);
                Log.d(TAG, "openContainer: url is " + url + " urlParams is " + urlParams);
                PageRouter.openPageByUrl(context, assembleUrl, urlParams,requestCode);
            };

            FlutterBoost.BoostLifecycleListener boostLifecycleListener= new FlutterBoost.BoostLifecycleListener(){

                @Override
                public void beforeCreateEngine() {
                    Log.d(TAG, "beforeCreateEngine: ");
                }

                @Override
                public void onEngineCreated() {
                    Log.d(TAG, "onEngineCreated: ");
                }

                @Override
                public void onPluginsRegistered() {
                    Log.d(TAG, "onPluginsRegistered: ");
                }

                @Override
                public void onEngineDestroy() {
                    Log.d(TAG, "onEngineDestroy: ");
                }
            };

            Platform platform = new FlutterBoost
                    .ConfigBuilder(this,router)
                    .isDebug(true)
                    .whenEngineStart(FlutterBoost.ConfigBuilder.ANY_ACTIVITY_CREATED)
                    .renderMode(FlutterView.RenderMode.texture)
                    .lifecycleListener(boostLifecycleListener)
                    .build();

            FlutterBoost.instance().init(platform);
        }
    }

    public static boolean  isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            Log.d("qqqqqqqq", "isOnline: ");
            networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }
        return false;
    }
}
