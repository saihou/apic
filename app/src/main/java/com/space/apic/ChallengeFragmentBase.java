package com.space.apic;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


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
            TextView merchantName = (TextView) card.findViewById(R.id.merchant_name);
            TextView challengeDuration = (TextView) card.findViewById(R.id.challenge_duration);
            merchantName.setText(placeHolderChallengeFavourites.get(i).merchaintName);
            challengeDuration.setText(placeHolderChallengeFavourites.get(i).challengeDuration);

            Button favouriteButton = (Button) card.findViewById(R.id.challenge_later);
            Button joinButton = (Button) card.findViewById(R.id.challenge_join);
            Button challengeDetailsButton = (Button) card.findViewById(R.id.challenge_details);
            Button uberButton = (Button) card.findViewById(R.id.challenge_uber);
            if(pageType == "favourites_challenge") {
                favouriteButton.setVisibility(View.GONE);
            } else if (pageType == "history_challenge") {
                joinButton.setVisibility(View.GONE);
            }
            challengeDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChallengeCardData challengeData = placeHolderChallengeFavourites.get(position);
                    Bundle bundle = new Bundle();
                    //TODO: set bundle arguments for card
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
                }
            });
            inclusionViewGroup.addView(card);
        }
        return view;
    }

    public void createPlaceHolderChallengeFavourites() {
        if (pageType == "favourites_challenge") {
            placeHolderChallengeFavourites.add(new ChallengeCardData("FLUFFY", "6 days left", "Little Sheep Hotpot", "0.4 mi", "HELLO IT'S ME. I'M EATING GOOD FOOD. COME JOIN ME NAO.", "content://media/external/images/media/12671"));
            placeHolderChallengeFavourites.add(new ChallengeCardData("Naruto", "10 days left", "Little Sheep Hotpot", "0.4 mi", "HELLO IT'S ME. I'M EATING GOOD FOOD. COME JOIN ME NAO.", "content://media/external/images/media/12671"));
        } else if (pageType == "history_challenge"){
            placeHolderChallengeFavourites.add(new ChallengeCardData("YAYY IT WORKS", "7777 days left", "Little Sheep Hotpot", "0.4 mi", "HELLO IT'S ME. I'M EATING GOOD FOOD. COME JOIN ME NAO.","content://media/external/images/media/12671"));
            placeHolderChallengeFavourites.add(new ChallengeCardData("OnePiece", "10 days left", "Little Sheep Hotpot", "0.4 mi", "HELLO IT'S ME. I'M EATING GOOD FOOD. COME JOIN ME NAO.","content://media/external/images/media/12671"));
            placeHolderChallengeFavourites.add(new ChallengeCardData("Pokemon", "10 days left", "Little Sheep Hotpot", "0.4 mi", "HELLO IT'S ME. I'M EATING GOOD FOOD. COME JOIN ME NAO.","content://media/external/images/media/12671"));
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
