package com.space.apic;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by saihou on 2/19/16.
 */
public class ChallengeRecyclerAdapter extends RecyclerView.Adapter<ChallengeRecyclerAdapter.ViewHolder> {
    private ArrayList<ChallengeCardData> mDataset;
    private MainActivity activity;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        TextView merchantName;
        TextView challengeDuration;
        ImageView picture;
        GestureDetectorCompat gestureDetector;
        TextView challengeRestaurant;
        TextView challengeDistance;
        TextView caption;
        Button joinChallenge;
        Button viewChallengeDetails;
        Button uberRequest;

        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.challenge_card);
            merchantName = (TextView) itemView.findViewById(R.id.merchant_name);
            challengeDuration = (TextView) itemView.findViewById(R.id.challenge_duration);
            picture = (ImageView) itemView.findViewById(R.id.picture);
            challengeRestaurant = (TextView) itemView.findViewById(R.id.challenge_restaurant);
            challengeDistance = (TextView) itemView.findViewById(R.id.challenge_distance);
            caption = (TextView) itemView.findViewById(R.id.caption);
            joinChallenge = (Button) itemView.findViewById(R.id.challenge_join);
            viewChallengeDetails = (Button) itemView.findViewById(R.id.challenge_details);
            uberRequest = (Button) itemView. findViewById(R.id.challenge_uber);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChallengeRecyclerAdapter(ArrayList<ChallengeCardData> myDataset, MainActivity activity) {
        mDataset = myDataset;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChallengeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.challenge_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ChallengeCardData data = mDataset.get(position);
        holder.merchantName.setText(data.getUsername());
        holder.challengeDuration.setText(data.getTime());
        holder.challengeRestaurant.setText(data.getChallengeRestaurant());
        holder.challengeDistance.setText(data.getChallengeDistance());
        holder.caption.setText(data.getCaption());

        try {
            int picture = Integer.parseInt(data.getPicture());
            Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), picture);
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();
            int scale = 2;
            if (width > 800) {
                height = height/scale;
                width = width/scale;
            }
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            holder.picture.setImageBitmap(scaledBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.joinChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheet.Builder(activity, R.style.BottomSheet_StyleDialog)
                        .title("Choose how you want to upload a picture")
                        .grid() // <-- important part
                        .sheet(R.menu.home_join_bottom_sheet)
                        .listener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String merchantName = holder.merchantName.getText().toString().trim();
                                String merchantDistance = holder.challengeDistance.getText().toString().trim();
                                Utils.mostRecentMerchantName = merchantName;
                                Utils.mostRecentMerchantDistance = merchantDistance;

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

        holder.viewChallengeDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChallengeCardData challengeData = mDataset.get(position);
                Bundle bundle = new Bundle();
                FragmentManager fm = activity.getFragmentManager();
                ChallengeDetailsDialogFragment challengeDetailsDialog = new ChallengeDetailsDialogFragment();
                challengeDetailsDialog.setArguments(bundle);
                challengeDetailsDialog.show(fm, "challenge_details_dialog");
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
