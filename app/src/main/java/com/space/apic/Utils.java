package com.space.apic;

import android.location.Location;
import android.net.Uri;

/**
 * Created by saihou on 2/23/16.
 */
public class Utils {
    public static Uri mostRecentPhoto;
    public static HomeCardData mostRecentPost = null;
    public static String mostRecentMerchantName = null;
    public static String mostRecentMerchantDistance = null;

    public static boolean canReadStorage = false;
    public static Location lastKnownLocation = null;

    public static String getUsername() {
        return "LaunchHackathon";
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
