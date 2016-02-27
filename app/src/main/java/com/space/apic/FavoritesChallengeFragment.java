package com.space.apic;

import android.app.FragmentManager;
import android.content.Context;
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
 * {@link FavoritesChallengeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FavoritesChallengeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesChallengeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<ChallengeCardData> placeHolderChallengeFavourites = new ArrayList<ChallengeCardData>();

    private OnFragmentInteractionListener mListener;

    public FavoritesChallengeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesChallengeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesChallengeFragment newInstance(String param1, String param2) {
        FavoritesChallengeFragment fragment = new FavoritesChallengeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_challenge_list, container, false);
        createPlaceHolderChallengeFavourites();
        ViewGroup inclusionViewGroup = (ViewGroup)view.findViewById(R.id.inclusionlayout);
        for(int i = 0; i < placeHolderChallengeFavourites.size(); i++) {
            final int position = i;
            View card = LayoutInflater.from(getContext()).inflate(R.layout.challenge_card, null);
            Button favouriteButton = (Button) card.findViewById(R.id.challenge_later);
            favouriteButton.setVisibility(View.GONE);
            TextView merchantName = (TextView) card.findViewById(R.id.merchant_name);
            TextView challengeDuration = (TextView) card.findViewById(R.id.challenge_duration);
            merchantName.setText(placeHolderChallengeFavourites.get(i).merchaintName);
            challengeDuration.setText(placeHolderChallengeFavourites.get(i).challengeDuration);
            Button challengeDetailsButton = (Button) card.findViewById(R.id.challenge_details);
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
            inclusionViewGroup.addView(card);
        }
        return view;
    }

    public void createPlaceHolderChallengeFavourites() {
        placeHolderChallengeFavourites.add(new ChallengeCardData("FLUFFY", "6 days left", "Little Sheep Hotpot", "0.4 mi", "HELLO IT'S ME. I'M EATING GOOD FOOD. COME JOIN ME NAO.","content://media/external/images/media/12671"));
        placeHolderChallengeFavourites.add(new ChallengeCardData("OnePice", "10 days left", "Little Sheep Hotpot", "0.4 mi", "HELLO IT'S ME. I'M EATING GOOD FOOD. COME JOIN ME NAO.","content://media/external/images/media/12671"));
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
