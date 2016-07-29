package com.pikazo.utils;

import android.os.Build;

/**
 * Created by jorgesolis on 7/28/16.
 */
public class DeviceUtils {


    public static String getOSVersion(){
        return System.getProperty("os.version");
    }

    public static String getDevice(){
        return Build.DEVICE;
    }

    public static String getModel(){
        return Build.MODEL;
    }

    public static String getManufacturer(){
        return Build.MANUFACTURER;
    }

    public static int getSDKVersion(){
        return Build.VERSION.SDK_INT;
    }


}
