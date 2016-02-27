package com.saihou.adpic;

import android.location.Location;
import android.net.Uri;

/**
 * Created by saihou on 2/23/16.
 */
public class Utils {
    public static Uri mostRecentPhoto;
    public static boolean canUseCamera = false;
    public static boolean canWriteStorage = false;
    public static boolean canReadStorage = false;
    public static Location lastKnownLocation = null;

    public static String getUsername() {
        return "nekonekonik";
    }

    public static void setLastKnownLocation(Location lastKnownLocation) {
        Utils.lastKnownLocation = lastKnownLocation;
    }
    public static double getLastKnownLongitude() {
        return lastKnownLocation.getLongitude();
    }
    public static double getLastKnownLatitude() {
        return lastKnownLocation.getLatitude();
    }
}
