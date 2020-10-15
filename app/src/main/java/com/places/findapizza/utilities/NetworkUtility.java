package com.places.findapizza.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtility {

    /**
     * Determines the network connection state. Network can be either Wifi/Data.
     *
     * @param context A valid context
     * @return {@code true} if a trace of an active Wifi/data connection found,
     *         {@code false} otherwise
     */
    public static boolean isInternetConnectionAvailable (Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService (Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo ();

        if (netInfo != null && netInfo.isConnected () && netInfo.isAvailable ()) {
            return true;
        }

        return false;
    }
}
