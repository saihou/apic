package com.saihou.adpic;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
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
    private ArrayList<HomeCardData> mCardData;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mCardData = new ArrayList<>();
        mAdapter = new HomeRecyclerAdapter(mCardData, (MainActivity) getActivity());
        mRecyclerView.setAdapter(mAdapter);

        //Marshmallow check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constants.WRITE_STORAGE_REQUEST_PERMISSION);
            } else {
                Utils.canReadStorage = true;
                Utils.canWriteStorage = true;
            }
        } else {
            //Put codes that are not MARSHMALLOW here
            createPlaceholderData();
        }

        final SwipeRefreshLayout refreshView = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        refreshView.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        return view;

    }

    private void createPlaceholderData() {
        mCardData.add(new HomeCardData("itssofluffy", "30 mins ago", "Little Sheep Hotpot", "0.4 mi", "HELLO IT'S ME. I'M EATING GOOD FOOD. COME JOIN ME NAO.","content://media/external/images/media/12671"));
        mCardData.add(new HomeCardData("itssofluffy", "15 mins ago", "Koi Palace", "0.9 mi", "The 流沙包 here are really good!!!","content://media/external/images/media/12672"));
        mCardData.add(new HomeCardData("itssofluffy", "27 mins ago", "In the forest", "5.4 mi", "Troll troll troll troll troll troll troll troll troll troll troll troll troll troll troll","content://media/external/images/media/12696"));
        mCardData.add(new HomeCardData("imgonnadie", "Just now", "Chocolate Origins", "9001 mi", "After so long!! Haha #shoppingmadness","content://media/external/images/media/12673"));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.TAKE_PIC_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
//                Uri imageUri = (Uri) data.getData();
                Uri imageUri = Utils.mostRecentPhoto;
                Log.d("Image Location", imageUri.toString());
                mCardData.add(0, new HomeCardData(Utils.getUsername(), "Just now", "Little Sheep Hotpot", "0.4mi", "YAY!!!", imageUri.toString()));
                mAdapter.notifyDataSetChanged();
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        } else if (requestCode == Constants.SELECT_PIC_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                Uri imageUri = (Uri) data.getData();
                Log.d("Image Location", imageUri.toString());
                mCardData.add(0, new HomeCardData(Utils.getUsername(), "Just now", "Little Sheep Hotpot", "0.4mi", "YAY!!!", imageUri.toString()));
                mAdapter.notifyDataSetChanged();
            } else if (resultCode == getActivity().RESULT_CANCELED) {
                // User cancelled the image selection
            } else {
                // Image selection failed, advise user
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.READ_STORAGE_REQUEST_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    createPlaceholderData();
                    Utils.canReadStorage = true;
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case Constants.WRITE_STORAGE_REQUEST_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
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
