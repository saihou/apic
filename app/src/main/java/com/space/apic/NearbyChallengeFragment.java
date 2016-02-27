package com.space.apic;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NearbyChallengeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NearbyChallengeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NearbyChallengeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ChallengeCardData> mCardData;

    private OnFragmentInteractionListener mListener;

    public NearbyChallengeFragment() {
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
    public static NearbyChallengeFragment newInstance(String param1, String param2) {
        NearbyChallengeFragment fragment = new NearbyChallengeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_challenge_nearby, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.nearby_challenge_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mCardData = new ArrayList<>();
        mAdapter = new ChallengeRecyclerAdapter(mCardData, (MainActivity) getActivity());
        mRecyclerView.setAdapter(mAdapter);

        createPlaceholderData();

        return view;
    }

    private void createPlaceholderData() {
        mCardData.add(new ChallengeCardData("merchantName1", "30 mins left", "Little Sheep Hotpot", "0.4 mi", "HELLO IT'S ME. I'M EATING GOOD FOOD. COME JOIN ME NAO.","content://media/external/images/media/12671"));
        mCardData.add(new ChallengeCardData("merchantName2", "15 mins left", "Koi Palace", "0.9 mi", "The 流沙包 here are really good!!!", "content://media/external/images/media/12672"));
        mCardData.add(new ChallengeCardData("merchantName3", "27 mins left", "In the forest", "5.4 mi", "Troll troll troll troll troll troll troll troll troll troll troll troll troll troll troll","content://media/external/images/media/12696"));
        mCardData.add(new ChallengeCardData("merchantName4", "10 days left", "Chocolate Origins", "9001 mi", "After so long!! Haha #shoppingmadness","content://media/external/images/media/12673"));
        mAdapter.notifyDataSetChanged();
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