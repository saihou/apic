package com.space.apic;

import android.location.Location;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by saihou on 2/26/16.
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

    public static ArrayList<ChallengeCardData> challengePlaceholderData = new ArrayList<>();

    public static void init() {
        challengePlaceholderData.add(new ChallengeCardData("Chocolate Origin", "30 mins left", "Chocolate Origin", "0.4 mi", "Grab your friends here and get a lava cupcake each! Take a photo with everyone eating our best-selling lava cake!",String.valueOf(R.drawable.challenge_chocolate_origin)));
        challengePlaceholderData.add(new ChallengeCardData("The Black Horse", "15 mins left", "The Black Horse", "0.9 mi", "Strike up a conversation with our bartenders. Tell him/her what you love about us and take a selfie with any of them!!", String.valueOf(R.drawable.post_bar)));
        challengePlaceholderData.add(new ChallengeCardData("merchantName3", "27 mins left", "In the forest", "5.4 mi", "Troll troll troll troll troll troll troll troll troll troll troll troll troll troll troll","content://media/external/images/media/12696"));
        challengePlaceholderData.add(new ChallengeCardData("merchantName4", "10 days left", "Chocolate Origins", "9001 mi", "After so long!! Haha #shoppingmadness","content://media/external/images/media/12673"));

    }

    public static double getLastKnownLongitude() {
        return lastKnownLocation.getLongitude();
    }
    public static double getLastKnownLatitude() {
        return lastKnownLocation.getLatitude();
    }
}
