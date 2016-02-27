package com.space.apic;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreNearbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreNearbyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public StoreNearbyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoreNearbyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoreNearbyFragment newInstance(String param1, String param2) {
        StoreNearbyFragment fragment = new StoreNearbyFragment();
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
        View view = inflater.inflate(R.layout.fragment_store_nearby, container, false);
        View card1 = view.findViewById(R.id.card_1);
        View card2 = view.findViewById(R.id.card_2);
        View card3 = view.findViewById(R.id.card_3);
        View card4 = view.findViewById(R.id.card_4);
//        View card5 = view.findViewById(R.id.card_5);
//        View card6 = view.findViewById(R.id.card_6);
//        View card7 = view.findViewById(R.id.card_7);
//        View card8 = view.findViewById(R.id.card_8);

        populateCardData(card1, "Chocolate Origins", "0.4mi", "Get $10 off our yummy one-pound Chocolate Cake",
                            "200", R.drawable.coupon_chocolate_origin);
        populateCardData(card2, "Gokart Racer", "0.2mi", "Extra 5 minutes of race time to any driving deal purchased!",
                "250", R.drawable.coupon_gokart);
        populateCardData(card3, "Sichuan Hotpot", "0.2mi", "Free Roasted Pork worth $14.99!",
                "250", R.drawable.coupon_hotpot);
        populateCardData(card4, "Arcadia Ski Resort", "0.4mi", "Free lift tickets for two!",
                "1500", R.drawable.coupon_ski);
//        populateCardData(card5, "The Black Horse", "0.2mi", "Margarita on the house! Worth $12.99!",
//                "250", R.drawable.coupon_bar);
//        populateCardData(card6, "Burgers With Love", "0.3mi", "Bacon-wrapped fries on any burger purchased!",
//                "250", R.drawable.coupon_burger);
        return view;
    }

    private void populateCardData(View card, String sName, String sDist, String sDesc, String cost, int dPic) {
        TextView name = (TextView) card.findViewById(R.id.store_name);
        TextView distance = (TextView) card.findViewById(R.id.store_distance);
        TextView desc = (TextView) card.findViewById(R.id.store_desc);
        Button buy = (Button) card.findViewById(R.id.buy_button);
        ImageView pic = (ImageView) card.findViewById(R.id.store_picture);
        final ImageView star = (ImageView) card.findViewById(R.id.store_star);

        name.setText(sName);
        distance.setText(sDist);
        desc.setText(sDesc);
        pic.setImageResource(dPic);
        buy.setText(String.format(Constants.BUY_COUPON_TEXT, cost));

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star.setImageResource(R.drawable.ic_star_black_24dp);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirm your purchase")
                        .setMessage("Are you sure you want to purchase this coupon?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Log.d("PurchaseConfirmation", "Yes");
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
    }

}
