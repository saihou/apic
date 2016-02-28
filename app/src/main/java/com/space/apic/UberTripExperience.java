package com.space.apic;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

public class UberTripExperience extends FragmentActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler mHandler = new Handler();
    private ImageView car;
    private ArrayList<ChallengeCardData> data = new ArrayList<ChallengeCardData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uber_trip_experience);

        VideoView video_player_view = (VideoView) findViewById(R.id.video_player);
        String fileName = "android.resource://"+  getPackageName() + "/raw/ubervideo";
        video_player_view.setVideoURI(Uri.parse(fileName));
        video_player_view.start();

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        car = (ImageView) findViewById(R.id.car);
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressStatus++;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            car.setX(car.getX()+9);
                            progressBar.setProgress(progressStatus);
                        }
                    });
                }
                Snackbar snack = Snackbar.make(progressBar, "You have arrived! Start your challenge!", Snackbar.LENGTH_LONG);
                View view = snack.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                snack.setAction("Action", null).show();
            }
        }).start();

        RecyclerView gallery = (RecyclerView) findViewById(R.id.gallery);
        createPlaceholderData();
        UberGalleryCardAdapter adapter = new UberGalleryCardAdapter(data);
        gallery.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        gallery.setLayoutManager(layoutManager);
    }

    public void createPlaceholderData(){
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));
        data.add(new ChallengeCardData("","","","","CAPTION",String.valueOf(R.drawable.challenge_ski)));

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_uber_trip_experience, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
