package com.saihou.adpic;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by saihou on 2/19/16.
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {
    private ArrayList<HomeCardData> mDataset;
    private MainActivity activity;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        TextView username;
        TextView time;
        ImageView picture;
        ImageView heartIcon;
        GestureDetectorCompat gestureDetector;
        TextView challengeRestaurant;
        TextView challengeDistance;
        TextView caption;
        Button viewChallenge;
        Button joinNow;

        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.home_card);
            username = (TextView) itemView.findViewById(R.id.username);
            time = (TextView) itemView.findViewById(R.id.time);
            picture = (ImageView) itemView.findViewById(R.id.picture);
            heartIcon = (ImageView) itemView.findViewById(R.id.heart_icon);
            challengeRestaurant = (TextView) itemView.findViewById(R.id.challenge_restaurant);
            challengeDistance = (TextView) itemView.findViewById(R.id.challenge_distance);
            caption = (TextView) itemView.findViewById(R.id.caption);
            joinNow = (Button) itemView.findViewById(R.id.join_now_btn);
            viewChallenge = (Button) itemView.findViewById(R.id.view_challenge_btn);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public HomeRecyclerAdapter(ArrayList<HomeCardData> myDataset, MainActivity activity) {
        mDataset = myDataset;
        this.activity = activity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public HomeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        vh.gestureDetector = new GestureDetectorCompat(activity, new DoubleTapGestureListener(vh.heartIcon));
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        HomeCardData data = mDataset.get(position);
        holder.username.setText(data.getUsername());
        holder.time.setText(data.getTime());
        holder.challengeRestaurant.setText(data.getChallengeRestaurant());
        holder.challengeDistance.setText(data.getChallengeDistance());
        holder.caption.setText(data.getCaption());

        Uri uri = Uri.parse(data.getPicture());

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(uri));
            holder.picture.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        holder.picture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return holder.gestureDetector.onTouchEvent(event);
            }
        });
        holder.heartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.heartIcon.getTag().toString().equals(String.valueOf(R.drawable.ic_favorite_black_36dp))) {
                    holder.heartIcon.setImageResource(R.drawable.ic_favorite_border_black_36dp);
                    holder.heartIcon.setTag(R.drawable.ic_favorite_border_black_36dp);
                } else {
                    holder.heartIcon.setImageResource(R.drawable.ic_favorite_black_36dp);
                    holder.heartIcon.setTag(R.drawable.ic_favorite_black_36dp);
                }
            }
        });

        holder.joinNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BottomSheet.Builder(activity, R.style.BottomSheet_StyleDialog)
                        .title("Choose how you want to upload a picture")
                        .grid() // <-- important part
                        .sheet(R.menu.home_join_bottom_sheet)
                        .listener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == R.id.choose_gallery) {
                                    Intent pickIntent = new Intent(Intent.ACTION_PICK,
                                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    activity.startActivityForResult(pickIntent, Constants.SELECT_PIC_REQUEST_CODE);
                                } else {
                                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                                    File imagesFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), activity.getString(R.string.app_name));
                                    if (!imagesFolder.exists()) {
                                        imagesFolder.mkdirs();
                                    }
                                    File image = new File(imagesFolder, "IMG_ADPIC_" + timeStamp + ".png");
                                    Uri uriSavedImage = Uri.fromFile(image);
                                    Utils.mostRecentPhoto = uriSavedImage;

                                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                                    activity.startActivityForResult(cameraIntent, Constants.TAKE_PIC_REQUEST_CODE);
                                }
                            }
                        }).show();
            }
        });

        holder.viewChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.getSupportActionBar().setTitle(R.string.challenge);
                ChallengeFragment fragment = new ChallengeFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                activity.activeFragment = fragment;
                activity.navigationView.setCheckedItem(R.id.nav_challenge);
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

    class DoubleTapGestureListener extends GestureDetector.SimpleOnGestureListener {
        ImageView heartIcon;

        public DoubleTapGestureListener(ImageView heartIcon) {
            super();
            this.heartIcon = heartIcon;
        }

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            heartIcon.setImageResource(R.drawable.ic_favorite_black_36dp);
            heartIcon.setTag(R.drawable.ic_favorite_black_36dp);
            return true;
        }
    }
}
