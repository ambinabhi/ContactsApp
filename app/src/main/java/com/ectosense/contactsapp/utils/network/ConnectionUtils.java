package com.ectosense.contactsapp.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Abhilash MB on 25-07-2020.
 */
public class ConnectionUtils {
    public static boolean isConnectedToNetwork(Context mContext) {
        boolean isConnectedWifi = false;
        boolean isConnectedMobile = false;

        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();

        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI")) if (ni.isConnected()) isConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE")) {
                if (ni.isConnected()) isConnectedMobile = true;
            }
        }
        return isConnectedWifi || isConnectedMobile;
    }
}
