package com.space.apic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

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

        Button join = (Button) challengeDetailsView.findViewById(R.id.challenge_join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("LOL");
            }
        });

        builder.setView(challengeDetailsView);
        return builder.create();
    }
}
