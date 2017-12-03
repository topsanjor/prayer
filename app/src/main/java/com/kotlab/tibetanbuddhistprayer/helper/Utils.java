package com.kotlab.tibetanbuddhistprayer.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by topjor on 11/28/2017.
 */

public class Utils {
    // helper class...

    public static boolean isNetWorkAvailable(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =cm.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isConnectedOrConnecting();

    }

}
