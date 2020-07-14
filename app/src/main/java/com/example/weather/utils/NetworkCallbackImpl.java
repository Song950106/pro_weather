package com.example.weather.utils;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {

    private final String TAG = "NETWORK_MANAGER";

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        Log.d(TAG, "onAvailable: 网络已连接");
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        Log.e(TAG, "onLost: 网络已断开");
    }

//    @Override
//    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
//        super.onCapabilitiesChanged(network, networkCapabilities);
//        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
//            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
//                Log.d(TAG, "onCapabilitiesChanged: 网络类型为wifi");
//                post(NetType.WIFI);
//            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
//                Log.d(TAG, "onCapabilitiesChanged: 蜂窝网络");
//                post(NetType.CMWAP);
//            } else {
//                Log.d(TAG, "onCapabilitiesChanged: 其他网络");
//                post(NetType.AUTO);
//            }
//        }
//    }
}
