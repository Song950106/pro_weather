package com.example.weather;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.idlefish.flutterboost.containers.BoostFlutterActivity;

import java.util.HashMap;
import java.util.Map;

public class PageRouter {

    public final static Map<String, String> pageName = new HashMap<String, String>() {{
        put("mainPage", "mainPage");
    }};

    public static final String NATIVE_FIRST_PAGE_URL = "flutterbus://nativeFirstPage";
    public static final String NATIVE_SECOND_PAGE_URL = "flutterbus://nativeSecondPage";
    public static final String FLUTTER_FIRST_PAGE_URL = "flutterbus://flutterFirstPage";
    public static final String FLUTTER_SECOND_PAGE_URL = "flutterbus://flutterSecondPage";


    public static boolean openPageByUrl(Context context, String url, Map<String,Object> urlParams, int requestCode) {
        try {
            // flutter view
            if(pageName.containsKey(url)){
                Intent intent = BoostFlutterActivity.withNewEngine().url(pageName.get(url)).params(urlParams)
                        .backgroundMode(BoostFlutterActivity.BackgroundMode.opaque).build(context);
                if(context instanceof Activity){
                    Activity activity=(Activity)context;
                    activity.startActivityForResult(intent,requestCode);
                }else{
                    context.startActivity(intent);
                }
                return true;
            }
            //native view
            Intent intent;
            if (url.startsWith(NATIVE_FIRST_PAGE_URL)) {
//                intent = new Intent(context, FirstNativeActivity.class);
//                intent.putExtra("url", url);
//                context.startActivity(intent);
                return true;
            } else if (url.startsWith(NATIVE_SECOND_PAGE_URL)) {
//                intent = new Intent(context, SecondNativeActivity.class);
//                intent.putExtra("url", url);
//                if(context instanceof Activity) {
//                    ((Activity)context).startActivityForResult(intent, requestCode);
//                }
                return true;
            } else if(url.startsWith(FLUTTER_FIRST_PAGE_URL)) {
//                intent = new Intent(context, FlutterFirstPageActivity.class);
//                intent.putExtra("url", url);
//                context.startActivity(intent);
                return true;
            } else if (url.startsWith(FLUTTER_SECOND_PAGE_URL)) {
//                intent = new Intent(context, FlutterSecondPageActivity.class);
//                intent.putExtra("url", url);
//                if(context instanceof Activity) {
//                    ((Activity)context).startActivityForResult(intent, requestCode);
//                }
                return true;
            } else {
                return false;
            }
        } catch (Throwable t) {
            return false;
        }
    }

}

