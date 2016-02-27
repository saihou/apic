package com.space.apic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MakeNewPostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MakeNewPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakeNewPostFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String restaurantName;
    private String mParam2;

    private MainActivity activity;

    private OnFragmentInteractionListener mListener;

    public MakeNewPostFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakeNewPostFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakeNewPostFragment newInstance(String param1, String param2) {
        MakeNewPostFragment fragment = new MakeNewPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurantName = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_new_post, container, false);
        ImageView imageToPost = (ImageView) view.findViewById(R.id.image_to_post);

        final Uri imageUri = Utils.mostRecentPhoto;
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(imageUri));
            imageToPost.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final EditText typedCaption = (EditText) view.findViewById(R.id.typed_caption);

        FloatingActionButton done = (FloatingActionButton) view.findViewById(R.id.done_button);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String caption = typedCaption.getText().toString().trim();
                HomeCardData newPost = new HomeCardData(Utils.getUsername(), "Just now", "Chocolate Origins",
                                                "0.0mi", caption, imageUri.toString());
                makeNewPost(newPost);
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void makeNewPost(HomeCardData cardData) {
        if (mListener != null) {
            Utils.mostRecentPost = cardData;
            mListener.onFragmentInteraction(cardData);
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
        void onFragmentInteraction(HomeCardData newCard);
    }
}
