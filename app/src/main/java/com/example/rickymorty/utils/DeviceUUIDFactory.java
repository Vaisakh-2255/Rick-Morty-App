package com.example.rickymorty.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

public class DeviceUUIDFactory {

    protected static String uuid;
    private static String TAG = "DeviceUUIDFactory";

    public static String getUuid(Context context) {
        Log.d(TAG,"getUUID called");
        if((uuid ==null) || (uuid == ""))
        {
            Log.d(TAG,"Fetching new UUID");
            String android_uuid = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            uuid = android_uuid;
        }
        Log.d(TAG, "UUID is +"+uuid);
        return uuid;


    }

    public static String getUuid()
    {
        return uuid;
    }
}
