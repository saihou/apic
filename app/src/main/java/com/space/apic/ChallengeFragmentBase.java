package com.space.apic;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cocosw.bottomsheet.BottomSheet;
import com.github.jorgecastilloprz.FABProgressCircle;
import com.github.jorgecastilloprz.listeners.FABProgressListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChallengeFragmentBase.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChallengeFragmentBase#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChallengeFragmentBase extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PAGETYPE = "";

    // TODO: Rename and change types of parameters
    private String pageType;

    public ArrayList<ChallengeCardData> placeHolderChallengeFavourites = new ArrayList<ChallengeCardData>();

    private OnFragmentInteractionListener mListener;

    public ChallengeFragmentBase() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pageType Parameter 1.
     * @return A new instance of fragment FavoritesChallengeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChallengeFragmentBase newInstance(String pageType) {
        ChallengeFragmentBase fragment = new ChallengeFragmentBase();
        Bundle args = new Bundle();
        args.putString(PAGETYPE, pageType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageType = getArguments().getString(PAGETYPE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge_list, container, false);
        ViewGroup inclusionViewGroup = (ViewGroup)view.findViewById(R.id.inclusionlayout);
        createPlaceHolderChallengeFavourites();
        for(int i = 0; i < placeHolderChallengeFavourites.size(); i++) {
            final int position = i;
            View card = LayoutInflater.from(getContext()).inflate(R.layout.challenge_card, null);

            ImageView cardPicture = (ImageView) card.findViewById(R.id.picture);
            try {
                int picture = Integer.parseInt(placeHolderChallengeFavourites.get(i).getPicture());
                Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), picture);
                int height = bitmap.getHeight();
                int width = bitmap.getWidth();
                int scale = 2;
                if (width > 800) {
                    height = height/scale;
                    width = width/scale;
                }
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
                cardPicture.setImageBitmap(scaledBitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }

            final TextView cardMerchantName = (TextView) card.findViewById(R.id.merchant_name);
            final TextView challengeDuration = (TextView) card.findViewById(R.id.challenge_duration);
            final TextView challengeRestaurant = (TextView) card.findViewById(R.id.challenge_restaurant);
            final TextView distance = (TextView) card.findViewById(R.id.challenge_distance);
            final TextView caption = (TextView) card.findViewById(R.id.caption);

            cardMerchantName.setText(placeHolderChallengeFavourites.get(i).merchaintName);
            challengeDuration.setText(placeHolderChallengeFavourites.get(i).challengeDuration);
            challengeRestaurant.setText(placeHolderChallengeFavourites.get(i).challengeDuration);
            distance.setText(placeHolderChallengeFavourites.get(i).getChallengeDistance());
            caption.setText(placeHolderChallengeFavourites.get(i).getCaption());

            Button favouriteButton = (Button) card.findViewById(R.id.challenge_later);
            Button joinButton = (Button) card.findViewById(R.id.challenge_join);
            Button challengeDetailsButton = (Button) card.findViewById(R.id.challenge_details);
            Button uberButton = (Button) card.findViewById(R.id.challenge_uber);
            if(pageType == "favourites_challenge") {
                favouriteButton.setVisibility(View.GONE);
            } else if (pageType == "history_challenge") {
                joinButton.setVisibility(View.GONE);
            }
            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new BottomSheet.Builder(getActivity(), R.style.BottomSheet_StyleDialog)
                            .title("Choose how you want to upload a picture")
                            .grid() // <-- important part
                            .sheet(R.menu.home_join_bottom_sheet)
                            .listener(new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String merchantName = cardMerchantName.getText().toString().trim();
                                    String merchantDistance = distance.getText().toString().trim();
                                    Utils.mostRecentMerchantName = merchantName;
                                    Utils.mostRecentMerchantDistance = merchantDistance;

                                    if (which == R.id.choose_gallery) {
                                        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        getActivity().startActivityForResult(pickIntent, Constants.SELECT_PIC_REQUEST_CODE);
                                    } else {
                                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                                        File imagesFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getActivity().getString(R.string.app_name));
                                        if (!imagesFolder.exists()) {
                                            imagesFolder.mkdirs();
                                        }
                                        File image = new File(imagesFolder, "IMG_APIC_" + timeStamp + ".png");
                                        Uri uriSavedImage = Uri.fromFile(image);
                                        Utils.mostRecentPhoto = uriSavedImage;

                                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                                        cameraIntent.putExtra("TEST", "ING");
                                        getActivity().startActivityForResult(cameraIntent, Constants.TAKE_PIC_REQUEST_CODE);
                                    }
                                }
                            }).show();
                }
            });
            challengeDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("merchantName",placeHolderChallengeFavourites.get(position).merchaintName);
                    bundle.putString("challengeDuration",placeHolderChallengeFavourites.get(position).challengeDuration);
                    bundle.putString("challengeDistance",placeHolderChallengeFavourites.get(position).challengeDistance);
                    bundle.putString("caption",placeHolderChallengeFavourites.get(position).caption);
                    bundle.putString("challengeRestaurant",placeHolderChallengeFavourites.get(position).challengeRestaurant);
                    FragmentManager fm = getActivity().getFragmentManager();
                    ChallengeDetailsDialogFragment challengeDetailsDialog = new ChallengeDetailsDialogFragment();
                    challengeDetailsDialog.setArguments(bundle);
                    challengeDetailsDialog.show(fm, "challenge_details_dialog");
                }
            });
            uberButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    try {
                        PackageManager pm = getContext().getPackageManager();
                        pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
                        String uri = "uber://?client_id=YOUR_CLIENT_ID&action=setPickup&pickup=my_location&dropoff[latitude]=37.802374&dropoff[longitude]=-122.405818&dropoff[nickname]=Coit%20Tower&dropoff[formatted_address]=1%20Telegraph%20Hill%20Blvd%2C%20San%20Francisco%2C%20CA%2094133&product_id=a1111c8c-c720-46c3-8534-2fcdd730040d";
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);
                    } catch (PackageManager.NameNotFoundException e) {
                        // No Uber app! Open mobile website.
                        String url = "https://m.uber.com/sign-up?client_id="+Constants.UBER_CLIENT_ID;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivityForResult(i, Constants.LAUNCH_UBER_REQUEST_CODE);
                    }
                    FloatingActionButton uberFAB = (FloatingActionButton)getActivity().findViewById(R.id.uber_button);
                    uberFAB.setVisibility(View.VISIBLE);
                    final FABProgressCircle fabProgressCircle = (FABProgressCircle)getActivity().findViewById(R.id.fabProgressCircle);
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
                    floatingUberButtonBehavior();
                }
            });
            inclusionViewGroup.addView(card);
        }
        return view;
    }

    private void floatingUberButtonBehavior() {
        try {
            PackageManager pm = getContext().getPackageManager();
            pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
            String uri = "uber://?client_id=YOUR_CLIENT_ID&action=setPickup&pickup=my_location&dropoff[latitude]=37.802374&dropoff[longitude]=-122.405818&dropoff[nickname]=Coit%20Tower&dropoff[formatted_address]=1%20Telegraph%20Hill%20Blvd%2C%20San%20Francisco%2C%20CA%2094133&product_id=a1111c8c-c720-46c3-8534-2fcdd730040d";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            // No Uber app! Open mobile website.
            String url = "https://m.uber.com/sign-up?client_id="+ Constants.UBER_CLIENT_ID;
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
        FloatingActionButton uberFAB = (FloatingActionButton)getActivity().findViewById(R.id.uber_button);
        uberFAB.setVisibility(View.VISIBLE);
        final FABProgressCircle fabProgressCircle = (FABProgressCircle)getActivity().findViewById(R.id.fabProgressCircle);
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

    public void createPlaceHolderChallengeFavourites() {
        if (pageType == "favourites_challenge") {
            placeHolderChallengeFavourites.add(new ChallengeCardData("The Black Horse", "15 mins left", "The Black Horse", "0.9 mi", "Strike up a conversation with our bartenders. Tell him/her what you love about us and take a selfie with any of them!!", String.valueOf(R.drawable.challenge_bar)));
        } else if (pageType == "history_challenge"){
            placeHolderChallengeFavourites.add(new ChallengeCardData("Arcadia Ski Resort", "10 days left", "Arcadia Ski Resort", "9001 mi", "Ski with your family and take a family photo with all your gears on! Remember to pose with our lovely mascot!",String.valueOf(R.drawable.challenge_ski)));
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
