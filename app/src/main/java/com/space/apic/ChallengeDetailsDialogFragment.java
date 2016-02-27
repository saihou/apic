package com.space.apic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;

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

        final TextView merchantNameTextView = (TextView) challengeDetailsView.findViewById(R.id.textView);
        final TextView merchantDetailsTextView = (TextView) challengeDetailsView.findViewById(R.id.textView2);

        Button join = (Button) challengeDetailsView.findViewById(R.id.challenge_join);
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
                                String merchantName = merchantNameTextView.getText().toString().trim();
                                String merchantDistance = merchantDetailsTextView.getText().toString().trim();
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
}
