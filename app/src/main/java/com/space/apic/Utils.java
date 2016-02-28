package com.space.apic;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.location.Location;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.github.jorgecastilloprz.FABProgressCircle;

import java.util.ArrayList;

/**
 * Created by saihou on 2/26/16.
 */
public class Utils {
    public static Uri mostRecentPhoto;
    public static HomeCardData mostRecentPost = null;
    public static String mostRecentMerchantName = null;
    public static String mostRecentMerchantDistance = null;
    public static String mostRecentChallengeClicked = null;
    public static boolean canReadStorage = false;
    public static Location lastKnownLocation = null;
    public static boolean isRiding = false;

    public static String getUsername() {
        return "LaunchHackathon";
    }

    public static void setLastKnownLocation(Location lastKnownLocation) {
        Utils.lastKnownLocation = lastKnownLocation;
    }

    public static ArrayList<ChallengeCardData> challengePlaceholderData = new ArrayList<>();

    public static void init() {
        challengePlaceholderData.add(new ChallengeCardData("Chocolate Origin", "30 mins left", "Chocolate Origin", "0.4 mi", "Grab your friends here and get a lava cupcake each! Take a photo with everyone eating our best-selling lava cake!",String.valueOf(R.drawable.challenge_chocolate_origin)));
        challengePlaceholderData.add(new ChallengeCardData("The Black Horse", "15 mins left", "The Black Horse", "0.9 mi", "Strike up a conversation with our bartenders. Tell him/her what you love about us and take a selfie with any of them!!", String.valueOf(R.drawable.challenge_bar)));
        challengePlaceholderData.add(new ChallengeCardData("Love With Burgers", "27 mins left", "Love With Burgers", "5.4 mi", "\"Siao Liao\" Burger! Good for four but don't worry, you don't have to finish it by yourself! Bring your friends along, get some help and pose with \"Siao Liao\"!",String.valueOf(R.drawable.challenge_burger)));
        challengePlaceholderData.add(new ChallengeCardData("Arcadia Ski Resort", "10 days left", "Arcadia Ski Resort", "9001 mi", "Ski with your family and take a family photo with all your gears on! Remember to pose with our lovely mascot!",String.valueOf(R.drawable.challenge_ski)));
        challengePlaceholderData.add(new ChallengeCardData("Diablo's Wings", "12 days left", "Diablo's Wings", "0.4 mi", "If you know us, I bet you know what we are famous for. If you don't, it is time to find out! Get a plate of our signature Diablo's wings, try it out and capture the moment!",String.valueOf(R.drawable.challenge_wings)));
        challengePlaceholderData.add(new ChallengeCardData("Real Escape Room", "17 days left", "Real Escape Room", "0.9 mi", "Escape the Haunted Cottage! Brand new room with experience that you will not forget! Escape the room and get one of our crews to grab a picture of you and your company!", String.valueOf(R.drawable.challenge_escaperoom)));
        challengePlaceholderData.add(new ChallengeCardData("Sichuan Hotpot", "30 days left", "Sichuan Hotpot", "5.4 mi", "We dare you to order \"Ma-La Hot Pot\", the best hotpot for this cool weather! Remember to capture the moment with this signature dish of ours!",String.valueOf(R.drawable.challenge_hotpot)));
        challengePlaceholderData.add(new ChallengeCardData("Gokart Racer", "30 days left", "Gokart Racer", "5.4 mi", "Gokart is fun! But it is more fun when you race with your friends! Get someone to take a photo of all of you in your kart getting ready to RACE!",String.valueOf(R.drawable.challenge_gokart)));
    }

    public static int lookupChallenge() {
        int key = 0;
        if (mostRecentChallengeClicked != null) {
            switch (mostRecentChallengeClicked) {
                case "Chocolate Origin":
                    return 0;
                case "The Black Horse":
                    return 1;
                case "Love With Burgers":
                    return 2;
                case "Arcadia Ski Resort":
                    return 3;
                case "Diablo's Wings":
                    return 4;
                case "Real Escape Room":
                    return 5;
                case "Sichuan Hotpot":
                    return 6;
                case "Gokart Racer":
                    return 7;
            }
        }
        return key;
    }

    public static double getLastKnownLongitude() {
        return lastKnownLocation.getLongitude();
    }
    public static double getLastKnownLatitude() {
        return lastKnownLocation.getLatitude();
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getHeight() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }
}
