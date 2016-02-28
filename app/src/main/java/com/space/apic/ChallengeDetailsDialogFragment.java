package com.space.apic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.github.jorgecastilloprz.FABProgressCircle;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tankangsoon on 26/2/16.
 */
public class ChallengeDetailsDialogFragment extends DialogFragment {

    private ChallengeCardData challengeData;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View challengeDetailsView = inflater.inflate(R.layout.challenge_details, null);

        Bundle bundle = getArguments();

        final TextView challengeRestaurant = (TextView) challengeDetailsView.findViewById(R.id.challenge_restaurant);
        final TextView caption = (TextView) challengeDetailsView.findViewById(R.id.caption);
        final TextView challengeDuration = (TextView) challengeDetailsView.findViewById(R.id.challenge_duration);
        final TextView challengeDistance = (TextView) challengeDetailsView.findViewById(R.id.challenge_distance);

        challengeRestaurant.setText(bundle.getString("challengeRestaurant"));
        caption.setText(bundle.getString("caption"));
        challengeDuration.setText(bundle.getString("challengeDuration"));
        challengeDistance.setText(bundle.getString("challengeDistance"));

        Button join = (Button) challengeDetailsView.findViewById(R.id.challenge_join);
        Button uber = (Button) challengeDetailsView.findViewById(R.id.challenge_uber);

        uber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingUberButtonBehavior();
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MainActivity activity = (MainActivity) getActivity();
                final Dialog activeDialog = getDialog();

                new BottomSheet.Builder(activity, R.style.BottomSheet_StyleDialog)
                        .title("Choose how you want to upload a picture")
                        .grid() // <-- important part
                        .sheet(R.menu.home_join_bottom_sheet)
                        .listener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String merchantName = challengeRestaurant.getText().toString().trim();
                                String merchantDistance = challengeDistance.getText().toString().trim();
                                Utils.mostRecentMerchantName = merchantName;
                                Utils.mostRecentMerchantDistance = merchantDistance;
                                activeDialog.dismiss();
                                if (which == R.id.choose_gallery) {
                                    Intent pickIntent = new Intent(Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    activity.startActivityForResult(pickIntent, Constants.SELECT_PIC_REQUEST_CODE);
                                } else {
                                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                                    File imagesFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), activity.getString(R.string.app_name));
                                    if (!imagesFolder.exists()) {
                                        imagesFolder.mkdirs();
                                    }
                                    File image = new File(imagesFolder, "IMG_APIC_" + timeStamp + ".png");
                                    Uri uriSavedImage = Uri.fromFile(image);
                                    Utils.mostRecentPhoto = uriSavedImage;

                                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                                    cameraIntent.putExtra("TEST", "ING");
                                    activity.startActivityForResult(cameraIntent, Constants.TAKE_PIC_REQUEST_CODE);
                                }
                            }
                        }).show();
            }
        });

        builder.setView(challengeDetailsView);
        return builder.create();
    }

    private void floatingUberButtonBehavior() {
        try {
            PackageManager pm = getActivity().getApplicationContext().getPackageManager();
            pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
            String uri = "uber://?client_id=YOUR_CLIENT_ID&action=setPickup&pickup=my_location&dropoff[latitude]=37.802374&dropoff[longitude]=-122.405818&dropoff[nickname]=Coit%20Tower&dropoff[formatted_address]=1%20Telegraph%20Hill%20Blvd%2C%20San%20Francisco%2C%20CA%2094133&product_id=a1111c8c-c720-46c3-8534-2fcdd730040d";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            // No Uber app! Open mobile website.
            String url = "https://m.uber.com/sign-up?client_id=" + Constants.UBER_CLIENT_ID;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        FloatingActionButton uberFAB = (FloatingActionButton) getActivity().findViewById(R.id.uber_button);
        uberFAB.setVisibility(View.VISIBLE);
        final FABProgressCircle fabProgressCircle = (FABProgressCircle) getActivity().findViewById(R.id.fabProgressCircle);
        fabProgressCircle.show();
        Utils.isRiding = true;
        fabProgressCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Go to Trip Experiences page
            }
        });
        //after a while, uber arrives after a few seconds
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(60000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                fabProgressCircle.beginFinalAnimation();
                Utils.isRiding = false;
            }
        }).start();
    }
}
